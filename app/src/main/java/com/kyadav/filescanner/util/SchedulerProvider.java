package com.kyadav.filescanner.util;

import io.reactivex.Scheduler;

/**
 * Created by kyadav on 18/04/18.
 */

public interface SchedulerProvider {
    Scheduler ui();

    Scheduler computation();

    Scheduler io();
}
