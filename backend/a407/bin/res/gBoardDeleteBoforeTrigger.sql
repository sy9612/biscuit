DROP TRIGGER IF EXISTS `a407`.`group_BEFORE_DELETE`;

DELIMITER $$
USE `a407`$$
CREATE DEFINER=`ssafy`@`%` TRIGGER `group_BEFORE_DELETE` BEFORE DELETE ON `group` FOR EACH ROW BEGIN
    delete from member
    where gId = old.gId;
    
    delete from gBoard
    where gId = old.gId;
END$$
DELIMITER ;
