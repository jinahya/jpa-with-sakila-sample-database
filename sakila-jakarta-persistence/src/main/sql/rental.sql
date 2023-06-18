-- show create table
SHOW CREATE TABLE rental
;

-- desc
DESC rental
;

-- select
SELECT *
FROM rental
;

-- count
SELECT COUNT(1)
FROM rental
;

-- --------------------------------------------------------------------------------------------------------- rental_date

-- MIN/MAX
EXPLAIN
SELECT MIN(rental_date) AS min_rental_date,
       MAX(rental_date) AS max_rental_date
FROM rental
;

-- year, month, and rentals
EXPLAIN
SELECT YEAR(rental_date)  AS year,
       MONTH(rental_date) AS month,
       COUNT(1)           AS rentals
FROM rental
GROUP BY year, month
ORDER BY year ASC, month ASC
;

-- rental_id and rental_date in the same order?
-- https://stackoverflow.com/q/53874286/330457
SELECT COUNT(*)
FROM (SELECT ROW_NUMBER() OVER (ORDER BY rental_id)              AS r_id,
             ROW_NUMBER() OVER (ORDER BY rental_date, rental_id) AS r_date
      FROM rental) t
WHERE r_id <> r_date
;

-- inventory.store <> customer.store
SELECT i.store_id, c.store_id
FROM rental AS r
         JOIN inventory AS i ON r.inventory_id = i.inventory_id
         JOIN customer AS c ON r.customer_id = c.customer_id
WHERE i.store_id <> c.store_id
;

-- best customers by stores
SELECT s.store_id, r.customer_id, c.first_name, c.last_name, COUNT(1) AS rental_count
FROM rental AS r
         JOIN inventory AS i ON r.inventory_id = i.inventory_id
         JOIN staff AS s ON i.store_id = s.store_id
         JOIN customer AS c ON r.customer_id = c.customer_id
GROUP BY s.store_id, c.customer_id
ORDER BY s.store_id, rental_count DESC
;


-- in 2005
SELECT *
FROM rental
WHERE YEAR(rental_date) = 2005
ORDER BY rental_id ASC
LIMIT 5
;
SELECT *
FROM rental
WHERE rental_date >= '2005-01-01 00:00:00'
  AND rental_date <= '2005-12-31 00:00:00'
ORDER BY rental_id ASC
LIMIT 5
;
SELECT *
FROM rental
WHERE rental_date >= '2005-01-01 00:00:00'
  AND rental_date < '2006-01-01 00:00:00'
ORDER BY rental_id ASC
LIMIT 5
;

-- in 2006
SELECT *
FROM rental
WHERE YEAR(rental_date) = 2006
ORDER BY rental_id ASC
LIMIT 5
;
SELECT *
FROM rental
WHERE rental_date >= '2006-01-01 00:00:00'
  AND rental_date <= '2006-12-31 00:00:00'
ORDER BY rental_id ASC
LIMIT 5
;
SELECT *
FROM rental
WHERE rental_date >= '2006-01-01 00:00:00'
  AND rental_date < '2007-01-01 00:00:00'
ORDER BY rental_id ASC
LIMIT 5
;

-- in January, 2006
SELECT *
FROM rental
WHERE YEAR(rental_date) = 2006
  AND MONTH(rental_date) = 2
ORDER BY rental_id ASC
LIMIT 5
;
SELECT *
FROM rental
WHERE rental_date >= '2006-02-01 00:00:00'
  AND rental_date <= '2006-02-28 00:00:00'
ORDER BY rental_id ASC
LIMIT 5
;
SELECT *
FROM rental
WHERE rental_date >= '2006-02-01 00:00:00'
  AND rental_date < '2006-03-01 00:00:00'
ORDER BY rental_id ASC
LIMIT 5
;

-- in 14 January, 2006
EXPLAIN
SELECT COUNT(1)
FROM rental
WHERE YEAR(rental_date) = 2006
  AND MONTH(rental_date) = 2
  AND DAYOFMONTH(rental_date) = 14
