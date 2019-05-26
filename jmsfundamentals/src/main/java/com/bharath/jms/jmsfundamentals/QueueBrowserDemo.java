package com.bharath.jms.jmsfundamentals;

import java.util.Enumeration;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class QueueBrowserDemo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		InitialContext initialContext= null;
		Connection connection=null;
		try
		{
			initialContext= new InitialContext();
			ConnectionFactory cf= (ConnectionFactory) initialContext.lookup("ConnectionFactory");
			connection= cf.createConnection();
			Session session = connection.createSession();
			Queue queue=(Queue) initialContext.lookup("queue/myQueue");
			
			MessageProducer producer= session.createProducer(queue);
			TextMessage message1= session.createTextMessage("Message 1");
			TextMessage message2= session.createTextMessage("Message 2");
			producer.send(message1);
			producer.send(message2);
			
			QueueBrowser browser= session.createBrowser(queue);
			
			Enumeration mesesagesEnum= browser.getEnumeration();
			while (mesesagesEnum.hasMoreElements())
			{
				TextMessage eachMessage= (TextMessage) mesesagesEnum.nextElement();
				System.out.println("Browsing: "+eachMessage.getText());
			}
			
			//consume 2 times
			MessageConsumer consumer=session.createConsumer(queue);
			connection.start();
			TextMessage messageReceived= (TextMessage) consumer.receive(5000);
			System.out.println("Message Received:"+ messageReceived.getText());
			messageReceived= (TextMessage) consumer.receive(5000);
			System.out.println("Message Received:"+ messageReceived.getText());
			
			
			//after consume then none will be printed. 
			mesesagesEnum= browser.getEnumeration();
			while (mesesagesEnum.hasMoreElements())
			{
				TextMessage eachMessage= (TextMessage) mesesagesEnum.nextElement();
				System.out.println("Browsing: "+eachMessage.getText());
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		} 
		finally
		{
			if (initialContext!=null)
			{
				try {
					initialContext.close();
				} catch (NamingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if (connection!=null)
			{
				try {
					connection.close();
				} catch (JMSException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}

}
