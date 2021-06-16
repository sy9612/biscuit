DROP TRIGGER IF EXISTS `a407`.`group_AFTER_INSERT`;

DELIMITER $$
USE `a407`$$
CREATE DEFINER = CURRENT_USER TRIGGER `a407`.`group_AFTER_INSERT` AFTER INSERT ON `group` FOR EACH ROW
BEGIN
    insert into member
    set
        email = new.email,
        nickname = new.nickname,
        gId = new.gId,
        permission = 3
        ;
END$$
DELIMITER ;