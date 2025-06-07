package com.rubeusufv.sync.Core.Session;

import com.rubeusufv.sync.Features.Domain.Models.UserModel;

public class SessionManagerMock implements SessionManagerContract {
    @Override
    public void setSessionUser(UserModel userModel) {
        //
    }

    @Override
    public UserModel getSessionUser() {
        UserModel user = UserModel.getMock();
        user.setIdRubeus(1);
        user.setTokenRubeus("token");
        return UserModel.getMock();
    }

    @Override
    public void saveSession(UserModel userModel) {

    }

    @Override
    public UserModel getSavedSessionUser() {
        return UserModel.getMock();
    }
}
