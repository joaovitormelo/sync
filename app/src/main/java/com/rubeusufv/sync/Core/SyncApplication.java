package com.rubeusufv.sync.Core;

import android.app.Application;
import android.content.Context;

/*
Classe responsável por inicializar instâncias básicas do sistema assim que a aplicação é criada,
como o context e injector
 */
public class SyncApplication extends Application {
    private static Context context;
    private static Injector injector;

    public void onCreate() {
        super.onCreate();
        SyncApplication.context = getApplicationContext();
        SyncApplication.injector = Injector.getInstance();
    }
    public static Context getAppContext() {
        return SyncApplication.context;
    }
}
