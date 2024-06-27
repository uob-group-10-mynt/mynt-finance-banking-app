package com.mynt.banking.repository;
import com.mynt.banking.user.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuiryAppUser extends JpaRepository<AppUser, Long> {

    @Query(value = "SELECT * FROM \"AppUser\";", nativeQuery = true)
    List<AppUser> findAllData();

//    SELECT email, password FROM public."AppUser" where email = 'james@jamesLove.com' ;

    @Query(value = "SELECT email FROM \"AppUser\" where email =  :email ;", nativeQuery = true)
    List<String> findUsername(@Param("email") String userEmail);

    @Query(value = "SELECT password FROM \"AppUser\" where email = :email ;", nativeQuery = true)
    List<String> findPassword(@Param("email") String userEmail);
}