;
EXPLAIN
SELECT *
FROM rental
WHERE YEAR(rental_date) = 2006
  AND MONTH(rental_date) = 2
  AND DAYOFMONTH(rental_date) = 14
ORDER BY rental_id ASC
LIMIT 5
;
SELECT *
FROM rental
WHERE rental_date >= '2006-02-14 00:00:00'
  AND rental_date <= '2006-02-14 23:59:59'
ORDER BY rental_id ASC
LIMIT 5
;
SELECT *
FROM rental
WHERE rental_date >= '2006-02-01 00:00:00'
  AND rental_date < '2006-02-15 00:00:00'
ORDER BY rental_id ASC
LIMIT 5
;

-- best years by rental count
SELECT YEAR(rental_date) AS year_of_rental_date,
       COUNT(1)          AS rental_count
FROM rental
GROUP BY year_of_rental_date
ORDER BY rental_count DESC
LIMIT 5
;

-- best year-months by rental cunt
SELECT YEAR(rental_date)  AS year_of_rental_date,
       MONTH(rental_date) AS month_of_rental_date,
       COUNT(1)           AS rental_count
FROM rental
GROUP BY year_of_rental_date, month_of_rental_date
ORDER BY rental_count DESC
LIMIT 5
;

-- best dates by rental count
SELECT DATE(rental_date) AS date_of_rental_date,
       COUNT(1)          AS rental_count
FROM rental
GROUP BY date_of_rental_date
ORDER BY rental_count DESC
LIMIT 5
;

-- best months in each year
SELECT YEAR(rental_date)  AS rental_year,
       MONTH(rental_date) AS rental_month,
       COUNT(1)           AS rentals
FROM rental
GROUP BY rental_year, rental_month
ORDER BY rentals DESC
;
SELECT YEAR(rental_date)                                                         AS rental_year,
       MONTH(rental_date)                                                        AS rental_month,
       COUNT(1)                                                                  AS rentals,
       DENSE_RANK() OVER (PARTITION BY YEAR(rental_date) ORDER BY COUNT(1) DESC) AS ranking
FROM rental
GROUP BY rental_year, rental_month
ORDER BY rentals DESC, rental_year ASC, rental_month ASC
;
SELECT r.rental_year,
       r.rental_month,
       r.rentals,
       r.ranking
FROM (SELECT YEAR(rental_date)                                                         AS rental_year,
             MONTH(rental_date)                                                        AS rental_month,
             COUNT(1)                                                                  AS rentals,
             DENSE_RANK() OVER (PARTITION BY YEAR(rental_date) ORDER BY COUNT(1) DESC) AS ranking
      FROM rental
      GROUP BY rental_year, rental_month
      ORDER BY rentals DESC, rental_year ASC, rental_month ASC) AS r
WHERE r.ranking < 2
;


-- -------------------------------------------------------------------------------------------------------- inventory_id

-- top stores of all time
SELECT s.store_id,
       COUNT(1) AS rentals
FROM rental AS r
         JOIN inventory AS i ON r.inventory_id = i.inventory_id
         JOIn store AS s ON i.store_id = s.store_id
GROUP BY i.store_id
HAVING rentals > 0
ORDER BY rentals DESC
LIMIT 5
;

-- 가장 많이 대여된 필름
SELECT f.film_id,
       CONCAT(f.title, ' (', f.release_year, ')') AS film,
       COUNT(1)                                   AS rentals,
       f.rental_rate,
       COUNT(1) * f.rental_rate                   AS sales
FROM rental AS r
         JOIN inventory AS i ON r.inventory_id = i.inventory_id
         JOIN film AS f ON i.film_id = f.film_id
GROUP BY i.film_id
ORDER BY rentals DESC
LIMIT 5
;

-- 가장 많은 매출을 올린 필름
SELECT f.film_id,
       CONCAT(f.title, ' (', f.release_year, ')') AS film,
       COUNT(1)                                   AS rentals,
       f.rental_rate,
       COUNT(1) * f.rental_rate                   AS sales
