package com.roomrentalmanagementbackend.repository;

import com.roomrentalmanagementbackend.dto.user.response.UserAccountResponse;
import com.roomrentalmanagementbackend.dto.user.response.UserInfoDTO;
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

    @Query("""
            SELECT new com.roomrentalmanagementbackend.dto.user.response.UserInfoDTO(u.username, u.email, u.role)
            FROM User u
            WHERE u.username = :username
            """)
    Optional<UserInfoDTO> findUserInfo(@Param("username") String username);

    @Query("""
            SELECT new com.roomrentalmanagementbackend.dto.user.response.UserAccountResponse(u.email, u.username, u.fullname, u.phone)
            FROM User u
            WHERE u.username = :username
            """)
    Optional<UserAccountResponse> findUserAccount(@Param("username") String username);

    @Query("SELECT u FROM User u WHERE LOWER(u.username) = LOWER(:username)")
    Optional<User> findUserByUsernameIgnoreCase(@Param("username") String username);


}
