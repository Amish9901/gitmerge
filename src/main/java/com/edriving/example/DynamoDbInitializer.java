package com.edriving.example;

import org.glassfish.jersey.server.monitoring.ApplicationEvent;
import org.glassfish.jersey.server.monitoring.ApplicationEventListener;
import org.glassfish.jersey.server.monitoring.RequestEvent;
import org.glassfish.jersey.server.monitoring.RequestEventListener;

import javax.ws.rs.ext.Provider;

@Provider
public class DynamoDbInitializer implements ApplicationEventListener {

    private final DynamoDb dynamoDb = new DynamoDb();

    @Override
    public void onEvent(ApplicationEvent applicationEvent) {
        if (applicationEvent.getType() == ApplicationEvent.Type.INITIALIZATION_START) {
            dynamoDb.initTable();
        }
    }

    @Override
    public RequestEventListener onRequest(RequestEvent requestEvent) {
        return null;
    }
}
