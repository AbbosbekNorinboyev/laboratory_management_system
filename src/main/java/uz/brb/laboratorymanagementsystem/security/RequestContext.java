package uz.brb.laboratorymanagementsystem.security;

public record RequestContext(
        String requestId,
        String sourceSystem,
        String ipAddress
) {
}
