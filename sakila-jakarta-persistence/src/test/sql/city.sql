-- desc
desc city
;

-- select
SELECT *
FROM city
;

-- countries with most cities
EXPLAIN
SELECT c.country_id,
       c2.country,
       COUNT(1) AS city_count,
       GROUP_CONCAT(c.city) AS cities
FROM city AS c
         JOIN country AS c2 ON c.country_id = c2.country_id
GROUP BY c.country_id
ORDER BY city_count DESC
LIMIT 10
;

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
