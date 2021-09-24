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

Inside application.properties
you can change time.type(the average time type to record coin values. Recorded number of values are calculated as "time.avg / time.period.refresh")

#Database Init parameters will be added

#frontend will be configured
