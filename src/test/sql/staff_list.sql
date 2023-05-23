-- desc
desc staff_list
;

-- select
SELECT *
FROM staff_list
;

-- can we use the `ID` column as an @ID?
SELECT ID, COUNT(1) AS c
FROM staff_list
GROUP BY ID
HAVING c > 1
;
