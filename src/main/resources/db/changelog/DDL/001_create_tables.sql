--liquibase formatted sql
--changeset myname:create-multiple-tables splitStatements:true endDelimiter:;

CREATE TABLE IF NOT EXISTS `answers`
(`id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, `answer` TEXT NOT NULL , `question` TEXT NOT NULL);

CREATE TABLE IF NOT EXISTS `bookings`
(`id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, `date` DATE, `passengers_count` INTEGER,
 `user_id` BIGINT NOT NULL, `booking_status` ENUM ('CREATED','CREATING','PROCESSED') NOT NULL,
 `from_place` VARCHAR(255), `passenger_name` VARCHAR(255), `phone_number` VARCHAR(255),
 `to_place` VARCHAR(255));

CREATE TABLE IF NOT EXISTS `updatable_contents`
(`id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, `lexic_id` TEXT NOT NULL,
`text_content` TEXT NOT NULL);

CREATE TABLE IF NOT EXISTS `users`
(`id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, `permissions` BIT NOT NULL,
`chat_id` BIGINT NOT NULL, `processing_booking_id` BIGINT,
`booking_state` ENUM ('DATE','FROM','NAME','NONE','PASSENGERS','PHONE','TO') NOT NULL,
`telegram_user_name` VARCHAR(255));


ALTER TABLE IF EXISTS `answers`
    ADD CONSTRAINT `question_unique` UNIQUE (`question`);

ALTER TABLE IF EXISTS `updatable_contents`
    ADD CONSTRAINT `lexic_id_unique` UNIQUE (`lexic_id`);

ALTER TABLE IF EXISTS `users`
    ADD CONSTRAINT `chat_id_unique` UNIQUE (`chat_id`);

ALTER TABLE IF EXISTS bookings
    ADD CONSTRAINT `user_id_foreign` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

CREATE INDEX `date_index` ON bookings (`date`);
