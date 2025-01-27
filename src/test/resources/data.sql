INSERT INTO users (avatar, first_name, last_name, username, email, password, birth_date, phone_number, enabled) VALUES 
    ('avatar1.png', 'alejandro', 'retamal', 'alejandro10', 'alejandro@gmail.com', 'alejandro12345', '1996-10-04', '+56952419637', true),
    ('avatar2.png', 'ester', 'guevara', 'ester17', 'ester@gmail.com', 'ester12345', '1994-01-17', '+56932189745', true),
    ('avatar3.png', 'pepe', 'pepon', 'pepe58', 'pepe@gmail.com', 'pepe12345', '1992-12-26', '+56931258759', true);

INSERT INTO roles (name)  VALUES 
    ('ROLE_ADMIN'),
    ('ROLE_USER'),
    ('ROLE_SELLER'),
    ('ROLE_GUEST');

INSERT INTO users_roles (user_id, role_id) VALUES
    (1, 2),
    (2, 2),
    (3, 2);