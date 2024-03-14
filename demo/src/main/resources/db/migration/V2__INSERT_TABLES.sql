INSERT INTO TIPOS_PROGRAMADOR (ID, NOMBRE) VALUES (1, 'FRONTEND'), (2, 'BACKEND'), (3, 'FULLSTACK');

INSERT INTO ROLES (ID, NOMBRE) VALUES (1, 'ADMIN'), (2, 'EMPLEADO'), (3, 'AMIGO');

INSERT INTO EMPLEADOS (ID, NOMBRE, EMAIL, PASSWORD, ID_TIPO_PROGRAMADOR, FECHA_NACIMIENTO, FECHA_CREACION, FECHA_ACTUALIZACION, ID_ROL) VALUES
(1, 'admin','admin@admin.com','$2a$10$ZQw6dmgnzHvp8vdFv3Nt7u8gVK5aVnOXiA.mnzNj3/hj3upqql.gK',1,'1993-04-13','2024-03-11 20:37:14.427','2024-03-11 20:37:14.428',1),
(2, 'empleado','empleado@empleado.com','$2a$10$ZQw6dmgnzHvp8vdFv3Nt7u8gVK5aVnOXiA.mnzNj3/hj3upqql.gK',3,'1993-04-13','2024-03-11 20:37:14.427','2024-03-11 20:37:14.428',2),
(3, 'amigo','amigo@amigo.com','$2a$10$ZQw6dmgnzHvp8vdFv3Nt7u8gVK5aVnOXiA.mnzNj3/hj3upqql.gK',1,'1993-04-13','2024-03-11 20:37:14.427','2024-03-11 20:37:14.428',3);


INSERT INTO PELICULAS_SERIES (ID, TITULO, ANIO, DIRECTOR, GENERO, TEMPORADAS, DURACION, ID_EMPLEADO_PROPOSICION,
 ID_EMPLEADO_IMPLANTACION, NOTA_MEDIA) VALUES
(1, 'Pulp Fiction', 1994, 'Quentin Tarantino', 'Drama', NULL, '02:34:00', 3, 3, 8.5),
(2, 'Interstellar', 2014, 'Christopher Nolan', 'Ciencia Ficción', NULL, '02:49:00', 2, 2, 9.2),
(3,'The Irishman', 2019, 'Martin Scorsese', 'Crimen', NULL, '03:29:00', 1, 2, 8.9),
(4,'Gone Girl', 2014, 'David Fincher', 'Suspense', NULL, '02:29:00', 1, 3, 8.7),
(5,'Parasite', 2019, 'Bong Joon-ho', 'Drama', NULL, '02:12:00', 3, 1, 8.8),
(6,'Get Out', 2017, 'Jordan Peele', 'Terror', NULL, '01:44:00', 2, 2, 8.3),
(7,'Lady Bird', 2017, 'Greta Gerwig', 'Drama', NULL, '01:34:00', 1, 1, 8.6),
(8,'Stranger Things', 2016, 'The Duffer Brothers', 'Ciencia Ficción', 4, NULL, 1, 2, 8.8),
(9,'Breaking Bad', 2008, 'Vince Gilligan', 'Drama', 3, NULL, 3, 1, 9.5),
(10,'Black Mirror', 2011, 'Charlie Brooker', 'Ciencia Ficción', 5, NULL, 3, 3, 8.9);

SELECT setval('cleverpy_test.empleados_id_seq', (SELECT MAX(id) FROM empleados e));
SELECT setval('cleverpy_test.peliculas_series_id_seq', (SELECT MAX(id) FROM peliculas_series e));
SELECT setval('cleverpy_test.roles_id_seq', (SELECT MAX(id) FROM roles e));
SELECT setval('cleverpy_test.tipos_programador_id_seq', (SELECT MAX(id) FROM tipos_programador e));







