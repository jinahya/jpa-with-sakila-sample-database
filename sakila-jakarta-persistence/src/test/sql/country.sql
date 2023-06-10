-- desc
desc country
;

-- select
SELECT *
FROM country
;

-- Country_findByCountryId
EXPLAIN
SELECT *
FROM country
WHERE country_id = :country_id
;

-- Country_findAll
EXPLAIN
SELECT *
FROM country
ORDER BY country_id ASC
LIMIT :offset,:limit
;
EXPLAIN
SELECT *
FROM country
WHERE country_id > :countryIdMinExclusive
ORDER BY country_id ASC
LIMIT :limit
;

-- Country_findAllByCountry
EXPLAIN
SELECT *
FROM country
WHERE country = :country
ORDER BY country_id ASC
;
