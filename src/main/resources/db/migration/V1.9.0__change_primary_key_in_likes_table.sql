alter TABLE likes drop CONSTRAINT likes_pkey;
alter TABLE likes drop CONSTRAINT likes_unique;
alter TABLE likes drop COLUMN id;
alter TABLE likes ADD PRIMARY KEY (cardId, userId);