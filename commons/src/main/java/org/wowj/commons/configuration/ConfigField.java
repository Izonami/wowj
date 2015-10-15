package org.wowj.commons.configuration;

import java.lang.annotation.*;

/**
 * Created by kuksin-mv on 13.10.2015.
 * @Target - указывается, какой элемент программы будет использоваться аннотацией
 * PACKAGE - назначением является целый пакет (package);
 * TYPE - класс, интерфейс, enum или другая аннотация:
 * METHOD - метод класса, но не конструктор (для конструкторов есть отдельный тип CONSTRUCTOR);
 * PARAMETER - параметр метода;
 * CONSTRUCTOR - конструктор;
 * FIELD - поля-свойства класса;
 * LOCAL_VARIABLE - локальная переменная (обратите внимание, что аннотация не может быть прочитана во время выполнения программы, то есть, данный тип аннотации может использоваться только на уровне компиляции как, например, аннотация @SuppressWarnings);
 * ANNOTATION_TYPE - другая аннотация.
 * @Retention - указывает в какой момент жизни программного кода будет доступка аннотация
 * SOURCE - аннотация доступна только в исходном коде и сбрасывается во время создания .class файла;
 * CLASS - аннотация хранится в .class файле, но недоступна во время выполнения программы;
 * RUNTIME - аннотация хранится в .class файле и доступна во время выполнения программы.
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ConfigField
{
    String config();
    String fieldName() default "";
    String value() default "";
}
