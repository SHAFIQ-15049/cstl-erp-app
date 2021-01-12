package software.cstl.service;

import org.springframework.context.annotation.Lazy;
import software.cstl.domain.PartialSalary;
import software.cstl.repository.PartialSalaryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import software.cstl.security.SecurityUtils;
import software.cstl.service.mediators.PayrollService;

import java.time.Instant;
import java.util.Optional;

/**
 * Service Implementation for managing {@link PartialSalary}.
 */
@Service
@Transactional
public class PartialSalaryService {

    private final Logger log = LoggerFactory.getLogger(PartialSalaryService.class);

    private final PartialSalaryRepository partialSalaryRepository;
    private final PayrollService payrollService;

    public PartialSalaryService(PartialSalaryRepository partialSalaryRepository,@Lazy PayrollService payrollService) {
        this.partialSalaryRepository = partialSalaryRepository;
        this.payrollService = payrollService;
    }

    /**
     * Save a partialSalary.
     *
     * @param partialSalary the entity to save.
     * @return the persisted entity.
     */
    public PartialSalary save(PartialSalary partialSalary) {
        log.debug("Request to save PartialSalary : {}", partialSalary);
        partialSalary = payrollService.assignPartialSalaryAndAllowances(partialSalary);
        return partialSalaryRepository.save(partialSalary);
    }

    /**
     * Get all the partialSalaries.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PartialSalary> findAll(Pageable pageable) {
        log.debug("Request to get all PartialSalaries");
        return partialSalaryRepository.findAll(pageable);
    }


    /**
     * Get one partialSalary by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PartialSalary> findOne(Long id) {
        log.debug("Request to get PartialSalary : {}", id);
        return partialSalaryRepository.findById(id);
    }

    /**
     * Delete the partialSalary by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PartialSalary : {}", id);
        partialSalaryRepository.deleteById(id);
    }
}
