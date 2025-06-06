package com.rubeusufv.sync.Core.Session;

import com.rubeusufv.sync.Features.Domain.Models.UserModel;

public class SessionManagerMock implements SessionManagerContract {
    @Override
    public void saveSession(UserModel userModel) {
        //
    }

    @Override
    public UserModel getSessionUser() {
        return UserModel.getMock();
    }
}
