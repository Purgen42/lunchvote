INSERT INTO USERS (name, email, password)
VALUES ('User', 'user@yandex.ru', '{noop}password'),
       ('Admin', 'admin@gmail.com', '{noop}admin'),
       ('User2', 'user2@yandex.ru', '{noop}password'),
       ('User3', 'user3@yandex.ru', '{noop}password');

INSERT INTO USER_ROLES (role, user_id)
VALUES ('USER', 1),
       ('ADMIN', 2),
       ('USER', 2),
       ('USER', 3),
       ('USER', 4);

INSERT INTO RESTAURANTS (name)
VALUES ('Restaurant 1'),
       ('Restaurant 2'),
       ('Restaurant 3');

INSERT INTO VOTES (date, restaurant_id, user_id)
VALUES (CAST( NOW() AS Date ), 1, 2),
       ('2021-12-31', 2, 2),
       (CAST( NOW() AS Date ), 2, 3),
       (CAST( NOW() AS Date ), 2, 4);

INSERT INTO DISHES (description, price, restaurant_id)
VALUES ('Первое', 35.35, 1),
       ('Второе', 70, 1),
       ('Компот', 35.35, 1);
