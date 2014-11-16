/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avasthi.research.fpmi.tacitknowledge;

import com.avasthi.research.fpmi.tacitknowledge.common.InterestingPhrase;
import com.avasthi.research.fpmi.tacitknowledge.common.NetworkEdge;
import com.avasthi.research.fpmi.tacitknowledge.common.NetworkNode;
import com.avasthi.research.fpmi.tacitknowledge.common.UsenetNetworkEdgeMessage;
import com.avasthi.research.fpmi.tacitknowledge.common.UsenetPostHeaders;
import com.avasthi.research.fpmi.tacitknowledge.common.UsenetPostPhraseScore;
import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.ejb.Local;

/**
 *
 * @author vavasthi
 */
@Local
public interface UsenetPostSessionLocal {

    List<UsenetPost> listUsenetPosts(String email);
    List<UsenetPost> listUsenetPosts();
    int countUsenetPosts(String email);
    int countUsenetPosts();

    List<Long> listIndividualIds();

    List<String> listMessageIds(long id, Date from, Date to);

    String getMessageBody(String id);
    Date getMinDateForUser(Long uid);
    Date getMaxDateForUser(Long uid);
    Date getMinDate();
    Date getMaxDate();

    void updateMessageId();

    void insertPhrases(long userid, Date from, Date to, List<UsenetPostPhraseScore> ppsList);
    UsenetPostHeaders getPost(String id);
    Set<NetworkNode> getNetworkNodes(Date from, Date to, String topic);
    List<NetworkEdge> getNetworkEdges(Long src, Long tgt, Date from, Date to, String topic);
    List<String> getTopics();  

    List<InterestingPhrase> getInterestingPhrasesForNewsgroupForYear(String topic, int year);
}
