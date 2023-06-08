-- desc
desc store
;

-- select
SELECT *
FROM store
;

-- manager_staff_id
SELECT s.store_id, s2.first_name, s2.last_name
FROM store AS s
         JOIN staff AS s2 ON s.manager_staff_id = s2.staff_id
;

-- address_id
SELECT store_id, a.address, a.district, c.city, c2.country
FROM store AS s
         JOIN address AS a ON s.address_id = a.address_id
         JOIN city AS c ON a.city_id = c.city_id
         JOIN country AS c2 on c.country_id = c2.country_id
;

-- Store_findByStoreId
EXPLAIN
SELECT *
FROM store
WHERE store_id = :storeId
;

-- Store_findAll
EXPLAIN
SELECT *
FROM store
WHERE store_id > :storeIdMinExclusive
ORDER BY store_id ASC
;