FROM rental AS r
         JOIN inventory AS i ON r.inventory_id = i.inventory_id
         JOIN film AS f ON i.film_id = f.film_id
GROUP BY i.film_id
ORDER BY sales DESC
LIMIT 5
;

-- 가장 안기가 많은 장르
SELECT c.category_id,
       c.name   AS category_name,
       COUNT(1) AS rentals
FROM rental AS r
         JOIN inventory AS i ON r.inventory_id = i.inventory_id
         JOIN film AS f ON i.film_id = f.film_id
         JOIN film_category AS fc ON f.film_id = fc.film_id
         JOIN category AS c ON fc.category_id = c.category_id
GROUP BY fc.category_id
ORDER BY rentals DESC
LIMIT 5
;

-- 가장 인기가 많은 장르; 2005-07
SELECT c.category_id,
       c.name           AS category_name,
       COUNT(1)         AS rentals,
       MIN(rental_date) AS min_rental_date,
       MAX(rental_date) AS max_rental_date
FROM rental AS r
         JOIN inventory AS i ON r.inventory_id = i.inventory_id
         JOIN film AS f ON i.film_id = f.film_id
         JOIN film_category AS fc ON f.film_id = fc.film_id
         JOIN category AS c ON fc.category_id = c.category_id
WHERE YEAR(rental_date) = 2005
  AND MONTH(rental_date) = 5
GROUP BY fc.category_id
ORDER BY rentals DESC
LIMIT 5
;

-- --------------------------------------------------------------------------------------------------------- customer_id

-- top customers of all time
SELECT c.customer_id,
       CONCAT_WS(' ', c.first_name, c.last_name) AS customer_name,
       COUNT(1)                                  AS rentals
FROM rental AS r
         JOIN customer AS c ON r.customer_id = c.customer_id
GROUP BY c.customer_id
ORDER BY customer_id ASC, rentals DESC
LIMIT 5
;
-- top customers in July, 2005
EXPLAIN
SELECT c.customer_id,
       CONCAT_WS(' ', c.first_name, c.last_name) AS customer_name,
       COUNT(1)                                  AS rentals,
       MIN(rental_date)                          AS min_rental_date,
       MAX(rental_date)                          AS max_rental_date
FROM rental AS r
         JOIN customer AS c ON r.customer_id = c.customer_id
WHERE YEAR(rental_date) = '2005'
  AND MONTH(rental_date) = '07'
GROUP BY c.customer_id
ORDER BY rentals DESC, customer_id ASC
LIMIT 5
;
EXPLAIN
SELECT c.customer_id,
       CONCAT_WS(' ', c.first_name, c.last_name) AS customer_name,
       COUNT(1)                                  AS rentals,
       MIN(rental_date)                          AS min_rental_date,
       MAX(rental_date)                          AS max_rental_date
FROM rental AS r
         JOIN customer AS c ON r.customer_id = c.customer_id
WHERE rental_date >= '2005-07-01 00:00:00.000'
  AND rental_date < '2005-08-01 00:00:00.000'
GROUP BY c.customer_id
ORDER BY rentals DESC, customer_id ASC
LIMIT 5
;

-- top customers of a specific store
SELECT c.customer_id,
       CONCAT_WS(' ', c.first_name, c.last_name) AS customer_name,
       COUNT(1)                                  AS rentals
FROM rental AS r
         JOIN inventory AS i ON r.inventory_id = i.inventory_id
         JOIN customer AS c ON r.customer_id = c.customer_id
WHERE i.store_id = :store_id
GROUP BY i.store_id, c.customer_id
ORDER BY rentals DESC, c.customer_id ASC
LIMIT 5
;
-- top customers of a specific store, in July, 2005
SELECT c.customer_id,
       CONCAT_WS(' ', c.first_name, c.last_name) AS customer_name,
       COUNT(1)                                  AS rentals,
       MIN(rental_date)                          AS min_rental_date,
       MAX(rental_date)                          AS max_rental_date
