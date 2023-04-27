-- 记录通过本公司渠道用户信息
CREATE TABLE `user` (
  `userId` int(11) NOT NULL AUTO_INCREMENT,
  `userName` varchar(255) NOT NULL COMMENT '用户名',
  `nickName` varchar(255) DEFAULT NULL COMMENT '昵称',
  `password` varchar(128) NOT NULL COMMENT '登陆密码',
  `payPassword` varchar(120) DEFAULT NULL COMMENT '支付密码',
  `telephone` varchar(15) DEFAULT NULL COMMENT '手机',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `type` tinyint(4) DEFAULT NULL COMMENT '账号类型，0：普通，1：游客，2：第三方',
  `status` tinyint(4) DEFAULT '1' COMMENT '账号状态0：禁用 1正常',
  `registerTime` datetime NULL DEFAULT NULL COMMENT '注册时间',
  `updateTime` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间',
  PRIMARY KEY (`userId`),
  UNIQUE KEY `userName` (`userName`),
  UNIQUE KEY `email` (`email`),
  UNIQUE KEY `telephone` (`telephone`)
) ENGINE=InnoDB AUTO_INCREMENT DEFAULT CHARSET=utf8mb4 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC