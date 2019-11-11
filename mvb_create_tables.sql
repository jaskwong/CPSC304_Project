create table vehicletype (
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

CREATE DOMAIN stat AS CHAR
CHECK(
   VALUE ~ 'AVAILABLE'
OR VALUE ~ 'MAINTENANCE'
OR VALUE ~ 'RENTED'
);

create table vehicle ( 
	v_id: varchar(5) not null,
	v_license: varchar(7),
	v_make: varchar(20),
	v_model varchar(20),
	v_year int,
	v_color CHAR(20),
	v_odometer int,
	v_status stat,
	FOREIGN KEY vt_name varchar(20) references vehicletype,
	v_location varchar,
	v_city varchar
);

create table ret ( 
	FOREIGN KEY rent_rid int references rent,
	ret_date date,
	ret_time time,
	ret_odometer int,
	ret_fulltank int not null
		constraint ret_fulltank check (active in (0, 1)),
	ret_value numeric(5,2)
);
                                   
create table reservation (
    reservation_confNo integer not null PRIMARY KEY,
    vehicletype_vtname varchar(20) not null,
    customer_cellphone integer not null,
    reservation_fromDate date,
    reservation_fromTime time,
    reservation_toDate date,
    reservation_toTime time,
    foreign key (vt_name) references vehicletype,
    foreign key (customer_cellphone) references customer,
);

create table rent (
    rent_rid integer not null PRIMARY KEY,
    v_vid varchar(5) not null,
    customer_cellphone integer not null,
    rent_fromDate date,
    rent_fromTime time,
    rent_toDate date,
    rent_toTime time,
    rent_odometer integer,
    rent_cardName varchar(20),
    rent_cardNo integer not,
    rent_ExpDate date,
    reservation_confNo integer,
    foreign key (v_id) references vehicle,
    foreign key (customer_cellphone) references customer,
    foreign key (reservation_confNo) references reservation,
);

create table customer (
    customer_cellphone integer not null PRIMARY KEY,
    customer_name varchar(20),
    customer_address varchar(50),
    customer_dlicense integer,
);

commit;
