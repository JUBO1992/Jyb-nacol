package com.example.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

/**
 * 年龄计算工具
 *
 * @author jubo
 * @date 2024/01/13
 */
public class AgeCalculator {

    /**
     * 出生日期
     */
    private Date birthDay;

    /**
     * 当前日期
     */
    private Date currentDay;

    /**
     * period
     */
    private Period period;

    public AgeCalculator(Date birthDay, Date currentDay) {
        this.birthDay = birthDay;
        this.currentDay = currentDay;
        this.period = periodCalc(birthDay, currentDay);
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
        this.period = periodCalc(this.birthDay, this.currentDay);
    }

    public Date getCurrentDay() {
        return currentDay;
    }

    public void setCurrentDay(Date currentDay) {
        this.currentDay = currentDay;
        this.period = periodCalc(this.birthDay, this.currentDay);
    }

    public Integer year() {
        if (Objects.isNull(period)) {
            return null;
        }
        // 第一种方式：对闰年2月29生日处理有些问题
        // return period.getYears();
        // 第二种方式：
        LocalDate d1 = this.birthDay.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate d2 = this.currentDay.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int birthYear = d1.getYear();
        int currentYear = d2.getYear();
        int dayOfMonth = d1.getDayOfMonth();
        // 闰年2月29特殊处理
        if (d1.getMonthValue() == 2 && d1.getDayOfMonth() == 29 && !isLeapYear(currentYear)) {
            dayOfMonth = 28;
        }
        LocalDate curYearBirthday = LocalDate.of(d2.getYear(), d1.getMonth(), dayOfMonth);
        Date date3 = Date.from(curYearBirthday.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        if (this.currentDay.before(date3)) {
            return currentYear - birthYear - 1;
        } else {
            return currentYear - birthYear;
        }
    }

    public Integer dayOfYear() {
        if (Objects.isNull(period)) {
            return null;
        }
        LocalDate d1 = this.birthDay.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate d2 = this.currentDay.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int currentYear = d2.getYear();
        int dayOfMonth = d1.getDayOfMonth();
        // 闰年2月29特殊处理
        if (d1.getMonthValue() == 2 && d1.getDayOfMonth() == 29 && !isLeapYear(currentYear)) {
            dayOfMonth = 28;
        }
        LocalDate curYearBirthday = LocalDate.of(d2.getYear(), d1.getMonth(), dayOfMonth);
        Date date3 = Date.from(curYearBirthday.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        int diff = daysDifferent(date3, this.currentDay);
        if (diff < 0) {
            int yearDays = 365;
            if (isLeapYear(currentYear - 1)) {
                yearDays = 366;
            }
            return yearDays + 1 + diff;
        } else {
            return diff + 1;
        }
    }

    private static boolean isLeapYear(int year) {
        if(year%4==0 && year%100!=0 || year%400==0) {
            return true;
        } else {
            return false;
        }
    }

    private static Period periodCalc(Date date1, Date date2) {
        if (Objects.isNull(date1) || Objects.isNull(date2)) {
            return null;
        }
        if (date2.before(date1)) {
            return null;
        }
        LocalDate d1 = date1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate d2 = date2.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return Period.between(d1, d2);
    }

    /**
     * date2比date1多的天数
     * @param date1
     * @param date2
     * @return
     */
    private static Integer differentDays(Date date1, Date date2) {
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

    private static Integer daysDifferent(Date date1, Date date2) {
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
        Date d1 = formatter.parse("2024-01-07");
        Date d2 = formatter.parse("2024-01-14");
        AgeCalculator ageCalculator = new AgeCalculator(d1, d2);
        System.out.println(ageCalculator.year());
        System.out.println(ageCalculator.dayOfYear());
    }
}
