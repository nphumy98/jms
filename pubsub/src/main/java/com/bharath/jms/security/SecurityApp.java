package com.bharath.jms.security;

import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.Message;
import javax.jms.Topic;
import javax.naming.InitialContext;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import com.bharath.jms.hr.Employee;

public class SecurityApp {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		InitialContext context= new InitialContext();
		Topic topic= (Topic) context.lookup("topic/empTopic");
		
		try
		(
				ActiveMQConnectionFactory cf= new ActiveMQConnectionFactory();
				JMSContext jmsContext= cf.createContext();
		)
		{
			jmsContext.setClientID("securityApp");
			JMSConsumer consumer= jmsContext.createDurableConsumer(topic, "subscription1");
			consumer.close();
			
			Thread.sleep(10000);
			
			consumer= jmsContext.createDurableConsumer(topic, "subscription1");
			Message message= consumer.receive();
			Employee employee= message.getBody(Employee.class);
			System.out.println(employee.getFirstName());
			
			consumer.close();
			jmsContext.unsubscribe("subscription1");
			
		}
	}

}
