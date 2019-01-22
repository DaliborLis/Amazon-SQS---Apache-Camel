package com.lis.sqs;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.SimpleRegistry;

/**
 *
 * @author lisssdal
 */
public class App {

    public static void main(String[] args) throws Exception {
        AmazonSQS client = AmazonSQSClientBuilder.defaultClient();
        System.out.println(client.getQueueUrl("MyQueue"));
        SimpleRegistry registry = new SimpleRegistry();
        registry.put("client", client);
        CamelContext camelContext = new DefaultCamelContext(registry);
        camelContext.addRoutes(new ConsumerRouteBuilder());
        ProducerTemplate template = camelContext.createProducerTemplate();
        camelContext.start();
        String json = "{'message':'Hello World'}";
        template.sendBody("aws-sqs://MyQueue?amazonSQSClient=#client&delay=5000&maxMessagesPerPoll=5", json);
        Thread.sleep(30000);
        camelContext.stop();
    }
}
