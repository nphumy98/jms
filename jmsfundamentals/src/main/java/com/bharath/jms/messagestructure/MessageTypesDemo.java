package com.bharath.jms.messagestructure;

import javax.jms.BytesMessage;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.StreamMessage;
import javax.jms.TextMessage;
import javax.naming.InitialContext;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

public class MessageTypesDemo {

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
//			BytesMessage bytesMessage= jmsContext.createBytesMessage();
//			bytesMessage.writeUTF("John");
//			bytesMessage.writeLong(123l);
//			producer.send(queue, bytesMessage);
			
//			StreamMessage streamMessage= jmsContext.createStreamMessage();
//			streamMessage.writeBoolean(true);
//			streamMessage.writeFloat(2.5f);
//			producer.send(queue, streamMessage);
			
//			MapMessage mapMessage= jmsContext.createMapMessage();
//			mapMessage.setBoolean("isCreditAvailable", true);
//			producer.send(queue, mapMessage);

//			ObjectMessage objectMessage= jmsContext.createObjectMessage();
//			Patient patient= new Patient();
//			patient.setId(123);
//			patient.setName("John");
//			objectMessage.setObject(patient);
//			producer.send(queue, objectMessage);
			
			
			//this way sent is without header
			Patient patient= new Patient();
			patient.setId(123);
			patient.setName("John");
			producer.send(queue, patient);
			
			
			
			//try to get message in 5 sec , if not then it stop
//			BytesMessage messageReceived= (BytesMessage)jmsContext.createConsumer(queue).receive(5000);
//			System.out.println(messageReceived.readUTF());
//			System.out.println(messageReceived.readLong());
			
//			StreamMessage messageReceived= (StreamMessage)jmsContext.createConsumer(queue).receive(5000);
//			System.out.println(messageReceived.readBoolean());
//			System.out.println(messageReceived.readFloat());
			
//			MapMessage messageReceived= (MapMessage)jmsContext.createConsumer(queue).receive(5000);
//			System.out.println(messageReceived.getBoolean("isCreditAvailable"));
			
//			ObjectMessage messageReceived=(ObjectMessage)jmsContext.createConsumer(queue).receive(5000);
//			Patient object= (Patient) messageReceived.getObject();
//			System.out.println(object.getId());
//			System.out.println(object.getName());
			
			Patient messageReceived=jmsContext.createConsumer(queue).receiveBody(Patient.class);
			System.out.println(messageReceived.getId());
			System.out.println(messageReceived.getName());
			
		}
	}

}
