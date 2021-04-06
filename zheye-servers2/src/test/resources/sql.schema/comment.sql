DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `content` text NOT NULL,
  `user_id` int(11) NOT NULL,
  `entity_id` int(11) NOT NULL,
  `type` int(11) NOT NULL COMMENT '1是answer的评论，2时给评论的评论',
  `created_date` datetime NOT NULL,
  `status` int(11) DEFAULT '0',
  `responded` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `entity_index` (`entity_id`,`type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `comment` (`id`, `content`, `user_id`, `entity_id`, `type`, `created_date`, `status`, `responded`)
VALUES
	(1, '你好，兄弟', 1, 1, 1, '2020-07-15 00:00:00', 1, NULL),
	(2, '你好', 2, 1, 2, '2020-07-15 00:00:00', 1, 1);

