-- desc
desc city
;

-- select
SELECT *
FROM city
;

-- countries with most cities
SELECT c.country_id, c2.country, COUNT(1) AS count
FROM city AS c
         JOIN country AS c2 ON c.country_id = c2.country_id
GROUP BY c.country_id
HAVING count > 1
ORDER BY count DESC
;

-- City_findAll
EXPLAIN
SELECT *
FROM city
;

-- City_findByCityId
EXPLAIN
SELECT *
FROM city
WHERE city_id = :city_id
;
