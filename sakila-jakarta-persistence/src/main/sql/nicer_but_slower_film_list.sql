-- desc
DESC nicer_but_slower_film_list
;

-- is FID unique?
SELECT FID, COUNT(1) AS count
FROM nicer_but_slower_film_list
GROUP BY FID
HAVING count > 1
;
