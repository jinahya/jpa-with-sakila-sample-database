-- desc
desc film_list
;

-- count
SELECT COUNT(1)
FROM film_list
;

--
SELECT FID, category, COUNT(1) AS c
FROM film_list
GROUP BY FID, category
HAVING c > 1
;
