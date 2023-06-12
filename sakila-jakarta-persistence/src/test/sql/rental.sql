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

-- rental_date
EXPLAIN
SELECT MIN(rental_date), MAX(rental_date)
FROM rental
;
-- https://stackoverflow.com/q/53874286/330457
SELECT COUNT(*)
FROM (SELECT ROW_NUMBER() OVER (ORDER BY rental_id)              AS r_id,
             ROW_NUMBER() OVER (ORDER BY rental_date, rental_id) AS r_date
      FROM rental) t
WHERE r_id <> r_date
;

-- inventory.store <> customer.store
SELECT i.store_id, c.store_id
FROM rental AS r
         JOIN inventory AS i ON r.inventory_id = i.inventory_id
         JOIN customer AS c ON r.customer_id = c.customer_id
WHERE i.store_id <> c.store_id
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
