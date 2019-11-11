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

create table reservations (
    reservations_confNo integer not null PRIMARY KEY,
    vt_name varchar(20) not null,
    customer_dlicense integer not null,
    reservations_fromDate date,
    reservations_fromTime time,
    reservations_toDate date,
    reservations_toTime time,
    foreign key (vt_name) references vehicletype,
    foreign key (customer_dlicense) references customers
);

create table rentals (
    rentals_rid integer not null PRIMARY KEY,
    v_id varchar(5) not null,
    customer_dlicense integer not null,
    rentals_fromDate date,
    rentals_fromTime time,
    rentals_toDate date,
    rentals_toTime time,
    rentals_odometer integer,
    rentals_cardName varchar(20),
    rentals_cardNo integer,
    rentals_ExpDate date,
    reservations_confNo integer,
    foreign key (v_id) references vehicles,
    foreign key (customer_dlicense) references customers,
    foreign key (reservations_confNo) references reservations
);


create table vehicles ( 
	v_id varchar(5) not null PRIMARY KEY,
	v_license varchar(7),
	v_make varchar(20),
	v_model varchar(20),
	v_year int,
	v_color CHAR(20),
	v_odometer int,
	v_status int not null
		constraint rets_fulltank check (active in (0, 1, 2)),
	FOREIGN KEY vt_name varchar(20) references vehicletypes,
	v_location varchar,
	v_city varchar
);


create table customers (
    customer_cellphone integer,
    customer_name varchar(20),
    customer_address varchar(50),
    customer_dlicense integer not null PRIMARY KEY
);

create table rets ( 
    rentals_rid int PRIMARY KEY,
	rets_date date,
	rets_time time,
	rets_odometer int,
	rets_fulltank int not null
		constraint rets_fulltank check (active in (0, 1)),
	rets_value numeric(5,2),
	foreign key (rentals_rid) references rentals
);

commit;
