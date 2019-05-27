package com.bharath.jms.wellness;

import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Topic;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import com.bharath.jms.hr.Employee;

public class WellnessApp {

	public static void main(String[] args) throws NamingException, JMSException {
		// TODO Auto-generated method stub
		InitialContext context= new InitialContext();
		Topic topic= (Topic) context.lookup("topic/empTopic");
		
		try
		(
				ActiveMQConnectionFactory cf= new ActiveMQConnectionFactory();
				JMSContext jmsContext= cf.createContext();
		)
		{
			JMSConsumer consumer= jmsContext.createConsumer(topic);
			Message message= consumer.receive();
			Employee employee= message.getBody(Employee.class);
			System.out.println(employee.getFirstName());
			
		}
	}

}
