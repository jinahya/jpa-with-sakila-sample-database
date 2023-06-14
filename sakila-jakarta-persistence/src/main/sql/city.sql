-- desc
DESC city
;

-- select
SELECT *
FROM city
;


-- ------------------------------------------------------------------------------------------------------------- city_id

-- ---------------------------------------------------------------------------------------------------------------- city

-- ---------------------------------------------------------------------------------------------------------- country_id

-- countries with most cities
EXPLAIN
SELECT c.country_id,
       c2.country,
       COUNT(1)             AS city_count,
       GROUP_CONCAT(c.city) AS cities
FROM city AS c
         JOIN country AS c2 ON c.country_id = c2.country_id
GROUP BY c.country_id
ORDER BY city_count DESC
LIMIT 5
;

-- cities of India(44)
SET @countryId = 44;
-- the first page using offset,limit
EXPLAIN
SELECT *
FROM city
WHERE country_id = @countryId
ORDER BY city_id ASC
LIMIT 0,5
;
-- the second page using offset,limit
EXPLAIN
SELECT *
FROM city
WHERE country_id = @countryId
ORDER BY city_id ASC
LIMIT 5,5
;
-- the first page using the keyset pagination
EXPLAIN
SELECT *
FROM city
WHERE country_id = @countryId
  AND city_id > 0
ORDER BY city_id ASC
LIMIT 5
;
-- the second page using the keyset pagination
EXPLAIN
SELECT *
FROM city
WHERE country_id = @countryId
  AND city_id > 44
ORDER BY city_id ASC
LIMIT 5
;

-- ---------------------------------------------------------------------------------------------------------------------

-- City_findByCityId
SET @cityId = 500;
EXPLAIN
SELECT *
FROM city
WHERE city_id = @cityId
;

-- City_findAll
EXPLAIN
SELECT *
FROM city
ORDER BY city_id ASC
LIMIT :offset,:limit
;
EXPLAIN
SELECT *
FROM city
WHERE city_id > :cityIdMinExclusive
ORDER BY city_id ASC
LIMIT :limit
;
