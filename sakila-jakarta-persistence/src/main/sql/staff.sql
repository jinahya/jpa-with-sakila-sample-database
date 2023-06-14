-- desc
DESC staff
;

-- select
SELECT *
FROM staff
;


-- active
SELECT DISTINCT active
FROM staff
;

-- password
SELECT DISTINCT CHAR_LENGTH(password)
FROM staff
;

-- cities and countries
SELECT s.staff_id, c.city_id, c.city, c2.country_id, c2.country
FROM staff AS s
         JOIN address AS a ON s.address_id = a.address_id
         JOIN city AS c ON c.city_id = a.city_id
         JOIN country AS c2 ON c.country_id = c2.country_id
;

--
EXPLAIN
SELECT *
FROM staff AS s
         JOIN address AS a ON s.address_id = a.address_id
;


-- Staff_findAllByCity
select s.staff_id,
       s.active,
       s.address_id,
       a.address_id,
       a.address,
       a.address2,
       a.city_id,
       c.city_id,
       c.city,
       c.country_id,
       c.last_update,
       a.district,
       a.last_update,
       a.location,
       a.phone,
       a.postal_code,
       s.email,
       s.first_name,
       s.last_name,
       s.last_update,
       s.password,
       s.picture,
       s.store_id,
       s.username
from staff s
         join address AS a ON a.address_id = s.address_id
         join city AS c ON c.city_id = a.city_id
where c.city_id = ?
  and s.staff_id > ?
order by s.staff_id
limit ?
;
