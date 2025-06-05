package com.rubeusufv.sync.Features.Data.AuthData;

import com.rubeusufv.sync.Features.Domain.Models.UserModel;

public class AuthDataMock implements AuthDataContract {
    @Override
    public UserModel fetchUser(String login) {
        return UserModel.getMock();
    }

}
