package direct;

import com.rabbitmq.client.*;

/**
 * 路由 消息消費者
 */
public class Recv02 {
    private final static String EXCHANGE_NAME = "exchange_direct";

    public static void main(String[] args) throws Exception {
        //創建連接工廠
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        factory.setUsername("sa");
        factory.setVirtualHost("/sa");
        factory.setPassword("12345");
        factory.setPort(5672);

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        //綁定交換機
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
        //獲取隊列
        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName,EXCHANGE_NAME,Send.WARING_ROUTING_KEY);
        System.out.println("[*] Waiting for messages.");
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.printf("[x] 消費者收到消息 :%s",message);
            System.out.println();
        };
        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {
        });
    }
}
