create table bingos (
  id bigserial primary key,
  userId text not null,
  episode bigint,
  cards text not null
);

