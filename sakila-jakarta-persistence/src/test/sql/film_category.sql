-- desc
desc film_category
;

-- count
SELECT COUNT(1)
FROM film_category
;

-- most categorized films
SELECT fc.film_id, f.title, COUNT(1) AS count, GROUP_CONCAT(c.name) AS categories
FROM film_category AS fc
         JOIN film AS f ON fc.film_id = f.film_id
         JOIN category AS c ON c.category_id = fc.category_id
GROUP BY film_id
ORDER BY count DESC
LIMIT 10
;

-- most popular categories
SELECT fc.category_id, c.name, COUNT(1) AS count, GROUP_CONCAT(f.title) AS films
FROM film_category AS fc
         JOIN film AS f ON fc.film_id = f.film_id
         JOIN category AS c ON c.category_id = fc.category_id
GROUP BY c.category_id
ORDER BY count DESC
LIMIT 10
;

-- films of Sir Alec Guinness CH CBE
SELECT f.*
FROM film_actor AS fa
         JOIN actor AS a ON a.actor_id = fa.actor_id
         JOIN film AS f ON fa.film_id = f.film_id
WHERE a.last_name = 'Guinness'
  AND a.first_name = 'Alec'
ORDER BY f.release_year DESC
;
