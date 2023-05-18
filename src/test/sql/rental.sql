-- desc
desc rental
;

-- select
SELECT *
FROM rental
;

-- count
SELECT COUNT(1)
FROM rental
;

-- best customers by stores
SELECT s.store_id, r.customer_id, c.first_name, c.last_name, COUNT(1) AS rental_count
FROM rental AS r
         JOIN inventory AS i ON r.inventory_id = i.inventory_id
         JOIN staff AS s ON i.store_id = s.store_id
         JOIN customer AS c ON r.customer_id = c.customer_id
GROUP BY s.store_id, c.customer_id
ORDER BY s.store_id, rental_count DESC
;
