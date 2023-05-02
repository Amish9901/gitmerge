package com.edriving.example;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.*;

import java.util.List;

public class DynamoDb {

    private static final String DYNAMODB_ENDPOINT_URL = "http://localhost:8000";
    private static final String REGION = "us-west-2";
    private static final String TABLE_NAME = "my-table";

    private final AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
            .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(DYNAMODB_ENDPOINT_URL, REGION))
            // local dynamodb separates the data by the user
            // You need to use the same access key and secret key to access the same data
            // SO you need to use the same access/secret as you use for "aws configure"
            // else you will not see the table you created with aws cli
            .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials("accessKey", "secretKey")))
            .build();

    public void initTable() {
        List<String> tables = listTable();
        if (!tables.contains(TABLE_NAME)) {
            createTable();
        } else {
            System.out.printf("Table %s already exists%n", TABLE_NAME);
        }
    }

    private void createTable() {
        CreateTableRequest createTableRequest = new CreateTableRequest()
                .withTableName(TABLE_NAME)
                .withKeySchema(new KeySchemaElement("id", KeyType.HASH), new KeySchemaElement("date", KeyType.RANGE))
                .withAttributeDefinitions(new AttributeDefinition("id", ScalarAttributeType.S), new AttributeDefinition("date", ScalarAttributeType.S))
                .withProvisionedThroughput(new ProvisionedThroughput(1L, 1L));

        CreateTableResult result = client.createTable(createTableRequest);
        System.out.printf("Table %s is created%n", result.getTableDescription().getTableName());
    }

    private List<String> listTable() {
        return client.listTables().getTableNames();
    }
}
