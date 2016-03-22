package com.tonilopezmr.dagger2;

import android.app.Application;
import android.support.annotation.VisibleForTesting;

import com.tonilopezmr.dagger2.di.DaggerMainComponent;
import com.tonilopezmr.dagger2.di.MainComponent;

/**
 * @author Antonio López.
 */
public class PersonApplication extends Application{

    private MainComponent mainComponent;

    @Override public void onCreate() {
        super.onCreate();
        mainComponent = DaggerMainComponent.create();
    }

    public MainComponent getMainComponent() {
        return mainComponent;
    }

    @VisibleForTesting
    public void setComponent(MainComponent mainComponent) {
        this.mainComponent = mainComponent;
    }

}
