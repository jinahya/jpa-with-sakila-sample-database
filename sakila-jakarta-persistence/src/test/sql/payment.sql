-- desc
desc payment
;

-- select
SELECT *
FROM payment
;

-- customer's store - staff's store
SELECT c.store_id, s.store_id, p.payment_date
FROM payment AS p
         JOIN customer AS c ON p.customer_id = c.customer_id
         JOIN staff AS s ON p.staff_id = s.staff_id
WHERE c.store_id <> s.store_id
;

-- customers with total amount
SELECT c.customer_id, SUM(amount) AS total_amount, c.first_name, c.last_name
FROM payment AS p
         JOIN customer AS c ON p.customer_id = c.customer_id
         JOIN store AS s ON c.store_id = s.store_id
GROUP BY c.customer_id
HAVING total_amount > 170
ORDER BY total_amount DESC
;

-- staffs with with total amount
SELECT p.staff_id, SUM(amount) AS total_amount, s.first_name, s.last_name
FROM payment AS p
         JOIN staff AS s ON p.staff_id = s.staff_id
GROUP BY p.staff_id
ORDER BY total_amount DESC
;
