package org.wowj.commons.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by kuksin-mv on 14.10.2015.
 */
public class PropertiesUtils
{
    public static Properties load(final String file) throws IOException
    {
        return load(new File(file));
    }

    public static Properties load(final File file) throws IOException
    {
        try(FileInputStream fis = new FileInputStream(file))
        {
            final Properties p = new Properties();
            p.load(fis);
            return p;
        }
        catch (FileNotFoundException e)
        {
            e.getMessage();
            return null;
        }
    }
}
