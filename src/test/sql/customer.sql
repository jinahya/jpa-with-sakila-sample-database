-- desc
desc customer
;

-- select
SELECT *
FROM customer
;

-- count
SELECT COUNT(1)
FROM customer
;

-- store_id
SELECT c.customer_id, s.store_id
FROM customer AS c
         LEFT JOIN store AS s ON s.store_id = c.store_id
WHERE s.store_id IS NULL
;
