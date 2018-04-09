CREATE TABLE IF NOT EXISTS Card (
  id         BIGSERIAL NOT NULL PRIMARY KEY,
  locked     BOOLEAN   NOT NULL,
  pin        INT       NOT NULL,
  card_id    BIGINT    NOT NULL,
  account_id BIGINT    NOT NULL REFERENCES Account (id)
);


CREATE TABLE IF NOT EXISTS Account (
  id         BIGSERIAL NOT NULL PRIMARY KEY,
  locked     BOOLEAN   NOT NULL,
  balance    DECIMAL   NOT NULL,
  account_id BIGINT    NOT NULL,
  client_id  BIGINT    NOT NULL REFERENCES Client (id)
);

CREATE TABLE IF NOT EXISTS Client (
  id        BIGSERIAL NOT NULL PRIMARY KEY,
  person_id BIGINT    NOT NULL REFERENCES Person (id)
);


CREATE TABLE IF NOT EXISTS ClientNews (
  id        BIGSERIAL NOT NULL PRIMARY KEY,
  news_id   BIGINT    NOT NULL REFERENCES News (id),
  client_id BIGINT    NOT NULL REFERENCES Client (id)
);

CREATE TABLE IF NOT EXISTS News (
  id          BIGSERIAL    NOT NULL PRIMARY KEY,
  admin_id    BIGINT       NOT NULL REFERENCES Admin (id),
  date        TIMESTAMP    NOT NULL,
  title       VARCHAR(100) NOT NULL,
  body        TEXT         NOT NULL,
  news_status VARCHAR(15)  NOT NULL
);

CREATE TABLE IF NOT EXISTS Admin (
  id        BIGSERIAL NOT NULL PRIMARY KEY,
  person_id BIGINT    NOT NULL REFERENCES Person (id)
);


CREATE TABLE IF NOT EXISTS Person (
  id           BIGSERIAL   NOT NULL PRIMARY KEY,
  name         VARCHAR(30) NOT NULL,
  surname      VARCHAR(50) NOT NULL,
  phone_number VARCHAR(20) NOT NULL,
  role_id      VARCHAR(10) NOT NULL
);

CREATE TABLE IF NOT EXISTS Transaction (
  id      BIGSERIAL  NOT NULL PRIMARY KEY,
  date    TIMESTAMP  NOT NULL,
  from_id BIGINT     NOT NULL REFERENCES Account (id),
  to_id   BIGINT     NOT NULL REFERENCES Account (id),
  money   DECIMAL(2) NOT NULL
);

CREATE TABlE IF NOT EXISTS UnlockAccountRequest (
  id         BIGSERIAL NOT NULL PRIMARY KEY,
  account_id BIGINT    NOT NULL REFERENCES Account (id)
);

CREATE TABLE IF NOT EXISTS UnlockCardRequest (
  id      BIGSERIAL NOT NULL PRIMARY KEY,
  card_id BIGINT    NOT NULL REFERENCES Card (id)
);


