package software.cstl.web.rest;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import software.cstl.service.DutyLeaveService;
import software.cstl.service.dto.DutyLeaveDTO;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api")
public class DutyLeaveResource {

    private final Logger log = LoggerFactory.getLogger(DutyLeaveResource.class);

    private final DutyLeaveService dutyLeaveService;

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    public DutyLeaveResource(DutyLeaveService dutyLeaveService) {
        this.dutyLeaveService = dutyLeaveService;
    }

    @PutMapping("/duty-leaves")
    public ResponseEntity<List<DutyLeaveDTO>> update(@RequestBody  List<DutyLeaveDTO> dutyLeaveDTOS) throws URISyntaxException {
        log.debug("REST request to update duty leaves : {}", dutyLeaveDTOS);
        List<DutyLeaveDTO> results = dutyLeaveService.save(dutyLeaveDTOS);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, "duty-leaves", results.get(0).getId().toString()))
            .body(results);
    }

    /**
     * {@code GET  /duty-leaves} : get all the dutyLeaves.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dutyLeaves in body.
     */
    @GetMapping("/duty-leaves")
    public List<DutyLeaveDTO> getAllDutyLeaves() {
        log.debug("REST request to get all DutyLeaves");
        return dutyLeaveService.findAll();
    }

    /**
     * {@code GET  /duty-leaves/:id} : get the "id" dutyLeave.
     *
     * @param id the id of the dutyLeaveDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dutyLeaveDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/duty-leaves/{id}")
    public ResponseEntity<DutyLeaveDTO> getDutyLeave(@PathVariable Long id) {
        log.debug("REST request to get DutyLeave : {}", id);
        Optional<DutyLeaveDTO> dutyLeaveDTO = dutyLeaveService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dutyLeaveDTO);
    }
}
