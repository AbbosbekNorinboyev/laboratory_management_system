package uz.brb.laboratorymanagementsystem.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import uz.brb.laboratorymanagementsystem.entity.AuthUser;

import java.util.List;
import java.util.stream.Collectors;

public final class RequestContextHolder {
    private static final ThreadLocal<RequestContext> STORAGE = new ThreadLocal<>();

    private RequestContextHolder() {
    }

    public static void set(RequestContext context) {
        STORAGE.set(context);
    }

    public static RequestContext get() {
        return STORAGE.get();
    }

    public static List<String> getPermissions() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            return List.of();
        }
        return auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .filter(a -> !a.startsWith("ROLE_"))
                .collect(Collectors.toList());
    }

    public static List<String> getRoles() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            return List.of();
        }
        return auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .filter(a -> a.startsWith("ROLE_"))
                .map(a -> a.substring(5))
                .collect(Collectors.toList());
    }

    public static boolean hasPermission(String permission) {
        if (isAdmin()) {
            return true;
        }
        return getPermissions().contains(permission);
    }

    public static boolean hasRole(String role) {
        return getRoles().contains(role);
    }

    public static boolean isAdmin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            return false;
        }

        return auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(a -> a.equalsIgnoreCase("admin.system") ||
                        a.equalsIgnoreCase("ROLE_ADMIN") ||
                        a.equalsIgnoreCase("ROLE_Administrator") ||
                        a.equalsIgnoreCase("ROLE_ADMINISTRATOR") ||
                        a.equalsIgnoreCase("Administrator"));
    }

    public static AuthUser getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && auth.getPrincipal() instanceof AuthUser user) {
            return user;
        }
        return null;
    }

    public static Long getCurrentUserId() {
        AuthUser currentUser = getCurrentUser();
        return currentUser != null ? currentUser.getId() : null;
    }

    public static void clear() {
        STORAGE.remove();
    }
}
