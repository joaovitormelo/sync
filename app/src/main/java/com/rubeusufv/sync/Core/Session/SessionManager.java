package com.rubeusufv.sync.Core.Session;

import com.rubeusufv.sync.Features.Domain.Models.UserModel;

public class SessionManager implements SessionManagerContract {
    private UserModel sessionUser;

    @Override
    public void saveSession(UserModel user) {
        this.sessionUser = user;
    }

    @Override
    public UserModel getSessionUser() {
        return this.sessionUser;
    }
}
