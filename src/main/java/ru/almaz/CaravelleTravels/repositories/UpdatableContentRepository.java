package ru.almaz.CaravelleTravels.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.almaz.CaravelleTravels.entities.UpdatableContent;

@Repository
public interface UpdatableContentRepository extends JpaRepository<UpdatableContent, Long> {
}
