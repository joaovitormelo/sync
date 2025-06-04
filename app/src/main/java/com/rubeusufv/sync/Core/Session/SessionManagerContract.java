package com.rubeusufv.sync.Core.Session;

import com.rubeusufv.sync.Features.Domain.Models.User;

public interface SessionManagerContract {
    void saveSession(User user);
}
