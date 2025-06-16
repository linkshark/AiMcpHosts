/*
 Navicat Premium Dump SQL

 Source Server         : 本机Mysql8
 Source Server Type    : MySQL
 Source Server Version : 80403 (8.4.3)
 Source Host           : localhost:3306
 Source Schema         : mcp

 Target Server Type    : MySQL
 Target Server Version : 80403 (8.4.3)
 File Encoding         : 65001

 Date: 16/06/2025 18:01:44
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student` (
  `increment_id` bigint NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `student_name` varchar(255) DEFAULT NULL COMMENT '学生姓名',
  `grade` int DEFAULT NULL COMMENT '成绩',
  `subject` varchar(255) DEFAULT NULL COMMENT '科目',
  `test_date` datetime DEFAULT NULL COMMENT '考试时间',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`increment_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of student
-- ----------------------------
BEGIN;
INSERT INTO `student` (`increment_id`, `student_name`, `grade`, `subject`, `test_date`, `create_time`, `update_time`) VALUES (1, 'shark', 10000, '语文', '2025-06-16 16:02:58', '2025-06-16 17:55:16', '2025-06-16 17:55:27');
INSERT INTO `student` (`increment_id`, `student_name`, `grade`, `subject`, `test_date`, `create_time`, `update_time`) VALUES (2, 'paul', 98, '数学', '2025-06-16 17:41:49', '2025-06-16 17:55:18', '2025-06-16 17:55:29');
INSERT INTO `student` (`increment_id`, `student_name`, `grade`, `subject`, `test_date`, `create_time`, `update_time`) VALUES (3, 'shark', 89, '语文', '2025-06-16 17:41:49', '2025-06-16 17:55:20', '2025-06-16 17:55:31');
INSERT INTO `student` (`increment_id`, `student_name`, `grade`, `subject`, `test_date`, `create_time`, `update_time`) VALUES (4, 'shark', 96, '数学', '2025-06-16 17:41:49', '2025-06-16 17:55:23', '2025-06-16 17:55:33');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
