package com.mynt.mynt.repository;
import com.mynt.mynt.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FindPassword extends JpaRepository<AppUser, Long> {
    @Query(value = "SELECT * FROM User", nativeQuery = true)
    List<AppUser> findByNameNative(@Param("name") String name);
}
