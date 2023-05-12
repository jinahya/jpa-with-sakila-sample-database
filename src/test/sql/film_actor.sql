-- desc
desc film_actor
;

-- count
SELECT COUNT(1)
FROM film_actor
;

--
SELECT a.*, COUNT(fa.film_id) AS c
FROM film_actor AS fa
         JOIN actor AS a ON a.actor_id = fa.actor_id
GROUP BY a.actor_id
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
