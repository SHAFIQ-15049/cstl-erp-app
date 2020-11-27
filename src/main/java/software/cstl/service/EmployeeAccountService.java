package software.cstl.service;

import software.cstl.domain.EmployeeAccount;
import software.cstl.repository.EmployeeAccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link EmployeeAccount}.
 */
@Service
@Transactional
public class EmployeeAccountService {

    private final Logger log = LoggerFactory.getLogger(EmployeeAccountService.class);

    private final EmployeeAccountRepository employeeAccountRepository;

    public EmployeeAccountService(EmployeeAccountRepository employeeAccountRepository) {
        this.employeeAccountRepository = employeeAccountRepository;
    }

    /**
     * Save a employeeAccount.
     *
     * @param employeeAccount the entity to save.
     * @return the persisted entity.
     */
    public EmployeeAccount save(EmployeeAccount employeeAccount) {
        log.debug("Request to save EmployeeAccount : {}", employeeAccount);
        return employeeAccountRepository.save(employeeAccount);
    }

    /**
     * Get all the employeeAccounts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EmployeeAccount> findAll(Pageable pageable) {
        log.debug("Request to get all EmployeeAccounts");
        return employeeAccountRepository.findAll(pageable);
    }


    /**
     * Get one employeeAccount by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EmployeeAccount> findOne(Long id) {
        log.debug("Request to get EmployeeAccount : {}", id);
        return employeeAccountRepository.findById(id);
    }

    /**
     * Delete the employeeAccount by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete EmployeeAccount : {}", id);
        employeeAccountRepository.deleteById(id);
    }
}
