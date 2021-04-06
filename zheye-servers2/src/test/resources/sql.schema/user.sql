DROP TABLE IF EXISTS user;
CREATE TABLE `user` (
    `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
    `name` varchar(64) NOT NULL DEFAULT '',
    `password` varchar(128) NOT NULL DEFAULT '',
    `salt` varchar(32) NOT NULL DEFAULT '',
    `head_url` varchar(256) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
INSERT INTO `user` (`id`, `name`, `password`, `salt`, `head_url`)
VALUES
	(1, 'zhangSan', 'fd1d02d91cb8e8642488a01038d14929', 'a4d94', '/default.jpg'),
	(2, 'zhangsan3', '352d77160690a5cb059de76d957565cc', '517f0', '/default.jpg');