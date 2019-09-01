/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avasthi.research.fpmi.tacitknowledge.contentparsers;

import com.avasthi.research.fpmi.tacitknowledge.common.UsenetInitiatePeriodicPhraseAdjacencyCalculation;
import com.avasthi.research.fpmi.tacitknowledge.common.UsenetInitiatePhraseAdjacencyCalculation;
import com.avasthi.research.fpmi.tacitknowledge.common.UsenetInitiatePhraseNetworkEdge;
import com.avasthi.research.fpmi.tacitknowledge.common.UsenetInterestingPhraseMessage;
import com.avasthi.research.fpmi.tacitknowledge.common.UsenetInterestingPhraseMessages;
import com.avasthi.research.fpmi.tacitknowledge.common.UsenetMessageIds;
import com.avasthi.research.fpmi.tacitknowledge.common.UsenetNetworkEdgeMessage;
import com.avasthi.research.fpmi.tacitknowledge.common.UsenetPostMessage;
import com.avasthi.research.fpmi.tacitknowledge.common.UsenetUpgradeTableMessage;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author vavasthi
 */
public class UsenetPostMessageQueueSender {

    private static ConnectionFactory connectionFactory;

    private static Queue queue;
    Context context = null;
    

    private static final String INITIAL_CONTEXT_FACTORY = "org.jboss.naming.remote.client.InitialContextFactory";
    private static final String PROVIDER_URL = "http-remoting://localhost:8080";
    private static final String DEFAULT_CONNECTION_FACTORY = "jms/RemoteConnectionFactory";
    private static final String USERNAME = "tacitknowledgeQueue";
    private static final String PASSWORD = "tacitknowledgeQueue123";
    

    Connection connection;
    Session session;
    MessageProducer messageProducer;

    private static UsenetPostMessageQueueSender self = new UsenetPostMessageQueueSender();

    public static UsenetPostMessageQueueSender instance() {
        if (self == null) {
            self = new UsenetPostMessageQueueSender();
        }
        return self;
    }

    private UsenetPostMessageQueueSender() {
        try {
            final Properties env = new Properties();
            env.put(Context.INITIAL_CONTEXT_FACTORY, INITIAL_CONTEXT_FACTORY);
            env.put(Context.PROVIDER_URL, System.getProperty(Context.PROVIDER_URL, PROVIDER_URL));
  
            env.put(Context.SECURITY_PRINCIPAL, USERNAME);
            env.put(Context.SECURITY_CREDENTIALS, PASSWORD);
            context = new InitialContext(env);

            String connectionFactoryString
                    = System.getProperty("connection.factory", UsenetPostMessageQueueSender.DEFAULT_CONNECTION_FACTORY);

            Logger.getLogger(UsenetPostMessageQueueSender.class.getName()).info("Attempting to acquire connection factory \"" + connectionFactoryString + "\"");
            connectionFactory = (ConnectionFactory) context.lookup(connectionFactoryString);
            queue = (Queue) context.lookup("jms/queue/usenetPostQueue");
            connection = connectionFactory.createConnection(USERNAME, PASSWORD);
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            messageProducer = session.createProducer(queue);
        } catch (NamingException ex) {
            Logger.getLogger(UsenetPostMessageQueueSender.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JMSException ex) {
            Logger.getLogger(UsenetPostMessageQueueSender.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void send(UsenetPostMessage message) {
        try {
            Message m = session.createObjectMessage(message);
            m.setIntProperty(UsenetMessageIds.MESSAGE_TYPE_PROPERTY, UsenetMessageIds.POST_MESSAGE);
            messageProducer.send(m);
        } catch (JMSException ex) {
            Logger.getLogger(UsenetPostMessageQueueSender.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void send(UsenetInterestingPhraseMessage message) {
        try {
            Message m = session.createObjectMessage(message);
            m.setIntProperty(UsenetMessageIds.MESSAGE_TYPE_PROPERTY, UsenetMessageIds.INTERESTING_PHRASE_MESSAGE);
            messageProducer.send(m);
        } catch (JMSException ex) {
            Logger.getLogger(UsenetPostMessageQueueSender.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void send(UsenetInterestingPhraseMessages messages) {
        try {
            Message m = session.createObjectMessage(messages);
            m.setIntProperty(UsenetMessageIds.MESSAGE_TYPE_PROPERTY, UsenetMessageIds.INTERESTING_PHRASE_MESSAGES);
            messageProducer.send(m);
        } catch (JMSException ex) {
            Logger.getLogger(UsenetPostMessageQueueSender.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void send(UsenetNetworkEdgeMessage message) {
        try {
            Message m = session.createObjectMessage(message);
            m.setIntProperty(UsenetMessageIds.MESSAGE_TYPE_PROPERTY, UsenetMessageIds.NETWORK_EDGE_MESSAGE);
            messageProducer.send(m);
        } catch (JMSException ex) {
            Logger.getLogger(UsenetPostMessageQueueSender.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void send(UsenetUpgradeTableMessage message) {
        try {
            Message m = session.createObjectMessage(message);
            m.setIntProperty(UsenetMessageIds.MESSAGE_TYPE_PROPERTY, UsenetMessageIds.UPGRADE_TABLE_MESSAGE);
            messageProducer.send(m);
        } catch (JMSException ex) {
            Logger.getLogger(UsenetPostMessageQueueSender.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void send(UsenetInitiatePhraseAdjacencyCalculation message) {
        try {
            Message m = session.createObjectMessage(message);
            m.setIntProperty(UsenetMessageIds.MESSAGE_TYPE_PROPERTY, UsenetMessageIds.INITIATE_PHRASE_ADJACENCY);
            messageProducer.send(m);
        } catch (JMSException ex) {
            Logger.getLogger(UsenetPostMessageQueueSender.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void send(UsenetInitiatePeriodicPhraseAdjacencyCalculation message) {
        try {
            Message m = session.createObjectMessage(message);
            m.setIntProperty(UsenetMessageIds.MESSAGE_TYPE_PROPERTY, UsenetMessageIds.INITIATE_PERIODIC_PHRASE_ADJACENCY);
            messageProducer.send(m);
        } catch (JMSException ex) {
            Logger.getLogger(UsenetPostMessageQueueSender.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void send(UsenetInitiatePhraseNetworkEdge message) {
        try {
            Message m = session.createObjectMessage(message);
            m.setIntProperty(UsenetMessageIds.MESSAGE_TYPE_PROPERTY, UsenetMessageIds.INITIATE_PHRASE_NETWORK_EDGE);
            messageProducer.send(m);
        } catch (JMSException ex) {
            Logger.getLogger(UsenetPostMessageQueueSender.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
