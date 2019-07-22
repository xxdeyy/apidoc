-- --------------------------------------------------------
-- 主机:                           127.0.0.1
-- 服务器版本:                        5.7.26-log - MySQL Community Server (GPL)
-- 服务器OS:                        Win64
-- HeidiSQL 版本:                  10.2.0.5599
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Dumping database structure for api_doc
CREATE DATABASE IF NOT EXISTS `api_doc` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci */;
USE `api_doc`;

-- Dumping structure for table api_doc.admin
CREATE TABLE IF NOT EXISTS `admin` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(45) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL,
  `password_salt` varchar(6) DEFAULT NULL,
  `true_name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- Dumping data for table api_doc.admin: ~0 rows (大约)
DELETE FROM `admin`;
/*!40000 ALTER TABLE `admin` DISABLE KEYS */;
INSERT INTO `admin` (`id`, `username`, `password`, `password_salt`, `true_name`) VALUES
	(1, 'admin', 'a1ab70bf0154fbf888fe45293fafb588', '258963', '管理员');
/*!40000 ALTER TABLE `admin` ENABLE KEYS */;

-- Dumping structure for table api_doc.api
CREATE TABLE IF NOT EXISTS `api` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `module_id` int(11) NOT NULL COMMENT '模块id',
  `name` varchar(200) NOT NULL DEFAULT '' COMMENT '名称',
  `uri` varchar(200) NOT NULL DEFAULT '' COMMENT 'uri',
  `method` varchar(45) NOT NULL DEFAULT '' COMMENT '请求方法：get or post',
  `function` varchar(255) DEFAULT NULL COMMENT '功能',
  `content_type` varchar(45) DEFAULT NULL COMMENT '请求的content-type，分为form和json',
  `params` varchar(3000) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '请求参数。这里存储的是json，应用程序负责转化',
  `json_params` varchar(7000) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '请求参数直接为json格式',
  `response` varchar(10000) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL DEFAULT '' COMMENT '响应数据',
  `memo` varchar(255) DEFAULT NULL COMMENT '备注',
  `sort_no` int(11) NOT NULL COMMENT '排序号',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='接口详情';

-- Dumping data for table api_doc.api: ~2 rows (大约)
DELETE FROM `api`;
/*!40000 ALTER TABLE `api` DISABLE KEYS */;
INSERT INTO `api` (`id`, `module_id`, `name`, `uri`, `method`, `function`, `content_type`, `params`, `json_params`, `response`, `memo`, `sort_no`, `create_time`, `update_time`) VALUES
	(1, 1, '接口1-1', '/api/module1-1', 'GET', '', 'form', '[{"key":"uuid","type":"string","required":true,"description":"uuid","sortNo":1},{"key":"name","type":"string","required":true,"description":"名称","sortNo":2}]', NULL, '{\n    "code": 1,\n    "msg": "成功",\n    "data": {\n        "info": {\n            "trueName": "王医生",\n            "sex": "MALE"                             // 枚举属性，包含MALE、FEMALE、UNKNOWN\n        }\n    }\n}', '', 1, '2019-07-22 21:04:51', '2019-07-22 21:52:36'),
	(2, 1, '接口1-2', '/api/module1-2', 'POST', '', 'json', NULL, '[\n    {\n        "guid": "9642be63-6d90-4fd6-ab11-5d41eb0c9f59",                      // 排班guid。新增时不传\n        "userGuid": "44bdf23b-e23b-4e53-bbc0-af925b5f43ac",               // 员工guid\n        "userName": "李护士",                                                                      // 员工名称\n        "settingGuid": "44bdf23b-e23b-4e53-bbc0-af925b5f43ac",           // 班次guid\n        "settingName": "晚班",                                                                     // 班次名称\n        "year": "2016",                                                                                  // 年份\n        "month": "1",                                                                                    // 月份\n        "day": "8",                                                                                         // 日\n        "beginTime": "2016-01-08 12:00:00",                                              // 开始时间\n        "endTime": "2016-01-08 21:00:00",                                                 // 结束时间\n        "color": "#FAFAFA",                                                                         // 显示颜色\n    },\n    ……\n]', '{\n    "code": 1,\n    "msg": "成功",\n    "data": {}\n}', '', 2, '2019-07-22 21:06:09', '2019-07-22 21:55:13');
/*!40000 ALTER TABLE `api` ENABLE KEYS */;

-- Dumping structure for table api_doc.api_module
CREATE TABLE IF NOT EXISTS `api_module` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL DEFAULT '' COMMENT '模块名称',
  `sort_no` int(11) NOT NULL COMMENT '排序号',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='接口所属的模块';

-- Dumping data for table api_doc.api_module: ~2 rows (大约)
DELETE FROM `api_module`;
/*!40000 ALTER TABLE `api_module` DISABLE KEYS */;
INSERT INTO `api_module` (`id`, `name`, `sort_no`, `create_time`, `update_time`) VALUES
	(1, '模块1', 1, '2019-07-22 21:00:32', '2019-07-22 21:00:32'),
	(2, '模块2', 2, '2019-07-22 21:00:36', '2019-07-22 21:00:36');
/*!40000 ALTER TABLE `api_module` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
