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
			JMSConsumer consumer1= jmsContext.createConsumer(requestQueue);
			JMSConsumer consumer2= jmsContext.createConsumer(requestQueue);
			//consumer.setMessageListener(new EgibilityCheckListener());
			
			//5 times loop, total 10 messages are consumed
			for(int i=1;i<=10;i+=2)
			{
				System.out.println("Consumer1: "+consumer1.receive());
				System.out.println("Consumer2: "+consumer2.receive());
			}
			
			//see lecture 6.48 he explain
			//Thread.sleep(10000);
		}
	}

}
