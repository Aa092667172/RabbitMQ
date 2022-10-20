package direct;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.nio.charset.StandardCharsets;

/**
 * 路由模式 對應的key才會收到對應消息
 */
public class Send {
    private final static String EXCHANGE_NAME = "exchange_direct";
    public final static String INFO_ROUTING_KEY = "info";
    public final static String ERROR_ROUTING_KEY = "error";
    public final static String WARING_ROUTING_KEY = "waring";


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
            //綁定交換機 更改為路由模式 Direct
            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);

            String infoMessage = "一般消息";
            String errorMessage = "錯誤消息";
            String waringMessage = "警告發布消息";

            channel.basicPublish(EXCHANGE_NAME, INFO_ROUTING_KEY, null, infoMessage.getBytes(StandardCharsets.UTF_8));
            channel.basicPublish(EXCHANGE_NAME, ERROR_ROUTING_KEY, null, errorMessage.getBytes(StandardCharsets.UTF_8));
            channel.basicPublish(EXCHANGE_NAME, WARING_ROUTING_KEY, null, waringMessage.getBytes(StandardCharsets.UTF_8));
            System.out.println("[x] Sent '" + infoMessage +"'");
            System.out.println("[x] Sent '" + errorMessage +"'");
            System.out.println("[x] Sent '" + waringMessage +"'");

            //綁定交換機
            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
            String message = "發布者 發布消息囉";
            System.out.println(message);
            channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes(StandardCharsets.UTF_8));
        }
    }
}
