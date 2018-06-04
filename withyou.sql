/*
Navicat MySQL Data Transfer

Source Server         : 47.93.185.32_3306
Source Server Version : 50096
Source Host           : 47.93.185.32:3306
Source Database       : withyou

Target Server Type    : MYSQL
Target Server Version : 50096
File Encoding         : 65001

Date: 2018-05-30 09:36:23
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_assignment
-- ----------------------------
DROP TABLE IF EXISTS `t_assignment`;
CREATE TABLE `t_assignment` (
`id`  int(255) NOT NULL AUTO_INCREMENT ,
`assid`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' ,
`title`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`content`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`classid`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`uptime`  timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP ,
PRIMARY KEY (`id`, `assid`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
/*!50003 AUTO_INCREMENT=10 */

;

-- ----------------------------
-- Records of t_assignment
-- ----------------------------
BEGIN;
INSERT INTO `t_assignment` VALUES ('7', '1ae8e017-4f47-4282-9a87-38ef8b63e7a7', '第一次作业', '第一章作业全部完成', '1ae8e017-4f47-4282-9a87-38ef8b63e7a7', '2018-05-25 01:25:23'), ('8', '1632ca26-a811-41fb-9b18-5d35ea116a9e', '第二次作业', '安静思考两地分居阿斯顿发', '1ae8e017-4f47-4282-9a87-38ef8b63e7a7', null), ('9', '79b5d596-72b9-467d-ad12-1e9b9adf741d', '第三次作业', '作业内容', '1ae8e017-4f47-4282-9a87-38ef8b63e7a7', null);
COMMIT;

