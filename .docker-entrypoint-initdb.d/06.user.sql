use sakila;


-- ---------------------------------------------------------------------------------------------------------------------
SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0;
INSERT INTO `country` (`country`)
VALUES ('The Country for Old Men');
SET @the_country_id = LAST_INSERT_ID();
INSERT INTO `city` (`city`, `country_id`)
VALUES ('Ensaio sobre a Cegueira', @the_country_id);
SET @the_city_id = LAST_INSERT_ID();
INSERT INTO `address`(`address`, `district`, `city_id`, `phone`, `location`)
VALUES ('', '', @the_city_id, '', /*!50705 0x000000000101000000000000000000F03F000000000000F0BF */);
SET @the_address_id = LAST_INSERT_ID();
INSERT INTO `staff` (`first_name`, `last_name`, `address_id`, `store_id`, `username`) VALUES ('José', 'Saramago', @the_address_id, 0, 'whoami');
SET @the_staff_id = LAST_INSERT_ID();
INSERT INTO `store` (`manager_staff_id`, `address_id`) VALUES (@the_staff_id, @the_address_id);
SET @the_store_id = LAST_INSERT_ID();
UPDATE `staff` SET `store_id` = @the_store_id WHERE `staff_id` = @the_staff_id;
SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS
;

-- ---------------------------------------------------------------------------------------------------------------------
-- Citizen Kane
-- https://www.imdb.com/title/tt0033467/
SET @film_title = 'Citizen Kane';
INSERT INTO film(`title`, `description`, `release_year`, `language_id`, `length`, `rating`)
VALUES (@film_title,
        'Following the death of publishing tycoon Charles Foster Kane,
        reporters scramble to uncover the meaning of his final utterance: \'Rosebud.\'',
        1941,
        LANGUAGE_ID('English'),
        119,
        'PG');
SET @film_id = FILM_ID(@film_title);
INSERT INTO film_category (film_id, category_id)
VALUES (@film_id, CATEGORY_ID('Drama'));
INSERT INTO film_category (film_id, category_id)
VALUES (@film_id, CATEGORY_ID('Mystery'));
INSERT INTO film_actor(`actor_id`, `film_id`)
VALUES (ACTOR_ID('Orson', 'Welles'), @film_id);
INSERT INTO film_actor(`actor_id`, `film_id`)
VALUES (ACTOR_ID('Joseph', 'Cotten'), @film_id);

-- ---------------------------------------------------------------------------------------------------------------------
-- The Shawshank Redemption
-- https://www.imdb.com/title/tt0111161/
SET @film_title = 'The Shawshank Redemption';
INSERT INTO film(`title`, `description`, `release_year`, `language_id`, `length`, `rating`)
VALUES (@film_title,
        'Over the course of several years, two convicts form a friendship, seeking consolation and, eventually, redemption through basic compassion.',
        1994,
        LANGUAGE_ID('English'),
        142,
        'R');
SET @film_id = FILM_ID(@film_title);
INSERT INTO film_category (film_id, category_id)
VALUES (@film_id, CATEGORY_ID('Drama'));
INSERT INTO film_actor(`actor_id`, `film_id`)
VALUES (ACTOR_ID('Tim', 'Robbins'), @film_id);
INSERT INTO film_actor(`actor_id`, `film_id`)
VALUES (ACTOR_ID('Morgan', 'Freeman'), @film_id);
INSERT INTO film_actor(`actor_id`, `film_id`)
VALUES (ACTOR_ID('Bob', 'Gunton'), @film_id);
INSERT INTO film_actor(`actor_id`, `film_id`)
VALUES (ACTOR_ID('William', 'Sadler'), @film_id);
INSERT INTO film_actor(`actor_id`, `film_id`)
VALUES (ACTOR_ID('Clancy', 'Brown'), @film_id);

-- ---------------------------------------------------------------------------------------------------------------------
-- Thor: Ragnarok
-- https://www.imdb.com/title/tt3501632/
SET @film_title = 'Thor: Ragnarok';
INSERT INTO film(`title`, `description`, `release_year`, `language_id`, `length`, `rating`)
VALUES (@film_title,
        'Imprisoned on the planet Sakaar, Thor must race against time to return to Asgard and stop Ragnarök, the destruction of his world, at the hands of the powerful and ruthless villain Hela.',
        2017,
        LANGUAGE_ID('English'),
        130,
        'PG-13');
