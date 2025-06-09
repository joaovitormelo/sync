package com.rubeusufv.sync.Features.Data.AuthData;

import com.rubeusufv.sync.Features.Domain.Models.UserModel;

public class AuthDataMock implements AuthDataContract {
    @Override
    public UserModel fetchUserByEmail(String email) {
        try {
            Thread.sleep(500);
        } catch(InterruptedException error) {
            //
        }
        return UserModel.getMock();
    }

}
