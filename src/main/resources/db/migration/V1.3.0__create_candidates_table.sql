create table candidates (
  id bigserial primary key,
  firstName text not null,
  lastName text not null,
  age int not null,
  location text not null,
  job text not null,
  info text not null,
  episode int,
  image text not null
);

insert into candidates (id, firstName, lastName, age, location, job, info, episode, image)
 values (0, '', '', 0, '', '', '', -1, '');

alter table users add constraint fk_winner foreign key(winner) references candidates(id);
alter table users add constraint fk_finalist foreign key(finalist) references candidates(id);