alter table likes add constraint likes_unique unique (cardId, userId);