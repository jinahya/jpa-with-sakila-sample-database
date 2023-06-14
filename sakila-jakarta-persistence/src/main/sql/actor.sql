-- desc
DESC actor
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

-- ---------------------------------------------------------------------------------------------------------- first_name

-- 가장 많은 名
EXPLAIN
SELECT first_name, COUNT(1) AS count
FROM actor
GROUP BY first_name
ORDER BY count DESC, first_name ASC
LIMIT 5
;

-- ----------------------------------------------------------------------------------------------------------- last_name

-- 가장 많은 姓
EXPLAIN
SELECT last_name, COUNT(1) AS count
FROM actor
GROUP BY last_name
ORDER BY count DESC, last_name ASC
LIMIT 5
;

-- ----------------------------------------------------------------------------------------------- first_name, last_name

-- 同名異人?
EXPLAIN
SELECT first_name, last_name, COUNT(1) AS count
FROM actor
GROUP BY first_name, last_name
HAVING count > 1
ORDER BY first_name, last_name
;

-- ---------------------------------------------------------------------------------------------------------------------

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
ORDER BY actor_id ASC
LIMIT :offset,:limit
;
EXPLAIN
SELECT *
FROM actor
WHERE actor_id > :actorIdMinExclusive
ORDER BY actor_id ASC
LIMIT :limit
;

-- Actor_findAllByLastName ('KILMER')
EXPLAIN
SELECT *
FROM actor
WHERE last_name = :lastName
ORDER BY actor_id ASC
LIMIT :offset,:limit
;
EXPLAIN
SELECT *
FROM actor
WHERE last_name = :lastName
  AND actor_id > :actorIdMinExclusive
ORDER BY actor_id ASC
LIMIT :limit
;
