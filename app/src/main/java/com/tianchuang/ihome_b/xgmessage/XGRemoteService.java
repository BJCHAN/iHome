package com.tianchuang.ihome_b.xgmessage;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
/**
 * 信鸽推送服务
 * */
public class XGRemoteService extends Service {
    public XGRemoteService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
      return null;
    }
}
