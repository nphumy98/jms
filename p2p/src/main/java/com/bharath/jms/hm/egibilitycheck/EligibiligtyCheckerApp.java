package com.bharath.jms.hm.egibilitycheck;

import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.naming.InitialContext;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import com.bharath.jms.hm.egibilitycheck.listener.EgibilityCheckListener;
import com.bharath.jms.hm.model.Patient;

public class EligibiligtyCheckerApp {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		InitialContext initialContext= new InitialContext();
		Queue requestQueue= (Queue) initialContext.lookup("queue/requestQueue");
		
		try
		(
				ActiveMQConnectionFactory cf= new ActiveMQConnectionFactory();
				JMSContext jmsContext= cf.createContext();
		)
		{
			JMSConsumer consumer= jmsContext.createConsumer(requestQueue);
			consumer.setMessageListener(new EgibilityCheckListener());
			
		}
	}

}
