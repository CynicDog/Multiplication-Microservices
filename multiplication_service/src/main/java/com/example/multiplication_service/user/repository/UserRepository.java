package com.example.multiplication_service.user.repository;

import com.example.multiplication_service.user.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByAlias(final String alias);

    List<User> findAllByIdIn(final List<Long> ids);
}
