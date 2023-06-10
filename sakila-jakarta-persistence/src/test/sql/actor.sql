-- desc
desc actor
;

-- count
EXPLAIN
SELECT COUNT(1)
FROM actor
;

-- select
EXPLAIN
SELECT *
FROM actor
ORDER BY actor_id ASC
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
EXPLAIN
SELECT last_name, COUNT(1) AS count
FROM actor
GROUP BY last_name
ORDER BY count DESC, last_name ASC
LIMIT 5
;

-- Actor_findByActorId (200)
EXPLAIN
SELECT *
FROM actor
WHERE actor_id = :actorId
;

-- Actor_findAll
EXPLAIN
SELECT *
FROM actor
WHERE actor_id > :actorIdMinExclusive
ORDER BY actor_id ASC
LIMIT :offset,:limit
;

-- Actor_findAllByLastName
EXPLAIN
SELECT *
FROM actor
WHERE last_name = :lastName
  AND actor_id > :actorIdMinExclusive
ORDER BY actor_id ASC
LIMIT :offset,:limit
;
