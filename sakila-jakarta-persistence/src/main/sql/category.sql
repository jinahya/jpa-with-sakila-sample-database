-- SHOW CREATE TABLE
SHOW CREATE TABLE category;

-- DESC
DESC category;

-- SELECT *
SELECT *
FROM category
ORDER BY category_id ASC
;

-- --------------------------------------------------------------------------------------------------------- category_id

-- ---------------------------------------------------------------------------------------------------------------- name

-- duplicate names
SELECT name, COUNT(1) AS count
FROM category
GROUP BY name
HAVING count > 1
;

-- --------------------------------------------------------------------------------------------------------- last_update
