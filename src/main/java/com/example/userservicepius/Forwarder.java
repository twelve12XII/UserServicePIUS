package com.example.userservicepius;




import com.example.userservicepius.security.AuthComponent;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
@RequestMapping("api")
public class Forwarder {
    private final RestTemplate restTemplate = new RestTemplate();
    private final AuthComponent authComponent;

    public Forwarder(AuthComponent authComponent) {
        this.authComponent = authComponent;
    }

    @Value("${services.doctor.url}")
    private String doctorsServiceURL;

    @Value("${services.history.url}")
    private String historyServiceURL;


    @RequestMapping(value = "{*path}")
    public ResponseEntity<String> forwardAllPaths(HttpServletRequest request, @RequestBody(required = false) Map body) {
        Long userId = authComponent.authCheck(request.getHeader("Authorization"));
        HttpHeaders httpHeader = new HttpHeaders();
        httpHeader.add("user-id", String.valueOf(userId));
        HttpEntity<?> httpEntity;

        if (request.getMethod().equals("GET")) {
            httpEntity = new HttpEntity<>(httpHeader);
        } else {
            httpEntity = new HttpEntity<>(body, httpHeader);
        }

        try{
            if (request.getRequestURI().startsWith("/api/doctors")) {
                return restTemplate.exchange(
                        doctorsServiceURL + request.getRequestURI(),
                        HttpMethod.valueOf(request.getMethod()),
                        httpEntity,
                        String.class
                );
            }
            if(request.getRequestURI().startsWith("/api/history")){
                return restTemplate.exchange(
                        historyServiceURL + request.getRequestURI(),
                        HttpMethod.valueOf(request.getMethod()),
                        httpEntity,
                        String.class
                );
            }
        }
        catch (HttpStatusCodeException e){
            throw new ResponseStatusException(e.getStatusCode(), e.getMessage(), e);
        }

        return null;
    }

}
