-- desc
desc inventory
;

-- select
SELECT *
FROM inventory
;

-- DVDs in stores
SELECT store_id, COUNT(1) AS disks
FROM inventory
GROUP BY store_id
ORDER BY disks DESC
;

-- titles in stores
SELECT i.store_id, i.film_id, f.title, COUNT(1) AS c
FROM inventory AS i
         JOIN film AS f ON i.film_id = f.film_id
GROUP BY i.store_id, i.film_id
ORDER BY i.store_id, c DESC
;
