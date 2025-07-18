package net.pm.appoitmentservice.appoitmentservice.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class ZoomHelper {

    // Use your new credentials here:
    private static final String CLIENT_ID = "XOy0yZUFTYWhVMzTFLAxQA";
    private static final String CLIENT_SECRET = "96Xb2QWt7HJc2qQUrYzqMxSkxZBMeX1I";
    private static final String ACCOUNT_ID = "2qMXfUYBT1WtEh3rNzsnXw";

    public static ZoomMeeting createMeeting(net.pm.appoitmentservice.appoitmentservice.entity.Slot slot) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            ObjectMapper mapper = new ObjectMapper();

            // ===========================
            // 1️⃣ Get access token
            // ===========================
            String credentials = CLIENT_ID + ":" + CLIENT_SECRET;
            String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes(StandardCharsets.UTF_8));

            HttpHeaders tokenHeaders = new HttpHeaders();
            tokenHeaders.set("Authorization", "Basic " + encodedCredentials);
            tokenHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            // Pass parameters as form data string (important!)
            String tokenBody = "grant_type=account_credentials&account_id=" + ACCOUNT_ID;

            HttpEntity<String> tokenRequest = new HttpEntity<>(tokenBody, tokenHeaders);

            ResponseEntity<JsonNode> tokenResponse = restTemplate.exchange(
                    "https://zoom.us/oauth/token",
                    HttpMethod.POST,
                    tokenRequest,
                    JsonNode.class
            );

            if (!tokenResponse.getStatusCode().is2xxSuccessful()) {
                throw new RuntimeException("Failed to get access token from Zoom");
            }

            String accessToken = tokenResponse.getBody().get("access_token").asText();

            // ===========================
            // 2️⃣ Create meeting
            // ===========================
            HttpHeaders meetingHeaders = new HttpHeaders();
            meetingHeaders.setBearerAuth(accessToken);
            meetingHeaders.setContentType(MediaType.APPLICATION_JSON);

            ObjectNode body = mapper.createObjectNode();
            body.put("topic", "Appointment with Doctor ID: " + slot.getDoctorId());
            body.put("type", 2); // Scheduled meeting
            body.put("start_time", slot.getDate() + "T" + slot.getStartTime() + ":00Z");
            body.put("duration", 30);
            body.put("timezone", "UTC");

            HttpEntity<String> meetingRequest = new HttpEntity<>(body.toString(), meetingHeaders);

            ResponseEntity<JsonNode> meetingResponse = restTemplate.postForEntity(
                    "https://api.zoom.us/v2/users/me/meetings",
                    meetingRequest,
                    JsonNode.class
            );

            if (!meetingResponse.getStatusCode().is2xxSuccessful()) {
                throw new RuntimeException("Failed to create Zoom meeting");
            }

            String joinUrl = meetingResponse.getBody().get("join_url").asText();
            String startUrl = meetingResponse.getBody().get("start_url").asText();

            return new ZoomMeeting(joinUrl, startUrl);

        } catch (Exception e) {
            throw new RuntimeException("Failed to create Zoom meeting", e);
        }
    }

    public static class ZoomMeeting {
        private final String joinUrl;
        private final String startUrl;

        public ZoomMeeting(String joinUrl, String startUrl) {
            this.joinUrl = joinUrl;
            this.startUrl = startUrl;
        }

        public String getJoinUrl() {
            return joinUrl;
        }

        public String getStartUrl() {
            return startUrl;
        }
    }
}