-- ----------------------------
-- Table structure for t_classinfo
-- ----------------------------
DROP TABLE IF EXISTS `t_classinfo`;
CREATE TABLE `t_classinfo` (
`id`  int(255) NOT NULL AUTO_INCREMENT ,
`uuid`  varchar(55) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0' ,
`imgpath`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`classname`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`teacherid`  varchar(55) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`teachername`  varchar(55) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`grade`  varchar(55) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`major`  varchar(55) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`college`  varchar(55) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`filepath`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`id`, `uuid`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
/*!50003 AUTO_INCREMENT=7 */

;

-- ----------------------------
-- Records of t_classinfo
-- ----------------------------
BEGIN;
INSERT INTO `t_classinfo` VALUES ('3', '1ae8e017-4f47-4282-9a87-38ef8b63e7a7', '1ae8e017-4f47-4282-9a87-38ef8b63e7a7.JPG', '面向对象编程', 'jay', '周杰伦', '2016', '计算机科学与技术', '计算机学院', 'FileUpload\\classOnline\\1ae8e017-4f47-4282-9a87-38ef8b63e7a7'), ('4', '30e21cf5-0f8f-45d3-9d26-2c724a7902f1', '30e21cf5-0f8f-45d3-9d26-2c724a7902f1.JPG', '数据库编程技术', 'jay', '周杰伦', '2017', '计算机科学与技术', '计算机学院', 'FileUpload\\classOnline\\30e21cf5-0f8f-45d3-9d26-2c724a7902f1'), ('5', '27424e45-8d16-49e6-ae4c-c8dd84f23957', '27424e45-8d16-49e6-ae4c-c8dd84f23957.JPG', 'javaScript编程', 'jay', '周杰伦', '2016', '计算机科学与技术', '计算机科学与技术学院', 'FileUpload\\classOnline\\27424e45-8d16-49e6-ae4c-c8dd84f23957'), ('6', '0ff01afb-8c0c-458c-bf0b-288f494f9bad', '0ff01afb-8c0c-458c-bf0b-288f494f9bad.JPG', '计算机组成原理', 'jay', '周杰伦', '2016', '物联网', '计算机学院', 'FileUpload\\classOnline\\0ff01afb-8c0c-458c-bf0b-288f494f9bad');
COMMIT;

-- ----------------------------
-- Table structure for t_classmessage
-- ----------------------------
DROP TABLE IF EXISTS `t_classmessage`;
CREATE TABLE `t_classmessage` (
`id`  int(255) NOT NULL AUTO_INCREMENT ,
`uuid`  varchar(55) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' ,
`classid`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' ,
`title`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`content`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`classname`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`major`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`college`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`grade`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`time`  timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP ,
PRIMARY KEY (`id`, `uuid`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
/*!50003 AUTO_INCREMENT=20 */

;

-- ----------------------------
-- Records of t_classmessage
-- ----------------------------
BEGIN;
INSERT INTO `t_classmessage` VALUES ('15', '58f941e2-50bf-4029-b4f5-25a1d810d906', '1ae8e017-4f47-4282-9a87-38ef8b63e7a7', '停课通知', '下周周一上午一二节课停课一次', '面向对象编程', '计算机科学与技术', '计算机学院', '2017', '2018-05-24 17:37:06'), ('16', 'a8b28008-910f-4197-b7f2-3004c03bdfb5', '1ae8e017-4f47-4282-9a87-38ef8b63e7a7', '下周进行期中测验', '希望同学们做好准备/', '面向对象编程', '计算机科学与技术', '计算机学院', '2017', '2018-05-24 17:37:06'), ('17', '281db618-8c34-4afd-8650-974754007230', '1ae8e017-4f47-4282-9a87-38ef8b63e7a7', '下周进行期中测验', '希望同学们做好准备/', '面向对象编程', '计算机科学与技术', '计算机学院', '2017', '2018-05-24 17:37:06'), ('18', '5a127419-8432-441e-b058-cb645cbfbbc4', '1ae8e017-4f47-4282-9a87-38ef8b63e7a7', '12313', 'sadfasd 撒地方', '面向对象编程', '计算机科学与技术', '计算机学院', '2017', null), ('19', '2b4d0f56-3d55-4bcf-b464-1eb1132c302c', '0ff01afb-8c0c-458c-bf0b-288f494f9bad', '很高兴认识大家', '大家上课前带着电脑谢谢', '计算机组成原理', '物联网', '计算机学院', '2016', null);
COMMIT;

-- ----------------------------
-- Table structure for t_engineer
-- ----------------------------
DROP TABLE IF EXISTS `t_engineer`;
CREATE TABLE `t_engineer` (
`id`  int(255) NOT NULL AUTO_INCREMENT ,
`username`  varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`password`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`realname`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`regtime`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP ,
PRIMARY KEY (`id`, `username`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
/*!50003 AUTO_INCREMENT=3 */

;

-- ----------------------------
-- Records of t_engineer
-- ----------------------------
BEGIN;
INSERT INTO `t_engineer` VALUES ('1', '11', '11', '王孝', '2018-05-23 10:41:07'), ('2', '22', '22', '刘司', '2018-05-23 10:41:11');
COMMIT;

-- ----------------------------
-- Table structure for t_errcode
-- ----------------------------
DROP TABLE IF EXISTS `t_errcode`;
CREATE TABLE `t_errcode` (
`id`  int(255) NOT NULL AUTO_INCREMENT ,
`code`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`desc`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`id`, `code`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
/*!50003 AUTO_INCREMENT=1 */

;

-- ----------------------------
-- Records of t_errcode
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for t_lostres
-- ----------------------------
DROP TABLE IF EXISTS `t_lostres`;
CREATE TABLE `t_lostres` (
`id`  int(255) NOT NULL AUTO_INCREMENT ,
`uuid`  varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'uuid主键' ,
`uploadid`  varchar(55) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`restype`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`resname`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '物品名称' ,
`resfeature`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '物品特征' ,
`respicpath`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '物品图片路径' ,
`picktime`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '拾获时间' ,
`picklocale`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '拾获地点' ,
`resflag`  varchar(5) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '1' COMMENT '事务状态' ,
`pickname`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '联系人姓名' ,
`pickphone`  varchar(22) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '联系人电话' ,
`owername`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`owerphone`  varchar(21) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`uploadtime`  timestamp NULL DEFAULT CURRENT_TIMESTAMP ,
PRIMARY KEY (`id`, `uuid`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
/*!50003 AUTO_INCREMENT=17 */

;

-- ----------------------------
-- Records of t_lostres
-- ----------------------------
BEGIN;
INSERT INTO `t_lostres` VALUES ('12', '03a3dc4d-666e-4463-9dcb-c94018bb8116', '160907190138', 'lost', '黑色普拉达', '穿Hisense', '03a3dc4d-666e-4463-9dcb-c94018bb8116.JPG', '2018-0909', '操场', '0', '齐德龙', '17854100299', null, null, null), ('13', '2df066bc-be73-4eb3-8979-e87e584dc2ce', '160907190138', 'lost', '黑色普拉达', '穿黑色', '2df066bc-be73-4eb3-8979-e87e584dc2ce.JPG', '2018-0909', '餐厅二楼', '1', '齐德龙', '17854100299', null, null, null), ('14', '4485137f-ecbb-426f-95ef-4decc0a0c83f', '160907190138', 'found', '我的失物', '案件顺丰快递阿斯顿发生', '4485137f-ecbb-426f-95ef-4decc0a0c83f.JPG', '2018-0909', '餐厅二楼', '0', 'almost', '17854100299', 'a', 'a', null), ('15', '74b9509c-6610-4086-b568-3a4c943c7b6c', '160907190133', 'lost', '捡到iPhone56一个', '崭新的iPhone56一个', '74b9509c-6610-4086-b568-3a4c943c7b6c.png', '2018-06-06', '二餐东北角', '1', '吴奇隆', '17854100299', null, null, null), ('16', 'e04b9974-eae0-4b2e-9317-9e4c59330a5b', '160907190133', 'found', '金士顿U盘一个', '白色16gU盘一个', 'e04b9974-eae0-4b2e-9317-9e4c59330a5b.jpeg', '2018-06-04', '工程中心', '1', ' 周杰伦', '17854100299', null, null, null);
COMMIT;

-- ----------------------------
-- Table structure for t_managers
-- ----------------------------
DROP TABLE IF EXISTS `t_managers`;
CREATE TABLE `t_managers` (
`id`  int(255) NOT NULL AUTO_INCREMENT ,
`username`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`password`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`regtime`  timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP ,
PRIMARY KEY (`id`, `username`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
/*!50003 AUTO_INCREMENT=2 */

;

-- ----------------------------
-- Records of t_managers
-- ----------------------------
BEGIN;
INSERT INTO `t_managers` VALUES ('1', 'admin', 'admin', '2018-05-17 14:09:13');
COMMIT;

-- ----------------------------
-- Table structure for t_repairinfo
-- ----------------------------
DROP TABLE IF EXISTS `t_repairinfo`;
CREATE TABLE `t_repairinfo` (
`id`  int(255) NOT NULL AUTO_INCREMENT ,
`uuid`  varchar(55) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`uploadid`  varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`realname`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`connphone`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`location`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`device`  varchar(254) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`repairdesc`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`picture`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`repairid`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`uploadtime`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP ,
`repairpeople`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`repairflag`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '2' ,
`feedback`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`id`, `uuid`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
/*!50003 AUTO_INCREMENT=8 */

;

-- ----------------------------
-- Records of t_repairinfo
-- ----------------------------
BEGIN;
INSERT INTO `t_repairinfo` VALUES ('6', 'a3acae41-110c-458b-8abe-9f74e20fd56e', '160907190138', '洛天依', '17854100299', '经济学院', '投影仪', '阿里山帅', 'a3acae41-110c-458b-8abe-9f74e20fd56e.JPG', '11', '2018-05-26 01:12:33', null, '0', '已根据需求解决'), ('7', '9022a428-e4aa-4235-a41e-28a07df2c8ba', '160907190138', '周杰伦', '17854100299', '数据地点', '投影', '灯泡不亮了', '9022a428-e4aa-4235-a41e-28a07df2c8ba.JPG', '11', '2018-05-26 07:33:45', null, '0', null);
COMMIT;

-- ----------------------------
-- Table structure for t_student
-- ----------------------------
DROP TABLE IF EXISTS `t_student`;
CREATE TABLE `t_student` (
`userid`  int(255) NOT NULL AUTO_INCREMENT ,
`username`  varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名' ,
`password`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码' ,
`realname`  varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '未登记姓名' COMMENT '真实姓名' ,
`grade`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`college`  varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '未登记' COMMENT '手机号' ,
`major`  varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '未登记姓名' COMMENT '宿舍号' ,
`dormitory`  varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`regtime`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '未登记' ,
PRIMARY KEY (`userid`, `username`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
/*!50003 AUTO_INCREMENT=3 */

;

-- ----------------------------
-- Records of t_student
-- ----------------------------
BEGIN;
INSERT INTO `t_student` VALUES ('1', '160907190138', '11', 'devin', '2016', '计算机学院', '计算机科学与技术', '8119', '2018-05-26 01:15:36'), ('2', '160907190133', '112233', '王小龙', '2017', '计算机学院', '计算机科学与技术', '8223', '2018-05-26 06:54:21');
COMMIT;

-- ----------------------------
-- Table structure for t_teacher
-- ----------------------------
DROP TABLE IF EXISTS `t_teacher`;
CREATE TABLE `t_teacher` (
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`username`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`password`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`realname`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`connphone`  varchar(12) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`college`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
/*!50003 AUTO_INCREMENT=3 */

;

-- ----------------------------
-- Records of t_teacher
-- ----------------------------
BEGIN;
INSERT INTO `t_teacher` VALUES ('1', 'jay', '11', '周杰伦', '15662797503', '音乐'), ('2', 'jay', '11', '周杰伦', '15662797503', '音乐');
COMMIT;

-- ----------------------------
-- Procedure structure for getEngineerWorkList
-- ----------------------------
DROP PROCEDURE IF EXISTS `getEngineerWorkList`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `getEngineerWorkList`()
begin  
select count(*) as 'undone' from t_repairinfo where repairflag = 1 ;
select count(*) as 'done' from t_repairinfo where repairflag = 0 ; 
end
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for sp2
-- ----------------------------
DROP PROCEDURE IF EXISTS `sp2`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp2`()
begin  
select count(*) as 'undone' from t_repairinfo where repairflag = 1 ;
select count(*) as 'done' from t_repairinfo where repairflag = 0 ; 
end
;;
DELIMITER ;

-- ----------------------------
-- Auto increment value for t_assignment
-- ----------------------------
ALTER TABLE `t_assignment` AUTO_INCREMENT=10;

-- ----------------------------
-- Auto increment value for t_classinfo
-- ----------------------------
ALTER TABLE `t_classinfo` AUTO_INCREMENT=7;

-- ----------------------------
-- Auto increment value for t_classmessage
-- ----------------------------
ALTER TABLE `t_classmessage` AUTO_INCREMENT=20;

-- ----------------------------
-- Auto increment value for t_engineer
-- ----------------------------
ALTER TABLE `t_engineer` AUTO_INCREMENT=3;

-- ----------------------------
-- Auto increment value for t_errcode
-- ----------------------------
ALTER TABLE `t_errcode` AUTO_INCREMENT=1;

-- ----------------------------
-- Auto increment value for t_lostres
-- ----------------------------
ALTER TABLE `t_lostres` AUTO_INCREMENT=17;

-- ----------------------------
-- Auto increment value for t_managers
-- ----------------------------
ALTER TABLE `t_managers` AUTO_INCREMENT=2;

-- ----------------------------
-- Auto increment value for t_repairinfo
-- ----------------------------
ALTER TABLE `t_repairinfo` AUTO_INCREMENT=8;

-- ----------------------------
-- Auto increment value for t_student
-- ----------------------------
ALTER TABLE `t_student` AUTO_INCREMENT=3;

-- ----------------------------
-- Auto increment value for t_teacher
-- ----------------------------
ALTER TABLE `t_teacher` AUTO_INCREMENT=3;
