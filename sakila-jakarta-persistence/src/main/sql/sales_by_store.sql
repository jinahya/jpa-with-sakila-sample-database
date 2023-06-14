-- desc
DESC sales_by_store
;

-- select
SELECT *
FROM sales_by_store
;

--
SELECT store, COUNT(1) AS count
FROM sales_by_store
GROUP BY store
HAVING count > 1
;
