drop table vehicletypes cascade constraints;
drop table vehicles cascade constraints;
drop table reservations cascade constraints;
drop table customers cascade constraints;
drop table rentals cascade constraints;
drop table rets cascade constraints;

create table vehicletypes (
	vt_name varchar(20) not null PRIMARY KEY,
	vt_features varchar(250),
	vt_wrate numeric(5,2),
	vt_drate numeric(5,2),
	vt_hrate numeric(5,2),
	vt_wirate numeric(5,2),
	vt_dirate numeric(5,2),
	vt_hirate numeric(5,2),
	vt_krate numeric(5,2)
);

create table vehicles (
	v_license varchar(7) not null PRIMARY KEY,
	v_make varchar(20),
	v_model varchar(20),
	v_year int,
	v_color CHAR(20),
	v_odometer int,
	v_status CHAR(1),
	vt_name varchar(20),
	v_location varchar(50),
	v_city varchar(50),
	foreign key (vt_name) references vehicletypes
);

create table customers (
    customer_cellphone integer,
    customer_name varchar(20),
    customer_address varchar(50),
    customer_dlicense integer not null PRIMARY KEY
);

create table reservations (
    reservations_confNo integer not null PRIMARY KEY,
    vt_name varchar(20) not null,
    customer_dlicense integer not null,
    reservations_from timestamp,
    reservations_to timestamp,
    foreign key (customer_dlicense) references customers,
	foreign key (vt_name) references vehicletypes
);

create table rentals (
    rentals_rid integer not null PRIMARY KEY,
    v_license varchar(7) not null,
    customer_dlicense integer not null,
    rentals_from timestamp,
    rentals_to timestamp,
    rentals_odometer integer,
    rentals_cardName varchar(20),
    rentals_cardNo integer,
    rentals_ExpDate timestamp,
    reservations_confNo integer,
    foreign key (v_license) references vehicles,
    foreign key (customer_dlicense) references customers,
    foreign key (reservations_confNo) references reservations
);

create table rets (
    rentals_rid int PRIMARY KEY,
	rets_datetime timestamp,
	rets_odometer int,
	rets_fulltank int,
	rets_value numeric(7,2),
	foreign key (rentals_rid) references rentals
);


