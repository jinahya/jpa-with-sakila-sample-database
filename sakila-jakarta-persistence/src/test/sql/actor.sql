-- desc
desc actor
;

-- select
SELECT *
FROM actor
;

-- count
SELECT COUNT(1)
FROM actor
;

-- most common first_names
EXPLAIN
SELECT first_name, COUNT(1) AS count
FROM actor
GROUP BY first_name
ORDER BY count DESC, first_name ASC
LIMIT 5
;

-- most common last_names
SELECT last_name, COUNT(1) AS count
FROM actor
GROUP BY last_name
ORDER BY count DESC, last_name ASC
LIMIT 5
;



-- films of Sir Alec Guinness CH CBE
EXPLAIN
SELECT f.*
FROM actor AS a
         JOIN film_actor AS fa ON a.actor_id = fa.actor_id
         JOIN film AS f ON f.film_id = fa.film_id
WHERE a.last_name = 'Guinness'
  AND a.first_name = 'Alec'
ORDER BY f.release_year ASC
;


-- Actor_findByActorId
SELECT *
FROM actor
WHERE actor_id = :actor_id
;

-- Actor_findAll
SELECT *
FROM actor
LIMIT :limit
;

-- Actor_findAllByActorIdGreaterThan
SELECT a.*
FROM actor AS a
WHERE a.actor_id > :actorIdMinExclusive
ORDER BY a.actor_id ASC
LIMIT :limit
;

-- Actor_findAllLastName
SELECT a.*
FROM actor AS a
WHERE a.last_name = :lastName
LIMIT :limit
;

--
EXPLAIN
SELECT first_name, COUNT(1) AS c
FROM actor
GROUP BY first_name
HAVING c > 1
ORDER BY c DESC
;

--
EXPLAIN
SELECT last_name, COUNT(1) AS c
FROM actor
GROUP BY last_name
HAVING c > 1
ORDER BY c DESC
;

--
EXPLAIN
SELECT first_name, last_name, COUNT(1) AS c
FROM actor
GROUP BY first_name, last_name
HAVING c > 1
ORDER BY c DESC
;
EXPLAIN
SELECT first_name, last_name, COUNT(1) AS c
FROM actor
GROUP BY last_name, first_name
HAVING c > 1
ORDER BY c DESC
;
