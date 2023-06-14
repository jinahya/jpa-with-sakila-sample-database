-- desc
DESC sales_by_film_category
;

-- select
SELECT *
FROM sales_by_film_category
;

--
SELECT category, COUNT(1) AS count
FROM sales_by_film_category
GROUP BY category
HAVING count > 1
;
