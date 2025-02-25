package com.bharath.jms.messagestructure;

import java.util.HashMap;
import java.util.Map;

import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.Queue;
import javax.jms.TemporaryQueue;
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
			message.setJMSCorrelationID(message.getJMSMessageID());
			producer.send(queue,message );
			System.out.println("message ID:"+message.getJMSMessageID());
			
			Map<String, TextMessage> requestMessages=new HashMap<>();
			requestMessages.put(message.getJMSMessageID(), message);
			
			JMSConsumer consumer = jmsContext.createConsumer(queue);
			TextMessage messageReceived= (TextMessage) consumer.receive();
			System.out.println("messageReceived text:"+messageReceived.getText());
			System.out.println("messageReceived ID:"+messageReceived.getJMSMessageID());
			
			JMSProducer replyProducer = jmsContext.createProducer();
			TextMessage replyMessage=jmsContext.createTextMessage("You are awesome!!");
			replyMessage.setJMSCorrelationID(messageReceived.getJMSMessageID());
			
			replyProducer.send(messageReceived.getJMSReplyTo(),replyMessage);

			JMSConsumer replyConsumer = jmsContext.createConsumer(replyQueue);
			//System.out.println(replyConsumer.receiveBody(String.class));
			TextMessage replyReceived= (TextMessage) replyConsumer.receive();
			System.out.println(requestMessages.get(replyReceived.getJMSCorrelationID()).getText());
			
		}
	}

}
