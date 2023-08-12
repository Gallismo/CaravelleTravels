package ru.almaz.CaravelleTravels.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "answers")
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Колонка для часто задаваемых вопросов
    @Column(nullable = false, unique = true, name = "question")
    private String question;

    // Колонка с ответами на вопросы
    @Column(nullable = false, name = "answer")
    private String answer;
}
