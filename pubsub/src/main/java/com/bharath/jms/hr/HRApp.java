package com.bharath.jms.hr;

import javax.jms.JMSContext;
import javax.jms.Topic;
import javax.naming.InitialContext;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

public class HRApp {

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
			Employee employee= new Employee();
			employee.setId(123);
			employee.setFirstName("Bharath");
			employee.setLastName("Thippireddy");
			employee.setDestination("Software Architect");
			employee.setEmail("Jimmy.com");
			employee.setPhone("123456");
			
			jmsContext.createProducer().send(topic, employee);
			System.out.println("Message Sent");
			
		}
	}

}
