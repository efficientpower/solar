package org.wjh.solar.jms;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

@Component
public class IpNotifier {

    private static final Log logger = LogFactory.getLog(IpNotifier.class);

    @Autowired
    @Qualifier("jmsTemplate")
    private JmsTemplate jmsTemplate;
    @Autowired
    @Qualifier("ipQueue")
    private Destination destination;

    public void send(final String ip) {
        MessageCreator messageCreator = new MessageCreator() {
            public Message createMessage(Session session) {
                ObjectMessage message = null;
                try {
                    message = session.createObjectMessage();
                    message.setObject(ip);
                } catch (JMSException e) {
                    logger.error("初始化jms失败", e);
                }
                return (Message) message;
            }
        };
        jmsTemplate.send(this.destination, messageCreator);
        logger.info("send ip=" + ip);
    }
}
