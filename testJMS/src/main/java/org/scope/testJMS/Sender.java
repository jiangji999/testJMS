package org.scope.testJMS;

import java.util.Date;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class Sender {

	public static void main(String[] srgs) throws JMSException,
			InterruptedException {
		ConnectionFactory factory;
		Connection connection = null;
		Session session = null;
		Destination destination = null;
		MessageProducer producer = null;

		// 创建连接工厂
		factory = new ActiveMQConnectionFactory(
				ActiveMQConnection.DEFAULT_USER,
				ActiveMQConnection.DEFAULT_PASSWORD, "tcp://localhost:61616");
		// 创建连接
		connection = factory.createConnection();
		// 建立连接
		connection.start();
		// 建立session
		session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
		// 指定消息队列
		destination = session.createQueue("xiaoyunduo");
		// 创建消息发生器
		producer = session.createProducer(destination);

		for (int i = 0; i < 5; i++) {
			MapMessage message = session.createMapMessage();
			message.setLong("mess", new Date().getTime());
			Thread.sleep(1000);
			// 发送消息
			producer.send(message);
		}

		session.commit();
		session.close();
		connection.close();

	}
}
