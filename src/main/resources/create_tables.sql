CREATE TABLE patient (
    id BIGINT auto_increment,
    name VARCHAR(50) not null,
    birth DATE not null,
    primary key (id)
);

insert into patient (id, name, birth) values (1, 'Алексей', '1971-04-16');
insert into patient (id, name, birth) values (2, 'Ольга', '1971-04-16');
insert into patient (id, name, birth) values (3, 'Сергей', '1976-09-13');
insert into patient (id, name, birth) values (4, 'Сева', '2015-02-15');

CREATE TABLE pressure (
    id BIGINT auto_increment,
    patient_id BIGINT,
    sys INT,
    dia INT,
    pulse INT,
    dtm TIMESTAMP,
    primary key (id)
);

