# RestTemplate



Database should be created as :

create table coininfo (
    coin_id bigserial primary key,
    title varchar(128) not null,
    symbol varchar(128) not null,
    price double precision not null,
    average double precision,
    time time
);

