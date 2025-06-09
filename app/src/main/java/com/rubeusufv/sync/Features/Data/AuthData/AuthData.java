package com.rubeusufv.sync.Features.Data.AuthData;

import com.rubeusufv.sync.Features.Domain.Models.UserModel;
import com.rubeusufv.sync.Tools.Database.DatabaseEntry;
import com.rubeusufv.sync.Tools.Database.SQLiteDatabaseAdapter;

import java.util.ArrayList;

public class AuthData implements AuthDataContract {

    public UserModel fetchUserByEmail(String email){
        String where = "email = ?";
        String[] whereArgs = new String[]{ email };

        ArrayList<DatabaseEntry> result = SQLiteDatabaseAdapter.getInstance().select(
                "User",
                new String[]{("*")},
                where,
                whereArgs,
                "",
                ""
        );

        // Converte cada resultado em EventModel
        ArrayList<UserModel> users = new ArrayList<>();
        for (DatabaseEntry entry : result) {
            users.add(UserModel.fromDatabaseEntry(entry));
        }

        // O campo email é UNIQUE no banco, então sempre vai retornar so um registro
        return users.get(0);
    }

}
