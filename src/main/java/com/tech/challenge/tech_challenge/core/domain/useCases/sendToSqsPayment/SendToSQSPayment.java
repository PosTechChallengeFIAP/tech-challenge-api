package com.tech.challenge.tech_challenge.core.domain.useCases.sendToSqsPayment;

import java.util.UUID;

import org.springframework.stereotype.Service;

import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;
import software.amazon.awssdk.services.sqs.model.SendMessageResponse;

@Service
public class SendToSQSPayment implements ISendToSQSPayment{
    public void execute(UUID orderId, UUID paymentId, String paymentStatus) {
        String queueUrl = System.getenv("PAYMENT_QUEUE_URL");

        try (SqsClient sqsClient = SqsClient.builder()
                .region(Region.of("us-west-2"))
                .credentialsProvider(DefaultCredentialsProvider.create())
                .build()) {

            String body = String.format(
                "{\"type\":\"payment.lambda\",\"data\":{\"orderId\":\"%s\",\"paymentId\":\"%s\",\"status\":\"%s\"}}"
                , orderId.toString(), paymentId.toString(), paymentStatus);

            SendMessageRequest sendMsgRequest = SendMessageRequest.builder()
                    .queueUrl(queueUrl)
                    .messageBody(body)
                    .build();

            SendMessageResponse sendMessageResponse = sqsClient.sendMessage(sendMsgRequest);

            System.out.println("Mensagem enviada com ID: " + sendMessageResponse.messageId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
