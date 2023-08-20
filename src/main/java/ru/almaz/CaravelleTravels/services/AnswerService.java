package ru.almaz.CaravelleTravels.services;

import ru.almaz.CaravelleTravels.entities.Answer;

import java.util.List;

public interface AnswerService {
    List<Answer> findAll();

    Answer getById(Long id);

    void save(Answer answer);

    void update(Long id, Answer updatedAnswer);

    void delete(Long id);
}
