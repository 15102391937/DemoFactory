package com.cgy.chengy.demofactory.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ForegroundCallbacks implements Application.ActivityLifecycleCallbacks {
    private static ForegroundCallbacks instance;
    private static final long CHECK_DELAY = 600;//变为后台600毫秒后才执行后台回调
    public static final String TAG = ForegroundCallbacks.class.getName();
    private boolean foreground = false, paused = true;
    private Handler handler = new Handler();
    private List<Listener> listeners = new CopyOnWriteArrayList<>();
    private Runnable check;

    public static ForegroundCallbacks init(Application application) {
        if (instance == null) {
            instance = new ForegroundCallbacks();
            application.registerActivityLifecycleCallbacks(instance);
        }
        return instance;
    }

    public static ForegroundCallbacks get(Application application) {
        if (instance == null) {
            init(application);
        }
        return instance;
    }

    public static ForegroundCallbacks get(Context ctx) {
        if (instance == null) {
            Context appCtx = ctx.getApplicationContext();
            if (appCtx instanceof Application) {
                init((Application) appCtx);
            }
            throw new IllegalStateException("Foreground is not initialised and " + "cannot obtain the Application object");
        }
        return instance;
    }

    public static ForegroundCallbacks get() {
        return instance;
    }

    public boolean isForeground() {
        return foreground;
    }

    public boolean isBackground() {
        return !foreground;
    }

    public void addListener(Listener listener) {
        listeners.add(listener);
    }

    public void removeListener(Listener listener) {
        listeners.remove(listener);
    }

    @Override
    public void onActivityResumed(Activity activity) {
        paused = false;
        if (check != null) handler.removeCallbacks(check);
        //如果不在前台
        if (!foreground) {
            foreground = true;
            for (Listener l : listeners) {
                try {
                    //回到前台了
                    l.onBecameForeground();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    @Override
    public void onActivityPaused(Activity activity) {
        paused = true;
        if (check != null) handler.removeCallbacks(check);
        handler.postDelayed(check = () -> {
            if (foreground && paused) {
                foreground = false;
                for (Listener l : listeners) {
                    try {
                        //变为后台了
                        l.onBecameBackground();
                    } catch (Exception e) {
                        e.printStackTrace();

                    }
                }
            }
        }, CHECK_DELAY);
    }
    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
    }
    @Override
    public void onActivityStarted(Activity activity) {
    }
    @Override
    public void onActivityStopped(Activity activity) {
    }
    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
    }
    @Override
    public void onActivityDestroyed(Activity activity) {
    }
    public interface Listener {
        void onBecameForeground();
        void onBecameBackground();
    }
}