package software.cstl.service;

import software.cstl.domain.Employee;
import software.cstl.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing {@link Employee}.
 */
@Service
@Transactional
public class EmployeeService {

    private final Logger log = LoggerFactory.getLogger(EmployeeService.class);

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    /**
     * Save a employee.
     *
     * @param employee the entity to save.
     * @return the persisted entity.
     */
    public Employee save(Employee employee) {
        log.debug("Request to save Employee : {}", employee);
        return employeeRepository.save(employee);
    }

    /**
     * Get all the employees.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Employee> findAll(Pageable pageable) {
        log.debug("Request to get all Employees");
        return employeeRepository.findAll(pageable);
    }



    /**
     *  Get all the employees where Address is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Employee> findAllWhereAddressIsNull() {
        log.debug("Request to get all employees where Address is null");
        return StreamSupport
            .stream(employeeRepository.findAll().spliterator(), false)
            .filter(employee -> employee.getAddress() == null)
            .collect(Collectors.toList());
    }


    /**
     *  Get all the employees where PersonalInfo is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Employee> findAllWherePersonalInfoIsNull() {
        log.debug("Request to get all employees where PersonalInfo is null");
        return StreamSupport
            .stream(employeeRepository.findAll().spliterator(), false)
            .filter(employee -> employee.getPersonalInfo() == null)
            .collect(Collectors.toList());
    }

    /**
     * Get one employee by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Employee> findOne(Long id) {
        log.debug("Request to get Employee : {}", id);
        return employeeRepository.findById(id);
    }

    public Employee findByEmpId(String empId){
        return employeeRepository.findByEmpId(empId);
    }

    /**
     * Delete the employee by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Employee : {}", id);
        employeeRepository.deleteById(id);
    }

    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }
}
