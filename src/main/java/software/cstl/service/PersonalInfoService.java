package software.cstl.service;

import software.cstl.domain.PersonalInfo;
import software.cstl.repository.PersonalInfoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link PersonalInfo}.
 */
@Service
@Transactional
public class PersonalInfoService {

    private final Logger log = LoggerFactory.getLogger(PersonalInfoService.class);

    private final PersonalInfoRepository personalInfoRepository;

    public PersonalInfoService(PersonalInfoRepository personalInfoRepository) {
        this.personalInfoRepository = personalInfoRepository;
    }

    /**
     * Save a personalInfo.
     *
     * @param personalInfo the entity to save.
     * @return the persisted entity.
     */
    public PersonalInfo save(PersonalInfo personalInfo) {
        log.debug("Request to save PersonalInfo : {}", personalInfo);
        return personalInfoRepository.save(personalInfo);
    }

    /**
     * Get all the personalInfos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PersonalInfo> findAll(Pageable pageable) {
        log.debug("Request to get all PersonalInfos");
        return personalInfoRepository.findAll(pageable);
    }


    /**
     * Get one personalInfo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PersonalInfo> findOne(Long id) {
        log.debug("Request to get PersonalInfo : {}", id);
        return personalInfoRepository.findById(id);
    }

    /**
     * Delete the personalInfo by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PersonalInfo : {}", id);
        personalInfoRepository.deleteById(id);
    }
}
