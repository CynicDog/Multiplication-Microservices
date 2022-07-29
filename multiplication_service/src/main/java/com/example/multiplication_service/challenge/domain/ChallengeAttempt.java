package com.example.multiplication_service.challenge.domain;

import com.example.multiplication_service.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChallengeAttempt {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "USER_ID")
    private User user;

    private int factorA;
    private int factorB;

    private int resultAttempt;
    private boolean correct;
}
