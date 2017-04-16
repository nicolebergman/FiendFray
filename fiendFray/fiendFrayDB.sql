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
  flushHand int(11) not null,
  fullHouse int(11) not null,
  fourKind int(11) not null,
  straightFlush int(11) not null,
  fiveKind int(11) not null,
  royalFlush int(11) not null
);

INSERT INTO handToDamage (nothing, onePair, twoPair, threeKind, straight, flushHand, fullHouse, fourKind, straightFlush, fiveKind, royalFlush) VALUES (0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
INSERT INTO handToDamage (nothing, onePair, twoPair, threeKind, straight, flushHand, fullHouse, fourKind, straightFlush, fiveKind, royalFlush) VALUES (0, 2, 4, 6, 8, 10, 12, 14, 16, 18, 20);
INSERT INTO handToDamage (nothing, onePair, twoPair, threeKind, straight, flushHand, fullHouse, fourKind, straightFlush, fiveKind, royalFlush) VALUES (0, 3, 6, 9, 12, 15, 18, 21, 24, 27, 30);
INSERT INTO handToDamage (nothing, onePair, twoPair, threeKind, straight, flushHand, fullHouse, fourKind, straightFlush, fiveKind, royalFlush) VALUES (0, 4, 8, 12, 16, 20, 24, 28, 31, 36, 40);
INSERT INTO handToDamage (nothing, onePair, twoPair, threeKind, straight, flushHand, fullHouse, fourKind, straightFlush, fiveKind, royalFlush) VALUES (0, 5, 10, 15, 20, 25, 30, 35, 40, 45, 50);
INSERT INTO handToDamage (nothing, onePair, twoPair, threeKind, straight, flushHand, fullHouse, fourKind, straightFlush, fiveKind, royalFlush) VALUES (0, 6, 12, 18, 24, 30, 36, 42, 48, 54, 60);

CREATE TABLE weapons (
  weaponID int(11) not null,
  price int(11) not null,
  FOREIGN KEY fk1(weaponID) REFERENCES handToDamage(weaponID)
);

INSERT INTO weapons (weaponID, price) VALUES (1, 10);
INSERT INTO weapons (weaponID, price) VALUES (2, 20);
INSERT INTO weapons (weaponID, price) VALUES (3, 40);
INSERT INTO weapons (weaponID, price) VALUES (4, 80);
INSERT INTO weapons (weaponID, price) VALUES (5, 160);
INSERT INTO weapons (weaponID, price) VALUES (6, 320);

CREATE TABLE pets (
  id int(11) primary key not null auto_increment,
  petID int(11) not null,
  petName varchar(200) not null,
  currentLevel int(11) not null,
  currentXP int(11) not null,
  requiredXPToLevelUp int(11) not null,
  maxHP int(11) not null,
  currentHP int(11) not null,
  weaponID int(11) not null,
  FOREIGN KEY fk1(weaponID) REFERENCES weapons(weaponID)
);

INSERT INTO pets (petID, petName, currentLevel, currentXP, requiredXPToLevelUp, maxHP, currentHP, weaponID) VALUES (3, 'Banana Cat', 1, 0, 100, 35, 35, 1);
INSERT INTO pets (petID, petName, currentLevel, currentXP, requiredXPToLevelUp, maxHP, currentHP, weaponID) VALUES (2, 'Apple Dog', 1, 0, 100, 40, 40, 1);
INSERT INTO pets (petID, petName, currentLevel, currentXP, requiredXPToLevelUp, maxHP, currentHP, weaponID) VALUES (1, 'Silly Birdy', 1, 0, 100, 30, 30, 1);

CREATE TABLE users (
  id int(11) primary key not null auto_increment,
  username varchar(200) not null,
  pass varchar(200) not null,
  gems int(11) not null,
  isGuest boolean,
  isOnline boolean,
  FOREIGN KEY fk1(id) REFERENCES pets(id)
);

INSERT INTO users (username, pass, gems, isGuest, isOnline) VALUES ('nick', 'nick', 15, false, false);
INSERT INTO users (username, pass, gems, isGuest, isOnline) VALUES ('test', 'test', 40, false, false);
INSERT INTO users (username, pass, gems, isGuest, isOnline) VALUES ('jmiller', 'jmiller', 20, false, false);