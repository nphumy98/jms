package com.bharath.jms.jmsfundamentals;

import javax.jms.JMSContext;
import javax.jms.Queue;
import javax.naming.InitialContext;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

public class JMSContextDemo {

	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		InitialContext context= new InitialContext();
		Queue queue= (Queue) context.lookup("queue/myQueue");
		try 
		(
			ActiveMQConnectionFactory cf= new ActiveMQConnectionFactory();		
			JMSContext jmsContext= cf.createContext();
		){
			jmsContext.createProducer().send(queue, "Arise Awake and stop not til the goal is reached");
			String messageReceived= jmsContext.createConsumer(queue).receiveBody(String.class);
			System.out.println(messageReceived);
		}
	}

}
