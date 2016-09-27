package org.scope.testJMS;

import java.util.Date;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.MessageConsumer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class Receiver {

	public static void main(String[] args) throws JMSException {

		ConnectionFactory factory = null;
		Connection connection = null;
		Session session = null;
		Destination destination = null;
		MessageConsumer consumer = null;

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
		// 产生消费者
		consumer = session.createConsumer(destination);

		for (int i = 0; i < 5; i++) {
			// 获取 jms server 中的消息
			MapMessage message = (MapMessage) consumer.receive(1000);
			session.commit();
			System.out.println("收到消息：" + message);

		}

		session.close();
		connection.close();

	}
}
