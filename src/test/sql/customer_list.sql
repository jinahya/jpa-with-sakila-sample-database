-- desc
desc customer_list
;

-- select
SELECT *
FROM customer_list
;

SELECT cu.customer_id                                   AS ID,
       CONCAT(cu.first_name, _utf8mb4' ', cu.last_name) AS name,
       a.address                                        AS address,
       a.postal_code                                    AS `zip code`,
       a.phone                                          AS phone,
       city.city                                        AS city,
       country.country                                  AS country,
       IF(cu.active, _utf8mb4'active', _utf8mb4'')      AS notes,
       cu.store_id                                      AS SID
FROM customer AS cu
         JOIN address AS a ON cu.address_id = a.address_id
         JOIN city ON a.city_id = city.city_id
         JOIN country ON city.country_id = country.country_id
;


-- is ID unique?
SELECT ID, COUNT(1) AS c
FROM customer_list
GROUP BY ID
HAVING c > 1
;

-- ID -> customer.customer_id
SELECT cl.ID, c.customer_id
FROM customer_list AS cl
         LEFT JOIN customer AS c ON cl.ID = c.customer_id
WHERE c.customer_id IS NULL
;

-- SID -> store.store_id
SELECT cl.SID, s.store_id
FROM customer_list AS cl
         LEFT JOIN store AS s ON cl.SID = s.store_id
WHERE s.store_id IS NULL
;
