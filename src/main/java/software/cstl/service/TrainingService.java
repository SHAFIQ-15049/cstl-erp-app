package software.cstl.service;

import software.cstl.domain.Training;
import software.cstl.repository.TrainingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Training}.
 */
@Service
@Transactional
public class TrainingService {

    private final Logger log = LoggerFactory.getLogger(TrainingService.class);

    private final TrainingRepository trainingRepository;

    public TrainingService(TrainingRepository trainingRepository) {
        this.trainingRepository = trainingRepository;
    }

    /**
     * Save a training.
     *
     * @param training the entity to save.
     * @return the persisted entity.
     */
    public Training save(Training training) {
        log.debug("Request to save Training : {}", training);
        return trainingRepository.save(training);
    }

    /**
     * Get all the trainings.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Training> findAll(Pageable pageable) {
        log.debug("Request to get all Trainings");
        return trainingRepository.findAll(pageable);
    }


    /**
     * Get one training by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Training> findOne(Long id) {
        log.debug("Request to get Training : {}", id);
        return trainingRepository.findById(id);
    }

    /**
     * Delete the training by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Training : {}", id);
        trainingRepository.deleteById(id);
    }
}