SET @film_id = FILM_ID(@film_title);
INSERT INTO film_category (film_id, category_id)
VALUES (@film_id, CATEGORY_ID('Action'));
INSERT INTO film_category (film_id, category_id)
VALUES (@film_id, CATEGORY_ID('Adventure'));
INSERT INTO film_category (film_id, category_id)
VALUES (@film_id, CATEGORY_ID('Comedy'));
INSERT INTO film_actor(`actor_id`, `film_id`)
VALUES (ACTOR_ID('Criss', 'Hemsworth'), @film_id);
INSERT INTO film_actor(`actor_id`, `film_id`)
VALUES (ACTOR_ID('Tom', 'Hiddleston'), @film_id);
INSERT INTO film_actor(`actor_id`, `film_id`)
VALUES (ACTOR_ID('Cate', 'Blanchett'), @film_id);
INSERT INTO film_actor(`actor_id`, `film_id`)
VALUES (ACTOR_ID('Mark', 'Ruffalo'), @film_id);
INSERT INTO film_actor(`actor_id`, `film_id`)
VALUES (ACTOR_ID('Idris', 'Elba'), @film_id);
INSERT INTO film_actor(`actor_id`, `film_id`)
VALUES (ACTOR_ID('Jeff', 'Goldblum'), @film_id);
INSERT INTO film_actor(`actor_id`, `film_id`)
VALUES (ACTOR_ID('Tessa', 'Thompson'), @film_id);
INSERT INTO film_actor(`actor_id`, `film_id`)
VALUES (ACTOR_ID('Karl', 'Urban'), @film_id);
INSERT INTO film_actor(`actor_id`, `film_id`)
VALUES (ACTOR_ID('Anthony', 'Hopkins'), @film_id);
INSERT INTO film_actor(`actor_id`, `film_id`)
VALUES (ACTOR_ID('Benedict', 'Cumberbatch'), @film_id);
INSERT INTO film_actor(`actor_id`, `film_id`)
VALUES (ACTOR_ID('Taika', 'Waititi'), @film_id);
INSERT INTO film_actor(`actor_id`, `film_id`)
VALUES (ACTOR_ID('Rachel', 'House'), @film_id);
INSERT INTO film_actor(`actor_id`, `film_id`)
VALUES (ACTOR_ID('Clancy', 'Brown'), @film_id);

-- ---------------------------------------------------------------------------------------------------------------------
-- Gone with the Wind
-- https://www.imdb.com/title/tt0031381/
SET @film_title = 'Gone with the Wind';
INSERT INTO film(`title`, `description`, `release_year`, `language_id`, `length`, `rating`)
VALUES (@film_title,
        'American motion picture classic in which a manipulative woman and a roguish man conduct a turbulent romance during the Civil War and Reconstruction periods.',
        1939,
        LANGUAGE_ID('English'),
        238,
        'PG');
SET @film_id = FILM_ID(@film_title);
INSERT INTO film_category (film_id, category_id)
VALUES (@film_id, CATEGORY_ID('Drama'));
INSERT INTO film_category (film_id, category_id)
VALUES (@film_id, CATEGORY_ID('Romance'));
INSERT INTO film_category (film_id, category_id)
VALUES (@film_id, CATEGORY_ID('War'));
INSERT INTO film_actor(`actor_id`, `film_id`)
VALUES (ACTOR_ID('Clark', 'Gable'), @film_id);
INSERT INTO film_actor(`actor_id`, `film_id`)
VALUES (ACTOR_ID('Vivien', 'Leigh'), @film_id);

-- ---------------------------------------------------------------------------------------------------------------------
-- Lawrence of Arabia
-- https://www.imdb.com/title/tt0056172/
SET @film_title = 'Lawrence of Arabia';
INSERT INTO film(`title`, `description`, `release_year`, `language_id`, `length`, `rating`)
VALUES (@film_title,
        'The story of T.E. Lawrence, the English officer who successfully united and led the diverse, often warring, Arab tribes during World War I in order to fight the Turks.',
        1962,
        LANGUAGE_ID('English'),
        218,
        'PG');
SET @film_id = FILM_ID(@film_title);
INSERT INTO film_category (film_id, category_id)
VALUES (@film_id, CATEGORY_ID('Adventure'));
INSERT INTO film_category (film_id, category_id)
VALUES (@film_id, CATEGORY_ID('Biography'));
INSERT INTO film_category (film_id, category_id)
VALUES (@film_id, CATEGORY_ID('Drama'));
INSERT INTO film_actor(`actor_id`, `film_id`)
VALUES (ACTOR_ID('Peter', 'O\'Toole'), @film_id);
INSERT INTO film_actor(`actor_id`, `film_id`)
VALUES (ACTOR_ID('Alec', 'Guinness'), @film_id);

-- ---------------------------------------------------------------------------------------------------------------------
-- Star Wars
-- https://www.imdb.com/title/tt0076759/
SET @film_title = 'Star Wars';
INSERT INTO film(`title`, `description`, `release_year`, `language_id`, `length`, `rating`)
VALUES (@film_title,
        'Luke Skywalker joins forces with a Jedi Knight, a cocky pilot, a Wookiee and two droids to save the galaxy from the Empire''s world-destroying battle station, while also attempting to rescue Princess Leia from the mysterious Darth Vader.',
        1977,
        LANGUAGE_ID('English'),
        121,
        'G');
