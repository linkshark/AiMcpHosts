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

CREATE TABLE `mcp_server` (
                              `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
                              `name` varchar(500) NOT NULL COMMENT '名称',
                              `random_str` varchar(100) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '随机字符串',
                              `secret_key` varchar(100) NOT NULL COMMENT '访问密钥',
                              `description` varchar(255) DEFAULT NULL COMMENT '描述',
                              `create_time` datetime NOT NULL COMMENT '创建时间',
                              `update_time` datetime NOT NULL COMMENT '更新时间',
                              `is_deleted` tinyint NOT NULL DEFAULT '0' COMMENT '逻辑删除 0未删除 1已删除',
                              PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='mcp服务信息';


CREATE TABLE `tool` (
                        `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
                        `server_id` bigint NOT NULL COMMENT 'server id',
                        `name` varchar(100) NOT NULL COMMENT '工具名称',
                        `description` varchar(500) NOT NULL COMMENT '描述',
                        `url` varchar(100) NOT NULL COMMENT 'url',
                        `method` varchar(10) NOT NULL COMMENT '请求方式',
                        `create_time` datetime NOT NULL COMMENT '创建时间',
                        `update_time` datetime NOT NULL COMMENT '更新时间',
                        `is_deleted` tinyint NOT NULL DEFAULT '0' COMMENT '逻辑删除 0未删除 1已删除',
                        PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='工具信息';

CREATE TABLE `tool_param` (
                              `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
                              `tool_id` bigint DEFAULT NULL COMMENT '工具id',
                              `name` varchar(50) DEFAULT NULL COMMENT '名称',
                              `description` varchar(500) DEFAULT NULL COMMENT '描述',
                              `required` tinyint DEFAULT '0' COMMENT '是否是必填 1是 0否',
                              `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                              `update_time` datetime DEFAULT NULL COMMENT '更新时间',
                              `is_deleted` tinyint DEFAULT '0' COMMENT '逻辑删除 0未删除 1已删除',
                              PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='工具参数信息';

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
