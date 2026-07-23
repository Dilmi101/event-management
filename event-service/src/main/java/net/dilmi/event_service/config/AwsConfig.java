package net.dilmi.event_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.services.lambda.LambdaClient;

@Configuration
public class AwsConfig {

    @Bean
    public LambdaClient lambdaClient() {
        // Region and credentials both resolve from the pod environment: AWS_REGION,
        // and the AWS_ROLE_ARN / AWS_WEB_IDENTITY_TOKEN_FILE injected by EKS's Pod
        // Identity webhook via the event-service ServiceAccount's IRSA annotation.
        return LambdaClient.builder().build();
    }
}
