-- desc
desc film
;

-- count
EXPLAIN
SELECT COUNT(1)
FROM film
;

-- select
EXPLAIN
SELECT *
FROM film
ORDER BY film_id ASC
;

-- most popular languages
SELECT l.language_id,
       l.name,
       COUNT(1) AS count
FROM film AS f
         JOIN language AS l ON f.language_id = l.language_id
GROUP BY l.language_id
ORDER BY count DESC
LIMIT 10
;

-- most popular original languages
SELECT l.language_id,
       l.name   AS language,
       COUNT(1) AS count
FROM film AS f
         JOIN language AS l ON f.original_language_id = l.language_id
GROUP BY l.language_id
ORDER BY count DESC
LIMIT 10
;

-- duplicate titles?
SELECT title, COUNT(1) AS count
FROM film
GROUP BY title
HAVING count > 1
ORDER BY count
;

-- most occurred title prefix
-- https://dev.mysql.com/doc/refman/8.0/en/string-functions.html#function_substring
SET @len = 1;
EXPLAIN
SELECT SUBSTRING(title, 1, @len) AS prefix,
       count(1)                  AS count,
       GROUP_CONCAT(title)       AS titles
FROM film
GROUP BY SUBSTRING(title, 1, @len)
ORDER BY count DESC
;

-- release_year
EXPLAIN
SELECT release_year, COUNT(1) AS count
FROM film
GROUP BY release_year
ORDER BY count DESC
;
