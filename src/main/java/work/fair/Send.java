package work.fair;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.nio.charset.StandardCharsets;

/**
 * 工作輪詢: 設定處理完才能接受下一條 能者多勞
 */
public class Send {
    private final static String QUEUE_NAME = "work_fair";

    public static void main(String[] args) throws Exception {
        //創建連接工廠
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        factory.setUsername("sa");
        factory.setVirtualHost("/sa");
        factory.setPassword("12345");
        factory.setPort(5672);

        try(Connection connection = factory.newConnection();
            Channel channel = connection.createChannel()){
            channel.queueDeclare(QUEUE_NAME,false,false,false,null);
            for (int i = 0; i <= 20; i++) {
                String message = "hello world 工作隊列: " + i ;
                channel.basicPublish("",QUEUE_NAME,null,message .getBytes(StandardCharsets.UTF_8));
                System.out.println();
                System.out.printf("[x] Sent '%s'",message);
            }
        }
    }
}
