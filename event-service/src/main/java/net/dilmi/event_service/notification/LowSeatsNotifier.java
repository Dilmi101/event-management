package net.dilmi.event_service.notification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.services.lambda.LambdaClient;
import software.amazon.awssdk.services.lambda.model.InvocationType;
import software.amazon.awssdk.services.lambda.model.InvokeRequest;

import java.time.Instant;
import java.util.UUID;

@Component
public class LowSeatsNotifier {

    private static final Logger log = LoggerFactory.getLogger(LowSeatsNotifier.class);

    private final LambdaClient lambdaClient;
    private final String functionName;

    public LowSeatsNotifier(LambdaClient lambdaClient,
                             @Value("${notifications.low-seats.lambda-function-name:event-low-seats-notifier}") String functionName) {
        this.lambdaClient = lambdaClient;
        this.functionName = functionName;
    }

    /**
     * @return true if the invoke request was accepted (InvocationType.EVENT only confirms
     * acceptance, not that the Lambda itself ran successfully) -- false if it never even
     * reached that point (credentials, network, permissions), so the caller can undo its
     * debounce marker and let a future reservation retry.
     */
    public boolean notifyLowSeats(UUID eventId, int seatsAvailable, int threshold) {
        try {
            String payload = String.format(
                    "{\"eventId\":\"%s\",\"seatsAvailable\":%d,\"threshold\":%d,\"timestamp\":\"%s\"}",
                    eventId, seatsAvailable, threshold, Instant.now());

            InvokeRequest request = InvokeRequest.builder()
                    .functionName(functionName)
                    .invocationType(InvocationType.EVENT)
                    .payload(SdkBytes.fromUtf8String(payload))
                    .build();

            lambdaClient.invoke(request);
            log.info("Triggered low-seats Lambda for event {} (seatsAvailable={}, threshold={})",
                    eventId, seatsAvailable, threshold);
            return true;
        } catch (Exception e) {
            // A failed notification must never affect the seat reservation itself.
            log.error("Failed to invoke low-seats Lambda for event {}: {}", eventId, e.getMessage(), e);
            return false;
        }
    }
}
