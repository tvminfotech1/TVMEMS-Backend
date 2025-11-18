package com.tvm.internal.tvm_internal_project.repo;

import com.tvm.internal.tvm_internal_project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface   UserRepo extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByAadhar(String aadhar);
    Optional<User> findByMobile(Long mob);

    Optional<User> findByEmailOrMobile(String email, Long mobile);

    @Query("SELECT u.id FROM User u WHERE u.email = :email")
    Long findIdByEmail(@Param("email") String email);

    @Query("SELECT u.fullName FROM User u WHERE u.email = :email")
    String findNameByEmail(@Param("email") String email);

    Optional<User> findByEmployeeId(Long employeeId);

    @Query("SELECT e FROM User e WHERE FUNCTION('MONTH', e.dob) = :month AND FUNCTION('DAY', e.dob) = :day")
    Optional<List<User>> findBirthdays(@Param("month") int month, @Param("day") int day);

      @Query("SELECT e FROM User e WHERE FUNCTION('MONTH', e.joiningDate) = :month AND FUNCTION('DAY', e.joiningDate) = :day")
    Optional<List<User>> findAnniversaries(@Param("month") int month, @Param("day") int day);

    @Query("SELECT e FROM User e WHERE FUNCTION('YEAR', e.joiningDate) = :year AND FUNCTION('MONTH', e.joiningDate) = :month AND FUNCTION('DAY', e.joiningDate) = :day")
    Optional<List<User>> findTodayOnboardings(@Param("year") int year, @Param("month") int month, @Param("day") int day);

}