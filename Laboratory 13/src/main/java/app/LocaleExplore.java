package app;

import comm.DisplayLocales;
import comm.SetLocale;
import comm.Info;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;

public class LocaleExplore {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        DisplayLocales displayLocales = new DisplayLocales();
        SetLocale setLocale = new SetLocale();
        Info info = new Info();

        Locale currentLocale = Locale.getDefault();
        ResourceBundle messages = ResourceBundle.getBundle("res.Messages", currentLocale);

        while (true) {
            System.out.println(messages.getString("prompt"));
            String command = scanner.nextLine();

            if (command.equalsIgnoreCase("locales")) {
                displayLocales.execute();
            } else if (command.startsWith("setlocale")) {
                String[] parts = command.split(" ");
                if (parts.length == 2) {
                    setLocale.execute(parts[1]);
                    currentLocale = setLocale.getCurrentLocale();
                    messages = ResourceBundle.getBundle("res.Messages", currentLocale);
                } else {
                    System.out.println(messages.getString("invalid"));
                }
            } else if (command.equalsIgnoreCase("info")) {
                info.execute(currentLocale);
            } else {
                System.out.println(messages.getString("invalid"));
            }
        }
    }
}
