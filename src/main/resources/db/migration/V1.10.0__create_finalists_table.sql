create table finalists (
  userId text primary key,
  winner int,
  second int,
  constraint fk_finalists_userId foreign key(userId) references users(email)
);

