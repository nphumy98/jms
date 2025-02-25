package com.bharath.jms.messagestructure;

import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.Message;
import javax.jms.Queue;
import javax.naming.InitialContext;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

public class MessageExpirationDemo {

	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		InitialContext context= new InitialContext();
		Queue queue= (Queue) context.lookup("queue/myQueue");
		Queue expiryQueue= (Queue) context.lookup("queue/expiryQueue");
		try 
		(
			ActiveMQConnectionFactory cf= new ActiveMQConnectionFactory();		
			JMSContext jmsContext= cf.createContext();
		){
			JMSProducer producer= jmsContext.createProducer();
			//set the message live for 2 seconds. after that it move to expire queue
			producer.setTimeToLive(2000); 
			producer.send(queue, "Arise Awake and stop not til the goal is reached");
			
			Thread.sleep(5000); //make thread sleep 5 second to make sure message expire
			
			//try to get message in 5 sec , if not then it stop
			Message messageReceived= jmsContext.createConsumer(queue).receive(5000);
			System.out.println(messageReceived);
			
			//actually the message not lost. it go to expiryQueue
			System.out.println(jmsContext.createConsumer(expiryQueue).receiveBody(String.class));
		}
	}

}
