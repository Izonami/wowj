package configuration;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.lang.reflect.Field;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by kuksin-mv on 13.10.2015.
 */
public class ConfigEngine
{
    private static final Pattern pt = Pattern.compile("\\_([A-Za-z0-9]{1})");

    /**
     * "src/main/resources/config/"
     * @param configClass
     * @param config
     */
    public static void loadConfig(Class<?> configClass, String config, String dir)
    {
        try
        {
            Log log = LogFactory.getLog(configClass);
            ExProperties settings = new ExProperties();
            LineNumberReader lnr = new LineNumberReader(new InputStreamReader(new FileInputStream(dir + config + ".properties"), "UTF-8"));
            settings.load(lnr);
            lnr.close();

            for(Field field : configClass.getDeclaredFields())
            {
                ConfigField configField;
                if((configField = field.getAnnotation(ConfigField.class)) != null && config.equals(configField.config()))
                {
                    String fieldName = configField.fieldName();
                    String fld = field.getType().getSimpleName().toLowerCase();

                    if(fieldName == null || fieldName.isEmpty())
                        fieldName = getNormalName(field.getName());

                    try
                    {
                        switch (fld)
                        {
                            case "boolean" : field.setBoolean(null, settings.getBooleanProperty(fieldName, configField.value()));
                                break;
                            case "byte" : field.setByte(null, settings.getByteProperty(fieldName, configField.value()));
                                break;
                            case "int" : field.setInt(null, settings.getIntProperty(fieldName, configField.value()));
                                break;
                            case "long" : field.setLong(null, settings.getLongProperty(fieldName, configField.value()));
                                break;
                            case "float" : field.setFloat(null, settings.getFloatProperty(fieldName, configField.value()));
                                break;
                            case "double" : field.setDouble(null, settings.getDoubleProperty(fieldName, configField.value()));
                                break;
                            case "string" : field.set(null, settings.getProperty(fieldName, configField.value()));
                                break;
                            default : log.info("Unknown field type: " + field.getType().getSimpleName() + " field name: " + field.getName() + " config: " + config + ".properties");
                                break;
                        }
                    }
                    catch(NumberFormatException e)
                    {
                        e.printStackTrace();
                        throw new Error("Failed to Load config/" + dir + " " + config + ".properties file. Field: " + field.getName() + " " + e.getMessage());
                    }
                    log.debug(config + ": set " + field.getName() + "{" + fieldName + "} = " + field.get(null));
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new Error("Failed to Load config/" + dir + " " + config + ".properties file.");
        }
    }

    /**
     *
     * @param name
     * @return
     */
    private static String getNormalName(String name)
    {
        name = name.toLowerCase();
        StringBuffer sb = new StringBuffer();
        Matcher m = pt.matcher(name);
        while(m.find())
        {
            m.appendReplacement(sb, m.group(0).replace("_" + m.group(1), m.group(1).toUpperCase()));
        }

        m.appendTail(sb);
        return sb.replace(0, 1, sb.toString().substring(0, 1).toUpperCase()).toString();
    }
}