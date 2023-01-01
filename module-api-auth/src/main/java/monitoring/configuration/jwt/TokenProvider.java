package monitoring.configuration.jwt;

public interface TokenProvider {
    String createToken(String payload); // create token by payload
    String getPayload(String token);    // get payload by token
    boolean validateToken(String token); //valiate token

}
