DELETE FROM public.account_status;

INSERT INTO public.account_status (name, description)
VALUES ('NEW', 'Счёт создан в БД'),
       ('IN_CREATION', 'Запрос на создание счета был отправлен в смежную систему'),
       ('CREATED', 'Счёт создан в смежной системе'),
       ('CANCELLED', 'Счёт аннулирован'),
       ('CLOSED', 'Счёт закрыт');
