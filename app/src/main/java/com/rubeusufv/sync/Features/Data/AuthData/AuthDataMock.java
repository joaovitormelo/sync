package com.rubeusufv.sync.Features.Data.AuthData;

import com.rubeusufv.sync.Features.Domain.Models.UserModel;

public class AuthDataMock implements AuthDataContract {
    @Override
    public UserModel fetchUserByEmail(String email) {
        return UserModel.getMock();
    }

}
