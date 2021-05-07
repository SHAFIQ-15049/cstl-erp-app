package software.cstl.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import software.cstl.domain.Weekend;
import software.cstl.domain.enumeration.WeekendStatus;
import software.cstl.service.dto.WeekendDateMapDTO;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
@Transactional
public class WeekendDateMapService {

    private final Logger log = LoggerFactory.getLogger(WeekendDateMapService.class);

    private final WeekendService weekendService;

    private final CommonService commonService;

    public WeekendDateMapService(WeekendService weekendService, CommonService commonService) {
        this.weekendService = weekendService;
        this.commonService = commonService;
    }

    /**
     * Get all the weekendDateMaps.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<WeekendDateMapDTO> findAll(Pageable pageable) {
        log.debug("Request to get all WeekendDateMaps of current year");
        LocalDate currentDate = LocalDate.now();
        return findAllWeekendDateMapDTOs(currentDate.getYear());
    }

    /**
     * Get list of weekendDateMapDTO.
     *
     * @param fromDate the start date.
     * @param toDate the end date.
     * @return the WeekendDateMapDTO DTOs.
     */
    public List<WeekendDateMapDTO> findAllWeekendDateMapDTOs(LocalDate fromDate, LocalDate toDate) {
        log.debug("Request to get WeekendDateMapDTO : {} {}", fromDate, toDate);

        List<Weekend> weekends = weekendService.findAll(WeekendStatus.ACTIVE);
        List<WeekendDateMapDTO> weekendDateMapDTOS = new ArrayList<>();

        LocalDate startDate = fromDate;
        LocalDate endDate = toDate.plusDays(1);
        long serial = 0L;

        while(startDate.isBefore(endDate)) {
            DayOfWeek dayOfWeek = startDate.getDayOfWeek();
            String day = dayOfWeek.getDisplayName(TextStyle.FULL, Locale.ENGLISH).trim().toUpperCase();
            for(Weekend weekend: weekends) {
                if (day.equalsIgnoreCase(weekend.getDay().toString().trim().toUpperCase())) {
                    serial = serial + 1;
                    weekendDateMapDTOS.add(new WeekendDateMapDTO(serial, startDate, weekend.getId(), weekend.getDay()));
                }
            }
            startDate = startDate.plusDays(1);
        }

        return weekendDateMapDTOS;
    }

    /**
     * Get list of weekendDateMapDTO.
     *
     * @param year the year.
     * @param month the month.
     * @return the WeekendDateMapDTO DTOs.
     */
    public List<WeekendDateMapDTO> findAllWeekendDateMapDTOs(int year, Month month) {
        log.debug("Request to get WeekendDateMapDTO : {} {}", year, month);
        LocalDate startDateOfTheMonth = commonService.getFirstDayOfTheMonth(year, month);
        LocalDate lastDateOfTheMonth = commonService.getLastDayOfTheMonth(year, month);
        return findAllWeekendDateMapDTOs(startDateOfTheMonth, lastDateOfTheMonth);
    }

    /**
     * Get list of weekendDateMapDTO.
     *
     * @param year the year.
     * @return the WeekendDateMapDTO DTOs.
     */
    public List<WeekendDateMapDTO> findAllWeekendDateMapDTOs(int year) {
        log.debug("Request to get WeekendDateMapDTO : {}", year);
        LocalDate startDateOfTheYear = commonService.getFirstDayOfTheYear(year);
        LocalDate lastDateOfTheYear = commonService.getLastDayOfTheYear(year);
        return findAllWeekendDateMapDTOs(startDateOfTheYear, lastDateOfTheYear);
    }
}
