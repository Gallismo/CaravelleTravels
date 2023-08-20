package ru.almaz.CaravelleTravels.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.almaz.CaravelleTravels.entities.Answer;
import ru.almaz.CaravelleTravels.repositories.AnswerRepository;
import ru.almaz.CaravelleTravels.services.AnswerService;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class AnswerServiceImpl implements AnswerService {
    private final AnswerRepository answerRepository;

    @Autowired
    public AnswerServiceImpl(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    @Override
    public List<Answer> findAll() {
        return answerRepository.findAll();
    }

    @Override
    public Answer getById(Long id) {
        return answerRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void save(Answer answer) {
        answerRepository.save(answer);
    }

    @Override
    @Transactional
    public void update(Long id, Answer updatedAnswer) {
        updatedAnswer.setId(id);
        answerRepository.save(updatedAnswer);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        answerRepository.deleteById(id);
    }
}
