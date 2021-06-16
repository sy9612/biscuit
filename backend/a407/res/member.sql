CREATE TABLE `member` (
  `mId` int NOT NULL AUTO_INCREMENT,
  `email` varchar(30) NOT NULL,
  `nickname` varchar(20) NOT NULL,
  `gId` int NOT NULL,
  `permission` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`mId`),
  UNIQUE KEY `mId_UNIQUE` (`mId`),
  KEY `fk_member_gId_idx` (`gId`),
  KEY `fk_member_email_idx` (`email`),
  KEY `fk_member_nickname_idx` (`nickname`),
  CONSTRAINT `fk_member_email` FOREIGN KEY (`email`) REFERENCES `user` (`email`),
  CONSTRAINT `fk_member_nickname` FOREIGN KEY (`nickname`) REFERENCES `user` (`nickname`),
  CONSTRAINT `fk_member_gId` FOREIGN KEY (`gId`) REFERENCES `group` (`gId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;