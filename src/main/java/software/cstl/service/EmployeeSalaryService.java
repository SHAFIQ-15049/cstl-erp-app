package software.cstl.service;

import software.cstl.domain.EmployeeSalary;
import software.cstl.repository.EmployeeSalaryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import software.cstl.domain.EmployeeSalary;
import software.cstl.domain.enumeration.ActiveStatus;
import software.cstl.repository.EmployeeSalaryRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link EmployeeSalary}.
 */
@Service
@Transactional
public class EmployeeSalaryService {

    private final Logger log = LoggerFactory.getLogger(EmployeeSalaryService.class);

    private final EmployeeSalaryRepository employeeSalaryRepository;

    public EmployeeSalaryService(EmployeeSalaryRepository employeeSalaryRepository) {
        this.employeeSalaryRepository = employeeSalaryRepository;
    }

    /**
     * Save a employeeSalary.
     *
     * @param employeeSalary the entity to save.
     * @return the persisted entity.
     */
    public EmployeeSalary save(EmployeeSalary employeeSalary) {
        log.debug("Request to save EmployeeSalary : {}", employeeSalary);
        return employeeSalaryRepository.save(employeeSalary);
    }

    /**
     * Get all the employeeSalaries.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EmployeeSalary> findAll(Pageable pageable) {
        log.debug("Request to get all EmployeeSalaries");
        return employeeSalaryRepository.findAll(pageable);
    }


    /**
     * Get one employeeSalary by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EmployeeSalary> findOne(Long id) {
        log.debug("Request to get EmployeeSalary : {}", id);
        return employeeSalaryRepository.findById(id);
    }

    /**
     * Delete the employeeSalary by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete EmployeeSalary : {}", id);
        employeeSalaryRepository.deleteById(id);
    }

    public List<EmployeeSalary> getAll() {
        return employeeSalaryRepository.findAll();
    }

    public List<EmployeeSalary> getAllByActiveStatus() {
        return this.getAll().stream().filter(employeeSalary -> employeeSalary.getStatus().equals(ActiveStatus.ACTIVE)).collect(Collectors.toList());
    }
}
