package ru.almaz.CaravelleTravels.entities;

import jakarta.persistence.*;
import lombok.Data;
import ru.almaz.CaravelleTravels.config.RepliesText;

@Entity
@Data
@Table(name = "custom_bot_replies")
public class CustomBotReply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(value = EnumType.ORDINAL)
    @Column(name = "default_text", unique = true)
    private RepliesText defaultText;

    @Column(name = "custom_text")
    private String customText;
}
