package com.toolkit.inventory.Security.Repository;

import com.toolkit.inventory.Security.Domain.User;
import com.toolkit.inventory.Security.Projection.UserView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;
import java.util.Set;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);

    @Query(value = "SELECT u.userId AS userId, u.username AS username " +
            "FROM User u " +
            "WHERE u.username LIKE %:searchDesc% " +
            "ORDER BY u.username ")
    Page<UserView> findUsers(
            @RequestParam("searchDesc") String searchDesc,
            Pageable pageable);
}
