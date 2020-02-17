package fr.restoflash.api.local.android.sync;


import android.accounts.Account;
import android.annotation.TargetApi;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SyncResult;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import fr.restoflash.api.RestoFlashApi;
import fr.restoflash.api.exception.RequestFailedException;
import fr.restoflash.api.model.Status;

/**
 * Created by Alex on 26/04/2018.
 */

public class RestoflashSyncAdapter extends AbstractThreadedSyncAdapter {


    ContentResolver mContentResolver;


    public RestoflashSyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
        mContentResolver = context.getContentResolver();
    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public RestoflashSyncAdapter(
            Context context,
            boolean autoInitialize,
            boolean allowParallelSyncs) {
        super(context, autoInitialize, allowParallelSyncs);
        mContentResolver = context.getContentResolver();
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {

        try {
            RestoFlashApi api = RestoFlashApi.getInstance();
            Log.i("RestoFlashService", "onPerformSync");
            if (api == null || api.getStatus().getValue() != Status.INITIALIZED) {
                Log.i("RestoFlashService", "Resto Flash not initialized do nothing");
                return;
            }

            if (api.unsyncedPaymentCount() == 0) {
                Log.i("RestoFlashService", "nothing to syncrhonize");
                return;
            }
            Log.i("RestoFlashService", "**** sync started ****");
            try {
                api.batchSync();
                Log.i("RestoFlashService", "**** sync finished ****");
            } catch (RequestFailedException e) {
                e.printStackTrace();
                RestoFlashService.syncPeriodically(getContext());
            } catch (Exception other) {
                other.printStackTrace();
                Log.i("RestoFlashService", "**** sync error  :" + other.getLocalizedMessage() + "****");
            }
            if (api.unsyncedPaymentCount() > 0) {
                RestoFlashService.syncPeriodically(getContext());
            } else {
                RestoFlashService.removePeriodicSync(getContext());
            }
        }
        catch (Exception e)
        {
        }

    }
}
