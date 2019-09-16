package br.com.lingua.liguadeouro.utils;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

/**
 * Funções úteis para manipulação de coleções
 */
public final class SecurityUtils {

    /**
     * Obtem o username do usuário atual.
     *
     * @return username
     */
    public static Optional<String> getCurrentUserName() {

        final SecurityContext securityContext = SecurityContextHolder.getContext();

        return Optional.ofNullable(securityContext.getAuthentication())
                .map(authentication -> {
                    Optional<UserDetails> oUserDetails = getCurrentUser();
                    if (oUserDetails.isPresent()) {
                        return oUserDetails.get().getUsername();
                    }
                    if (authentication.getPrincipal() instanceof String) {
                        return (String) authentication.getPrincipal();
                    }
                    return "system";
                });
    }

    /**
     * Obtem o usuário atual.
     *
     * @return username
     */
    public static Optional<UserDetails> getCurrentUser() {

        final SecurityContext securityContext = SecurityContextHolder.getContext();

        return Optional.ofNullable(securityContext.getAuthentication())
                .map(authentication -> {
                    if (authentication.getPrincipal() instanceof UserDetails) {
                        return (UserDetails) authentication.getPrincipal();
                    }
                    return null;
                });
    }

    /**
     * Se o usuário atual tiver uma autoridade específica (função de segurança).
     * <p>
     * O nome desse método vem do método isUserInRole () na API Servlet
     *
     * @param role a ser testada
     * @return true se o usuário atual tiver está role, false caso contrário
     */
    public static boolean isCurrentUserInRole(final String role) {

        final SecurityContext securityContext = SecurityContextHolder.getContext();

        return Optional.ofNullable(securityContext.getAuthentication())
                .map(authentication -> authentication.getAuthorities().stream()
                        .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(role)))
                .orElse(false);
    }

    /**
     * Obtem as roles do usuário atual.
     *
     * @return true if the user is authenticated, false otherwise
     */
    public static Optional<List<String>> getCurrentUserRoles() {

        SecurityContext securityContext = SecurityContextHolder.getContext();

        return Optional.ofNullable(securityContext.getAuthentication())
                .map(authentication -> authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(toList()));

    }


}