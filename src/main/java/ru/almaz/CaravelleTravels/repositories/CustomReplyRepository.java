package ru.almaz.CaravelleTravels.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.almaz.CaravelleTravels.config.RepliesText;
import ru.almaz.CaravelleTravels.entities.CustomBotReply;

import java.util.Optional;

public interface CustomReplyRepository extends JpaRepository<CustomBotReply, Long> {
    Optional<CustomBotReply> findByDefaultText(RepliesText defaultText);
}
