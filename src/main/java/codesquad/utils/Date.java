package codesquad.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Date {
    public static String getCurrentTime() {
        Calendar calendar = Calendar.getInstance();
        java.util.Date date = calendar.getTime();

        return new SimpleDateFormat("yyyy-MM-dd hh:mm").format(date);
    }
}
