/*
 Navicat Premium Data Transfer

 Source Server         : MySQL
 Source Server Type    : MySQL
 Source Server Version : 80032 (8.0.32)
 Source Host           : localhost:3306
 Source Schema         : tv-two-cinema-dev

 Target Server Type    : MySQL
 Target Server Version : 80032 (8.0.32)
 File Encoding         : 65001

 Date: 19/05/2023 18:17:32
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for film
-- ----------------------------
DROP TABLE IF EXISTS `film`;
CREATE TABLE `film`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '影片名称',
  `director` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '导演',
  `actor` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '主演',
  `release_time` datetime NOT NULL COMMENT '上映日期、时间',
  `duration` time NOT NULL COMMENT '时长',
  `poster` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '保存海报在数据库服务器上的相对路径',
  `status` tinyint(1) NOT NULL COMMENT '电影状态，下映时为0，上映时为1',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_film_name`(`name` ASC) USING BTREE COMMENT '按影片名称查询的普通索引'
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of film
-- ----------------------------
INSERT INTO `film` VALUES (1, '泰坦尼克号', 'me', '小李子', '1980-08-01 12:59:00', '03:30:00', '基础管理.png', 1, '2023-05-15 11:29:53', '2023-05-15 20:48:57');
INSERT INTO `film` VALUES (2, '血战上海滩', 'none', '不知道', '1956-02-01 20:59:00', '02:00:00', '/ret', 1, '2023-05-15 16:37:16', '2023-05-15 20:31:05');
INSERT INTO `film` VALUES (3, '血战上海滩', 'none+1', '不知道+1', '1956-02-01 20:59:00', '02:00:00', '/ret+1', 1, '2023-05-15 16:38:27', '2023-05-15 16:38:27');

-- ----------------------------
-- Table structure for hall
-- ----------------------------
DROP TABLE IF EXISTS `hall`;
CREATE TABLE `hall`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '影厅表主键',
  `capacity` int NOT NULL COMMENT '影厅可容纳人数',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of hall
-- ----------------------------
INSERT INTO `hall` VALUES (1, 10, '2023-05-15 16:55:42', '2023-05-15 16:55:42');
INSERT INTO `hall` VALUES (2, 1, '2023-05-15 20:17:45', '2023-05-15 20:17:45');

-- ----------------------------
-- Table structure for order
-- ----------------------------
DROP TABLE IF EXISTS `order`;
CREATE TABLE `order`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` bigint NOT NULL COMMENT '外键，关联user表主键',
  `schedule_id` bigint NOT NULL COMMENT '外键，关联schedule表主键',
  `seat_id` bigint NOT NULL COMMENT '外键，关联seat表主键',
  `price` decimal(10, 2) NOT NULL COMMENT '票价',
  `status` tinyint(1) NOT NULL COMMENT '状态，标识订单是否有效， 1为有效，0为无效',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间戳',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后一次更新时间戳',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_order_user_id`(`user_id` ASC) USING BTREE,
  INDEX `fk_order_schedule_id`(`schedule_id` ASC) USING BTREE,
  INDEX `fk_order_seat_id`(`seat_id` ASC) USING BTREE,
  CONSTRAINT `fk_order_schedule_id` FOREIGN KEY (`schedule_id`) REFERENCES `schedule` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `fk_order_seat_id` FOREIGN KEY (`seat_id`) REFERENCES `seat` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `fk_order_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of order
-- ----------------------------
INSERT INTO `order` VALUES (1, 1, 1, 1, 25.00, 1, '2023-05-15 17:23:51', '2023-05-15 21:11:37');
INSERT INTO `order` VALUES (2, 1, 1, 2, 25.00, 0, '2023-05-15 18:11:52', '2023-05-15 21:16:33');

-- ----------------------------
-- Table structure for schedule
-- ----------------------------
DROP TABLE IF EXISTS `schedule`;
CREATE TABLE `schedule`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `film_id` bigint NOT NULL COMMENT '外键，关联film表主键',
  `hall_id` bigint NOT NULL COMMENT '外键，关联hall表主键',
  `price` decimal(10, 2) NOT NULL COMMENT '该场次的票价',
  `start_time` datetime NOT NULL COMMENT '该场次开始的日期时间',
  `end_time` datetime NOT NULL COMMENT '该场次结束的日期时间',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间戳',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后一次更新时间戳',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_schedule_film_id`(`film_id` ASC) USING BTREE,
  INDEX `fk_schedule_hall_id`(`hall_id` ASC) USING BTREE,
  CONSTRAINT `fk_schedule_film_id` FOREIGN KEY (`film_id`) REFERENCES `film` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `fk_schedule_hall_id` FOREIGN KEY (`hall_id`) REFERENCES `hall` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of schedule
-- ----------------------------
INSERT INTO `schedule` VALUES (1, 1, 1, 25.00, '2023-01-29 00:00:00', '2023-01-29 03:30:00', '2023-05-15 17:01:07', '2023-05-15 17:01:16');

-- ----------------------------
-- Table structure for seat
-- ----------------------------
DROP TABLE IF EXISTS `seat`;
CREATE TABLE `seat`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `hall_id` bigint NOT NULL COMMENT '外键，关联hall表主键',
  `row` int NOT NULL COMMENT '座位行号',
  `column` int NOT NULL COMMENT '座位列号',
  `status` tinyint(1) NOT NULL DEFAULT 0 COMMENT '座位状态，1为占用，0为未被占用',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_seat_hall_id`(`hall_id` ASC) USING BTREE,
  CONSTRAINT `fk_seat_hall_id` FOREIGN KEY (`hall_id`) REFERENCES `hall` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of seat
-- ----------------------------
INSERT INTO `seat` VALUES (1, 1, 1, 1, 1, '2023-05-15 16:56:25', '2023-05-15 17:23:51');
INSERT INTO `seat` VALUES (2, 1, 1, 2, 1, '2023-05-15 16:56:25', '2023-05-15 18:14:50');
INSERT INTO `seat` VALUES (3, 1, 1, 3, 0, '2023-05-15 20:16:44', '2023-05-15 20:16:44');
INSERT INTO `seat` VALUES (4, 1, 1, 4, 0, '2023-05-15 20:17:34', '2023-05-15 20:17:34');
INSERT INTO `seat` VALUES (5, 2, 1, 1, 0, '2023-05-15 20:18:00', '2023-05-15 20:18:00');
INSERT INTO `seat` VALUES (6, 1, 1, 5, 0, '2023-05-15 20:18:37', '2023-05-15 20:18:37');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户表主键',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '用户邮箱',
  `password` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '用户密码',
  `status` tinyint(1) NOT NULL DEFAULT 1 COMMENT '用户状态，0为禁用，1为启用',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '用户创建时间戳',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '用户信息最后一次更新时间戳',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_usertable_email`(`email` ASC) USING BTREE COMMENT '唯一索引，用户邮箱'
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'admin@tv', 'ad', 1, '2023-05-07 09:30:38', '2023-05-15 18:09:55');
INSERT INTO `user` VALUES (2, '3116725220@qq.com', '123456', 1, '2023-05-07 10:42:46', '2023-05-07 10:43:10');
INSERT INTO `user` VALUES (3, '123456@qq.com', '123456789', 1, '2023-05-13 16:26:07', '2023-05-13 16:26:07');
INSERT INTO `user` VALUES (4, 'silent@qq.com', 'silent', 1, '2023-05-13 16:39:37', '2023-05-13 16:39:37');
INSERT INTO `user` VALUES (5, 'child@qq.com', 'child1', 1, '2023-05-13 16:57:08', '2023-05-13 16:57:08');
INSERT INTO `user` VALUES (6, 'zhangsan@qq.com', 'zhangsan', 1, '2023-05-13 16:59:34', '2023-05-13 16:59:34');
INSERT INTO `user` VALUES (7, 'hello@world', '123456', 1, '2023-05-13 17:09:14', '2023-05-13 17:09:14');

SET FOREIGN_KEY_CHECKS = 1;