SET @film_id = FILM_ID(@film_title);
INSERT INTO film_category (film_id, category_id)
VALUES (@film_id, CATEGORY_ID('Action'));
INSERT INTO film_category (film_id, category_id)
VALUES (@film_id, CATEGORY_ID('Adventure'));
INSERT INTO film_category (film_id, category_id)
VALUES (@film_id, CATEGORY_ID('Fantasy'));
INSERT INTO film_actor(`actor_id`, `film_id`)
VALUES (ACTOR_ID('Mark', 'Hamill'), @film_id);
INSERT INTO film_actor(`actor_id`, `film_id`)
VALUES (ACTOR_ID('Harrison', 'Ford'), @film_id);
INSERT INTO film_actor(`actor_id`, `film_id`)
VALUES (ACTOR_ID('Carrie', 'Fisher'), @film_id);
INSERT INTO film_actor(`actor_id`, `film_id`)
VALUES (ACTOR_ID('Alec', 'Guinness'), @film_id);

-- ---------------------------------------------------------------------------------------------------------------------
-- Indiana Jones and the Dial of Destiny
-- https://www.imdb.com/title/tt1462764/
SET @film_title = 'Indiana Jones and the Dial of Destiny';
INSERT INTO film(`title`, `description`, `release_year`, `language_id`, `length`, `rating`)
VALUES (@film_title,
        'Archaeologist Indiana Jones races against time to retrieve a legendary artifact that can change the course of history.',
        2023,
        LANGUAGE_ID('English'),
        144,
        'PG-13');
SET @film_id = FILM_ID(@film_title);
INSERT INTO film_category (film_id, category_id)
VALUES (@film_id, CATEGORY_ID('Action'));
INSERT INTO film_category (film_id, category_id)
VALUES (@film_id, CATEGORY_ID('Adventure'));
INSERT INTO film_actor(`actor_id`, `film_id`)
VALUES (ACTOR_ID('Harrison', 'Ford'), @film_id);
INSERT INTO film_actor(`actor_id`, `film_id`)
VALUES (ACTOR_ID('Mads', 'Mikkelsen'), @film_id);

-- ---------------------------------------------------------------------------------------------------------------------
-- 하녀 The Housemaid, 下女, 1960
-- https://movie.daum.net/moviedb/main?movieId=1632
SET @film_title = '하녀';
INSERT INTO film(`title`, `description`, `release_year`, `language_id`, `length`, `rating`)
VALUES (@film_title,
        '헌신적인 가장 동식, 아내를 위해 젊은 여인을 하녀로 맞이하다',
        1960,
        LANGUAGE_ID('Korean'),
        108,
        NULL);
SET @film_id = FILM_ID(@film_title);
INSERT INTO film_category (film_id, category_id)
VALUES (@film_id, CATEGORY_ID('스릴러'));
INSERT INTO film_actor(`actor_id`, `film_id`)
VALUES (ACTOR_ID('진규', '김'), @film_id);
INSERT INTO film_actor(`actor_id`, `film_id`)
VALUES (ACTOR_ID('증녀', '주'), @film_id);
INSERT INTO film_actor(`actor_id`, `film_id`)
VALUES (ACTOR_ID('은심', '이'), @film_id);
INSERT INTO film_actor(`actor_id`, `film_id`)
VALUES (ACTOR_ID('앵란', '엄'), @film_id);
INSERT INTO film_actor(`actor_id`, `film_id`)
VALUES (ACTOR_ID('성기', '안'), @film_id);

-- ---------------------------------------------------------------------------------------------------------------------
-- 七人の侍
-- https://www.imdb.com/title/tt0047478/
SET @film_title = '七人の侍';
INSERT INTO film(`title`, `description`, `release_year`, `language_id`, `length`, `rating`)
VALUES (@film_title,
        'Farmers from a village exploited by bandits hire a veteran samurai for protection, who gathers six other samurai to join him.',
        1954,
        LANGUAGE_ID('Japanese'),
        207,
        'PG');
SET @film_id = FILM_ID(@film_title);
INSERT INTO film_category (film_id, category_id)
VALUES (@film_id, CATEGORY_ID('Action'));
INSERT INTO film_category (film_id, category_id)
VALUES (@film_id, CATEGORY_ID('Drama'));
INSERT INTO film_actor(`actor_id`, `film_id`)
VALUES (ACTOR_ID('敏郎', '三船'), @film_id);
INSERT INTO film_actor(`actor_id`, `film_id`)
VALUES (ACTOR_ID('喬', '志村'), @film_id);