FROM rental AS r
         JOIN inventory AS i ON r.inventory_id = i.inventory_id
         JOIN customer AS c ON r.customer_id = c.customer_id
WHERE i.store_id = :store_id
  AND YEAR(rental_date) = '2005'
  AND MONTH(rental_date) = '07'
GROUP BY i.store_id, c.customer_id
ORDER BY rentals DESC, c.customer_id ASC
LIMIT 5
;

-- top customers in each store
SELECT i.store_id,
       c.customer_id,
       COUNT(c.customer_id)                                                            AS rentals,
       CONCAT_WS(' ', c.first_name, c.last_name)                                       AS customer_name,
       DENSE_RANK() OVER (PARTITION BY i.store_id ORDER BY COUNT(c.customer_id) DESC ) AS ranking
FROM rental AS r
         JOIN inventory AS i ON r.inventory_id = i.inventory_id
         JOIN customer AS c ON r.customer_id = c.customer_id
GROUP BY i.store_id, c.customer_id
ORDER BY i.store_id ASC, ranking ASC, c.customer_id ASC
LIMIT 5
;
SELECT *
FROM (SELECT i.store_id,
             c.customer_id,
             COUNT(c.customer_id)                                                            AS rentals,
             DENSE_RANK() OVER (PARTITION BY i.store_id ORDER BY COUNT(c.customer_id) DESC ) AS ranking
      FROM rental AS r
               JOIN inventory AS i ON r.inventory_id = i.inventory_id
               JOIN customer AS c ON r.customer_id = c.customer_id
      GROUP BY i.store_id, c.customer_id
      ORDER BY i.store_id ASC, ranking ASC, c.customer_id ASC) AS r
WHERE r.ranking < 3
;
WITH r AS (SELECT i.store_id,
                  c.customer_id,
                  COUNT(c.customer_id)                                                            AS rentals,
                  DENSE_RANK() OVER (PARTITION BY i.store_id ORDER BY COUNT(c.customer_id) DESC ) AS ranking
           FROM rental AS r
                    JOIN inventory AS i ON r.inventory_id = i.inventory_id
                    JOIN customer AS c ON r.customer_id = c.customer_id
           GROUP BY i.store_id, c.customer_id
           ORDER BY i.store_id ASC, ranking ASC, c.customer_id ASC)
SELECT *
FROM r
WHERE r.ranking < 3
;

-- top customers in each store, in July, 2005
SELECT i.store_id,
       c.customer_id,
       COUNT(c.customer_id)                                                            AS rentals,
       CONCAT_WS(' ', c.first_name, c.last_name)                                       AS customer_name,
       DENSE_RANK() OVER (PARTITION BY i.store_id ORDER BY COUNT(c.customer_id) DESC ) AS ranking
FROM rental AS r
         JOIN inventory AS i ON r.inventory_id = i.inventory_id
         JOIN customer AS c ON r.customer_id = c.customer_id
WHERE YEAR(r.rental_date) = 2005
  AND MONTH(r.rental_date) = 5
GROUP BY i.store_id, c.customer_id
ORDER BY i.store_id ASC, ranking ASC, c.customer_id ASC
LIMIT 5
;
SELECT *
FROM (SELECT i.store_id,
             c.customer_id,
             COUNT(c.customer_id)                                                            AS rentals,
             DENSE_RANK() OVER (PARTITION BY i.store_id ORDER BY COUNT(c.customer_id) DESC ) AS ranking
      FROM rental AS r
               JOIN inventory AS i ON r.inventory_id = i.inventory_id
               JOIN customer AS c ON r.customer_id = c.customer_id
      WHERE YEAR(r.rental_date) = 2005
        AND MONTH(r.rental_date) = 5
      GROUP BY i.store_id, c.customer_id
      ORDER BY i.store_id ASC, ranking ASC, c.customer_id ASC) AS r
