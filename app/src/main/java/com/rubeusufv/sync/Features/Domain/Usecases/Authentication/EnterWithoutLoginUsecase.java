package com.rubeusufv.sync.Features.Domain.Usecases.Authentication;

import com.rubeusufv.sync.Core.Session.SessionManagerContract;
import com.rubeusufv.sync.Features.Domain.Models.UserModel;

public class EnterWithoutLoginUsecase {
    private SessionManagerContract sessionManager;

    public EnterWithoutLoginUsecase(SessionManagerContract sessionManager) {
        this.sessionManager = sessionManager;
    }

    //Retorna verdadeiro se pode entrar, e falso caso contrário
    public boolean enterWithoutLogin() {
        UserModel user = sessionManager.getSavedSessionUser();
        if (user == null) return false;
        sessionManager.setSessionUser(user);
        return true;
    }
}
