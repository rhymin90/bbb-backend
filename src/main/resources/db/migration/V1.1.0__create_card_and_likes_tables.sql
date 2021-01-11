create table cards (
  id bigserial primary key,
  content text not null,
  userId text not null,
  created bigint,
  deletable boolean default true
);

create table likes (
  id bigserial primary key,
  cardId bigint,
  userId text not null,
  constraint fk_card foreign key(cardId) references cards(id)
);

