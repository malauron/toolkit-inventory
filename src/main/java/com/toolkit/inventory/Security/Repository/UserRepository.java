package com.toolkit.inventory.Security.Repository;

import com.toolkit.inventory.Security.Domain.User;
import com.toolkit.inventory.Security.Projection.UserView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@RepositoryRestResource
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    @Query(value = "SELECT u.userId AS userId, u.username AS username, u.roles AS roles " +
            "FROM User u " +
            "WHERE u.username LIKE %:searchDesc% " +
            "ORDER BY u.username ")
    Page<UserView> findUsers(
            @RequestParam("searchDesc") String searchDesc,
            Pageable pageable);




//    @Query(value = "select u.* from users u " +
//            "inner join user_roles r on r.user_id = u.user_id limit 1 " +
//            "WHERE u.user_id = :userId order by r.role_name", nativeQuery = true)
//    Page<UserView> findUsers(
//            @RequestParam("searchDesc") String searchDesc,
//            Pageable pageable);
}
