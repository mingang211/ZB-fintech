package com.zerobase.zbfintech.entity;

import com.zerobase.zbfintech.dto.SignupForm;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Locale;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    @Column(unique = true)
    private String email;
    private String password;
    private Long transaction_password;

    private LocalDateTime verifyExpiredAt;
    private String verificationCode;
    private boolean isEmailVerified;

    public static User from(SignupForm form){
        return User.builder()
                .username(form.getUsername())
                .email(form.getEmail().toLowerCase(Locale.ROOT))
                .password(form.getPassword())
                .isEmailVerified(false)
                .build();
    }
}
