package com.example.store.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fasterxml.jackson.databind.json.JsonMapper;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.access.AccessDeniedException;

class JsonAccessDeniedHandlerTest {

    @Test
    void handleShouldWriteForbiddenJsonResponse() throws Exception {
        JsonAccessDeniedHandler handler = new JsonAccessDeniedHandler(
                new JsonSecurityErrorResponseWriter(JsonMapper.builder().findAndAddModules().build())
        );
        MockHttpServletRequest request = new MockHttpServletRequest("POST", "/api/products");
        MockHttpServletResponse response = new MockHttpServletResponse();

        handler.handle(request, response, new AccessDeniedException("forbidden"));

        assertEquals(403, response.getStatus());
        assertTrue(response.getContentType().startsWith("application/json"));
        assertTrue(response.getContentAsString().contains("\"message\":\"Access denied\""));
        assertTrue(response.getContentAsString().contains("\"error\":\"Forbidden\""));
    }
}
