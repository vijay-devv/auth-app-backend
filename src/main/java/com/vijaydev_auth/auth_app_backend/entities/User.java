package com.vijaydev_auth.auth_app_backend.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_id")
    private UUID id;
    @Column(nullable = false ,length = 500)
    private String username;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false,   length = 500)
    private String password;
    private String ImageUrl;
    private boolean enable=true;
    private Instant createdAt;
    private Instant updatedAt;

    @Enumerated(EnumType.STRING)
    private Provider provider=Provider.LOCAL;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles=new HashSet<>();

@PrePersist
    protected void onCreate(){
        Instant now = Instant.now();
        if(createdAt == null){
            createdAt = now;
        }
        this.updatedAt = now;
    }

    @PostPersist
    protected void onUpdate(){
        this.updatedAt = Instant.now();
    }

}
