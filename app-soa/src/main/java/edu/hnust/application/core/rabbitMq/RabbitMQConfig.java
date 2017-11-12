package edu.hnust.application.core.rabbitMq;

public class RabbitMQConfig {
    public static final String HOST = System.getenv("RABBITMQ_NODE_IP_ADDRESS");
    public static final Integer PORT = Integer.valueOf(5672);    
    public static final String USER = "admin";    
    public static final String PASSWORD = "admin4321";    
    public RabbitMQConfig() {
        
    }
}