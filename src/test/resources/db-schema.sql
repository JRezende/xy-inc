-- -----------------------------------------------------
-- Schema xy_inc_db
-- -----------------------------------------------------
DROP SCHEMA `xy_inc_db`;
CREATE SCHEMA IF NOT EXISTS `xy_inc_db` ;
USE `xy_inc_db` ;

-- -----------------------------------------------------
-- Table `xy_inc_db`.`POI`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `xy_inc_db`.`POI` (
  `ID` BIGINT NOT NULL AUTO_INCREMENT,
  `NAME` VARCHAR(100) NOT NULL,
  `COORD_X` BIGINT NOT NULL,
  `COORD_Y` BIGINT NOT NULL,
  PRIMARY KEY (`ID`)); 
  
INSERT INTO xy_inc_db.POI (ID,NAME,COORD_X,COORD_Y) VALUES (1,'Lanchonete',27,12);
INSERT INTO xy_inc_db.POI (ID,NAME,COORD_X,COORD_Y) VALUES (2,'Posto',31,18);
INSERT INTO xy_inc_db.POI (ID,NAME,COORD_X,COORD_Y) VALUES (3,'Joalheria',15,12);
INSERT INTO xy_inc_db.POI (ID,NAME,COORD_X,COORD_Y) VALUES (4,'Floricultura',19,21);
INSERT INTO xy_inc_db.POI (ID,NAME,COORD_X,COORD_Y) VALUES (5,'Pub',12,8);
INSERT INTO xy_inc_db.POI (ID,NAME,COORD_X,COORD_Y) VALUES (6,'Supermercado',23,6);
INSERT INTO xy_inc_db.POI (ID,NAME,COORD_X,COORD_Y) VALUES (7,'Churrascaria',28,2);  