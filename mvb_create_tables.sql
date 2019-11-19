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
	v_id varchar(5) not null PRIMARY KEY,
	v_license varchar(7),
	v_make varchar(20),
	v_model varchar(20),
	v_year int,
	v_color CHAR(20),
	v_odometer int,
	v_status int,
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
    foreign key (vt_name) references vehicletype,
    foreign key (customer_dlicense) references customers
);

create table rentals (
    rentals_rid integer not null PRIMARY KEY,
    v_id varchar(5) not null,
    customer_dlicense integer not null,
    rentals_from timestamp,
    rentals_to timestamp,
    rentals_odometer integer,
    rentals_cardName varchar(20),
    rentals_cardNo integer,
    rentals_ExpDate date,
    reservations_confNo integer,
    foreign key (v_id) references vehicles,
    foreign key (customer_dlicense) references customers,
    foreign key (reservations_confNo) references reservations
);

create table rets ( 
    rentals_rid int PRIMARY KEY,
	rets_datetime timestamp,
	rets_odometer int,
	rets_fulltank int,
	rets_value numeric(5,2),
	foreign key (rentals_rid) references rentals
);


insert into vehicletypes values('Sedan', 'a passenger car in a three-box configuration with separate compartments for engine, passenger, and cargo', 123.54, 346.32, 221.54, 234.64, 236.35,853.68, 560.57);
insert into vehicletypes values('SUV','a category of motor vehicles that combine elements of road-going passenger cars with features from off-road vehicles',234.75, 344.63, 213.45, 456.23, 423.53, 394.39, 812.34);
insert into vehicletypes values('Convertible','a passenger car that can be driven with or without a roof in place', 123.23, 901.23, 561.28, 223.11, 342.34, 123.22, 345.23);
insert into vehicles values('098', '28491', 'Honda', 'middle', 2018, 'green', 10000, 2000, 'SUV', 'West', 'Vancouver');
insert into vehicles values('099', '28345', 'Benz', 'middle', 2018, 'red', 10000, 2000, 'SUV', 'West', 'Vancouver');
insert into vehicles values('100', '28235', 'Honda', 'small', 2018, 'green', 10000, 2000, 'Sedan', 'West', 'Vancouver');
insert into vehicles values('101', '28623', 'BMW', 'small', 2018, 'white', 10000, 2000, 'Sedan', 'West', 'Vancouver');
insert into vehicles values('234', '28556', 'Volkswagen', 'small', 2018, 'green', 10000, 2000, 'Convertible', 'West', 'Vancouver');
insert into vehicles values('573', '28211', 'Chevrolet', 'small', 2018, 'black', 10000, 2000, 'Convertible', 'West', 'Vancouver');
insert into vehicles values('224', '28111', 'Honda', 'middle', 2018, 'green', 10000, 2000, 'SUV', 'West', 'Vancouver');
insert into vehicles values('236', '28222', 'Benz', 'middle', 2018, 'red', 10000, 2000, 'SUV', 'West', 'Vancouver');
insert into vehicles values('678', '28333', 'BMW', 'small', 2018, 'black', 10000, 2000, 'Sedan', 'West', 'Vancouver');
insert into vehicles values('993', '28444', 'BMW', 'small', 2018, 'red', 10000, 2000, 'Sedan', 'West', 'Vancouver');
insert into vehicles values('123', '28555', 'Honda', 'middle', 2018, 'white', 10000, 2000, 'Convertible', 'West', 'Vancouver');
insert into vehicles values('596', '22222', 'Benz', 'middle', 2018, 'green', 10000, 2000, 'Convertible', 'West', 'Vancouver');
insert into vehicles values('444', '21111', 'Chevrolet', 'large', 2018, 'green', 10000, 2000, 'SUV', 'West', 'Vancouver');
insert into vehicles values('888', '23333', 'Volkswagen', 'large', 2018, 'red', 10000, 2000, 'SUV', 'West', 'Vancouver');
insert into vehicles values('453', '24444', 'Chevrolet', 'middle', 2018, 'white', 10000, 2000, 'Sedan', 'West', 'Vancouver');
insert into vehicles values('988', '11111', 'Vlokswagen', 'middle', 2018, 'white', 10000, 2000, 'Sedan', 'West', 'Vancouver');
insert into vehicles values('667', '22223', 'Chevrolet', 'middle', 2018, 'black', 10000, 2000, 'Convertible', 'West', 'Vancouver');
insert into vehicles values('662', '24122', 'Benz', 'middle', 2018, 'green', 10000, 2000, 'Convertible', 'West', 'Vancouver');
insert into customers values(3748399876, 'Jackson', '3857 qwe st.', 22291782);
insert into customers values(7483987534, 'Tom', '6482 ert st.', 82392872);
insert into customers values(7483747384, 'Jerry', '4356 rty st.', 84920493);
insert into customers values(7563546223, 'Kevin', '6475 uty st.', 90865432);
insert into customers values(3578394574, 'Leo', '7563 yrt st.', 67299832);
insert into reservations values(67829387, 'SUV', 22291782, to_timestamp('2016-05-24 17:09:54', 'YYYY-MM-DD HH24:MI:SS'), to_timestamp('2016-05-24 17:09:54', 'YYYY-MM-DD HH24:MI:SS'));
--insert into reservations values(92830192, 'SUV', 82392872, 2019-1-1 08:00:00, 2019-2-1 08:00:00);
--insert into reservations values(74829203, 'Sedan', 84920493, 2019-2-3 09:30:00, 2019-9-1 10:00:00);
--insert into reservations values(83837652, 'Sedan', 22291782, 2019-5-6 11:30:00, 2019-8-12 11:00:00);
--insert into reservations values(74739821, 'Convertible', 90865432, 2019-4-2 11:22:00, 2019-4-30 09:22:00);
--insert into reservations values(64372832, 'Convertible', 67299832, 2019-2-3 10:00:00, 2019-4-5 11:20:00);
--insert into rentals values(74839203, '098', 22291782, 2019-1-1 09:00:00, 2019-2-1 09:00:00, 50020, 'Jackson', 2839875487208973, 2020-1-1, 67829387);
--insert into rentals values(59330234, '099', 82392872, 2019-1-1 08:00:00, 2019-2-1 08:00:00, 98393, 'Tom', 6473827493875364, 2020-2-3, 92830192);
--insert into rentals values(47289183, '100', 84920493, 2019-2-3 09:30:00, 2019-9-1 10:00:00, 84593, 'Jerry', 6583748590284533,2020-3-4, 74829203);
--insert into rentals values(94839832, '101', 22291782, 2019-5-6 11:30:00, 2019-8-12 11:00:00, 94855, 'Jackson', 2839875487208973, 2020-1-1, 83837652);
--insert into rentals values(14847723, '234', 90865432, 2019-4-2 11:22:00, 2019-4-30 09:22:00, 49384, 'Kevin', 7483927463849222, 2020-3-4, 74739821);
--insert into rentals values(94728475, '573', 67299832, 2019-2-3 10:00:00, 2019-4-5 11:20:00, 34623, 'Leo', 3474839384733842, 2021-2-1, 64372832);
--insert into rets values(74839203, 2019-2-1 09:00:00, 10020, 23, 239.24);
--insert into rets values(59330234, 2019-2-1 08:00:00, 98393, 45, 345.33);
--insert into rets values(47289183, 2019-9-1 10:00:00, 84593, 34, 238.44);
--insert into rets values(94839832, 2019-8-12 11:00:00, 94855, 55, 873.44);
--insert into rets values(14847723, 2019-4-30 09:22:00, 49384, 77, 758.33);
--insert into rets values(94728475, 2019-4-5 11:20:00, 34623, 88, 839.33);
commit;