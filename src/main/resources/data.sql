insert into category (name, created_at, updated_at) value ('고기', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

insert into ingredient (category_id, created_at, updated_at, expiration_period, name, url, count_type, storage_type)
values (1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 30, '등심', 'test', 'AMOUNT', 'COLD'),
       (1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 30, '안심', 'test2', 'AMOUNT', 'COLD');



