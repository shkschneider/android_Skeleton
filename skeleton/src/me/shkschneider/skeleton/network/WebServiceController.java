package me.shkschneider.skeleton.network;

import android.os.Handler;

import me.shkschneider.skeleton.helpers.LogHelper;
import me.shkschneider.skeleton.java.TimeUnitsHelper;
import me.shkschneider.skeleton.tasks.DelayedRunnable;
import me.shkschneider.skeleton.tasks.QueuedTasks;
import me.shkschneider.skeleton.tasks.RetryableRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class WebServiceController {

    private static final int MAX_TRIES = 3;
    private static final int RETRY_SECONDS = 1;

    private static WebServiceController INSTANCE;

    private List<WebService> mWebServices;

    public static WebServiceController get() {
        if (INSTANCE == null) {
            INSTANCE = new WebServiceController();
        }
        return INSTANCE;
    }

    public WebServiceController() {
        mWebServices = new ArrayList<WebService>();
    }

    public void queue(final WebService webService) {
        mWebServices.add(webService);
    }

    public int size() {
        return mWebServices.size();
    }

    public void flush() {
        if (mWebServices.size() == 0) {
            return ;
        }

        final QueuedTasks queuedTasks = new QueuedTasks();
        for (final WebService webService : mWebServices) {
            queuedTasks.queue(new RetryableRunnable() {

                @Override
                public void run() {
                    if (mWebServices.contains(webService)) {
                        mWebServices.remove(webService);
                    }
                    webService.tries++;

                    webService.run(new WebService.Callback() {

                        @Override
                        public void webserviceFailure(final Exception e) {
                            if (webService.tries > MAX_TRIES) {
                                webService.failure(e);
                                return ;
                            }

                            // retry
                            LogHelper.warning(String.format("Failed %d/%d, retry in %d seconds", webService.tries, MAX_TRIES, RETRY_SECONDS));
                            new DelayedRunnable() {

                                @Override
                                public void run() {
                                    super.run();

                                    retry();
                                }

                            }.delay(new Handler(), TimeUnitsHelper.milliseconds(RETRY_SECONDS, TimeUnit.SECONDS));
                        }

                        @Override
                        public void webserviceSuccess(int responseCode, Object result) {
                            webService.success(responseCode, result);
                            // recursive
                            flush();
                        }

                    });
                }

            });
        }
        queuedTasks.run();
    }

}
