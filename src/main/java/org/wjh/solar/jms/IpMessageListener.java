package org.wjh.solar.jms;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

@Component
public class IpMessageListener implements MessageListener {

	private static final Log logger = LogFactory.getLog(IpMessageListener.class);

	@Override
	public void onMessage(Message msg) {
		// TODO Auto-generated method stub
		ObjectMessage objMsg = (ObjectMessage) msg;
		try {
			String ip = (String) objMsg.getObject();
			logger.info("receive ip=" + ip);
		} catch (JMSException e) {
			logger.error("获取消息内容时出错", e);
		} catch (Exception e) {
			logger.error("IpMessageListener出错", e);
		}
	}

}
