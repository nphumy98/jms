package com.bharath.jms.payroll;

import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.Message;
import javax.jms.Topic;
import javax.naming.InitialContext;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import com.bharath.jms.hr.Employee;

public class PayrollApp {

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
			JMSConsumer consumer= jmsContext.createConsumer(topic);
			Message message= consumer.receive();
			Employee employee= message.getBody(Employee.class);
			System.out.println(employee.getFirstName());
			
		}
	}

}
