package ru.almaz.CaravelleTravels.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.almaz.CaravelleTravels.entities.UpdatableContent;
import ru.almaz.CaravelleTravels.repositories.UpdatableContentRepository;
import ru.almaz.CaravelleTravels.services.UpdatableContentService;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class UpdatableContentServiceImpl implements UpdatableContentService {

    private final UpdatableContentRepository updatableContentRepository;

    @Autowired
    public UpdatableContentServiceImpl(UpdatableContentRepository updatableContentRepository) {
        this.updatableContentRepository = updatableContentRepository;
    }

    @Override
    public List<UpdatableContent> findAll() {
        return updatableContentRepository.findAll();
    }

    @Override
    @Transactional
    public void save(UpdatableContent updatableContent) {
        updatableContentRepository.save(updatableContent);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        updatableContentRepository.deleteById(id);
    }

    @Override
    public UpdatableContent findById(Long id) {
        return updatableContentRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void update(Long id, UpdatableContent updatableContent) {
        updatableContent.setId(id);
        updatableContentRepository.save(updatableContent);
    }
}
