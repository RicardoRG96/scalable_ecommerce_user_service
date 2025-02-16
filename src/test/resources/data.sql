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

INSERT INTO addresses (user_id, title, address_line_1, address_line_2, country, city, commune, postal_code, landmark) 
    VALUES (1, 'Casa en Antofagasta', 'Avenida Argentina 123', 'Departamento 5A', 'Chile', 'Antofagasta', 'Antofagasta', '1240000', 'Cerca del Mall Plaza Antofagasta');

INSERT INTO addresses (user_id, title, address_line_1, address_line_2, country, city, commune, postal_code, landmark) 
    VALUES (1, 'Oficina en Rancagua', 'Calle Estado 456', 'Piso 3', 'Chile', 'Rancagua', 'Rancagua', '2820000', 'Frente a la Plaza de Los Héroes');

INSERT INTO addresses (user_id, title, address_line_1, address_line_2, country, city, commune, postal_code, landmark) 
    VALUES (2, 'Departamento en Puerto Montt', 'Avenida Diego Portales 789', 'Departamento 10B', 'Chile', 'Puerto Montt', 'Puerto Montt', '5480000', 'Cerca del Costanera Center');

INSERT INTO addresses (user_id, title, address_line_1, address_line_2, country, city, commune, postal_code, landmark) 
    VALUES (2, 'Casa en Iquique', 'Calle Thompson 1011', '', 'Chile', 'Iquique', 'Iquique', '1100000', 'Frente a la playa Cavancha');

INSERT INTO addresses (user_id, title, address_line_1, address_line_2, country, city, commune, postal_code, landmark) 
    VALUES (3, 'Casa en Chillán', 'Avenida O’Higgins 1213', '', 'Chile', 'Chillán', 'Chillán', '3780000', 'Cerca de la Plaza de Armas');

INSERT INTO addresses (user_id, title, address_line_1, address_line_2, country, city, commune, postal_code, landmark) 
    VALUES (3, 'Casa en Punta Arenas', 'Calle Magallanes 1415', '', 'Chile', 'Punta Arenas', 'Punta Arenas', '6200000', 'Frente al Estrecho de Magallanes');