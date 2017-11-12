package edu.hnust.application.core.rabbitMq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;

public class MQSender {
	private String queueName;
	private String messageTitle = "";
	private Connection connection = null;
	private Channel channel = null;

	public MQSender(String queueName) throws IOException {
		this(queueName, RabbitMQConfig.HOST);
	}

	public MQSender(String queueName, String host) throws IOException {
		this(queueName, host, null);
	}
	
	public MQSender(String queueName, String host, String messageTitle) throws IOException {
		this.queueName = queueName;
		if (queueName == null) {
			return;
		}
		if (StringUtils.isNotBlank(messageTitle)) {
			this.messageTitle = messageTitle + " " + (StringUtils.isNotBlank(host)?host:RabbitMQConfig.HOST);
		}
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(StringUtils.isNotBlank(host)?host:RabbitMQConfig.HOST);
		factory.setPort(RabbitMQConfig.PORT.intValue());
		factory.setUsername(RabbitMQConfig.USER);
		factory.setPassword(RabbitMQConfig.PASSWORD);
		this.connection = factory.newConnection();
		this.channel = this.connection.createChannel();
		boolean durable = true;
		this.channel.queueDeclare(queueName, durable, false, false, null);
	}

	public void send(String message) throws IOException {
		if ((this.connection == null) || (this.channel == null)
				|| (message == null)) {
			return;
		}
		this.channel.basicPublish("", this.queueName,
				MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
		System.out.println("[x] Sent(" + this.messageTitle + ") '" + message
				+ "'");
	}

	public void close() throws IOException {
		if ((this.connection == null) || (this.channel == null)) {
			return;
		}
		this.channel.close();
		this.connection.close();
	}

	public void sendAndClose(String channel, String title, String content) {
		MQSender mq = null;
		try {
			mq = new MQSender(channel);
			mq.setMessageTitle(title);
			mq.send(content);
		} catch (Exception e) {
			e.printStackTrace();
			try {
				if (mq != null) {
					mq.close();
				}
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		} finally {
			try {
				if (mq != null) {
					mq.close();
				}
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
	}

	public String getQueueName() {
		return this.queueName;
	}

	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}

	public String getMessageTitle() {
		return this.messageTitle;
	}

	public void setMessageTitle(String messageTitle) {
		this.messageTitle = messageTitle;
	}
}
