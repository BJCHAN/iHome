package com.tianchuang.ihome_b.xgmessage;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class XGRemoteService extends Service {
    public XGRemoteService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
      return null;
    }
}
