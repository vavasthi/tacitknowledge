DELIMITER $$
create procedure populate_phrases_table()
BEGIN

DECLARE window_date_start DATE;
DECLARE window_date_end DATE;
DECLARE period_date_end DATE;

set window_date_start = makedate(1970, 1);
set window_date_end = date_add(window_date_start, INTERVAL 6 MONTH);
set period_date_end = last_day( makedate(2014,270) );

label1 : LOOP

  insert into TopicPeriodicAdjacencyDependency select null, last_day(window_date_end), a.topic, d.id, e.id, sum(a.count) from TopicPeriodicAdjacencyDependencyExactTimestamp a, TopicPeriodicAdjacencyPhrasesExactTimestamp b, TopicPeriodicAdjacencyPhrasesExactTimestamp c, TopicPeriodicPhraseMaster d, TopicPeriodicPhraseMaster e where a.date >= window_date_start and a.date < date_add(window_date_end, interval 1 day)  and a.firstPhraseId = b.id and a.secondPhraseId = c.id and b.phrase = d.phrase and c.phrase = e.phrase and a.topic = b.topic and b.topic = c.topic group by a.topic,d.id, e.id;

  insert into TopicPeriodicAdjacencyPhrases select null, last_day(window_date_end), a.topic, b.id , sum(a.count) from TopicPeriodicAdjacencyPhrasesExactTimestamp a,  TopicPeriodicPhraseMaster b where a.date >= window_date_start and a.date < date_add(window_date_end, interval 1 day)  and a.phrase = b.phrase group by topic, b.id;


  set window_date_start = date_add(window_date_start, INTERVAL 3 MONTH);
  set window_date_end = date_add(window_date_start, INTERVAL 6 MONTH);
  if window_date_start >= period_date_end then
    LEAVE label1;
  end if;
  ITERATE label1;
  end LOOP label1;
END$$

