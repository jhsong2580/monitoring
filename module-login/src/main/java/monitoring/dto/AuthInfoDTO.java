package monitoring.dto;

public abstract class AuthInfoDTO {

    abstract void checkIdIsNull();

    abstract void checkAccessTokenIsNull();


    public abstract String getAuthUserId();

    public abstract String getAccessToken();

    public abstract String getEmail();
}
