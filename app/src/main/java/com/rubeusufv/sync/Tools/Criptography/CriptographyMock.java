package com.rubeusufv.sync.Tools.Criptography;

public class CriptographyMock implements CriptographyContract {

    @Override
    public boolean matchPasswords(String passwordA, String passwordB) {
        return passwordA == passwordB;
    }
}
