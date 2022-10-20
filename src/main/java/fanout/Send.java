package fanout;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.nio.charset.StandardCharsets;

/**
 * 訂閱者/發布者
 */
public class Send {
    private final static String EXCHANGE_NAME = "exchange_fanout";


    public static void main(String[] args) throws Exception {
        //創建連接工廠
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        factory.setUsername("sa");
        factory.setVirtualHost("/sa");
        factory.setPassword("12345");
        factory.setPort(5672);

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            //綁定交換機
            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);
            String message = "發布者 發布消息囉";
            System.out.println(message);
            channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes(StandardCharsets.UTF_8));
        }
    }
}
