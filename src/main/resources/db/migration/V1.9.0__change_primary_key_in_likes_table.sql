alter table likes drop constraint if exists likes_pkey;
alter table likes drop constraint if exists likes_unique;
alter table likes drop column id;
alter table likes add primary key (cardId, userId);