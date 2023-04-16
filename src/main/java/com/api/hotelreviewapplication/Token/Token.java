package com.api.hotelreviewapplication.Token;

import com.api.hotelreviewapplication.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "token")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;
    @Column(unique = true)
    public String token;
    public boolean revoked;
    public boolean expired;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    public User user;
}
