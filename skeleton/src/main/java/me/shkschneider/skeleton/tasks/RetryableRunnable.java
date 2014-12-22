package me.shkschneider.skeleton.tasks;

public class RetryableRunnable implements Runnable {

    @Override
    public void run() {
        // Empty
    }

    public void retry() {
        run();
    }

}
