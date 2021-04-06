DROP TABLE IF EXISTS `question`;
CREATE TABLE `question` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL,
  `content` text,
  `user_id` int(11) NOT NULL,
  `created_date` datetime NOT NULL,
  `comment_count` int(11) NOT NULL,
  `support_count` int(11) DEFAULT '0',
  `unsupport_count` int(11) DEFAULT '0',
  `look_num` int(11) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `date_index` (`created_date`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
INSERT INTO `question` (`id`, `title`, `content`, `user_id`, `created_date`, `comment_count`, `support_count`, `unsupport_count`, `look_num`)
VALUES
	(1, '如何做一名优秀的高端绿茶?', NULL, 1, '2020-07-09 00:00:00', 0, 0, 0, 0);
