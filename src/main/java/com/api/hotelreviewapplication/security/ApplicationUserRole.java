package com.api.hotelreviewapplication.security;

import com.google.common.collect.Sets;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static com.api.hotelreviewapplication.security.ApplicationUserPermission.*;

public enum ApplicationUserRole {
    ADMIN(Sets.newHashSet(HOTEL_READ, HOTEL_CREATE, HOTEL_UPDATE, HOTEL_DELETE, REVIEW_READ, REVIEW_CREATE, REVIEW_UPDATE, REVIEW_DELETE)),
    MODERATOR(Sets.newHashSet(HOTEL_READ, HOTEL_CREATE, HOTEL_UPDATE, REVIEW_READ, REVIEW_UPDATE, REVIEW_DELETE)),
    USER(Sets.newHashSet(HOTEL_READ, REVIEW_READ, REVIEW_CREATE, REVIEW_UPDATE_OWN, REVIEW_DELETE_OWN));

    private final Set<ApplicationUserPermission> permissions;

    ApplicationUserRole(Set<ApplicationUserPermission> permissions) {
        this.permissions = permissions;
    }
    public Set<ApplicationUserPermission> getPermissions() {
        return permissions;
    }
    public Set<SimpleGrantedAuthority> grantedAuthorities() {
        Set<SimpleGrantedAuthority> permissions = getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
        permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return permissions;
    }
}
