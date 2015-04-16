
package com.vein.translater.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class MyMockService extends Service {

    public MyMockService() {
        super();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new Binder();
    }
}
