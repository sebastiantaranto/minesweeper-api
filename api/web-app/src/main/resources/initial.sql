CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
);

INSERT INTO hibernate_sequence SET next_val=1;

CREATE TABLE `game` (
  `id` bigint(20) NOT NULL,
  `created` datetime NOT NULL,
  `modified` datetime DEFAULT NULL,
  `status` varchar(20) NOT NULL,
  `game_rows` INTEGER NOT NULL,
  `game_columns` INTEGER NOT NULL,
  `mines` INTEGER NOT NULL,
  `data` MEDIUMTEXT NOT NULL,
  `time_spent` BIGINT(20)
  PRIMARY KEY (`id`)
);
