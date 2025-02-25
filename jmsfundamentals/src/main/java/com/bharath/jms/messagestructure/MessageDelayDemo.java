package com.bharath.jms.messagestructure;

import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.Message;
import javax.jms.Queue;
import javax.naming.InitialContext;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

public class MessageDelayDemo {

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
			//set message delivery delay to 3 sec
			producer.setDeliveryDelay(3000);
			producer.send(queue, "Arise Awake and stop not til the goal is reached");
			

			
			//try to get message in 5 sec , if not then it stop
			Message messageReceived= jmsContext.createConsumer(queue).receive(5000);
			System.out.println(messageReceived);
		}
	}

}
