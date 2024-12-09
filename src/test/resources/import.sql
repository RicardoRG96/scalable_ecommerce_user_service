INSERT INTO users (username, email, password, enabled) 
    VALUES ('ricardo', 'ricardo@gmail.com', 'ricardo12345', true),
    ('mateo', 'mateo@gmail.com', 'mateo12345', true),
    ('carla', 'carla@gmail.com', 'carla12345', true);

INSERT INTO roles (name) 
    VALUES ('ROLE_ADMIN'),
    ('ROLE_USER'),
    ('ROLE_SELLER'),
    ('ROLE_GUEST');