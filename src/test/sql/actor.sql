-- desc
desc actor
;

-- count
SELECT COUNT(1)
FROM actor
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
