drop table localidad if exists;

create table localidad 
(id bigint generated by default as identity, 
 codigo_postal varchar(8), 
 nombre varchar(255), 
 provincia varchar(255), 
 primary key (id));
 
 
insert into localidad
values
(11809, '1021', 'LURO', 'Bs.As.' ),
(12104, '4051', 'CHASCOMUS', 'Bs.As.' ),
(12202, '3220', 'CHIVILCOY', 'Bs.As.' ),
(12507, '8921', 'PEHUEN-CO', 'Bs.As.' ),
(12702, '4728', 'CORONEL PRINGLES', 'Bs.As.' ),
(12803, '3290', 'CORONEL SUAREZ', 'Bs.As.' ),
(12909, '4187', 'MUNRO', 'Bs.As.' ),
(13013, '6611', 'SAN JUSTO', 'Bs.As.' ),
(13309, '1011', 'LOS CARDALES', 'Bs.As.' ),
(13503, '9990', 'MIRAMAR', 'Bs.As.' ),
(14003, '4070', 'ZARATE', 'Bs.As.' ),
(14307, '1001', 'MAR DE TUYU', 'Bs.As.' ),
(14410, '1004', 'VILLA GESELL', 'Bs.As.' );
	