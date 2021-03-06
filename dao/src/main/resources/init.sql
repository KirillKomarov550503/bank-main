CREATE TABLE IF NOT EXISTS Person (
  id           BIGSERIAL   NOT NULL PRIMARY KEY,
  name         VARCHAR(30) NOT NULL,
  surname      VARCHAR(50) NOT NULL,
  login        VARCHAR(30),
  password     VARCHAR(30),
  phone_number VARCHAR(20) NOT NULL,
  passport_id  BIGINT      NOT NULL,
  role         VARCHAR(10) NOT NULL
);

CREATE TABLE IF NOT EXISTS Client (
  id        BIGSERIAL NOT NULL PRIMARY KEY,
  person_id BIGINT    NOT NULL
);

CREATE TABLE IF NOT EXISTS Account (
  id         BIGSERIAL NOT NULL PRIMARY KEY,
  locked     BOOLEAN   NOT NULL,
  balance    DECIMAL   NOT NULL,
  account_id BIGINT    NOT NULL,
  client_id  BIGINT    NOT NULL
);

CREATE TABLE IF NOT EXISTS Card (
  id         BIGSERIAL NOT NULL PRIMARY KEY,
  locked     BOOLEAN   NOT NULL,
  pin        INT       NOT NULL,
  card_id    BIGINT    NOT NULL,
  account_id BIGINT    NOT NULL
);


CREATE TABLE IF NOT EXISTS Admin (
  id        BIGSERIAL NOT NULL PRIMARY KEY,
  person_id BIGINT    NOT NULL
);

CREATE TABLE IF NOT EXISTS News (
  id          BIGSERIAL     NOT NULL PRIMARY KEY,
  admin_id    BIGINT        NOT NULL,
  date        VARCHAR(30)   NOT NULL,
  title       VARCHAR(100)  NOT NULL,
  body        VARCHAR(1000) NOT NULL,
  news_status VARCHAR(15)   NOT NULL
);

CREATE TABLE IF NOT EXISTS ClientNews (
  id        BIGSERIAL NOT NULL PRIMARY KEY,
  news_id   BIGINT    NOT NULL,
  client_id BIGINT    NOT NULL
);


CREATE TABLE IF NOT EXISTS Transaction (
  id      BIGSERIAL   NOT NULL PRIMARY KEY,
  date    VARCHAR(30) NOT NULL,
  from_id BIGINT      NOT NULL,
  to_id   BIGINT      NOT NULL,
  money   DECIMAL(2)  NOT NULL
);

CREATE TABlE IF NOT EXISTS Request (
  id         BIGSERIAL   NOT NULL PRIMARY KEY,
  request_id BIGINT      NOT NULL,
  type       VARCHAR(20) NOT NULL
);


