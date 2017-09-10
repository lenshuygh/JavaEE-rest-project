package com.lens.bookstore.util;

/**
 * Created by lens on 13/08/2017.
 */
public class TextUtil {
    public String sanitizeText(String textToSanitize){
        return textToSanitize.replaceAll("\\s+"," ");
    }
}
