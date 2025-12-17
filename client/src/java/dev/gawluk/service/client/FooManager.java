package dev.gawluk.service.client;

import android.content.Context;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;

import dev.gawluk.service.IFooService;

public class FooManager  {
    private static final String TAG = "FooManager";

    private final IFooService service;
    private final Context context;

    private static FooManager instance;

    public static synchronized FooManager getInstance(Context context) {
        if (instance == null) {
            try {
                instance = new FooManager(context);
            } catch (ServiceNotFoundException e) {
                Log.e(TAG, "Failed to connect to FooService", e);
                return null;
            }
        }
        return instance;
    }

    private FooManager(Context context) throws ServiceNotFoundException {
        this.context = context;
        IBinder binder = ServiceManager.getService(IFooService.NAME);
        if (binder == null) {
            throw new ServiceNotFoundException("Service " + IFooService.NAME + " not found");
        }
        service = IFooService.Stub.asInterface(binder);
    }

    public void doWork() {
        try {
            service.bar();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static class ServiceNotFoundException extends Exception {
        public ServiceNotFoundException(String msg) { super(msg); }
    }
}