CREATE TABLE IF NOT EXISTS hits
(
    id INT8 NOT NULL AUTO_INCREMENT,
    app       TINYTEXT,
    uri       TINYTEXT,
    ip        TINYTEXT,
    timestamp DATETIME,
    PRIMARY KEY (id)
);