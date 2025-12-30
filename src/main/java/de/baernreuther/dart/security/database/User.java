package de.baernreuther.dart.security.database;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@RequiredArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue
    private int id;
    @Column(unique = true)
    @NonNull
    private String username;
    @Column(nullable = false)
    @NonNull
    private String password;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public @org.jspecify.annotations.NonNull String getPassword() {
        return this.password;
    }

    @Override
    public @org.jspecify.annotations.NonNull String getUsername() {
        return this.username;
    }
}
