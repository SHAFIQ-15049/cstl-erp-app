package software.cstl.utils;

import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import software.cstl.domain.enumeration.MonthType;
import software.cstl.domain.enumeration.WeekDay;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Locale;

@Slf4j
public class CodeNodeErpUtils {
    private static final char[] banglaDigits = {'০','১','২','৩','৪','৫','৬','৭','৮','৯'};
    private static final char[] englishDigits = {'0','1','2','3','4','5','6','7','8','9'};

    public  static final String  getDigitBanglaFromEnglish(String number){
        if(number==null)
            return new String("");
        StringBuilder builder = new StringBuilder();
        try{
            for(int i =0;i<number.length();i++){
                if(Character.isDigit(number.charAt(i))){
                    if(((int)(number.charAt(i))-48)<=9){
                        builder.append(banglaDigits[(int)(number.charAt(i))-48]);
                    }else{
                        builder.append(number.charAt(i));
                    }
                }else{
                    builder.append(number.charAt(i));
                }
            }
        }catch(Exception e){
            log.debug("getDigitBanglaFromEnglish: ",e);
            return new String("");
        }
        return builder.toString();
    }

    public static String currencyWithChosenLocalisation(BigDecimal value) {
        NumberFormat nf = NumberFormat.getCurrencyInstance();
        return nf.format(value).replace("$","");
    }

    public static int getTotalDayOccurrenceOfWeekDay(int year, MonthType monthType, WeekDay weekDay){

        int weekDayOrdinalValue = getWeekDayOrdinalValue(weekDay); // we need this ordinal value as we are not using java standard week day enum

        YearMonth yearMonth = YearMonth.of(year, (monthType.ordinal()+1));
        LocalDate firstDay = LocalDate.of(yearMonth.getYear(), yearMonth.getMonth(), 1);
        LocalDate lastDay = LocalDate.of(yearMonth.getYear(), yearMonth.getMonth(), yearMonth.lengthOfMonth());
        int totalDays = 0;
        while (!firstDay.isAfter(lastDay)){
            if(firstDay.getDayOfWeek().equals(DayOfWeek.of(weekDayOrdinalValue))){
                totalDays+=1;
            }
            firstDay = firstDay.plusDays(1);
        }

        return totalDays;
    }

    public static int getWeekDayOrdinalValue(WeekDay weekDay) {
        int weekDayOrdinalValue = 0;
        if (weekDay.equals(WeekDay.MONDAY)) {
            weekDayOrdinalValue=1;
        }else if(weekDay.equals(WeekDay.SUNDAY)){
            weekDayOrdinalValue=7;
        }else{
            weekDayOrdinalValue = weekDay.ordinal();
        }
        return weekDayOrdinalValue;
    }
}
