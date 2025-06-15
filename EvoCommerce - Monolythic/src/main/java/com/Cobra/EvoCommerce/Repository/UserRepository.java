package com.Cobra.EvoCommerce.Repository;

import com.Cobra.EvoCommerce.Model.User.Users;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {

    Optional<Users> findByUserName(String userName);

    boolean existsByEmail(String email);

    boolean existsByUserName(String userName);
}
