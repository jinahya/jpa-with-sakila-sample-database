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

-- most common first_name
SELECT first_name, COUNT(1) AS c
FROM actor
GROUP BY first_name
HAVING c > 1
ORDER BY c DESC
;

-- most common last_name
SELECT last_name, COUNT(1) AS c
FROM actor
GROUP BY last_name
HAVING c > 1
ORDER BY c DESC
;

-- Actor_findAll
SELECT *
FROM actor
LIMIT :limit
;

-- Actor_findByActorId
SELECT *
FROM actor
WHERE actor_id = :actor_id
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
