-- BD Postgres postgres:16.2
--OK
create table public.empresa(
    id serial not null,
    nit varchar(100) not null,
    razon_social varchar(100) not null,
    correo_electronico varchar(100) not null,
    fecha_registro timestamp without time zone not null,
    CONSTRAINT PK_EMPRESA primary key(id)
);
--OK
-- drop table public.tercero;
create table public.tercero(
    id serial not null,
    document_number varchar(100) not null,
	fullname varchar(200) not null,
    telephone_number varchar(100) not null,
	address_ varchar(100) not null,
	email varchar(100) not null,
	t_state varchar(100) not null,
    CONSTRAINT PK_TERCERO primary key(id)
);
--OK

--drop table public.service;
create table public.service(
    id serial not null,
    code varchar(50) unique,
	title varchar(100) not null,
    description varchar(250),
    duration_in_minute integer not null,
    service_concurrency integer not null,
    state bool not null default true,
    CONSTRAINT PK_SERVICE primary key(id)
);
-- Migracion para bases existentes:
-- alter table public.service add column code varchar(50) unique;
-- alter table public.service add column state boolean not null default true;

--drop table public.service_instance;
create table public.service_instance(
    id serial not null,       
    service_id int not null,
    employee_id int,
    state boolean not null,
    CONSTRAINT PK_SERVICE_INSTANCE primary key(id),
    CONSTRAINT fk_service_instance
        FOREIGN KEY(service_id) 
        REFERENCES public.service(id)
);

--drop table public.employee;
create table public.employee(
    id serial not null,       
    full_name varchar(250) not null,
    state boolean not null,
    CONSTRAINT PK_employee_INSTANCE primary key(id)
);



drop table public.appointment;
create table public.appointment(
    id serial not null,       
    service_instance int not null,
	customer_id int,
	created_date timestamp without time zone not null,
	appointment_date date not null,
	appointment_time time without time zone not null,
	updated_date timestamp without time zone not null,
    status varchar(10) not null,
    CONSTRAINT PK_appointment primary key(id)
);

INSERT INTO public.service
(id, description, duration_in_minute, service_concurrency)
values
(nextval('service_id_seq'::regclass), 'Servicio 1', 30, 0),
(nextval('service_id_seq'::regclass), 'Servicio 2', 15, 0);

INSERT INTO public.service
(id, description, duration_in_minute, service_concurrency)
values
(nextval('service_id_seq'::regclass), 'Servicio 3', 20, 0);

drop view view_appointment;
create or replace view view_appointment as (
select 
a.id,
a.created_date,
a.appointment_date,
a.appointment_time,
a.service_instance,
ss.id as service_id,
ss.title as description, 
t.id as customer_id,
t.fullname as customer_name,
t.telephone_number as customer_telephone,
t.email as customer_email,
a.status,
e.full_name as employee_name 
from appointment a
join service_instance si on si.id = a.service_instance 
join service ss on ss.id = si.service_id
left join tercero t on t.id = a.customer_id
left join employee e on e.id = a.customer_id 
order by a.appointment_date, a.appointment_time 
);

create or replace view view_service_instance as (
select si.id,e.full_name,si.state from service_instance si
left join employee e on si.employee_id = e.id
);

select * from tercero;