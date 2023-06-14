-- desc
DESC film_list
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


select f1_0.category,
       f1_0.FID,
       f1_0.actors,
       f1_0.description,
       f1_0.length,
       f1_0.price,
       f1_0.rating,
       f1_0.title
from film_list f1_0
where f1_0.FID = 1010
;
