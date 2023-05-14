-- desc
desc actor_info
;

-- count
SELECT COUNT(1)
FROM actor_info
;

-- actor_id
SELECT actor_id, COUNT(1) AS c
FROM actor_info
GROUP BY actor_id
HAVING c > 1
ORDER BY c DESC
;
