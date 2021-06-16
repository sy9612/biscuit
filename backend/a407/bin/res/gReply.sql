CREATE TABLE `a407`.`gReply` (
  `rId` INT NOT NULL AUTO_INCREMENT,
  `bId` INT NOT NULL,
  `email` VARCHAR(30) NOT NULL,
  `nickname` VARCHAR(20) NOT NULL,
  `contents` VARCHAR(300) NOT NULL,
  `date` DATETIME NOT NULL,
  PRIMARY KEY (`rId`),
  UNIQUE INDEX `rId_UNIQUE` (`rId` ASC) VISIBLE,
  INDEX `fk_bId_idx` (`bId` ASC) VISIBLE,
  INDEX `fk_email_idx` (`email` ASC) VISIBLE,
  INDEX `fk_nickname_idx` (`nickname` ASC) VISIBLE,
  CONSTRAINT `fk_gReply_bId`
    FOREIGN KEY (`bId`)
    REFERENCES `a407`.`gBoard` (`bId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci
COMMENT = 'reply for Group Board';
