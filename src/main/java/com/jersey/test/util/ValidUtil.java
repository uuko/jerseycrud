package com.jersey.test.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidUtil {
    public static Boolean isValidEmail(String input) {
        String regexPattern = "^(.+)@(\\S+)$";
        Pattern pattern = Pattern.compile(regexPattern);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

    public static Boolean isValidNumber(String input){
        boolean isNumeric = true;
        for (int i = 0; i < input.length(); i++) {
            if (!Character.isDigit(input.charAt(i))) {
                isNumeric = false;
            }
        }
        return isNumeric;
    }
}
