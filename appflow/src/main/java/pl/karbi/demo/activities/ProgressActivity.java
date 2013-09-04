package pl.karbi.demo.activities;

import pl.karbi.demo.R;
import android.app.Activity;
import android.widget.TextView;

import com.googlecode.androidannotations.annotations.AfterInject;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_progress)
public class ProgressActivity extends Activity {

    @ViewById
    protected TextView message;

    @AfterInject
    protected final void updateMessage() {
        message.setText("Waiting ...");
    }

}
