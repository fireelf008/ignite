/*
 Navicat Premium Data Transfer

 Source Server         : 本地MySQL
 Source Server Type    : MySQL
 Source Server Version : 50720
 Source Host           : localhost:3306
 Source Schema         : test

 Target Server Type    : MySQL
 Target Server Version : 50720
 File Encoding         : 65001

 Date: 09/08/2019 19:23:56
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tb_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `age` int(11) NULL DEFAULT NULL,
  `sex` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_user
-- ----------------------------
INSERT INTO `tb_user` VALUES (1, '张三', 20, '男', '2019-08-09 06:17:33', '2019-08-09 06:17:33');
INSERT INTO `tb_user` VALUES (2, '李四', 25, '男', '2019-08-09 06:17:50', '2019-08-09 06:17:50');
INSERT INTO `tb_user` VALUES (3, '王五', 22, '女', '2019-08-09 06:17:59', '2019-08-09 06:17:59');
INSERT INTO `tb_user` VALUES (4, '赵六', 19, '女', '2019-08-09 06:18:04', '2019-08-09 06:18:04');
INSERT INTO `tb_user` VALUES (5, '田七', 28, '女', '2019-08-09 06:18:11', '2019-08-09 06:18:11');
INSERT INTO `tb_user` VALUES (6, '王八', 30, '男', '2019-08-09 08:22:48', '2019-08-09 08:22:48');

SET FOREIGN_KEY_CHECKS = 1;
