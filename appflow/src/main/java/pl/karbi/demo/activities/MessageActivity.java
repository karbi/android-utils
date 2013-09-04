package pl.karbi.demo.activities;

import pl.karbi.demo.MessageInput;
import pl.karbi.demo.VoidOutput;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public final class MessageActivity extends BaseUiActivity<MessageInput, VoidOutput> {

    public static VoidOutput start(final MainUiActivity activity, final MessageInput input) {
        return (VoidOutput) start(activity, input, MessageActivity.class);
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final TextView messageText = new TextView(getApplicationContext());
        setContent(messageText);
        messageText.setText(getInput().getMessage());

        final Button okButton = addButton();
        okButton.setText("Ok");
        okButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View v) {
                putOutput(new VoidOutput());
                finish();
            }
        });
    }
}
