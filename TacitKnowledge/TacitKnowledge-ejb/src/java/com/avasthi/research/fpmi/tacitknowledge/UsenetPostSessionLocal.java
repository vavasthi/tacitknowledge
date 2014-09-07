/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avasthi.research.fpmi.tacitknowledge;

import com.avasthi.research.fpmi.tacitknowledge.common.UsenetPostPhraseScore;
import java.util.List;
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

    List<String> listMessageIds(long id);

    String getMessageBody(String id);

    void updateMessageId();

    void insertPhrases(long userid, List<UsenetPostPhraseScore> ppsList);
    

}
