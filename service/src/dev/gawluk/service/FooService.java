package dev.gawluk.service;

import com.android.server.SystemService;
import android.content.Context;
import android.util.Log;

public class FooService extends SystemService {
    private static final String TAG = "FooService";

    public FooService(Context context) {
        super(context);
    }

    @Override
    public void onStart() {
        Log.d(TAG, "Starting Service");
        publishBinderService(IFooService.NAME, new BinderService());
    }

    private static final class BinderService extends IFooService.Stub {
        @Override
        public void bar() {
            Log.d(TAG, "bar()");
        }
    }
}