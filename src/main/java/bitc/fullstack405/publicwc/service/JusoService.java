package bitc.fullstack405.publicwc.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class JusoService {
    @Value("${kakao.rest.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;

    public JusoService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getAddress(String query) {
        String url = "https://dapi.kakao.com/v2/local/search/keyword.json?query=" + query;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK " + apiKey);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        return response.getBody();
    }
}
