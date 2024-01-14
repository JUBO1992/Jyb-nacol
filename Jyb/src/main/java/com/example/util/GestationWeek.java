package com.example.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

/**
 * 孕周计算工具
 *
 * @author jubo
 * @date 2024/01/13
 */
public class GestationWeek {

    /**
     * 孕周首日
     */
    private Date firstDay;

    /**
     * 当前日期
     */
    private Date currentDay;

    /**
     * endDay和firstDay的日期差
     */
    private Integer diff;

    public GestationWeek(Date firstDay, Date currentDay) {
        this.firstDay = firstDay;
        this.currentDay = currentDay;
        this.diff = differentDays(firstDay, currentDay);
    }

    public Date getFirstDay() {
        return firstDay;
    }

    public void setFirstDay(Date firstDay) {
        this.firstDay = firstDay;
        this.diff = differentDays(this.firstDay, this.currentDay);
    }

    public Date getCurrentDay() {
        return currentDay;
    }

    public void setCurrentDay(Date currentDay) {
        this.currentDay = currentDay;
        this.diff = differentDays(this.firstDay, this.currentDay);
    }

    public Integer countDay() {
        if (Objects.isNull(this.diff)) {
            return null;
        }
        return this.diff;
    }

    public Integer countDown() {
        if (Objects.isNull(this.diff)) {
            return null;
        }
        return 280 - this.diff;
    }

    public Integer weekNum() {
        if (Objects.isNull(this.diff)) {
            return null;
        }
        return this.diff / 7;
    }

    public Integer dayNum() {
        if (Objects.isNull(this.diff)) {
            return null;
        }
        return this.diff % 7;
    }

    /**
     * date2比date1多的天数
     * @param date1
     * @param date2
     * @return
     */
    public static Integer differentDays(Date date1, Date date2) {
        if (Objects.isNull(date1) || Objects.isNull(date2)) {
            return null;
        }
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        int day1= cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);

        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if(year1 != year2) {//不同年
            int timeDistance = 0 ;
            for(int i = year1 ; i < year2 ; i ++)
            {
                if(i%4==0 && i%100!=0 || i%400==0)    //闰年
                {
                    timeDistance += 366;
                }
                else    //不是闰年
                {
                    timeDistance += 365;
                }
            }
            return timeDistance + (day2-day1) ;
        } else {//同一年
            System.out.println("判断day2 - day1 : " + (day2-day1));
            return day2-day1;
        }
    }

    public static Integer daysDifferent(Date date1, Date date2) {
        if (Objects.isNull(date1) || Objects.isNull(date2)) {
            return null;
        }
        LocalDate d1 = date1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate d2 = date2.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        long daysDifference = ChronoUnit.DAYS.between(d1, d2);
        return (int) daysDifference;
    }

    public static void main(String[] args) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date d1 = formatter.parse("2023-04-12");
        Date d2 = formatter.parse("2024-01-07");
        GestationWeek gestationWeek = new GestationWeek(d1, d2);
        System.out.println(gestationWeek.countDay());
        System.out.println(gestationWeek.countDown());
        System.out.println(gestationWeek.weekNum());
        System.out.println(gestationWeek.dayNum());
        System.out.println(daysDifferent(d1, d2));
    }
}
