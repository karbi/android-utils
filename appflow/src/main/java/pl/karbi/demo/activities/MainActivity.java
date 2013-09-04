package pl.karbi.demo.activities;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.karbi.demo.ApplicationThread;
import pl.karbi.demo.R;
import pl.karbi.demo.SerBase;
import pl.karbi.demo.SerDerived;
import pl.karbi.demo.UserInterface;
import pl.karbi.demo.test.ParcelTest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;

public final class MainActivity extends MainUiActivity {

    /**
     * Called when the activity is first created.
     * @param savedInstanceState If the activity is being re-initialized after
     * previously being shut down then this Bundle contains the data it most
     * recently supplied in onSaveInstanceState(Bundle). <b>Note: Otherwise it is null.</b>
     */
    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LOG.trace("onCreate()");

        if (savedInstanceState != null) {
            for (final String key : savedInstanceState.keySet()) {
                LOG.trace("key: {}", key);
            }

            showCounter = savedInstanceState.getInt("showCounter", 0);
        } else {
            LOG.trace("savedInstanceState is null");
        }

        final SerBase ser = new SerDerived();
        ser.serializeToBundle();

        final ScheduledExecutorService executor = new ScheduledThreadPoolExecutor(1);
        executor.schedule(new Runnable() {

            @Override
            public void run() {
                try {
                    new ParcelTest();
                } catch (final RuntimeException e) {
                    LOG.error("", e);
                }
            }
        }, 5, TimeUnit.SECONDS);
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        final MenuItem item = menu.findItem(R.id.action_settings);
        item.setOnMenuItemClickListener(new OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(final MenuItem item) {
                LOG.trace("Menu clicked");

                final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Title");

                builder.setItems(new String[] {"Orange",  "Appler", "Strawberry"}, new OnClickListener() {

                    @Override
                    public void onClick(final DialogInterface dialog, final int which) {
                        LOG.trace("which = {}", which);

                        if (which == 1) {
                            new ApplicationThread(new UserInterface(MainActivity.this)).start();
                        } else {

                            ProgressActivity_.intent(MainActivity.this).start();
                        }
                    }
                });

                final AlertDialog dialog = builder.create();
                dialog.show();

                return true;
            }
        });

        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        LOG.trace("onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        LOG.trace("onResume()");
        ++showCounter;

        setTitle(String.valueOf(showCounter));
    }

    @Override
    protected void onPause() {
        super.onPause();
        LOG.trace("onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        LOG.trace("onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LOG.trace("onDestroy()");
    }

    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("showCounter", showCounter);

        LOG.trace("onSaveInstanceState()");
    }

    private int showCounter;

    private static final Logger LOG = LoggerFactory.getLogger(MainActivity.class);
}
