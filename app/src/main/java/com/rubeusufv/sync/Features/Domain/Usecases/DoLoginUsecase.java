package com.rubeusufv.sync.Features.Domain.Usecases;

import com.rubeusufv.sync.Core.Exceptions.IncorrectPasswordException;
import com.rubeusufv.sync.Core.Exceptions.UserNotFoundException;
import com.rubeusufv.sync.Core.Session.SessionManagerContract;
import com.rubeusufv.sync.Features.Data.RubeusDataContract;
import com.rubeusufv.sync.Features.Domain.Models.UserModel;
import com.rubeusufv.sync.Tools.Criptography.CriptographyContract;

public class DoLoginUsecase {
    private RubeusDataContract rubeusData;
    private CriptographyContract criptography;
    private SessionManagerContract sessionManager;

    public DoLoginUsecase(RubeusDataContract rubeusData, CriptographyContract criptography, SessionManagerContract sessionManager) {
        this.rubeusData = rubeusData;
        this.criptography = criptography;
        this.sessionManager = sessionManager;
    }

    public void doLogin(String login, String password) {
        UserModel userModel = rubeusData.fetchUser(login);
        if (userModel == null) throw new UserNotFoundException(login);
        boolean passwordsMatch = criptography.matchPasswords(userModel.getPassword(), password);
        if (!passwordsMatch) throw new IncorrectPasswordException(login);
        sessionManager.saveSession(userModel);
    }
}
