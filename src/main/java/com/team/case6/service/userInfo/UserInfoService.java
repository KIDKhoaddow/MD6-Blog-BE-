package com.team.case6.service.userInfo;


import com.team.case6.model.entity.user.UserInfo;
import com.team.case6.repository.IUserInfoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserInfoService implements IUserInfoService {
    @Autowired
    private IUserInfoRepo userInfoRepository;

    @Override
    public List<UserInfo> findAll() {
        return userInfoRepository.findAll();
    }

    @Override
    public UserInfo save(UserInfo userInfo) {
        return userInfoRepository.save(userInfo);
    }

    @Override
    public void removeById(Long id) {
        userInfoRepository.deleteById(id);
    }

    @Override
    public Optional<UserInfo> findById(Long id) {
        return userInfoRepository.findById(id);
    }

    @Override
    public UserInfo findByUserId(Long id) {
        return userInfoRepository.findByUserId(id);
    }

    @Override
    public Long findUserByUserInfo(Long id) {
        return userInfoRepository.findUserByUserInfo(id);
    }
}
