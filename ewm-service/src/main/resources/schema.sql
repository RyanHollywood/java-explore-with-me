CREATE TABLE IF NOT EXISTS category
(
    id   INT8 NOT NULL AUTO_INCREMENT,
    name TINYTEXT,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS user
(
    id    INT8 NOT NULL AUTO_INCREMENT,
    name  TINYTEXT,
    email TINYTEXT,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS location
(
    id  INT8 NOT NULL AUTO_INCREMENT,
    lat FLOAT,
    lon FLOAT,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS event
(
    id INT8 NOT NULL AUTO_INCREMENT,
    annotation        TINYTEXT,
    category_id       INT8,
    create_on         DATETIME,
    confirmedRequests INT8,
    description       MEDIUMTEXT,
    eventDate         DATETIME,
    initiator_id      INT8,
    location_id       INT8,
    paid              BOOLEAN,
    participantLimit  INT4,
    publishedOn       TINYTEXT,
    requestModeration BOOLEAN,
    state             TINYTEXT,
    title             TINYTEXT,
    views             TINYTEXT,
    PRIMARY KEY (id),
    CONSTRAINT FOREIGN KEY (category_id) REFERENCES category (id),
    CONSTRAINT FOREIGN KEY (initiator_id) REFERENCES user(id),
    CONSTRAINT FOREIGN KEY (location_id) REFERENCES location(id)
);

CREATE TABLE IF NOT EXISTS compilation
(
    id     INT8 NOT NULL AUTO_INCREMENT,
    pinned BOOLEAN,
    title  TINYTEXT,
    PRIMARY KEY (id)
);