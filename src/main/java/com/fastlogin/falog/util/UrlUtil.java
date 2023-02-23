package com.fastlogin.falog.util;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@Configuration
public class UrlUtil {

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final String URL_REGEX =
            "((http|https)://)(www.)?"
                    + "[a-zA-Z0-9@:%._+~#?&/=]"
                    + "{2,256}\\.[a-z]"
                    + "{2,6}\\b([-a-zA-Z0-9@:%"
                    + "._+~#?&/=]*)";

    public String generateUniqueString(int length) {
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder(length);
        for (int i = 0; i < length; i++)
            stringBuilder.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        return stringBuilder.toString();
    }

    public boolean isValidUrl(String originalUrl) {
        Pattern p = Pattern.compile(URL_REGEX);
        if (originalUrl == null) {
            return false;
        }

        Matcher m = p.matcher(originalUrl);
        return m.matches();
    }
}
