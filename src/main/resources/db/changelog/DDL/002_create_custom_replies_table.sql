--liquibase formatted sql
--changeset myname:create-multiple-tables splitStatements:true endDelimiter:;

CREATE TABLE `custom_bot_replies`
(`default_text` TINYINT CHECK (`default_text` BETWEEN 0 AND 30),
`id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
`custom_text` VARCHAR(255));

ALTER TABLE IF EXISTS `custom_bot_replies`
    ADD CONSTRAINT `default_text_unique` UNIQUE (`default_text`);