WHERE r.ranking < 3
;
WITH r AS (SELECT i.store_id,
                  c.customer_id,
                  COUNT(c.customer_id)                                                            AS rentals,
                  DENSE_RANK() OVER (PARTITION BY i.store_id ORDER BY COUNT(c.customer_id) DESC ) AS ranking
           FROM rental AS r
                    JOIN inventory AS i ON r.inventory_id = i.inventory_id
                    JOIN customer AS c ON r.customer_id = c.customer_id
           WHERE YEAR(r.rental_date) = 2005
             AND MONTH(r.rental_date) = 5
           GROUP BY i.store_id, c.customer_id
           ORDER BY i.store_id ASC, ranking ASC, c.customer_id ASC)
SELECT *
FROM r
WHERE r.ranking < 3
;

-- ------------------------------------------------------------------------------------------------------------ staff_id

-- top staffs of all time
SELECT s.staff_id,
       CONCAT_WS(' ', s.first_name, s.last_name) AS staff_name,
       COUNT(1)                                  AS rentals
FROM rental AS r
         JOIN staff AS s ON r.staff_id = s.staff_id
GROUP BY s.staff_id
ORDER BY staff_id ASC, rentals DESC
LIMIT 5
;

-- top staffs in July, 2005
EXPLAIN
SELECT s.staff_id,
       CONCAT_WS(' ', s.first_name, s.last_name) AS name,
       COUNT(1)                                  AS rentals,
       MIN(rental_date)                          AS min_rental_date,
       MAX(rental_date)                          AS max_rental_date
FROM rental AS r
         JOIN staff AS s ON r.staff_id = s.staff_id
WHERE YEAR(rental_date) = '2005'
  AND MONTH(rental_date) = '07'
GROUP BY s.staff_id
ORDER BY rentals DESC, staff_id ASC
LIMIT 5
;
EXPLAIN
SELECT s.staff_id,
       CONCAT_WS(' ', s.first_name, s.last_name) AS staff_name,
       COUNT(1)                                  AS rentals,
       MIN(rental_date)                          AS min_rental_date,
       MAX(rental_date)                          AS max_rental_date
FROM rental AS r
         JOIN staff AS s ON r.staff_id = s.staff_id
WHERE rental_date >= '2005-07-01 00:00:00.000'
  AND rental_date < '2005-08-01 00:00:00.000'
GROUP BY s.staff_id
ORDER BY rentals DESC, staff_id ASC
LIMIT 5
;

-- top staffs of a specific store
SELECT s.staff_id,
       CONCAT_WS(' ', s.first_name, s.last_name) AS staff_name,
       COUNT(1)                                  AS rentals
FROM rental AS r
         JOIN inventory AS i ON r.inventory_id = i.inventory_id
         JOIN staff AS s ON r.staff_id = s.staff_id
WHERE i.store_id = :store_id
GROUP BY i.store_id, s.staff_id
ORDER BY rentals DESC, s.staff_id ASC
LIMIT 5
;
-- top staffs of a specific store, in July, 2005
SELECT s.staff_id,
       CONCAT_WS(' ', s.first_name, s.last_name) AS staff_name,
       COUNT(1)                                  AS rentals,
       MIN(rental_date)                          AS min_rental_date,
       MAX(rental_date)                          AS max_rental_date
FROM rental AS r
         JOIN inventory AS i ON r.inventory_id = i.inventory_id
         JOIN staff AS s ON r.staff_id = s.staff_id
WHERE i.store_id = :store_id
  AND YEAR(rental_date) = '2005'
  AND MONTH(rental_date) = '07'
GROUP BY i.store_id, s.staff_id
ORDER BY rentals DESC, s.staff_id ASC
LIMIT 5
;

-- top staffs in each store
SELECT i.store_id,
       s.staff_id,
       CONCAT_WS(' ', s.first_name, s.last_name)                                    AS staff_name,
       COUNT(s.staff_id)                                                            AS rentals,
       DENSE_RANK() OVER (PARTITION BY i.store_id ORDER BY COUNT(s.staff_id) DESC ) AS ranking
FROM rental AS r
         JOIN inventory AS i ON r.inventory_id = i.inventory_id
         JOIN staff AS s ON r.staff_id = s.staff_id
