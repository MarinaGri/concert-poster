package com.technokratos.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.experimental.UtilityClass;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@UtilityClass
public class HttpResponseUtil {

    public static void putExceptionInResponse(HttpServletResponse response,
            Exception exception, int exceptionStatus) throws IOException {

        response.setContentType(APPLICATION_JSON_VALUE);
        response.setStatus(exceptionStatus);

        Map<String, Object> body = new HashMap<>();
        body.put("message", exception.getMessage());
        body.put("exception_name", exception.getClass().getSimpleName());

        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), body);
    }
}
