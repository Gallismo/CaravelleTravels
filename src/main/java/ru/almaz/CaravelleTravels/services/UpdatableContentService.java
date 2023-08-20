package ru.almaz.CaravelleTravels.services;

import ru.almaz.CaravelleTravels.entities.UpdatableContent;

import java.util.List;

public interface UpdatableContentService {
    List<UpdatableContent> findAll();

    void save(UpdatableContent updatableContent);

    void deleteById(Long id);

    UpdatableContent findById(Long id);

    void update(Long id, UpdatableContent updatableContent);
}
