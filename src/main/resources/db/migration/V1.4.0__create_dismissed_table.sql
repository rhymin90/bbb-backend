create table dimissed (
  id bigserial primary key,
  userId text not null,
  episode int,
  candidateId int
);

alter table dimissed add constraint fk_dismissed_user foreign key(userId) references users(email);