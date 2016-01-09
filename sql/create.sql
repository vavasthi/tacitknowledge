CREATE TABLE `TopicPeriodicAdjacencyDependency` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `periodEnd` date NOT NULL DEFAULT '0000-00-00',
  `topic` varchar(512) NOT NULL,
  `firstPhraseId` bigint(20) NOT NULL,
  `secondPhraseId` bigint(20) NOT NULL,
  `count` decimal(10,0) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `TopicPeriodicAdjacencyDependencyPeriodEndIdx` (`periodEnd`),
  KEY `TopicPeriodicAdjacencyDependencyFirstPhraseIdIdx` (`firstPhraseId`),
  KEY `TopicPeriodicAdjacencyDependencySecondPhraseIdIdx` (`secondPhraseId`)
) ENGINE=InnoDB AUTO_INCREMENT=85041669 DEFAULT CHARSET=latin1;

CREATE TABLE `TopicPeriodicAdjacencyPhrases` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `periodEnd` date NOT NULL DEFAULT '0000-00-00',
  `topic` varchar(512) NOT NULL,
  `phraseId` bigint(20) NOT NULL,
  `count` decimal(10,0) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `TopicPeriodicAdjacencyPhrasesPeriodEndIdx` (`periodEnd`),
  KEY `TopicPeriodicAdjacencyPhrasesTopicIdx` (`topic`),
  KEY `TopicPeriodicAdjacencyPhrasesPhraseIdIdx` (`phraseId`)
) ENGINE=InnoDB AUTO_INCREMENT=94758467 DEFAULT CHARSET=latin1
