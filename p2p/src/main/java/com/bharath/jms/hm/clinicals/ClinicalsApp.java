package com.bharath.jms.hm.clinicals;

import java.util.HashMap;
import java.util.Map;

import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.MapMessage;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.TextMessage;
import javax.naming.InitialContext;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import com.bharath.jms.hm.model.Patient;

public class ClinicalsApp {
	public static void main(String[] args) throws Exception{
		InitialContext initialContext= new InitialContext();
		Queue requestQueue= (Queue) initialContext.lookup("queue/requestQueue");
		Queue replyQueue= (Queue) initialContext.lookup("queue/replyQueue");
		try
		(
				ActiveMQConnectionFactory cf= new ActiveMQConnectionFactory();
				JMSContext jmsContext= cf.createContext();
		)
		{
			JMSProducer producer=  jmsContext.createProducer();
			
			ObjectMessage objectMessage= jmsContext.createObjectMessage();
			Patient patient= new Patient();
			patient.setId(123);
			patient.setName("Bob");
			patient.setInsuranceProvider("Blue Cross Blue Shield");
			patient.setCopay(30d);
			patient.setAmountToBePayed(500d);
			objectMessage.setObject(patient);
			
			producer.send(requestQueue, objectMessage);
			
			
			JMSConsumer consumer= jmsContext.createConsumer(replyQueue);
			MapMessage replyMessage = (MapMessage) consumer.receive(30000);
			System.out.println("Patient eligibility is "+replyMessage.getBoolean("eligible"));
		}
	}
}
