package com.mynt.mynt.repository;
import com.mynt.mynt.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuiryAppUser extends JpaRepository<AppUser, Long> {

    @Query(value = "SELECT * FROM \"AppUser\";", nativeQuery = true)
    List<AppUser> findAllData();

}
