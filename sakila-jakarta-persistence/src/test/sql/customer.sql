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

SELECT COUNT(1) AS count
FROM customer AS c
         JOIN address AS a ON a.address_id = c.address_id
GROUP BY a.city_id
ORDER BY count DESC
;
