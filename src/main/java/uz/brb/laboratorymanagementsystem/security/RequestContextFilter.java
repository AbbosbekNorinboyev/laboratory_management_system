package uz.brb.laboratorymanagementsystem.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@Component("limsRequestContextFilter")
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RequestContextFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        String requestId = headerValue(request, "X-Request-Id");
        if (requestId == null) requestId = UUID.randomUUID().toString();

        String sourceSystem = headerValue(request, "X-Source-System");
        if (sourceSystem == null) sourceSystem = "lims-web";

        String ipAddress = request.getRemoteAddr();
        response.setHeader("X-Request-Id", requestId);

        // Faqat meta-ma'lumotlarni o'rnatamiz, sessiya mantiqi olib tashlandi
        RequestContextHolder.set(new RequestContext(requestId, sourceSystem, ipAddress));

        try {
            filterChain.doFilter(request, response);
        } finally {
            RequestContextHolder.clear();
        }
    }

    private String headerValue(HttpServletRequest request, String name) {
        String value = request.getHeader(name);
        return value != null && !value.isBlank() ? value : null;
    }
}
