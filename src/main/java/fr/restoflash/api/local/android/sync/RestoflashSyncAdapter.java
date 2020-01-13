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
import fr.restoflash.api.RestoFlashApiCore;
import fr.restoflash.api.exception.RequestFailedException;

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

        RestoFlashApi api = RestoFlashApi.getInstance();
        Log.i("RestoFlashService", "onPerformSync");
        if(api==null )
        {
            Log.i("RestoFlashService", "Resto Flash not initialized do nothing");
         //   EventBus.getDefault().post(new RestoFlash.SyncEventStart());
//            EventBus.getDefault().post(new RestoFlash.SyncEventStop());
            return;
        }
      //  EventBus.getDefault().post(new RestoFlash.SyncEventStart());

        if(api.unsyncedPaymentCount()==0)
        {
          //  EventBus.getDefault().post(new RestoFlash.SyncEventStop());
            return;
        }
        Log.i("RestoFlashService", "**** sync started ****");
        try {
            api.batchSync();
            Log.i("RestoFlashService", "**** sync finished ****");
        } catch (RequestFailedException e) {
            e.printStackTrace();
        }
        catch (Exception other)
        {
            other.printStackTrace();
            Log.i("RestoFlashService", "**** sync error  :" + other.getLocalizedMessage() + "****");
        }

    }
}
