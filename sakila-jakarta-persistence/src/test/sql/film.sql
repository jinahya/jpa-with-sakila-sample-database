-- desc
desc film
;

-- count
SELECT COUNT(1)
FROM film
;

-- select
SELECT *
FROM film
;

-- films with most actors
SELECT f.film_id, COUNT(fa.actor_id) AS actors, f.title
FROM film AS f
         JOIN film_actor AS fa ON f.film_id = fa.film_id
GROUP BY fa.film_id
ORDER BY actors DESC
LIMIT 5
;

-- films of Sir Alec Guinness CH CBE
EXPLAIN
SELECT f.*
FROM film AS f
         JOIN film_actor AS fa ON f.film_id = fa.film_id
         JOIN actor AS a ON a.actor_id = fa.actor_id
WHERE a.last_name = 'Guinness'
  AND a.first_name = 'Alec'
ORDER BY f.release_year ASC
;
EXPLAIN
SELECT f.*
FROM film AS f
         JOIN film_actor AS fa ON f.film_id = fa.film_id
         JOIN (SELECT * FROM actor WHERE last_name = 'Guinness' AND first_name = 'Alec') AS a
              ON fa.actor_id = a.actor_id
ORDER BY f.release_year ASC
;
