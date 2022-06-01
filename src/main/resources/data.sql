insert into user(id, firstname, lastname, email, password, is_enabled) VALUES
    (1,'Jan','Kowalski', 'admin','{bcrypt}$2a$10$0ccVt5R4P3SDbd0NeHYZb.MMilIfFwbnayBA62Cp1cvQM5Qa8KWpG',true),
    (2,'Dariusz','Nowak', 'daro@gmail.com','{bcrypt}$2a$10$MEbXa1J5uNOQBZngTJ4RS.lSn6oJCzSTRSiBODhDetkeFW4WisDLa',true),
    (3,'Michał','Janikowski', 'janik22@gmail.com','{bcrypt}$2a$10$MEbXa1J5uNOQBZngTJ4RS.lSn6oJCzSTRSiBODhDetkeFW4WisDLa',true),
    (4,'Piotr','Zebrzydowski', 'pioteroo@gmail.com','{bcrypt}$2a$10$MEbXa1J5uNOQBZngTJ4RS.lSn6oJCzSTRSiBODhDetkeFW4WisDLa',true),
    (5,'Jan','Nowy', 'new@gmail.com','{bcrypt}$2a$10$MEbXa1J5uNOQBZngTJ4RS.lSn6oJCzSTRSiBODhDetkeFW4WisDLa',true),
    (6,'Julia','Dobrowolska', 'user','{bcrypt}$2a$10$lKtkZm2kNUkSOfgX7NukmulEbSkEoeZTAnadrPeZPpLmQapG6wupi',true);

insert into user_role(role, description) VALUES ('ROLE_USER', 'default role for user');
insert into user_role(role, description) VALUES ('ROLE_ADMIN', 'admin role');
insert into user_roles(user_id, roles_id) VALUES ('1', '2');
insert into user_roles(user_id, roles_id) VALUES ('6', '1');