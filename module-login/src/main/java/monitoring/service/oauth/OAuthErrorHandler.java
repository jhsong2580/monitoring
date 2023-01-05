package monitoring.service.oauth;

import java.io.IOException;
import java.net.URI;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;

public class OAuthErrorHandler extends DefaultResponseErrorHandler {

    @Override
    public void handleError(URI url, HttpMethod method, ClientHttpResponse response)
        throws IOException {
        if (response.getStatusCode().is5xxServerError()) {
            throw new RuntimeException("OAuth API 서버 문제 발생");
        }
        if (response.getStatusCode().is4xxClientError()) {
            throw new IllegalArgumentException("OAuth API 잘못된 요청");
        }
    }
}
