package com.bharath.jms.messagestructure;

import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.Queue;
import javax.jms.TextMessage;
import javax.naming.InitialContext;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

public class RequestReplyDemo {

	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		InitialContext context= new InitialContext();
		Queue queue= (Queue) context.lookup("queue/requestQueue");
		Queue replyQueue= (Queue) context.lookup("queue/replyQueue");
		try 
		(
			ActiveMQConnectionFactory cf= new ActiveMQConnectionFactory();		
			JMSContext jmsContext= cf.createContext();
		){
			JMSProducer producer = jmsContext.createProducer();
			TextMessage message=jmsContext.createTextMessage("Arise Awake and stop not til the goal is reached");
			message.setJMSReplyTo(replyQueue);
			producer.send(queue,message );
			
			JMSConsumer consumer = jmsContext.createConsumer(queue);
			TextMessage messageReceived= (TextMessage) consumer.receive();
			System.out.println(messageReceived.getText());
			
			JMSProducer replyProducer = jmsContext.createProducer();
			replyProducer.send(messageReceived.getJMSDestination(),"You are awesome!!");
			
			JMSConsumer replyConsumer = jmsContext.createConsumer(replyQueue);
			System.out.println(replyConsumer.receiveBody(String.class));
		}
	}

}
