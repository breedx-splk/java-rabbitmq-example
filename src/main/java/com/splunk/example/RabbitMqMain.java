package com.splunk.example;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.utility.DockerImageName;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class RabbitMqMain {

    static final String QUEUE_NAME = "bunny-hop-a-school-bus";
    private final ScheduledExecutorService writerPool = Executors.newSingleThreadScheduledExecutor();

    public static void main(String[] args) throws Exception {
        new RabbitMqMain().run();
    }

    private void run() throws Exception {
        RabbitMQContainer rabbit = new RabbitMQContainer(DockerImageName.parse("rabbitmq:3.11.6"))
                .withExposedPorts(5672);
        System.out.println("Starting rabbit.");
        rabbit.start();
        System.out.println("Rabbit started");

        ConnectionFactory factory = new ConnectionFactory();
        String rabbitHost = rabbit.getHost();
        factory.setHost(rabbitHost);
        factory.setPort(rabbit.getMappedPort(5672));
        Connection connection = factory.newConnection();
        Channel writeChannel = connection.createChannel();
        writeChannel.queueDeclare(QUEUE_NAME, false, false, false, null);


        var writer = new QueueWriter(writeChannel);
        writerPool.scheduleWithFixedDelay(writer::write, 0, 1, TimeUnit.SECONDS);

        Channel readChannel = connection.createChannel();
        var reader = new QueueReader(readChannel);
        reader.runForever();
    }

}
