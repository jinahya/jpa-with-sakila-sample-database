SET GLOBAL log_bin_trust_function_creators = 1;

use sakila;

-- https://stackoverflow.com/a/3025332/330457

DELIMITER $$

CREATE FUNCTION ACTOR_ID(first_name_ VARCHAR(45), last_name_ VARCHAR(45)) RETURNS SMALLINT UNSIGNED
BEGIN
    DECLARE result SMALLINT UNSIGNED;
    INSERT INTO `actor` (`first_name`, `last_name`)
    SELECT first_name_, last_name_
    WHERE NOT EXISTS(SELECT * FROM `actor` WHERE `last_name` = last_name_ AND `first_name` = first_name_);
    SELECT `actor_id` INTO result FROM `actor` WHERE `last_name` = last_name_ AND `first_name` = first_name_;
    return result;
END $$

CREATE FUNCTION CATEGORY_ID(name_ VARCHAR(25)) RETURNS TINYINT UNSIGNED
BEGIN
    DECLARE result TINYINT UNSIGNED;
    INSERT INTO `category` (`name`)
    SELECT name_
    WHERE NOT EXISTS(SELECT * FROM `category` WHERE `name` = name_);
    SELECT `category_id` INTO result FROM `category` WHERE `name` = name_;
    return result;
END $$

CREATE FUNCTION FILM_ID(title_ VARCHAR(128)) RETURNS SMALLINT UNSIGNED
BEGIN
    DECLARE result SMALLINT UNSIGNED;
    INSERT INTO `film` (`title`)
    SELECT title_
    WHERE NOT EXISTS(SELECT * FROM `film` WHERE `title` = title_);
    SELECT `film_id` INTO result FROM `film` WHERE `title` = title_;
    return result;
END $$

CREATE FUNCTION LANGUAGE_ID(name_ VARCHAR(20)) RETURNS TINYINT UNSIGNED
BEGIN
    DECLARE result TINYINT UNSIGNED;
    INSERT INTO `language` (`name`)
    SELECT name_
    WHERE NOT EXISTS(SELECT * FROM `language` WHERE `name` = name_);
    SELECT `language_id` INTO result FROM `language` WHERE `name` = name_;
    return result;
END $$

DELIMITER ;
