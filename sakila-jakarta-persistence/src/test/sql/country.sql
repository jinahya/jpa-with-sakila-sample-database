-- desc
desc country
;

-- select
SELECT *
FROM country
;

-- Country_findAll
EXPLAIN
SELECT *
FROM country
WHERE country = :country
;

-- Country_findByCountryId
EXPLAIN
SELECT *
FROM country
WHERE country_id = :country_id
;

-- Country_findAllByCountry
EXPLAIN
SELECT *
FROM country
WHERE country = :country
;
