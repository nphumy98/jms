package com.bharath.jms.messagestructure;

import java.util.Enumeration;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

public class MessagePriority {
	public static void main(String[] args) throws Exception {
		InitialContext context= new InitialContext();
		Queue queue= (Queue) context.lookup("queue/myQueue");
		
		try
		(
				ActiveMQConnectionFactory cf= new ActiveMQConnectionFactory();		
				JMSContext jmsContext= cf.createContext();
		)
		{
			JMSProducer producer= jmsContext.createProducer();
			
			String[] messages= new String[3];
			messages[0]="Message One";
			messages[1]="Message Two";
			messages[2]="Message Three";
			
			//producer.setPriority(3);
			producer.send(queue, messages[0]);
			
			//producer.setPriority(1);
			producer.send(queue, messages[1]);
			
			//producer.setPriority(9);
			producer.send(queue, messages[2]);
			
			JMSConsumer consumer= jmsContext.createConsumer(queue);
			
			for(int i=0; i<3;i++)
			{
				Message receivedMessage= consumer.receive();
				System.out.println(receivedMessage.getJMSPriority());
				//System.out.println(consumer.receiveBody(String.class));
			}
			while(true)
			{
				System.out.println("hehe");
			}
		}
	}

}
