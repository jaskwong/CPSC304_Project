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

commit;
