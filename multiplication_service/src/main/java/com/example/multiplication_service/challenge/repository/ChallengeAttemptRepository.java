package com.example.multiplication_service.challenge.repository;

import com.example.multiplication_service.challenge.domain.ChallengeAttempt;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ChallengeAttemptRepository extends CrudRepository<ChallengeAttempt, Long> {
    List<ChallengeAttempt> findTop10ByUserAliasOrderByIdDesc(String userAlias);
//    same as;
//    @Query("SELECT a FROM ChallengeAttempt a WHERE a.user.alias = ?1 ORDER BY  a.id DESC ")
//    List<ChallengeAttempt> lastAttempts(String userAlias);
}
