package software.cstl.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;

@Service
public class CommonService {

    private final Logger log = LoggerFactory.getLogger(CommonService.class);

    public CommonService() {}

    public String[] getStringArrayBySeparatingStringContentUsingSeparator(String content, String separator) {
        log.debug("Request to separate a string content : {} using given separator : {}", content, separator);
        return content.split(separator);
    }

    public String getByteArrayToString(byte[] bytes) {
        log.debug("Request to convert byte array to string : {} ", bytes);
        return new String(bytes);
    }

    public LocalDate getFirstDayOfTheYear(int year) {
        log.debug("Request to get the first day of the year : {} ", year);
        return getFirstDayOfTheMonth(year, Month.JANUARY);
    }

    public LocalDate getLastDayOfTheYear(int year) {
        log.debug("Request to get the last day of the year : {} ", year);
        return getLastDayOfTheMonth(year, Month.DECEMBER);
    }

    public LocalDate getFirstDayOfTheMonth(int year, Month month) {
        log.debug("Request to get the first day of the month : {} {}", year, month);
        YearMonth yearMonth = YearMonth.of(year, month);
        return yearMonth.atDay(1);
    }

    public LocalDate getLastDayOfTheMonth(int year, Month month) {
        log.debug("Request to get the last day of the month : {} {}", year, month);
        YearMonth yearMonth = YearMonth.of(year, month);
        return yearMonth.atEndOfMonth();
    }

    public int getLengthOfYear(int year) {
        log.debug("Request to get the total number of days in a year : {}", year);
        YearMonth yearMonth = YearMonth.of(year, Month.FEBRUARY);
        return yearMonth.lengthOfYear();
    }
}
