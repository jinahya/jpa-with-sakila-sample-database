-- desc
desc film_category
;

-- count
SELECT COUNT(1)
FROM film_category
;

--
SELECT fc.film_id, f.title, COUNT(1) AS c
FROM film_category AS fc
         JOIN film f on fc.film_id = f.film_id
GROUP BY film_id
HAVING c > 1
ORDER BY c DESC
;

-- Films of Sir Alec Guinness CH CBE
SELECT f.*
FROM film_actor AS fa
         JOIN actor AS a ON a.actor_id = fa.actor_id
         JOIN film AS f ON fa.film_id = f.film_id
WHERE a.last_name = 'Guinness'
  AND a.first_name = 'Alec'
;
