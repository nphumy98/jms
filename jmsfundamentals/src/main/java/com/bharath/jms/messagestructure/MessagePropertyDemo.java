package com.bharath.jms.messagestructure;

import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.TextMessage;
import javax.naming.InitialContext;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

public class MessagePropertyDemo {

	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		InitialContext context= new InitialContext();
		Queue queue= (Queue) context.lookup("queue/myQueue");
		try 
		(
			ActiveMQConnectionFactory cf= new ActiveMQConnectionFactory();		
			JMSContext jmsContext= cf.createContext();
		){
			JMSProducer producer= jmsContext.createProducer();
			TextMessage textMessage= jmsContext.createTextMessage("Arise Awake and stop not til the goal is reached");
			textMessage.setBooleanProperty("loggedIn", true);
			textMessage.setStringProperty("userToken", "abc123");
			producer.send(queue, textMessage);
			

			
			//try to get message in 5 sec , if not then it stop
			Message messageReceived= jmsContext.createConsumer(queue).receive(5000);
			System.out.println(messageReceived);
			System.out.println(messageReceived.getBooleanProperty("loggedIn"));
			System.out.println(messageReceived.getStringProperty("userToken"));
		}
	}

}
