create table destinatii
(
    id_destinatie serial
        primary key,
    tara          varchar(50) not null,
    oras          varchar(50) not null,
    climat        varchar(20)
);

alter table destinatii
    owner to postgres;

create table hoteluri
(
    id_hotel      serial
        primary key,
    nume          varchar(100) not null,
    stele         integer
        constraint hoteluri_stele_check
            check ((stele >= 1) AND (stele <= 5)),
    adresa        varchar(200),
    id_destinatie integer
        references destinatii
);

alter table hoteluri
    owner to postgres;

create table ghizi
(
    id_ghid       serial
        primary key,
    nume          varchar(100) not null,
    telefon       varchar(15),
    limba_vorbita varchar(30)
);

alter table ghizi
    owner to postgres;

create table pachete_turistice
(
    id_pachet    serial
        primary key,
    nume_pachet  varchar(100)   not null,
    tip_pachet   varchar(30),
    data_inceput date           not null,
    data_sfarsit date           not null,
    pret_baza    numeric(10, 2) not null,
    nr_locuri    integer default 20,
    id_hotel     integer
        references hoteluri
);

alter table pachete_turistice
    owner to postgres;

create table pachete_ghizi
(
    id_pachet integer not null
        references pachete_turistice,
    id_ghid   integer not null
        references ghizi,
    primary key (id_pachet, id_ghid)
);

alter table pachete_ghizi
    owner to postgres;

create table clienti
(
    id_client serial
        primary key,
    nume      varchar(50) not null,
    prenume   varchar(50) not null,
    email     varchar(100)
        unique,
    cnp       varchar(13)
        unique,
    telefon   varchar(15),
    parola    varchar(100) default '1234'::character varying,
    rol       varchar(20)  default 'CLIENT'::character varying
);

alter table clienti
    owner to postgres;

create table rezervari
(
    id_rezervare   serial
        primary key,
    data_rezervare date        default CURRENT_DATE,
    status         varchar(20) default 'CONFIRMATA'::character varying,
    nr_persoane    integer     default 1,
    id_client      integer
        references clienti,
    id_pachet      integer
        references pachete_turistice
);

alter table rezervari
    owner to postgres;

create table facturi
(
    id_factura    serial
        primary key,
    serie_factura varchar(10)    not null,
    suma_totala   numeric(10, 2) not null,
    data_emitere  date default CURRENT_DATE,
    id_rezervare  integer
        unique
        references rezervari
);

alter table facturi
    owner to postgres;

