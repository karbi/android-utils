package pl.karbi.demo.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager.OnActivityResultListener;

public class MainUiActivity extends Activity {

    public final void setActivityResultListener(final OnActivityResultListener activityResultListener) {
        this.activityResultListener = activityResultListener;
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityResultListener = null;

    }

    @Override
    protected final void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (activityResultListener != null) {
            activityResultListener.onActivityResult(requestCode, resultCode, data);
            activityResultListener = null;
        }
    }

    private OnActivityResultListener activityResultListener;

}
