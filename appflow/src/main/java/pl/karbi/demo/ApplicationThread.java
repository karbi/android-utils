package pl.karbi.demo;

public final class ApplicationThread {

    public ApplicationThread(final UserInterface ui) {
        this.ui = ui;
    }

    public void start() {
        final Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                ui.showMessage("Starting thread");

                try {
                    Thread.sleep(3000);
                } catch (final InterruptedException e) {
                    return;
                }

                final String text = ui.getText("Enter some text");

                try {
                    Thread.sleep(3000);
                } catch (final InterruptedException e) {
                    return;
                }

                ui.showMessage("Entered text: " + text);
            }
        });

        thread.start();
    }

    private final UserInterface ui;

}
