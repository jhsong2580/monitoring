package monitoring.argumentResolver;

import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import monitoring.configuration.jwt.TokenExtractor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;


public class JwtTokenResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(JwtToken.class) &&
            parameter.getParameterType().equals(String.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
        NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String token = TokenExtractor.extract(
            webRequest.getNativeRequest(HttpServletRequest.class));

        if(ObjectUtils.isEmpty(token)){
            throw new IllegalArgumentException("token required");
        }

        return token;
    }
}
