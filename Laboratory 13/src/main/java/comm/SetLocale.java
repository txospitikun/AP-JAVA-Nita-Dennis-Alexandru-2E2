package comm;

import java.util.Locale;
import java.util.ResourceBundle;

public class SetLocale {
    private Locale currentLocale;

    public void execute(String languageTag) {
        currentLocale = Locale.forLanguageTag(languageTag);
        Locale.setDefault(currentLocale);
        ResourceBundle messages = ResourceBundle.getBundle("res.Messages", currentLocale);
        System.out.println(String.format(messages.getString("locale.set"), currentLocale.toString()));
    }

    public Locale getCurrentLocale() {
        return currentLocale;
    }
}
