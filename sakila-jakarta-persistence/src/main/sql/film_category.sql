-- SHOW CREATE TABLE
SHOW CREATE TABLE film_category;
# CREATE TABLE `film_category`
# (
#     `film_id`     smallint unsigned NOT NULL,
#     `category_id` tinyint unsigned  NOT NULL,
#     `last_update` timestamp         NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
#     PRIMARY KEY (`film_id`, `category_id`),
#     KEY `fk_film_category_category` (`category_id`),
#     CONSTRAINT `fk_film_category_category` FOREIGN KEY (`category_id`) REFERENCES `category` (`category_id`) ON DELETE RESTRICT ON UPDATE CASCADE,
#     CONSTRAINT `fk_film_category_film` FOREIGN KEY (`film_id`) REFERENCES `film` (`film_id`) ON DELETE RESTRICT ON UPDATE CASCADE
# ) ENGINE = InnoDB
#   DEFAULT CHARSET = utf8mb4
#   COLLATE = utf8mb4_0900_ai_ci
# ;

-- DESC
DESC film_category
;

-- COUNT(1)
SELECT COUNT(1)
FROM film_category
;

-- SELECT *
SELECT *
FROM film_category
ORDER BY film_id ASC
;

-- --------------------------------------------------------------------------------------------------------- category_id

-- categories with most films
EXPLAIN
SELECT fc.category_id,
       c.name                AS category_name,
       COUNT(1)              AS film_count,
       GROUP_CONCAT(f.title) AS film_titles
FROM film_category AS fc
         JOIN film AS f ON fc.film_id = f.film_id
         JOIN category AS c ON c.category_id = fc.category_id
GROUP BY c.category_id
ORDER BY film_count DESC
LIMIT 5
;

-- films with most categories
EXPLAIN
SELECT fc.film_id,
       f.title              AS film_title,
       COUNT(1)             AS category_count,
       GROUP_CONCAT(c.name) AS category_names
FROM film_category AS fc
         JOIN film AS f ON fc.film_id = f.film_id
         JOIN category AS c ON c.category_id = fc.category_id
GROUP BY film_id
ORDER BY category_count DESC
LIMIT 5
;

-- --------------------------------------------------------------------------------------------------------- last_update

-- ---------------------------------------------------------------------------------------------------------------------

-- FilmCategory_selectByFilmIdAndCategoryId
SET @filmId = 1004; -- Gone with the Wind
SET @categoryId = 7; -- Drama
EXPLAIN
SELECT fc.film_id,
       fc.category_id,
       f.title AS film_title,
       c.name  AS category_name
FROM film_category AS fc
         JOIN film AS f ON f.film_id = fc.film_id
         JOIN category AS c ON c.category_id = fc.category_id
WHERE fc.film_id = @filmId
  AND fc.category_id = @categoryId
;

-- FilmCategory_selectAll_rowset
EXPLAIN
SELECT *
FROM film_category
ORDER BY film_id ASC, category_id ASC
LIMIT :offset,:limit
;
EXPLAIN
SELECT *
FROM film_category
ORDER BY film_id ASC, category_id ASC
LIMIT 0,5
;
EXPLAIN
SELECT *
FROM film_category
ORDER BY film_id ASC, category_id ASC
LIMIT 5,5
;
EXPLAIN
SELECT *
FROM film_category
ORDER BY film_id ASC, category_id ASC
LIMIT 1010,5
;
EXPLAIN
SELECT *
FROM film_category
ORDER BY film_id ASC, category_id ASC
LIMIT 1015,5
;

-- FilmCategory_selectAll_keyset
EXPLAIN
SELECT *
FROM film_category
WHERE (film_id = ? AND category_id > ?)
   OR film_id > ?
ORDER BY film_id ASC, category_id ASC
LIMIT :limit
;
EXPLAIN
SELECT *
FROM film_category
WHERE (film_id = 0 AND category_id > 0)
   OR film_id > 0
ORDER BY film_id ASC, category_id ASC
LIMIT 5
;
EXPLAIN
SELECT *
FROM film_category
WHERE (film_id = 5 AND category_id > 8)
   OR film_id > 5
ORDER BY film_id ASC, category_id ASC
LIMIT 5
;
EXPLAIN
SELECT *
FROM film_category
WHERE (film_id = 1005 AND category_id > 7)
   OR film_id > 1005
ORDER BY film_id ASC, category_id ASC
LIMIT 5
;
EXPLAIN
SELECT *
FROM film_category
WHERE (film_id = 1006 AND category_id > 22)
   OR film_id > 1006
ORDER BY film_id ASC, category_id ASC
LIMIT 5
;

-- FilmCategory_selectByFilmId
SET @filmId = 1004; -- Gone with the Wind
EXPLAIN
SELECT fc.category_id,
       c.name AS category_name
FROM film_category AS fc
         JOIN category AS c ON c.category_id = fc.category_id
WHERE fc.film_id = @filmId
ORDER BY fc.category_id ASC
LIMIT 5
;

-- FilmCategory_selectByCategoryId
SET @categoryId = 15; -- Sports
EXPLAIN
SELECT fc.film_id,
       f.title AS film_title
FROM film_category AS fc
         JOIN film AS f ON f.film_id = fc.film_id
WHERE fc.category_id = @categoryId
ORDER BY fc.film_id ASC
LIMIT 5
;
