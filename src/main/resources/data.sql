INSERT INTO answers (question, answer) VALUES ('Сколько можно багажа', 'Не больше 10кг');
INSERT INTO answers (question, answer) VALUES ('Есть у вас кресло детское', 'Есть, для детей не больше 10 лет');
INSERT INTO answers (question, answer) VALUES ('Что то еще', 'Ответ на что то еще');

INSERT INTO updatable_contents (lexic_id, text_content) VALUES ('Информация о поездке', 'Цена 1000, мест 8, Уфа-Учалы, в 9 утра');
INSERT INTO updatable_contents (lexic_id, text_content) VALUES ('Информация о багаже', 'Не больше 10 кг, больше 10 кг доплата');


INSERT INTO users (permissions, chat_id, processing_booking_id, booking_state) VALUES (true, 1757613191, NULL, 'NONE');
INSERT INTO bookings (date, passengers_count, user_id, booking_status, from_place, passenger_name, phone_number, to_place) VALUES (NOW(), 2, 1, 'CREATED', 'Ufa', 'Almaz', '89999999999', 'Uchaly');