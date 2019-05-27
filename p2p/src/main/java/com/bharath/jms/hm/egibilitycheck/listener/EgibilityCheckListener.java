package com.bharath.jms.hm.egibilitycheck.listener;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import com.bharath.jms.hm.model.Patient;

public class EgibilityCheckListener implements MessageListener {

	@Override
	public void onMessage(Message message) {
		// TODO Auto-generated method stub
		ObjectMessage objectMessage = (ObjectMessage) message;
		try {
			Patient patient= (Patient) objectMessage.getObject();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
