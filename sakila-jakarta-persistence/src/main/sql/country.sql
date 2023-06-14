-- desc
DESC country
;

-- select
SELECT *
FROM country
;

-- ---------------------------------------------------------------------------------------------------------- country_id

-- ------------------------------------------------------------------------------------------------------------- country

-- --------------------------------------------------------------------------------------------------------- last_update

-- Country_selectByCountryId
EXPLAIN
SELECT *
FROM country
WHERE country_id = :country_id
;

-- Country_selectAll
-- using offset,limit
EXPLAIN
SELECT *
FROM country
ORDER BY country_id ASC
LIMIT :offset,:limit
;
-- using the keyset pagination
EXPLAIN
SELECT *
FROM country
WHERE country_id > 5
ORDER BY country_id ASC
LIMIT 5
;
-- the first page using the rowset pagination
EXPLAIN
SELECT *
FROM country
ORDER BY country_id ASC
LIMIT 0,5
;
-- the second page using the rowset pagination
EXPLAIN
SELECT *
FROM country
ORDER BY country_id ASC
LIMIT 5,5
;
-- the first page using the keyset pagination
EXPLAIN
SELECT *
FROM country
WHERE country_id > 0
ORDER BY country_id ASC
LIMIT 5
;
-- the second page using the keyset pagination
EXPLAIN
SELECT *
FROM country
WHERE country_id > 5
ORDER BY country_id ASC
LIMIT 5
;

-- Country_selectAllByCountry
EXPLAIN
SELECT *
FROM country
WHERE country = :country
ORDER BY country_id ASC
;
