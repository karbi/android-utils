package pl.karbi.demo;

import pl.karbi.demo.activities.MainUiActivity;
import pl.karbi.demo.activities.MessageActivity;

public final class UserInterface {

    public UserInterface(final MainUiActivity activity) {
        this.activity = activity;
    }

    public void showMessage(final String message) {
        MessageActivity.start(activity, new MessageInput(message));
    }

    public String getText(final String prompt) {
        return null;
    }

    private final MainUiActivity activity;
}
