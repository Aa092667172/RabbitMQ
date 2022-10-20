package work.test;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.util.concurrent.TimeUnit;

/**
 * 輪詢消息消費者
 */
public class Recv01 {
    private final static String QUEUE_NAME = "work";

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
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println("[*] Waiting for messages.");
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            try{
                TimeUnit.SECONDS.sleep(4);
            }catch (Exception e){

            }
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println();
            System.out.printf("[x] Received :%s", message);

            //手動確認
            //multiple:是否單線程確認
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(),false);
        };
        channel.basicConsume(QUEUE_NAME, false, deliverCallback, consumerTag -> {
        });
    }
}
