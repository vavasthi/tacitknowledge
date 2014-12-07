/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avasthi.research.fpmi.tacitknowledge.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author vavasthi
 */
public class UsenetInterestingPhraseMessages implements Serializable {

    List<UsenetInterestingPhraseMessage> messages = new ArrayList<UsenetInterestingPhraseMessage>();

    public List<UsenetInterestingPhraseMessage> getMessages() {
        return messages;
    }

    public void setMessages(List<UsenetInterestingPhraseMessage> messages) {
        this.messages = messages;
    }

    public void addMessage(UsenetInterestingPhraseMessage message) {
        messages.add(message);
    }
}
