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
