package edu.persistence.repository;

import edu.persistence.model.TwoNumbers;
import org.springframework.data.repository.CrudRepository;

public interface TwoNumbersRepository extends CrudRepository<TwoNumbers, Long> {
}