insert into vehicletypes values('Sedan', 'a passenger car in a three-box configuration with separate compartments for engine, passenger, and cargo', 100.54, 40.32, 12.54, 70.64, 9.35, 1.68, 1.50);
insert into vehicletypes values('SUV','a category of motor vehicles that combine elements of road-going passenger cars with features from off-road vehicles',200.75, 70.63, 20.45, 150.23, 19.53, 3.39, 1.00);
insert into vehicletypes values('Convertible','a passenger car that can be driven with or without a roof in place', 150.23, 60.23, 15.28, 105.11, 15.34, 2.22, 0.99);
insert into vehicles values('28491', 'Honda', 'middle', 2018, 'green', 10000, 'R', 'SUV', 'West', 'Vancouver');
insert into vehicles values('28345', 'Benz', 'middle', 2018, 'red', 10000, 'A', 'SUV', 'West', 'Vancouver');
insert into vehicles values('28235', 'Honda', 'small', 2018, 'green', 10000, 'R', 'Sedan', 'West', 'Vancouver');
insert into vehicles values('28623', 'BMW', 'small', 2018, 'white', 10000, 'M', 'Sedan', 'West', 'Vancouver');
insert into vehicles values('28556', 'Volkswagen', 'small', 2018, 'green', 10000, 'A', 'Convertible', 'West', 'Vancouver');
insert into vehicles values('28211', 'Chevrolet', 'small', 2018, 'black', 10000, 'R', 'Convertible', 'West', 'Vancouver');
insert into vehicles values('28111', 'Honda', 'middle', 2018, 'green', 10000, 'M', 'SUV', 'West', 'Vancouver');
insert into vehicles values('28222', 'Benz', 'middle', 2018, 'red', 10000, 'R', 'SUV', 'West', 'Vancouver');
insert into vehicles values('28333', 'BMW', 'small', 2018, 'black', 10000, 'R', 'Sedan', 'West', 'Vancouver');
insert into vehicles values('28444', 'BMW', 'small', 2018, 'red', 10000, 'R', 'Sedan', 'West', 'Vancouver');
insert into vehicles values('28555', 'Honda', 'middle', 2018, 'white', 10000, 'A', 'Convertible', 'West', 'Vancouver');
insert into vehicles values('22222', 'Benz', 'middle', 2018, 'green', 10000, 'A', 'Convertible', 'West', 'Vancouver');
insert into vehicles values('21111', 'Chevrolet', 'large', 2018, 'green', 10000, 'R', 'SUV', 'West', 'Vancouver');
insert into vehicles values('23333', 'Volkswagen', 'large', 2018, 'red', 10000, 'M', 'SUV', 'West', 'Vancouver');
insert into vehicles values('24444', 'Chevrolet', 'middle', 2018, 'white', 10000, 'M', 'Sedan', 'West', 'Vancouver');
insert into vehicles values('11111', 'Vlokswagen', 'middle', 2018, 'white', 10000, 'M', 'Sedan', 'West', 'Vancouver');
insert into vehicles values('22223', 'Chevrolet', 'middle', 2018, 'black', 10000, 'A', 'Convertible', 'West', 'Vancouver');
insert into vehicles values('24122', 'Benz', 'middle', 2016, 'green', 10000, 'A', 'Convertible', 'West', 'Vancouver');
insert into vehicles values('90231', 'Chevrolet', 'large', 2018, 'green', 29000, 'R', 'SUV', 'East', 'Vancouver');
insert into vehicles values('78311', 'Volkswagen', 'large', 2018, 'red', 48000, 'M', 'SUV', 'West', 'Vancouver');
insert into vehicles values('24411', 'Chevrolet', 'middle', 2008, 'white', 20000, 'M', 'Sedan', 'East', 'Vancouver');
insert into vehicles values('11040', 'Vlokswagen', 'middle', 2011, 'white', 30000, 'M', 'Sedan', 'West', 'Vancouver');
insert into vehicles values('22895', 'Chevrolet', 'middle', 2014, 'black', 40000, 'A', 'Convertible', 'East', 'Vancouver');
insert into vehicles values('99999', 'Benz', 'middle', 2013, 'green', 50000, 'A', 'Convertible', 'West', 'Vancouver');
insert into customers values(3748399876, 'Jackson', '3857 qwe st.', 22291782);
insert into customers values(7483987534, 'Tom', '6482 ert st.', 82392872);
insert into customers values(7483747384, 'Jerry', '4356 rty st.', 84920493);
insert into customers values(7563546223, 'Kevin', '6475 uty st.', 90865432);
insert into customers values(3578394574, 'Leo', '7563 yrt st.', 67299832);
insert into customers values(1232787583, 'Kate', '7462 erw st.', 93377278);
insert into reservations values(67829387, 'SUV', 22291782, TIMESTAMP '2019-01-01 09:00:00', TIMESTAMP '2019-02-01 09:00:00');
insert into reservations values(92830192, 'SUV', 82392872, TIMESTAMP '2019-01-01 08:00:00', TIMESTAMP '2019-02-01 08:00:00');
insert into reservations values(74829203, 'Sedan', 84920493, TIMESTAMP '2019-02-03 09:30:00', TIMESTAMP '2019-09-01 10:00:00');
insert into reservations values(83837652, 'Sedan', 22291782, TIMESTAMP '2019-05-06 11:30:00', TIMESTAMP '2019-08-12 11:00:00');
insert into reservations values(74739821, 'Convertible', 90865432, TIMESTAMP '2019-04-02 11:22:00', TIMESTAMP '2019-04-30 09:22:00');
insert into reservations values(64372832, 'Convertible', 67299832, TIMESTAMP '2019-02-03 10:00:00', TIMESTAMP '2019-04-05 11:20:00');
insert into reservations values(88888888, 'Convertible', 93377278, TIMESTAMP '2019-04-02 11:33:00', TIMESTAMP '2019-04-20 09:22:00');
insert into rentals values(74839203, '28491', 22291782, TIMESTAMP '2019-01-01 09:00:00', TIMESTAMP '2019-02-01 09:00:00', 50020, 'Jackson', 28398754, DATE '2020-1-1', 67829387);
insert into rentals values(59330234, '28222', 82392872, TIMESTAMP '2019-01-01 08:00:00', TIMESTAMP '2019-02-01 08:00:00', 98393, 'Tom', 64738274, DATE '2020-2-3', 92830192);
insert into rentals values(47289183, '28333', 84920493, TIMESTAMP '2019-02-03 09:30:00', TIMESTAMP '2019-09-01 10:00:00', 84593, 'Jerry', 65837483,DATE '2020-03-04', 74829203);
insert into rentals values(94839832, '28444', 22291782, TIMESTAMP '2019-05-06 11:30:00', TIMESTAMP '2019-08-12 11:00:00', 94855, 'Jackson', 28308973, DATE '2020-01-01', 83837652);
insert into rentals values(14847723, '28235', 90865432, TIMESTAMP '2019-04-02 11:22:00', TIMESTAMP '2019-04-30 09:22:00', 49384, 'Kevin', 63849222, DATE '2020-03-04', 74739821);
insert into rentals values(12345678, '22895', 93377278, TIMESTAMP '2019-04-02 11:33:00', TIMESTAMP '2019-04-20 09:22:00', 49084, 'Kate', 34251124, DATE '2020-12-04', 88888888);
insert into rets values(12345678, TIMESTAMP '2019-04-20 09:22:00', 74728, 1, 746.33);
insert into rets values(59330234, TIMESTAMP '2019-02-01 08:00:00', 98393, 1, 345.33);
insert into rets values(47289183, TIMESTAMP '2019-09-01 10:00:00', 84593, 1, 238.44);
insert into rets values(94839832, TIMESTAMP '2019-08-12 11:00:00', 94855, 1, 873.44);
insert into rets values(14847723, TIMESTAMP '2019-04-30 09:22:00', 49384, 1, 758.33);
commit;