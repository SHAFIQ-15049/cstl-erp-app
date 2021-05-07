package software.cstl.service;

import software.cstl.domain.HolidayType;
import software.cstl.repository.HolidayTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link HolidayType}.
 */
@Service
@Transactional
public class HolidayTypeService {

    private final Logger log = LoggerFactory.getLogger(HolidayTypeService.class);

    private final HolidayTypeRepository holidayTypeRepository;

    public HolidayTypeService(HolidayTypeRepository holidayTypeRepository) {
        this.holidayTypeRepository = holidayTypeRepository;
    }

    /**
     * Save a holidayType.
     *
     * @param holidayType the entity to save.
     * @return the persisted entity.
     */
    public HolidayType save(HolidayType holidayType) {
        log.debug("Request to save HolidayType : {}", holidayType);
        return holidayTypeRepository.save(holidayType);
    }

    /**
     * Get all the holidayTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<HolidayType> findAll(Pageable pageable) {
        log.debug("Request to get all HolidayTypes");
        return holidayTypeRepository.findAll(pageable);
    }


    /**
     * Get one holidayType by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<HolidayType> findOne(Long id) {
        log.debug("Request to get HolidayType : {}", id);
        return holidayTypeRepository.findById(id);
    }

    /**
     * Delete the holidayType by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete HolidayType : {}", id);
        holidayTypeRepository.deleteById(id);
    }

    /**
     * Get all holidayTypes.
     *
     * @return the entity.
     */
    public List<HolidayType> findAll() {
        return holidayTypeRepository.findAll();
    }
}
