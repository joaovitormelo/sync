package com.rubeusufv.sync.Features.Domain.Utils;

import java.util.regex.Pattern;

public class ValidationHelper {
    public static boolean validateHour(String hour) {
        if (hour == null) {
            return false;
        }
        // Regex for HH:mm format in 24-hour time
        String regex = "^([01]\\d|2[0-3]):[0-5]\\d$";
        return Pattern.matches(regex, hour);
    }
}
