package com.toolkit.inventory.Security.Filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.toolkit.inventory.Security.Utility.HttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

import static com.toolkit.inventory.Security.Utility.SecurityParams.FORBIDDEN_MEASSAGE;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Component
public class JwtAuthenticationEntryPoint extends Http403ForbiddenEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        HttpResponse httpResponse = new HttpResponse().builder()
                                        .httpStatusCode(FORBIDDEN.value())
                                        .httpStatus(FORBIDDEN)
                                        .reason(FORBIDDEN.getReasonPhrase().toUpperCase())
                                        .message(FORBIDDEN_MEASSAGE)
                                        .build();
        response.setContentType(APPLICATION_JSON_VALUE);
        response.setStatus(FORBIDDEN.value());
        OutputStream outputStream = response.getOutputStream();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(outputStream, httpResponse);
        outputStream.flush();
    }
}
