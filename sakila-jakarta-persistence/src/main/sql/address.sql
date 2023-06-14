-- SHOW CREATE TABLE
SHOW CREATE TABLE address
;

-- DESC
DESC address
;

-- SELECT *
SELECT *
FROM address
;

-- ---------------------------------------------------------------------------------------------------------- address_id

-- ------------------------------------------------------------------------------------------------------------- address

-- ------------------------------------------------------------------------------------------------------------ address2

-- ------------------------------------------------------------------------------------------------------------ district

-- ------------------------------------------------------------------------------------------------------------- city_id

-- count by cities
SELECT a.city_id,
       COUNT(1) AS address_count
FROM address AS a
GROUP BY a.city_id
ORDER BY address_count DESC, a.city_id ASC
LIMIT 5
;

-- --------------------------------------------------------------------------------------------------------- postal_code

-- --------------------------------------------------------------------------------------------------------------- phone

-- ------------------------------------------------------------------------------------------------------------ location

-- LENGTH(location)
SELECT DISTINCT LENGTH(location)
FROM address
;

SELECT a.address_id,
       ST_X(a.location) AS longitude,
       ST_Y(a.location) AS latitude,
       c.city,
       c2.country,
       CONCAT('https://maps.google.com/?q=', ST_Y(a.location), ',', ST_X(a.location))
FROM address AS a
         JOIN city AS c ON c.city_id = a.city_id
         JOIN country AS c2 ON c2.country_id = c.country_id
ORDER BY address_id ASC
LIMIT 5
;

-- ---------------------------------------------------------------------------------------------------------------------

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

-- Address_findAllByCountryId
EXPLAIN
SELECT a.address, a.district, c.city
FROM address AS a
         JOIN city AS c ON c.city_id = a.city_id
WHERE c.country_id = :countryId
  AND address_id > :addressIdMinExclusive
ORDER BY address_id ASC
;
