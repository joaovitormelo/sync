package com.rubeusufv.sync.Features.Data.AuthData;

import com.rubeusufv.sync.Features.Domain.Models.UserModel;

public interface AuthDataContract {
    UserModel fetchUser(String login);
}