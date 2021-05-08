package software.cstl.service.mediators;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import software.cstl.domain.OverTime;
import software.cstl.domain.enumeration.MonthType;

import java.time.Instant;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class OverTimeGenerationServiceTest {

    @Spy
    @InjectMocks
    private OverTimeGenerationService overTimeGenerationService;

    @Test
    public void generateOvertimeByDesignation(){
        Integer year = 0;
        MonthType monthType = MonthType.JANUARY;
        Long designationId = 1L;
        Instant fromDate = Instant.now();
        Instant toDate = Instant.now();
        List<OverTime> overTimes = overTimeGenerationService.generateOverTime(year, monthType);
    }

    @Test
    public void generateOverTimeForAnEmployee(){

    }
}
