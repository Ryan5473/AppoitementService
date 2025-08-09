package net.pm.appoitmentservice.appoitmentservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ZoomConfig {

    @Value("${zoom.clientId}")
    private String clientId;

    @Value("${zoom.clientSecret}")
    private String clientSecret;

    @Value("${zoom.accountId}")
    private String accountId;

    public String getClientId() { return clientId; }
    public String getClientSecret() { return clientSecret; }
    public String getAccountId() { return accountId; }
}
