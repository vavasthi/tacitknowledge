drop table TacitKnowledgeTopicPhrase;
CREATE TABLE  TacitKnowledgeTopicPhrase(
  id bigint(20) NOT NULL AUTO_INCREMENT,
  topic varchar(512) NOT NULL, 
  phrase varchar(512) NOT NULL,
  count decimal(10,0) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `TopicAdjacencyPhrasesPhraseIdx` (`phrase`)
);

DROP TABLE TacitKnowledgeTopicAdjacency;
CREATE TABLE TacitKnowledgeTopicAdjacency (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  topic varchar(512) NOT NULL, 
  firstPhraseId bigint(20) NOT NULL,
  secondPhraseId bigint(20) NOT NULL,
  count decimal(10,0) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `TopicAdjacencyDependencyFirstPhraseIdx` (`firstPhraseId`),
  KEY `TopicAdjacencyDependencySecondPhraseIdx` (`secondPhraseId`)
);
