package com.rubeusufv.sync.Features.Domain.Usecases.Authentication;

import android.util.Log;

import com.rubeusufv.sync.Core.Exceptions.DatabaseException;
import com.rubeusufv.sync.Core.Exceptions.IncorrectPasswordException;
import com.rubeusufv.sync.Core.Exceptions.UserNotFoundException;
import com.rubeusufv.sync.Core.Session.SessionManagerContract;
import com.rubeusufv.sync.Features.Data.AuthData.AuthDataContract;
import com.rubeusufv.sync.Features.Domain.Models.UserModel;
import com.rubeusufv.sync.Features.Domain.Utils.DevTools;
import com.rubeusufv.sync.Tools.Criptography.CriptographyContract;

public class DoLoginUsecase {
    private AuthDataContract authData;
    private CriptographyContract criptography;
    private SessionManagerContract sessionManager;

    public DoLoginUsecase(AuthDataContract authData, CriptographyContract criptography, SessionManagerContract sessionManager) {
        this.authData = authData;
        this.criptography = criptography;
        this.sessionManager = sessionManager;
    }

    public void doLogin(String email, String password, boolean keepMeConnected) {
        UserModel userModel;
        try {
            userModel = authData.fetchUserByEmail(email);
        } catch(Exception error) {
            throw new DatabaseException(
                "Não foi possível buscar usuário!", DevTools.getDetailsFromError(error)
            );
        }
        if (userModel == null) throw new UserNotFoundException(email);
        Log.d("LOGIN", "PASSWORDS: " + userModel.getPassword() + " " + password);
        boolean passwordsMatch = criptography.matchPasswords(userModel.getPassword(), password);
        if (!passwordsMatch) throw new IncorrectPasswordException(email);
        sessionManager.setSessionUser(userModel);
        if (keepMeConnected) {
            sessionManager.saveSession(userModel);
        }
    }
}
