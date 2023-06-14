-- desc
DESC film
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

-- -------------------------------------------------------------------------------------------------------- release_year

-- (개발)년도별 개수
SELECT release_year, COUNT(1) AS count
FROM film
GROUP BY release_year
ORDER BY release_year
;

-- --------------------------------------------------------------------------------------------------------- language_id

-- 가장 많은 (더빙) 언어
SELECT l.language_id,
       l.name   AS language_name,
       COUNT(1) AS count
FROM film AS f
         JOIN language AS l ON f.language_id = l.language_id
GROUP BY l.language_id
ORDER BY count DESC
LIMIT 5
;

-- ------------------------------------------------------------------------------------------------ original_language_id

-- 가장 많은 원어
SELECT l.language_id,
       l.name   AS language,
       COUNT(1) AS count
FROM film AS f
         JOIN language AS l ON f.original_language_id = l.language_id
GROUP BY l.language_id
ORDER BY count DESC
LIMIT 10
;


-- ----------------------------------------------------------------------------------------------------- rental_duration

-- 가장 긴 대여기간 순
SELECT *
FROM film
ORDER BY rental_duration DESC, film_id ASC
LIMIT 5
;

-- 가장 짧은 대여기간 순
SELECT *
FROM film
ORDER BY rental_duration ASC, film_id ASC
LIMIT 5
;

-- --------------------------------------------------------------------------------------------------------- rental_rate

-- MIN/MAX
SELECT MIN(rental_rate) AS min_rental_rate,
       MAX(rental_rate) AS max_rental_rate
FROM film
;

-- 대여료별 개수F
SELECT rental_rate,
       COUNT(1) AS count
FROM film
GROUP BY rental_rate
ORDER BY rental_rate ASC
;

-- -------------------------------------------------------------------------------------------------------------- length

-- 가장 짤은 러닝타임
SELECT film_id,
       title,
       length
FROM film
ORDER BY length ASC
LIMIT 5
;

-- 가장 긴 러닝타임
SELECT film_id,
       title,
       length
FROM film
ORDER BY length DESC
LIMIT 5
;

-- ---------------------------------------------------------------------------------------------------- replacement_cost

-- MIN/MAX
SELECT MIN(replacement_cost) AS max_replacement_cost,
       MAX(replacement_cost) AS max_replacement_cost
FROM film
;

-- 교체 비용이 가장 싼 영화(DVD)들
SELECT *
FROM film
WHERE replacement_cost = (SELECT MIN(replacement_cost) FROM film)
ORDER BY film_id ASC
;

-- 교체 비용이 가장 비싼 영화(DVD)들
SELECT *
FROM film
WHERE replacement_cost = (SELECT MAX(replacement_cost) FROM film)
ORDER BY film_id ASC
;

-- -------------------------------------------------------------------------------------------------------------- rating

-- 온 가족 이 다같이 볼 수 있는 영화(DVD)들
SELECT *
FROM film
WHERE rating = 'G'
;

-- 부모의 인도(guidance) 하에, 혹은 온 가족 이 다같이 볼 수 있는 영화(DVD)들
SELECT *
FROM film
WHERE rating = 'G'
   OR rating = 'PG'
;

-- ---------------------------------------------------------------------------------------------------- special_features

-- 삭제(Deleted) 장면(Scenes)과 주석(Commentaries)이 포함된 영화(DVD)들
SELECT *
FROM film
WHERE FIND_IN_SET('Commentaries', special_features) -- 2
  AND FIND_IN_SET('Deleted Scenes', special_features) -- 4
;

-- 삭제 장면과 주석만 있는 영화(DVD)들
SELECT *
FROM film
WHERE special_features = 12;
;
