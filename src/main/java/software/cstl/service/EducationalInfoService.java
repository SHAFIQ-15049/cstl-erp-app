package software.cstl.service;

import software.cstl.domain.EducationalInfo;
import software.cstl.repository.EducationalInfoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link EducationalInfo}.
 */
@Service
@Transactional
public class EducationalInfoService {

    private final Logger log = LoggerFactory.getLogger(EducationalInfoService.class);

    private final EducationalInfoRepository educationalInfoRepository;

    public EducationalInfoService(EducationalInfoRepository educationalInfoRepository) {
        this.educationalInfoRepository = educationalInfoRepository;
    }

    /**
     * Save a educationalInfo.
     *
     * @param educationalInfo the entity to save.
     * @return the persisted entity.
     */
    public EducationalInfo save(EducationalInfo educationalInfo) {
        log.debug("Request to save EducationalInfo : {}", educationalInfo);
        return educationalInfoRepository.save(educationalInfo);
    }

    /**
     * Get all the educationalInfos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EducationalInfo> findAll(Pageable pageable) {
        log.debug("Request to get all EducationalInfos");
        return educationalInfoRepository.findAll(pageable);
    }


    /**
     * Get one educationalInfo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EducationalInfo> findOne(Long id) {
        log.debug("Request to get EducationalInfo : {}", id);
        return educationalInfoRepository.findById(id);
    }

    /**
     * Delete the educationalInfo by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete EducationalInfo : {}", id);
        educationalInfoRepository.deleteById(id);
    }
}
