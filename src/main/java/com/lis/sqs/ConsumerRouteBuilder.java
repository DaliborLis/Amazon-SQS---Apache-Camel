package com.lis.sqs;

import org.apache.camel.builder.RouteBuilder;

/**
 *
 * @author lisssdal
 */
public class ConsumerRouteBuilder extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        System.out.println("CamelContext routers about to add.");
        from("aws-sqs://MyQueue?amazonSQSClient=#client").process(exchange -> {
            System.out.println("Received message: " + exchange.getIn().getBody(String.class));
        });
    }

}
