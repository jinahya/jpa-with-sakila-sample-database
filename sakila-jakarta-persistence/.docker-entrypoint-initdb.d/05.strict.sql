use sakila;

CREATE TABLE IF NOT EXISTS `strict_language`
(
    `id`        BIGINT  NOT NULL AUTO_INCREMENT,
    `iso_639_1` CHAR(2) NULL COMMENT 'ISO 639-1 two letter code',
    `iso_639_2` CHAR(3) NULL COMMENT 'ISO 639-2 three letter code',
    PRIMARY KEY (`id`),
    UNIQUE INDEX `iso_639_1_UNIQUE` (`iso_639_1` ASC) VISIBLE,
    UNIQUE INDEX `iso_639_2_UNIQUE` (`iso_639_2` ASC) VISIBLE,
    UNIQUE INDEX `iso_639_UNIQUE` (`iso_639_1` ASC, `iso_639_2` ASC) VISIBLE
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `strict_country`
(
    `id`           BIGINT  NOT NULL AUTO_INCREMENT,
    `iso_3166_1_2` CHAR(2) NULL COMMENT 'ISO 3166-1 alpha-2 two letter code',
    `iso_3166_1_3` CHAR(3) NULL COMMENT 'ISO 3166-1 alpha-3 three letter code',
    PRIMARY KEY (`id`),
    UNIQUE INDEX `iso_3166_1_2_UNIQUE` (`iso_3166_1_2` ASC) VISIBLE,
    UNIQUE INDEX `iso_3166_1_3_UNIQUE` (`iso_3166_1_3` ASC) VISIBLE,
    UNIQUE INDEX `iso_3166_1_UNIQUE` (`iso_3166_1_2` ASC, `iso_3166_1_3` ASC) VISIBLE
) ENGINE = InnoDB;
