package com.rubeusufv.sync.Features.Domain.Usecases;

import com.rubeusufv.sync.Core.Exceptions.ConnectionException;
import com.rubeusufv.sync.Core.Exceptions.DatabaseException;
import com.rubeusufv.sync.Core.Exceptions.IncorrectPasswordException;
import com.rubeusufv.sync.Core.Exceptions.UserNotFoundException;
import com.rubeusufv.sync.Core.Session.SessionManagerContract;
import com.rubeusufv.sync.Features.Data.AuthData.AuthDataContract;
import com.rubeusufv.sync.Features.Domain.Models.UserModel;
import com.rubeusufv.sync.Features.Domain.Utils.DevTools;
import com.rubeusufv.sync.Tools.Criptography.CriptographyContract;

import java.net.ConnectException;

public class DoLoginUsecase {
    private AuthDataContract authData;
    private CriptographyContract criptography;
    private SessionManagerContract sessionManager;

    public DoLoginUsecase(AuthDataContract authData, CriptographyContract criptography, SessionManagerContract sessionManager) {
        this.authData = authData;
        this.criptography = criptography;
        this.sessionManager = sessionManager;
    }

    public void doLogin(String login, String password) {
        UserModel userModel;
        try {
            userModel = authData.fetchUser(login);
        } catch(Exception error) {
            throw new DatabaseException(
                "Não foi possível buscar usuário!", DevTools.getDetailsFromError(error)
            );
        }
        if (userModel == null) throw new UserNotFoundException(login);
        boolean passwordsMatch = criptography.matchPasswords(userModel.getPassword(), password);
        if (!passwordsMatch) throw new IncorrectPasswordException(login);
        sessionManager.saveSession(userModel);
    }
}
