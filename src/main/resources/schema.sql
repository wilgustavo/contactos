
create table contacto 
(id bigint generated by default as identity, 
 nombre varchar(100),
 nacimiento date,
 email varchar(120),
 telefono varchar(20),
 direccion varchar(255),
 localidad bigint,
 primary key (id)) ; 

create table localidad 
(id bigint generated by default as identity, 
 codigo_postal varchar(8), 
 nombre varchar(255), 
 provincia varchar(255), 
 primary key (id));
 