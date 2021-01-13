package com.factor.gymnasium.Globals;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


public class DecisionReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.hasExtra("flag")) {
//            makeDecision(intent.getStringExtra("flag"), false);
        }
    }
}
