package com.splunk.example;

import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.util.Date;

import static com.splunk.example.RabbitMqMain.QUEUE_NAME;

public class QueueWriter {

    private final Channel channel;

    public QueueWriter(Channel channel) {
        this.channel = channel;
    }

    public void write() {
        try {
            System.out.println("Writer is going to write...");
            String message = "Hello from writer: " + new Date();
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
