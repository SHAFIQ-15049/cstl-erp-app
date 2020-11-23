package software.cstl.service;

import software.cstl.domain.Grade;
import software.cstl.repository.GradeRepository;
import software.cstl.repository.search.GradeSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Grade}.
 */
@Service
@Transactional
public class GradeService {

    private final Logger log = LoggerFactory.getLogger(GradeService.class);

    private final GradeRepository gradeRepository;

    private final GradeSearchRepository gradeSearchRepository;

    public GradeService(GradeRepository gradeRepository, GradeSearchRepository gradeSearchRepository) {
        this.gradeRepository = gradeRepository;
        this.gradeSearchRepository = gradeSearchRepository;
    }

    /**
     * Save a grade.
     *
     * @param grade the entity to save.
     * @return the persisted entity.
     */
    public Grade save(Grade grade) {
        log.debug("Request to save Grade : {}", grade);
        Grade result = gradeRepository.save(grade);
        gradeSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the grades.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Grade> findAll(Pageable pageable) {
        log.debug("Request to get all Grades");
        return gradeRepository.findAll(pageable);
    }


    /**
     * Get one grade by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Grade> findOne(Long id) {
        log.debug("Request to get Grade : {}", id);
        return gradeRepository.findById(id);
    }

    /**
     * Delete the grade by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Grade : {}", id);
        gradeRepository.deleteById(id);
        gradeSearchRepository.deleteById(id);
    }

    /**
     * Search for the grade corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Grade> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Grades for query {}", query);
        return gradeSearchRepository.search(queryStringQuery(query), pageable);    }
}
