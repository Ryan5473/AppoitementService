package net.pm.appoitmentservice.appoitmentservice.utility;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import net.pm.appoitmentservice.appoitmentservice.config.ZoomConfig;
import net.pm.appoitmentservice.appoitmentservice.entity.Slot;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@Component
public class ZoomHelper {

    private final ZoomConfig zoomConfig;
    private final RestTemplate restTemplate;
    private final ObjectMapper mapper;

    public ZoomHelper(ZoomConfig zoomConfig) {
        this.zoomConfig = zoomConfig;
        this.restTemplate = new RestTemplate();
        this.mapper = new ObjectMapper();
    }

    private String getAccessToken() {
        String url = "https://zoom.us/oauth/token";

        String credentials = zoomConfig.getClientId() + ":" + zoomConfig.getClientSecret();
        String encodedCredentials = java.util.Base64.getEncoder().encodeToString(credentials.getBytes());

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Basic " + encodedCredentials);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        String body = "grant_type=account_credentials&account_id=" + zoomConfig.getAccountId();

        HttpEntity<String> request = new HttpEntity<>(body, headers);

        ResponseEntity<JsonNode> response = restTemplate.exchange(url, HttpMethod.POST, request, JsonNode.class);

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Failed to get Zoom access token: " + response.getStatusCode());
        }

        return response.getBody().get("access_token").asText();
    }

    public ZoomMeetingUrls createZoomMeeting(Slot slot) {
        String accessToken = getAccessToken();

        String url = "https://api.zoom.us/v2/users/me/meetings";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        ObjectNode body = mapper.createObjectNode();
        // ✅ Use doctor name and ID safely from the working day relation
        Long doctorId = slot.getWorkingDay().getDoctor().getId();
        String doctorName = slot.getWorkingDay().getDoctor().getFullName();

        body.put("topic", "Appointment with Dr. " + doctorName + " (ID: " + doctorId + ")");
        body.put("type", 2); // Scheduled meeting
        body.put("start_time", getIso8601DateTime(slot.getDate(), slot.getStartTime()));
        body.put("duration", Math.max(calculateDurationInMinutes(slot), 1)); // ensure duration >= 1
        body.put("timezone", "UTC");

        HttpEntity<String> request = new HttpEntity<>(body.toString(), headers);

        ResponseEntity<JsonNode> response = restTemplate.postForEntity(url, request, JsonNode.class);

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Failed to create Zoom meeting: " + response.getStatusCode());
        }

        JsonNode responseBody = response.getBody();
        String startUrl = responseBody.get("start_url").asText();
        String joinUrl = responseBody.get("join_url").asText();

        return new ZoomMeetingUrls(startUrl, joinUrl);
    }

    private String getIso8601DateTime(String date, String time) {
        LocalDate localDate = LocalDate.parse(date);
        LocalTime localTime = LocalTime.parse(time);
        ZonedDateTime zdt = ZonedDateTime.of(localDate, localTime, ZoneOffset.UTC);
        return zdt.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }

    private int calculateDurationInMinutes(Slot slot) {
        LocalTime start = LocalTime.parse(slot.getStartTime());
        LocalTime end = LocalTime.parse(slot.getEndTime());
        return (int) java.time.Duration.between(start, end).toMinutes();
    }

    public static class ZoomMeetingUrls {
        public final String startUrl;
        public final String joinUrl;

        public ZoomMeetingUrls(String startUrl, String joinUrl) {
            this.startUrl = startUrl;
            this.joinUrl = joinUrl;
        }
    }
}


















/*package net.pm.appoitmentservice.appoitmentservice.utility;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import net.pm.appoitmentservice.appoitmentservice.config.ZoomConfig;
import net.pm.appoitmentservice.appoitmentservice.entity.Slot;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@Component
public class ZoomHelper {

    private final ZoomConfig zoomConfig;
    private final RestTemplate restTemplate;
    private final ObjectMapper mapper;

    public ZoomHelper(ZoomConfig zoomConfig) {
        this.zoomConfig = zoomConfig;
        this.restTemplate = new RestTemplate();
        this.mapper = new ObjectMapper();
    }

    private String getAccessToken() {
        String url = "https://zoom.us/oauth/token";

        String credentials = zoomConfig.getClientId() + ":" + zoomConfig.getClientSecret();
        String encodedCredentials = java.util.Base64.getEncoder().encodeToString(credentials.getBytes());

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Basic " + encodedCredentials);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        String body = "grant_type=account_credentials&account_id=" + zoomConfig.getAccountId();

        HttpEntity<String> request = new HttpEntity<>(body, headers);

        ResponseEntity<JsonNode> response = restTemplate.exchange(url, HttpMethod.POST, request, JsonNode.class);

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Failed to get Zoom access token: " + response.getStatusCode());
        }

        return response.getBody().get("access_token").asText();
    }

    public ZoomMeetingUrls createZoomMeeting(Slot slot) {
        String accessToken = getAccessToken();

        String url = "https://api.zoom.us/v2/users/me/meetings";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        ObjectNode body = mapper.createObjectNode();
        body.put("topic", "Appointment with Doctor ID " + slot.getDoctorId());
        body.put("type", 2); // Scheduled meeting
        body.put("start_time", getIso8601DateTime(slot.getDate(), slot.getStartTime()));
        body.put("duration", Math.max(calculateDurationInMinutes(slot), 1)); // Force duration >= 1
        body.put("timezone", "UTC");

        HttpEntity<String> request = new HttpEntity<>(body.toString(), headers);

        ResponseEntity<JsonNode> response = restTemplate.postForEntity(url, request, JsonNode.class);

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Failed to create Zoom meeting: " + response.getStatusCode());
        }

        JsonNode responseBody = response.getBody();
        String startUrl = responseBody.get("start_url").asText();
        String joinUrl = responseBody.get("join_url").asText();

        return new ZoomMeetingUrls(startUrl, joinUrl);
    }

    private String getIso8601DateTime(String date, String time) {
        LocalDate localDate = LocalDate.parse(date);
        LocalTime localTime = LocalTime.parse(time);
        ZonedDateTime zdt = ZonedDateTime.of(localDate, localTime, ZoneOffset.UTC);
        return zdt.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }

    private int calculateDurationInMinutes(Slot slot) {
        LocalTime start = LocalTime.parse(slot.getStartTime());
        LocalTime end = LocalTime.parse(slot.getEndTime());
        int duration = (int) java.time.Duration.between(start, end).toMinutes();
        return duration;
    }

    public static class ZoomMeetingUrls {
        public final String startUrl;
        public final String joinUrl;

        public ZoomMeetingUrls(String startUrl, String joinUrl) {
            this.startUrl = startUrl;
            this.joinUrl = joinUrl;
        }
    }
}
*/