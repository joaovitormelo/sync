package com.rubeusufv.sync.Features.Domain.Utils;

import com.rubeusufv.sync.Core.Exceptions.ConnectionException;
import com.rubeusufv.sync.Core.Exceptions.DatabaseException;

public class DevTools {
    public static String getDetailsFromError(Exception error) {
        if (error instanceof ConnectionException) {
            return ((ConnectionException) error).getDetails();
        }
        return "";
    }
}
