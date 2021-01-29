package software.cstl.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import software.cstl.service.WeekendDateMapService;
import software.cstl.service.dto.WeekendDateMapDTO;

import java.util.List;

@RestController
@RequestMapping("/api")
public class WeekendDateMapResource {

    private final Logger log = LoggerFactory.getLogger(WeekendDateMapResource.class);

    private final WeekendDateMapService weekendDateMapService;

    public WeekendDateMapResource(WeekendDateMapService weekendDateMapService) {
        this.weekendDateMapService = weekendDateMapService;
    }

    /**
     * {@code GET  /weekend-date-maps} : get all the weekendDateMaps.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of weekendDateMaps in body.
     */
    @GetMapping("/weekend-date-maps")
    public ResponseEntity<List<WeekendDateMapDTO>> getAllWeekendDateMaps(Pageable pageable) {
        log.debug("REST request to get a page of WeekendDateMaps");
        List<WeekendDateMapDTO> result = weekendDateMapService.findAll(pageable);
        return ResponseEntity.ok().body(result);
    }
}
