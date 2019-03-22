package edu.persistence.repository;

import edu.persistence.model.SumNumbers;
import org.springframework.data.repository.CrudRepository;

public interface SumNumbersRepository extends CrudRepository<SumNumbers, Long> {
}
