DROP DATABASE if exists fiendFrayDB;

CREATE DATABASE fiendFrayDB;

USE fiendFrayDB;

CREATE TABLE handToDamage (
  weaponID int(11) primary key not null auto_increment,
  nothing int(11) not null,
  onePair int(11) not null,
  twoPair int(11) not null,
  threeKind int(11) not null,
  straight int(11) not null,
  fullHouse int(11) not null,
  fourKind int(11) not null,
  fiveKind int(11) not null
);

INSERT INTO handToDamage (nothing, onePair, twoPair, threeKind, straight, fullHouse, fourKind, fiveKind) VALUES (0, 1, 2, 3, 5, 7, 8, 10);
INSERT INTO handToDamage (nothing, onePair, twoPair, threeKind, straight, fullHouse, fourKind, fiveKind) VALUES (0, 2, 4, 6, 8, 12, 14, 20);
INSERT INTO handToDamage (nothing, onePair, twoPair, threeKind, straight, fullHouse, fourKind, fiveKind) VALUES (0, 3, 5, 7, 9, 14, 16, 24);

CREATE TABLE weapons (
  weaponID int(11) not null,
  price int(11) not null,
  FOREIGN KEY fk1(weaponID) REFERENCES handToDamage(weaponID)
);

INSERT INTO weapons (weaponID, price) VALUES (1, 10);
INSERT INTO weapons (weaponID, price) VALUES (2, 20);
INSERT INTO weapons (weaponID, price) VALUES (3, 40);

CREATE TABLE pets (
  id int(11) primary key not null auto_increment,
  petName varchar(200) not null,
  currentLevel int(11) not null,
  currentXP int(11) not null,
  requiredXPToLevelUp int(11) not null,
  maxHP int(11) not null,
  currentHP int(11) not null,
  weaponID int(11) not null,
  FOREIGN KEY fk1(weaponID) REFERENCES weapons(weaponID)
);

INSERT INTO pets (petName, currentLevel, currentXP, requiredXPToLevelUp, maxHP, currentHP, weaponID) VALUES ('Banana Cat', 1, 0, 100, 30, 30, 1);
INSERT INTO pets (petName, currentLevel, currentXP, requiredXPToLevelUp, maxHP, currentHP, weaponID) VALUES ('Apple Dog', 1, 0, 100, 30, 30, 1);
INSERT INTO pets (petName, currentLevel, currentXP, requiredXPToLevelUp, maxHP, currentHP, weaponID) VALUES ('Silly Birdy', 1, 0, 100, 30, 30, 1);

CREATE TABLE users (
  id int(11) primary key not null auto_increment,
  username varchar(200) not null,
  pass varchar(200) not null,
  gems int(11) not null,
  isGuest boolean,
  FOREIGN KEY fk1(id) REFERENCES pets(id)
);

INSERT INTO users (username, pass, gems, isGuest) VALUES ('nick', 'nick', 10, false);
INSERT INTO users (username, pass, gems, isGuest) VALUES ('test', 'test', 10, false);
INSERT INTO users (username, pass, gems, isGuest) VALUES ('jmiller', 'jmiller', 10, false);