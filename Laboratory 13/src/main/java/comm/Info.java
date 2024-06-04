package comm;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.util.Currency;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

public class Info {
    public void execute(Locale locale) {
        ResourceBundle messages = ResourceBundle.getBundle("res.Messages", locale);
        System.out.println(String.format(messages.getString("info"), locale.toString()));

        System.out.println("Country: " + locale.getDisplayCountry() + " (" + locale.getDisplayCountry(locale) + ")");
        System.out.println("Language: " + locale.getDisplayLanguage() + " (" + locale.getDisplayLanguage(locale) + ")");
        System.out.println("Currency: " + Currency.getInstance(locale).getCurrencyCode() + " (" + Currency.getInstance(locale).getDisplayName() + ")");

        DateFormatSymbols dfs = new DateFormatSymbols(locale);
        String[] weekDays = dfs.getWeekdays();
        String[] months = dfs.getMonths();

        System.out.print("Week Days: ");
        for (int i = 2; i < weekDays.length; i++) { // weekDays[0] is an empty string
            System.out.print(weekDays[i] + (i < weekDays.length - 1 ? ", " : ""));
        }
        System.out.println();

        System.out.print("Months: ");
        for (int i = 0; i < months.length; i++) {
            System.out.print(months[i] + (i < months.length - 1 ? ", " : ""));
        }
        System.out.println();

        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.LONG, locale);
        System.out.println("Today: " + dateFormat.format(new Date()));
    }
}
