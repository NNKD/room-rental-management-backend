package com.roomrentalmanagementbackend.repository;

import com.roomrentalmanagementbackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("""
            SELECT u.email
            FROM User u
            WHERE u.role = :role
            """)
    List<String> findEmailByRole(@Param("role") int role);
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    List<User> findByRole(int role);
    @Query("SELECT DISTINCT u FROM User u JOIN u.rentalContracts rc")
    List<User> findUsersWithRentalContracts();
}
