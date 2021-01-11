create table users (
  email text primary key,
  username text not null,
  profile text not null,
  finalist bigint,
  winner bigint,
  score int default 0,
  tvNow boolean default false
);
