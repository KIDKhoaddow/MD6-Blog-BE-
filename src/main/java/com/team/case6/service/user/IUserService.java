package com.team.case6.service.user;

import com.team.case6.model.entity.user.User;
import com.team.case6.service.IGeneralService;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IUserService extends IGeneralService<User>, UserDetailsService {
    User findByUserName(String username);
    boolean isUsernameExist(String username);
}
