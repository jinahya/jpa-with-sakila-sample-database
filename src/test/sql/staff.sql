-- desc
desc staff
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
