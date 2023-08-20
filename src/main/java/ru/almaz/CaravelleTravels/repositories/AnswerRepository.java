package ru.almaz.CaravelleTravels.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.almaz.CaravelleTravels.entities.Answer;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {
}
