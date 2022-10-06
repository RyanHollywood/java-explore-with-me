CREATE TABLE IF NOT EXISTS categories
(
    id INT8 NOT NULL AUTO_INCREMENT,
    name TINYTEXT,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS users
(
    id INT8 NOT NULL AUTO_INCREMENT,
    name TINYTEXT,
    email TINYTEXT,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS locations
(
    id INT8 NOT NULL AUTO_INCREMENT,
    lat FLOAT,
    lon FLOAT,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS events
(
    id INT8 NOT NULL AUTO_INCREMENT,
    annotation TINYTEXT,
    category_id INT8,
    create_on DATETIME,
    confirmedRequests INT8,
    description MEDIUMTEXT,
    eventDate DATETIME,
    initiator_id INT8,
    location_id INT8,
    paid BOOLEAN,
    participantLimit INT4,
    publishedOn TINYTEXT,
    requestModeration BOOLEAN,
    state TINYTEXT,
    title TINYTEXT,
    views TINYTEXT,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS compilations
(
    id INT8 NOT NULL AUTO_INCREMENT,
    pinned BOOLEAN,
    title TINYTEXT,
    PRIMARY KEY (id)
);