package codesquad.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeUtils {
    private static final String DATE_FORMAT = "yyyy-MM-dd hh:mm";

    public static String getCurrentTime() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(TimeUtils.DATE_FORMAT));
    }
}
