/*
 Navicat Premium Data Transfer

 Source Server         : test
 Source Server Type    : MySQL
 Source Server Version : 80024 (8.0.24)
 Source Host           : localhost:3306
 Source Schema         : pictureshare

 Target Server Type    : MySQL
 Target Server Version : 80024 (8.0.24)
 File Encoding         : 65001

 Date: 23/11/2023 17:39:19
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for pic_information
-- ----------------------------
DROP TABLE IF EXISTS `pic_information`;
CREATE TABLE `pic_information`  (
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `upload_date` date NULL DEFAULT NULL,
  `pic_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `pic_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pic_information
-- ----------------------------
INSERT INTO `pic_information` VALUES ('024f328b105d4c8a9b913222e42ef938', '.jpg', '2023-10-13', '/sourcepic/024f328b105d4c8a9b913222e42ef938.jpg', 'http://8.134.191.8:8080/sourcepic/024f328b105d4c8a9b913222e42ef938.jpg');
INSERT INTO `pic_information` VALUES ('09d62f7c77eb43e2ad6035a5d674dff3', '.jpg', '2023-10-13', '/sourcepic/09d62f7c77eb43e2ad6035a5d674dff3.jpg', 'http://8.134.191.8:8080/sourcepic/09d62f7c77eb43e2ad6035a5d674dff3.jpg');
INSERT INTO `pic_information` VALUES ('09def38d2cfa4a29a9a3ecacd228b45b', '.jpg', '2023-10-11', '/sourcepic/09def38d2cfa4a29a9a3ecacd228b45b.jpg', 'http://8.134.191.8:8080/sourcepic/09def38d2cfa4a29a9a3ecacd228b45b.jpg');
INSERT INTO `pic_information` VALUES ('0b3e8adf277b4163811b9b6406d1924e', '.png', '2023-10-13', '/sourcepic/0b3e8adf277b4163811b9b6406d1924e.png', 'http://8.134.191.8:8080/sourcepic/0b3e8adf277b4163811b9b6406d1924e.png');
INSERT INTO `pic_information` VALUES ('0def918a1d9c40e78120dd73f6b2ec29', '.jpg', '2023-10-14', '/sourcepic/0def918a1d9c40e78120dd73f6b2ec29.jpg', 'http://8.134.191.8:8080/sourcepic/0def918a1d9c40e78120dd73f6b2ec29.jpg');
INSERT INTO `pic_information` VALUES ('10e3e9c064034ac8a3304add7cc1a5df', '.jpg', '2023-10-14', '/sourcepic/10e3e9c064034ac8a3304add7cc1a5df.jpg', 'http://8.134.191.8:8080/sourcepic/10e3e9c064034ac8a3304add7cc1a5df.jpg');
INSERT INTO `pic_information` VALUES ('11694a135153450d996cb9d37708d91d', '.jpg', '2023-10-14', '/sourcepic/11694a135153450d996cb9d37708d91d.jpg', 'http://8.134.191.8:8080/sourcepic/11694a135153450d996cb9d37708d91d.jpg');
INSERT INTO `pic_information` VALUES ('162a301c6feb4c88aaa8da76d8f89bb3', '.jpg', '2023-10-14', '/sourcepic/162a301c6feb4c88aaa8da76d8f89bb3.jpg', 'http://8.134.191.8:8080/sourcepic/162a301c6feb4c88aaa8da76d8f89bb3.jpg');
INSERT INTO `pic_information` VALUES ('19635e6ae72143ef8c4d6651b03896f5', '.jpg', '2023-10-14', '/sourcepic/19635e6ae72143ef8c4d6651b03896f5.jpg', 'http://8.134.191.8:8080/sourcepic/19635e6ae72143ef8c4d6651b03896f5.jpg');
INSERT INTO `pic_information` VALUES ('2eeba81c60a84b9080737b4db8d9708a', '.jpg', '2023-10-11', '/sourcepic/2eeba81c60a84b9080737b4db8d9708a.jpg', 'http://8.134.191.8:8080/sourcepic/2eeba81c60a84b9080737b4db8d9708a.jpg');
INSERT INTO `pic_information` VALUES ('4996787716994a40b25584ca42d24239', '.jpg', '2023-10-11', '/sourcepic/4996787716994a40b25584ca42d24239.jpg', 'http://8.134.191.8:8080/sourcepic/4996787716994a40b25584ca42d24239.jpg');
INSERT INTO `pic_information` VALUES ('5b59456964294437aa10f590a98a25fc', '.jpg', '2023-10-12', '/sourcepic/5b59456964294437aa10f590a98a25fc.jpg', 'http://8.134.191.8:8080/sourcepic/5b59456964294437aa10f590a98a25fc.jpg');
INSERT INTO `pic_information` VALUES ('633d29fcf6e5447c95ab2de13ff5eb61', '.png', '2023-10-13', '/sourcepic/633d29fcf6e5447c95ab2de13ff5eb61.png', 'http://8.134.191.8:8080/sourcepic/633d29fcf6e5447c95ab2de13ff5eb61.png');
INSERT INTO `pic_information` VALUES ('67bedff3e18847aaa249b1ca17381591', '.jpg', '2023-10-14', '/sourcepic/67bedff3e18847aaa249b1ca17381591.jpg', 'http://8.134.191.8:8080/sourcepic/67bedff3e18847aaa249b1ca17381591.jpg');
INSERT INTO `pic_information` VALUES ('67f04d252cea451b9283dea74c65293b', '.jpg', '2023-10-12', '/sourcepic/67f04d252cea451b9283dea74c65293b.jpg', 'http://8.134.191.8:8080/sourcepic/67f04d252cea451b9283dea74c65293b.jpg');
INSERT INTO `pic_information` VALUES ('7d36376348624eac8305b9040d75105e', '.jpg', '2023-10-13', '/sourcepic/7d36376348624eac8305b9040d75105e.jpg', 'http://8.134.191.8:8080/sourcepic/7d36376348624eac8305b9040d75105e.jpg');
INSERT INTO `pic_information` VALUES ('84ee3ccf76d84dcda2dc1edc958008ee', '.jpg', '2023-10-14', '/sourcepic/84ee3ccf76d84dcda2dc1edc958008ee.jpg', 'http://8.134.191.8:8080/sourcepic/84ee3ccf76d84dcda2dc1edc958008ee.jpg');
INSERT INTO `pic_information` VALUES ('903c1d5f0a00435898a3d94fae411dac', '.jpg', '2023-10-11', '/sourcepic/903c1d5f0a00435898a3d94fae411dac.jpg', 'http://8.134.191.8:8080/sourcepic/903c1d5f0a00435898a3d94fae411dac.jpg');
INSERT INTO `pic_information` VALUES ('9db30b3cd6694cd5a6032159d798ce5f', '.jpg', '2023-10-14', '/sourcepic/9db30b3cd6694cd5a6032159d798ce5f.jpg', 'http://8.134.191.8:8080/sourcepic/9db30b3cd6694cd5a6032159d798ce5f.jpg');
INSERT INTO `pic_information` VALUES ('9fcc4938f0b5472bb002eab510c242b0', '.png', '2023-10-13', '/sourcepic/9fcc4938f0b5472bb002eab510c242b0.png', 'http://8.134.191.8:8080/sourcepic/9fcc4938f0b5472bb002eab510c242b0.png');
INSERT INTO `pic_information` VALUES ('bc9145d464f44ecb8f698c25a3477f65', '.jpg', '2023-10-11', '/sourcepic/bc9145d464f44ecb8f698c25a3477f65.jpg', 'http://8.134.191.8:8080/sourcepic/bc9145d464f44ecb8f698c25a3477f65.jpg');
INSERT INTO `pic_information` VALUES ('bf9e458c6b7e45eb9c58bdd2508ec87e', '.jpg', '2023-10-11', '/sourcepic/bf9e458c6b7e45eb9c58bdd2508ec87e.jpg', 'http://8.134.191.8:8080/sourcepic/bf9e458c6b7e45eb9c58bdd2508ec87e.jpg');
INSERT INTO `pic_information` VALUES ('c127521d46824496b2135360874f6464', '.jpg', '2023-11-21', '/sourcepic/c127521d46824496b2135360874f6464.jpg', 'http://8.134.191.8:8080/sourcepic/c127521d46824496b2135360874f6464.jpg');
INSERT INTO `pic_information` VALUES ('c5300a094c4c4b188d00c6f72b280236', '.jpg', '2023-10-11', '/sourcepic/c5300a094c4c4b188d00c6f72b280236.jpg', 'http://8.134.191.8:8080/sourcepic/c5300a094c4c4b188d00c6f72b280236.jpg');
INSERT INTO `pic_information` VALUES ('ce8a5b2a36754d79ac4a9bd5fa3af8f0', '.jpg', '2023-10-14', '/sourcepic/ce8a5b2a36754d79ac4a9bd5fa3af8f0.jpg', 'http://8.134.191.8:8080/sourcepic/ce8a5b2a36754d79ac4a9bd5fa3af8f0.jpg');
INSERT INTO `pic_information` VALUES ('d87b12e6b8394cb4944cad853b45df0d', '.jpg', '2023-10-10', '/sourcepic/d87b12e6b8394cb4944cad853b45df0d.jpg', 'http://8.134.191.8:8080/sourcepic/d87b12e6b8394cb4944cad853b45df0d.jpg');
INSERT INTO `pic_information` VALUES ('df4567e27a884dbe91d1c1e93c146bff', '.png', '2023-10-13', '/sourcepic/df4567e27a884dbe91d1c1e93c146bff.png', 'http://8.134.191.8:8080/sourcepic/df4567e27a884dbe91d1c1e93c146bff.png');
INSERT INTO `pic_information` VALUES ('ff57d75f3fae4c23a23e0cdd5c9a3647', '.jpeg', '2023-10-14', '/sourcepic/ff57d75f3fae4c23a23e0cdd5c9a3647.jpeg', 'http://8.134.191.8:8080/sourcepic/ff57d75f3fae4c23a23e0cdd5c9a3647.jpeg');

-- ----------------------------
-- Table structure for pic_user
-- ----------------------------
DROP TABLE IF EXISTS `pic_user`;
CREATE TABLE `pic_user`  (
  `relation_id` int NOT NULL AUTO_INCREMENT,
  `pic_id` int NULL DEFAULT NULL,
  `user_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `like_check` tinyint(1) NULL DEFAULT NULL,
  `collect_check` tinyint(1) NULL DEFAULT NULL,
  PRIMARY KEY (`relation_id`) USING BTREE,
  INDEX `con_pic`(`pic_id` ASC) USING BTREE,
  INDEX `con_user`(`user_id` ASC) USING BTREE,
  CONSTRAINT `con_pic` FOREIGN KEY (`pic_id`) REFERENCES `picture` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `con_user` FOREIGN KEY (`user_id`) REFERENCES `user_information` (`usename`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pic_user
-- ----------------------------
INSERT INTO `pic_user` VALUES (1, 12, '123@qq.com', 1, 0);
INSERT INTO `pic_user` VALUES (3, 3, 'luo', 1, 1);
INSERT INTO `pic_user` VALUES (4, 4, 'luo', 1, 1);
INSERT INTO `pic_user` VALUES (5, 18, '123@qq.com', 1, 1);
INSERT INTO `pic_user` VALUES (6, 17, '123@qq.com', 1, 1);
INSERT INTO `pic_user` VALUES (7, 5, 'luo', 0, 1);
INSERT INTO `pic_user` VALUES (8, 15, '123@qq.com', 0, 1);
INSERT INTO `pic_user` VALUES (9, 16, '123@qq.com', 0, 1);
INSERT INTO `pic_user` VALUES (10, 8, 'luo', 0, 1);
INSERT INTO `pic_user` VALUES (11, 7, '123@qq.com', 0, 1);
INSERT INTO `pic_user` VALUES (12, 6, '123@qq.com', 0, 1);
INSERT INTO `pic_user` VALUES (13, 5, '123@qq.com', 0, 1);
INSERT INTO `pic_user` VALUES (14, 18, 'luo', 1, 0);
INSERT INTO `pic_user` VALUES (15, 22, 'luo', 1, 1);
INSERT INTO `pic_user` VALUES (16, 26, '123@qq.com', 0, 1);
INSERT INTO `pic_user` VALUES (17, 27, '123@qq.com', 0, 1);
INSERT INTO `pic_user` VALUES (18, 29, 'qwr', 0, 0);

-- ----------------------------
-- Table structure for picture
-- ----------------------------
DROP TABLE IF EXISTS `picture`;
CREATE TABLE `picture`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `usename` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `like_number` int NULL DEFAULT NULL,
  `collect_number` int NULL DEFAULT NULL,
  `download_number` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `con_infor`(`name` ASC) USING BTREE,
  INDEX `con_check`(`usename` ASC) USING BTREE,
  CONSTRAINT `con_check` FOREIGN KEY (`usename`) REFERENCES `user_information` (`usename`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `con_infor` FOREIGN KEY (`name`) REFERENCES `pic_information` (`name`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 31 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of picture
-- ----------------------------
INSERT INTO `picture` VALUES (3, 'd87b12e6b8394cb4944cad853b45df0d', 'miki', 'luo', 1, 1, 1);
INSERT INTO `picture` VALUES (4, '903c1d5f0a00435898a3d94fae411dac', 'miku', 'luo', 1, 1, 1);
INSERT INTO `picture` VALUES (5, 'bc9145d464f44ecb8f698c25a3477f65', 'miku', 'luo', 0, 2, 0);
INSERT INTO `picture` VALUES (6, '09def38d2cfa4a29a9a3ecacd228b45b', 'miku', 'luo', 0, 1, 0);
INSERT INTO `picture` VALUES (7, 'bf9e458c6b7e45eb9c58bdd2508ec87e', 'miku', 'luo', 0, 1, 0);
INSERT INTO `picture` VALUES (8, '2eeba81c60a84b9080737b4db8d9708a', 'miku', 'luo', 0, 1, 0);
INSERT INTO `picture` VALUES (10, '4996787716994a40b25584ca42d24239', 'miku', 'luo', 0, 0, 0);
INSERT INTO `picture` VALUES (11, '5b59456964294437aa10f590a98a25fc', 'miku', 'luo', 0, 0, 0);
INSERT INTO `picture` VALUES (12, '67f04d252cea451b9283dea74c65293b', 'hahah', '123@qq.com', 1, 0, 0);
INSERT INTO `picture` VALUES (13, '9fcc4938f0b5472bb002eab510c242b0', 'sup?', '123@qq.com', 0, 0, 0);
INSERT INTO `picture` VALUES (14, '633d29fcf6e5447c95ab2de13ff5eb61', 'sup?', '123@qq.com', 0, 0, 0);
INSERT INTO `picture` VALUES (15, '09d62f7c77eb43e2ad6035a5d674dff3', 'yoxi', '123@qq.com', 0, 1, 0);
INSERT INTO `picture` VALUES (16, '024f328b105d4c8a9b913222e42ef938', 'yoxi', '123@qq.com', 0, 1, 0);
INSERT INTO `picture` VALUES (17, '7d36376348624eac8305b9040d75105e', 'yoxi', '123@qq.com', 1, 1, 0);
INSERT INTO `picture` VALUES (18, 'df4567e27a884dbe91d1c1e93c146bff', 'yoxi', '123@qq.com', 2, 1, 0);
INSERT INTO `picture` VALUES (19, '0b3e8adf277b4163811b9b6406d1924e', 'yoxi', '123@qq.com', 0, 0, 0);
INSERT INTO `picture` VALUES (20, 'ce8a5b2a36754d79ac4a9bd5fa3af8f0', 'cool', '123@qq.com', 0, 0, 1);
INSERT INTO `picture` VALUES (21, '162a301c6feb4c88aaa8da76d8f89bb3', 'cool', '123@qq.com', 0, 0, 0);
INSERT INTO `picture` VALUES (22, '84ee3ccf76d84dcda2dc1edc958008ee', '瓜神', 'luo', 1, 1, 0);
INSERT INTO `picture` VALUES (23, '11694a135153450d996cb9d37708d91d', '瓜神', 'luo', 0, 0, 0);
INSERT INTO `picture` VALUES (24, '9db30b3cd6694cd5a6032159d798ce5f', '哈哈哈哈', 'qwe', 0, 0, 0);
INSERT INTO `picture` VALUES (25, '0def918a1d9c40e78120dd73f6b2ec29', 'cool', 'qwe', 0, 0, 0);
INSERT INTO `picture` VALUES (26, '10e3e9c064034ac8a3304add7cc1a5df', 'cool', 'qwe', 0, 1, 0);
INSERT INTO `picture` VALUES (27, 'ff57d75f3fae4c23a23e0cdd5c9a3647', 'Sir dumb', '123@qq.com', 0, 1, 0);
INSERT INTO `picture` VALUES (28, '67bedff3e18847aaa249b1ca17381591', 'mm', 'luo', 0, 0, 0);
INSERT INTO `picture` VALUES (29, '19635e6ae72143ef8c4d6651b03896f5', '饺子', '123@qq.com', 0, 0, 0);
INSERT INTO `picture` VALUES (30, 'c127521d46824496b2135360874f6464', 'bb', 'luo', 0, 0, 0);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `usename` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`usename`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('123@qq.com', '123456');
INSERT INTO `user` VALUES ('luo', '123');
INSERT INTO `user` VALUES ('qwe', '123');
INSERT INTO `user` VALUES ('qwr', '123');

-- ----------------------------
-- Table structure for user_information
-- ----------------------------
DROP TABLE IF EXISTS `user_information`;
CREATE TABLE `user_information`  (
  `usename` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `like_total` int NULL DEFAULT NULL,
  `collect_total` int NULL DEFAULT NULL,
  PRIMARY KEY (`usename`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_information
-- ----------------------------
INSERT INTO `user_information` VALUES ('123@qq.com', 3, 4);
INSERT INTO `user_information` VALUES ('luo', 4, 4);
INSERT INTO `user_information` VALUES ('qwe', 0, 0);
INSERT INTO `user_information` VALUES ('qwr', 0, 0);

SET FOREIGN_KEY_CHECKS = 1;
