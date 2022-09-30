package com.team.case6.service.userInfo;


import com.team.case6.model.entity.user.UserInfo;
import com.team.case6.service.IGeneralService;

public interface IUserInfoService extends IGeneralService<UserInfo> {
    UserInfo findByUserId(Long id);
    Long findUserByUserInfo(Long id);
}
