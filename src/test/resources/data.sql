INSERT INTO users (username, email, password, enabled) 
    VALUES ('alejandro', 'alejandro@gmail.com', 'alejandro12345', true),
    ('ester', 'ester@gmail.com', 'ester12345', true),
    ('pepe', 'pepe@gmail.com', 'pepe12345', true);

INSERT INTO roles (name) 
    VALUES ('ROLE_ADMIN'),
    ('ROLE_USER'),
    ('ROLE_SELLER'),
    ('ROLE_GUEST');