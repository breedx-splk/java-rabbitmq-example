package com.splunk.example;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static com.splunk.example.RabbitMqMain.QUEUE_NAME;
import static java.nio.charset.StandardCharsets.UTF_8;

public class QueueReader {
    private final Channel channel;

    public QueueReader(Channel channel) {
        this.channel = channel;
    }

    public void runForever() {
        try {
            channel.basicConsume(QUEUE_NAME, true, new DeliverCallback() {
                @Override
                public void handle(String consumerTag, Delivery delivery) throws IOException {
                    String message = new String(delivery.getBody(), UTF_8);
                    System.out.println(" [x] Received '" + message + "'");
                }
            }, tag -> {});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
