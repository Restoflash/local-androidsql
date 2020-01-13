package fr.restoflash.api.local.android.sync;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Service;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.PeriodicSync;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import java.util.List;

import fr.restoflash.api.local.android.dao.SerializablePaymentContentProvider;


/**
 * Created by Alex on 26/04/2018.
 */

public class RestoFlashService extends Service {


    public final static String ACTION_GET_API = "fr.restoflash.ACTION_GET_API";
    public final static String ACTION_INITIALIZE = "fr.restoflash.ACTION_INITIALIZE";


    private static RestoflashSyncAdapter syncAdapter = null;
    private static final Object syncAdapterLock = new Object();
    private static Account dummyAccount;
    public static final long SECONDS_PER_MINUTE = 60L;

    public static final long SYNC_INTERVAL_5_MINUTES = 5 *  SECONDS_PER_MINUTE;
    public static final long SYNC_INTERVAL_10_MINUTES = 10 *  SECONDS_PER_MINUTE;




    @Override
    public void onCreate() {
        Log.i("RestoFlashService", "onCreate");
        synchronized (syncAdapterLock) {
            if (syncAdapter == null) {
                syncAdapter = new RestoflashSyncAdapter(getApplicationContext(), true, false);
            }
        }


    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("RestoflashService", "Received start id " + startId + ": " + intent);
        return START_NOT_STICKY;
    }


    @Override
    public IBinder onBind(Intent intent) {
        return syncAdapter.getSyncAdapterBinder();
    }

    /**
     * Create a new dummy account for the sync adapter
     *
     * @param context The application context
     */
    public static Account createSyncAccount(Context context) {

        Account newAccount = new Account(
                "sync", "restoflash.fr");

        AccountManager accountManager =
                (AccountManager) context.getSystemService(
                        ACCOUNT_SERVICE);

        if (accountManager.addAccountExplicitly(newAccount, null, null)) {

        } else {
            /*
             * The account exists or some other error occurred
             */
        }
        return newAccount;
    }

    //   ContentResolver.requestSync(dummyAccount, SerializablePaymentContentProvider.AUTHORITY, null);


    public static void createDummyAccount(Context context)
    {
        if(dummyAccount==null)
            dummyAccount = createSyncAccount(context);
    }

    public static void syncAutomatically(Context context, boolean syncAutomatically) {
        createDummyAccount(context);
        ContentResolver.setSyncAutomatically(dummyAccount, SerializablePaymentContentProvider.AUTHORITY, syncAutomatically);
    }

    public static boolean isSyncAutomatically(Context context) {
        createDummyAccount(context);
        return ContentResolver.getSyncAutomatically(dummyAccount, SerializablePaymentContentProvider.AUTHORITY);
    }

    public static List<PeriodicSync> isSyncPeriodically(Context context)
    {
        createDummyAccount(context);
        return ContentResolver.getPeriodicSyncs(dummyAccount, SerializablePaymentContentProvider.AUTHORITY);

    }


    public static void syncPeriodically(Context context, boolean syncPeriodically, long periodInSeconds)
    {
        createDummyAccount(context);
        ContentResolver.removePeriodicSync(dummyAccount,SerializablePaymentContentProvider.AUTHORITY,  Bundle.EMPTY);

        if(syncPeriodically) {

            ContentResolver.addPeriodicSync(
                    dummyAccount,
                    SerializablePaymentContentProvider.AUTHORITY,
                    Bundle.EMPTY,
                    periodInSeconds);
        }

    }

    public static void requestSync(Context context)
    {
        // Pass the settings flags by inserting them in a bundle
        Bundle settingsBundle = new Bundle();
        settingsBundle.putBoolean(
                ContentResolver.SYNC_EXTRAS_MANUAL, true);
        settingsBundle.putBoolean(
                ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        /*
         * Request the sync for the default account, authority, and
         * manual sync settings
         */
        ContentResolver.requestSync(dummyAccount, SerializablePaymentContentProvider.AUTHORITY, settingsBundle);
    }




}
