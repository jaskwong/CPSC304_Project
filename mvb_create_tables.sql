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
