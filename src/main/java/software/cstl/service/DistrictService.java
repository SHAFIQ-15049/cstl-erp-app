package software.cstl.service;

import software.cstl.domain.District;
import software.cstl.repository.DistrictRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link District}.
 */
@Service
@Transactional
public class DistrictService {

    private final Logger log = LoggerFactory.getLogger(DistrictService.class);

    private final DistrictRepository districtRepository;

    public DistrictService(DistrictRepository districtRepository) {
        this.districtRepository = districtRepository;
    }

    /**
     * Save a district.
     *
     * @param district the entity to save.
     * @return the persisted entity.
     */
    public District save(District district) {
        log.debug("Request to save District : {}", district);
        return districtRepository.save(district);
    }

    /**
     * Get all the districts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<District> findAll(Pageable pageable) {
        log.debug("Request to get all Districts");
        return districtRepository.findAll(pageable);
    }


    /**
     * Get one district by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<District> findOne(Long id) {
        log.debug("Request to get District : {}", id);
        return districtRepository.findById(id);
    }

    /**
     * Delete the district by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete District : {}", id);
        districtRepository.deleteById(id);
    }
}
