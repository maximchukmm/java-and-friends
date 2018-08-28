package edu.repository;

import edu.model.JodaTime;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JodaTimeRepository extends JpaRepository<JodaTime, Long> {
}
