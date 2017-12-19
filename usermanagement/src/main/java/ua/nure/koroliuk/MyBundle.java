package main.java.ua.nure.koroliuk;


import java.util.Locale;
import java.util.ResourceBundle;
import java.text.MessageFormat;


public class MyBundle {
    private static final String FILENAME = "main//resources//words";
    private static ResourceBundle bundle = ResourceBundle.getBundle(FILENAME, Locale.getDefault());

    public static String getString(String key, Object... params) {
        String value = bundle.getString(key);
        if (params.length > 0) return MessageFormat.format(value, params);
        return value;
    }
}
