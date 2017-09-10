package com.lens.bookstore.util;

import java.util.Random;

/**
 * Created by lens on 13/08/2017.
 */
public class IsbnGenerator implements NumberGenerator {
    @Override
    public String generateNumber() {
        return "13-5677-" + Math.abs(new Random().nextInt());
    }
}
