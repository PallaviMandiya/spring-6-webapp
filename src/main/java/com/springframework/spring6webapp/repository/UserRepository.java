package com.springframework.spring6webapp.repository;

import com.springframework.spring6webapp.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
//    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);

    @Query(value = "SELECT * FROM user_tbl JOIN user_book ON user_tbl.uid = user_book.user_id JOIN book ON user_book.book_id = book.id WHERE book.title like :title", nativeQuery = true)
    Set<User> findByTitle(String title);

//    boolean existsByUsername(String username);

//    void deleteByUsername(String username);
}
