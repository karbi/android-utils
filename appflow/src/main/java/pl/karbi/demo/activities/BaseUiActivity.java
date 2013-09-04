package pl.karbi.demo.activities;


import pl.karbi.demo.R;
import pl.karbi.utils.Mutable;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager.OnActivityResultListener;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class BaseUiActivity<I extends Parcelable, O extends Parcelable> extends Activity {

    protected static Parcelable start(final MainUiActivity activity, final Parcelable input,
                                      final Class<? extends Activity> target) {
        final Mutable<Parcelable> output = new Mutable<Parcelable>();

        activity.setActivityResultListener(new OnActivityResultListener() {

            @Override
            public boolean onActivityResult(final int requestCode, final int resultCode, final Intent data) {
                synchronized (output) {
                    output.setValue(data.getParcelableExtra("output"));
                    output.notify();
                }

                return true;
            }
        });

        activity.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                final Intent intent = new Intent(activity, target);

                intent.putExtra("input", input);

                activity.startActivityForResult(intent, 0);
            }
        });

        synchronized (output) {
            try {
                output.wait();
            } catch (final InterruptedException e) {
                return null;
            }
        }

        return output.getValue();
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.ui_base);
    }

    protected final void setContent(final View content) {
        final LinearLayout contentLayout = (LinearLayout) findViewById(R.id.ui_base_content);
        contentLayout.addView(content);
    }

    protected final Button addButton() {
        final LinearLayout buttonsLayout = (LinearLayout) findViewById(R.id.ui_buttons_layout);

        final Button button = new Button(getApplicationContext());

        buttonsLayout.addView(button);

        return button;
    }

    @SuppressWarnings("unchecked")
    protected final I getInput() {
        final Intent intent = getIntent();

        return (I) intent.getParcelableExtra("input");
    }

    protected final void putOutput(final O output) {
        final Intent intent = new Intent();
        intent.putExtra("output", output);
        setResult(Activity.RESULT_OK, intent);
    }

}
