-- desc
DESC film_text
;

-- count
SELECT COUNT(1)
FROM film_text
;

-- film_id -> film.film_id
SELECT fa.film_id, fa.description, f.description, f.title
FROM film_text AS fa
         JOIN film AS f ON fa.film_id = f.film_id
ORDER BY fa.film_id DESC
;