GROUP BY i.store_id, s.staff_id
ORDER BY i.store_id ASC, ranking ASC, s.staff_id ASC
;
SELECT *
FROM (SELECT i.store_id,
             s.staff_id,
             CONCAT_WS(' ', s.first_name, s.last_name)                                    AS staff_name,
             COUNT(s.staff_id)                                                            AS rentals,
             DENSE_RANK() OVER (PARTITION BY i.store_id ORDER BY COUNT(s.staff_id) DESC ) AS ranking
      FROM rental AS r
               JOIN inventory AS i ON r.inventory_id = i.inventory_id
               JOIN staff AS s ON r.staff_id = s.staff_id
      GROUP BY i.store_id, s.staff_id
      ORDER BY i.store_id ASC, ranking ASC, s.staff_id ASC) AS r
WHERE r.ranking < 3
;
WITH r AS (SELECT i.store_id,
                  s.staff_id,
                  CONCAT_WS(' ', s.first_name, s.last_name)                                    AS staff_name,
                  COUNT(s.staff_id)                                                            AS rentals,
                  DENSE_RANK() OVER (PARTITION BY i.store_id ORDER BY COUNT(s.staff_id) DESC ) AS ranking
           FROM rental AS r
                    JOIN inventory AS i ON r.inventory_id = i.inventory_id
                    JOIN staff AS s ON r.staff_id = s.staff_id
           GROUP BY i.store_id, s.staff_id
           ORDER BY i.store_id ASC, ranking ASC, s.staff_id ASC)
SELECT *
FROM r
WHERE r.ranking < 3
;

-- top staffs in each store, in July, 2005
SELECT i.store_id,
       s.staff_id,
       CONCAT_WS(' ', s.first_name, s.last_name)                                    AS staff_name,
       COUNT(s.staff_id)                                                            AS rentals,
       DENSE_RANK() OVER (PARTITION BY i.store_id ORDER BY COUNT(s.staff_id) DESC ) AS ranking
FROM rental AS r
         JOIN inventory AS i ON r.inventory_id = i.inventory_id
         JOIN staff AS s ON r.staff_id = s.staff_id
WHERE YEAR(rental_date) = '2005'
  AND MONTH(rental_date) = '07'
GROUP BY i.store_id, s.staff_id
ORDER BY i.store_id ASC, ranking ASC, s.staff_id ASC
;
SELECT *
FROM (SELECT i.store_id,
             s.staff_id,
             CONCAT_WS(' ', s.first_name, s.last_name)                                    AS staff_name,
             COUNT(s.staff_id)                                                            AS rentals,
             DENSE_RANK() OVER (PARTITION BY i.store_id ORDER BY COUNT(s.staff_id) DESC ) AS ranking
      FROM rental AS r
               JOIN inventory AS i ON r.inventory_id = i.inventory_id
               JOIN staff AS s ON r.staff_id = s.staff_id
      WHERE YEAR(rental_date) = '2005'
        AND MONTH(rental_date) = '07'
      GROUP BY i.store_id, s.staff_id
      ORDER BY i.store_id ASC, ranking ASC, s.staff_id ASC) AS r
WHERE r.ranking < 3
;
WITH r AS (SELECT i.store_id,
                  s.staff_id,
                  CONCAT_WS(' ', s.first_name, s.last_name)                                    AS staff_name,
                  COUNT(s.staff_id)                                                            AS rentals,
                  DENSE_RANK() OVER (PARTITION BY i.store_id ORDER BY COUNT(s.staff_id) DESC ) AS ranking
           FROM rental AS r
                    JOIN inventory AS i ON r.inventory_id = i.inventory_id
                    JOIN staff AS s ON r.staff_id = s.staff_id
           WHERE YEAR(rental_date) = '2005'
             AND MONTH(rental_date) = '07'
           GROUP BY i.store_id, s.staff_id
           ORDER BY i.store_id ASC, ranking ASC, s.staff_id ASC)
SELECT *
FROM r
WHERE r.ranking < 3
;
