package com.rubeusufv.sync.Core.Session;

import com.rubeusufv.sync.Features.Domain.Models.UserModel;

public interface SessionManagerContract {
    void setSessionUser(UserModel userModel);
    UserModel getSessionUser();
    void saveSession(UserModel userModel);
    UserModel getSavedSessionUser();
}
