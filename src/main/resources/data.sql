insert into category (name, created_at, updated_at) value ('고기', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

insert into ingredient (category_id, created_at, updated_at, expiration_period, name, url, count_type, storage_type)
values (1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 30, '등심', 'test', 'AMOUNT', 'COLD'),
       (1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 30, '안심', 'test2', 'AMOUNT', 'COLD');


# insert into member (created_at, updated_at, first_name, last_name, email, password, provider, provider_id, role)
#     values(CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'JaeHyun', 'Jung', 'jhyun.rigel@gmail.com', 'pass_word', 'google', 100371066518631530073, 'USER');

# insert into user_storage(created_at, updated_at, expiration_date, quantity, ingredient_id, member_id, storage_type)
# values (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '2023-10-01', 30, 1, 1, 'COLD'),
#        (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '2023-10-01', 30, 2, 1, 'FREEZE')