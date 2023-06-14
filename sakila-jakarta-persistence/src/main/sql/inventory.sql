-- desc
DESC inventory
;

-- count
SELECT COUNT(1)
FROM inventory
;

-- select
SELECT *
FROM inventory
;

-- 가장 많은 개수의 영화(disk)
EXPLAIN
SELECT i.film_id, f.title, COUNT(1) AS disks
FROM inventory AS i
         JOIN film AS f ON i.film_id = f.film_id
GROUP BY i.film_id
HAVING disks > 1
ORDER BY disks DESC
LIMIT 10
;

-- 점포별 영화(disk) 개수
EXPLAIN
SELECT s.store_id,
       CONCAT_WS(', ',
                 a.address,
                 c.city,
                 c2.country) AS address,
       COUNT(1)              AS disks
FROM inventory AS i
         JOIN store AS s ON i.store_id = s.store_id
         JOIN address AS a ON s.address_id = a.address_id
         JOIN city AS c ON a.city_id = c.city_id
         JOIN country AS c2 ON c.country_id = c2.country_id
GROUP BY i.store_id
ORDER BY disks DESC
;

SELECT i.store_id, i.film_id, COUNT(*) AS disks
FROM inventory AS i
GROUP BY i.store_id, i.film_id
ORDER BY disks DESC
LIMIT 2
;

SELECT a.store_id, a.film_id, f.title, a.ranked_order
FROM (SELECT film_id,
             store_id,
             ROW_NUMBER() OVER (PARTITION BY store_id ORDER BY film_id DESC) ranked_order
      FROM inventory) a
         JOIN film AS f ON a.film_id = f.film_id
WHERE a.ranked_order < 5
;

-- Inventory_findByInventoryId
EXPLAIN
SELECT *
FROM inventory
WHERE inventory_id = :inventoryId
;

-- Inventory_findAll
EXPLAIN
SELECT *
FROM inventory
WHERE inventory_id > :inventoryIdMinExclusive
ORDER BY inventory_id ASC
LIMIT :limit
;

-- Inventory_findAllByFilmId
EXPLAIN
SELECT *
FROM inventory
WHERE film_id = :filmId
  AND inventory_id > :inventoryIdMinExclusive
ORDER BY inventory_id ASC
LIMIT :limit
;

-- Inventory_findAllByStoreId
EXPLAIN
SELECT *
FROM inventory
WHERE store_id = :storeId
  AND inventory_id > :inventoryIdMinExclusive
ORDER BY inventory_id ASC
LIMIT :limit
;


-- Inventory_findDistinctFilmsByStoreId
EXPLAIN
SELECT DISTINCT f.*
FROM inventory AS i
         JOIN film AS f ON i.film_id = f.film_id
WHERE i.store_id = :storeId
  AND i.film_id > :filmIdMinExclusive
ORDER BY f.film_id ASC -- i.film_id 하면 에러나네....
;
