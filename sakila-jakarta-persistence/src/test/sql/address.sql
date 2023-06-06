-- desc
desc address
;

-- select
SELECT *
FROM address
;

-- LENGTH(location)
SELECT DISTINCT LENGTH(location)
FROM address
;

-- count by cities
SELECT a.city_id, c.city, COUNT(1) AS count
FROM address AS a
         JOIN city AS c ON a.city_id = c.city_id
GROUP BY a.city_id
HAVING count > 1
ORDER BY count DESC
;


-- Address_findByAddressId
EXPLAIN
SELECT *
FROM address
WHERE address_id = :addresd_id
;

-- Address_findAll
EXPLAIN
SELECT *
FROM address
;

-- Address_findAllAddressIdGreaterThan
EXPLAIN
SELECT *
FROM address
WHERE address_id > :addressIdMinExclusive
ORDER BY address_id ASC
;

-- Address_findAllByCityId
EXPLAIN
SELECT *
FROM address
WHERE city_id = :cityId
  AND address_id > :addressIdMinExclusive
ORDER BY address_id ASC
;
