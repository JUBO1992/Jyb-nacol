package com.example;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author jubo
 * @date 2024/01/13
 */
public class BasicTest {

    @Test
    public void test01() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date d1 = formatter.parse("2023-04-12");
        Date d2 = formatter.parse("2025-04-12");
        System.out.println(d1);
    }
}
