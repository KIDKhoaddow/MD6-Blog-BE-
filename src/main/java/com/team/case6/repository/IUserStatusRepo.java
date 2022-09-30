package com.team.case6.repository;


import com.team.case6.model.entity.user.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserStatusRepo extends JpaRepository<UserStatus,Long> {
}
