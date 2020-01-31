package fr.restoflash.api.local.android.preference;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

import java.lang.reflect.Modifier;

import fr.restoflash.api.local.android.sync.RestoFlashService;
import fr.restoflash.api.platform.sync.SyncPolicy;


public class LocalAndroidPreferences {


    private static String preferenceID = "94d45527-4ff9-49d9-b86d-0f1a729cbeb7";
    private static String optionsID = "4b797937-7a11-4502-96a6-e9d129c474c3";



    /*

        FOREGROUND, /// tente de synchroniser immédiatement, si erreur ne retente pas en background
        BACKGROUND, /// le paiement est enregistré et sera remonté en background soit immédiatement soit au retour du réseau
        FAILOVER, ///  tente de synchroniser immédiatement si erreur http, tente de synchroniser en background au retour du réseau

    */

    @Expose
    public SyncPolicy.SyncRule ruleForQr ;
    @Expose
    public SyncPolicy.SyncRule ruleForCheckoutList;


       @Expose(serialize = false, deserialize = false)
       Context appContext;





    protected LocalAndroidPreferences()
    {


    }

    public static LocalAndroidPreferences getPreferences(Context context)
    {


        SharedPreferences preferences = context.getSharedPreferences(preferenceID,Context.MODE_PRIVATE);
        if(preferences.contains(optionsID))
        {
            String json = preferences.getString(optionsID, null);
            if(json!=null)
            {
                LocalAndroidPreferences instance =   new GsonBuilder()
                        .excludeFieldsWithoutExposeAnnotation()
                        .excludeFieldsWithModifiers(Modifier.STATIC)
                        .excludeFieldsWithModifiers(Modifier.PRIVATE)
                        .create().fromJson(json, LocalAndroidPreferences.class);
                instance.appContext = context.getApplicationContext();
                boolean shouldSave=false;
                if(instance.ruleForQr==null) {
                    instance.ruleForQr = SyncPolicy.SyncRule.BACKGROUND;
                    shouldSave=true;
                }
                if(instance.ruleForCheckoutList==null){
                    instance.ruleForCheckoutList=SyncPolicy.SyncRule.FOREGROUND;
                    shouldSave=true;}
                if(shouldSave)
                     instance.save();
                return instance;
            }
        }
        LocalAndroidPreferences instance =  new LocalAndroidPreferences();
        instance.ruleForQr= SyncPolicy.SyncRule.BACKGROUND;
        instance.ruleForCheckoutList = SyncPolicy.SyncRule.FOREGROUND;
        instance.appContext = context;
     //   instance.preferences = preferences;
        instance.save();
        instance.configureSync(context);
        return instance;

    }

    public void save()
    {
        String json = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .excludeFieldsWithModifiers(Modifier.STATIC)
                .excludeFieldsWithModifiers(Modifier.PRIVATE)
                .create().toJson(this, LocalAndroidPreferences.class);

        getSharedPreferences().edit().putString(optionsID, json).commit();
    }

    public SyncPolicy.SyncRule getRuleForQr() {
        return ruleForQr;
    }

    public void setRuleForQr(SyncPolicy.SyncRule ruleForQr) {
        this.ruleForQr = ruleForQr;
        configureSync(appContext);
        save();
    }

    public SyncPolicy.SyncRule getRuleForCheckoutList() {
        return ruleForCheckoutList;
    }

    public void setRuleForCheckoutList(SyncPolicy.SyncRule ruleForCheckoutList) {
        this.ruleForCheckoutList = ruleForCheckoutList;
        configureSync(appContext);
        save();
    }

    private SharedPreferences getSharedPreferences()
    {
        return appContext.getSharedPreferences(preferenceID,Context.MODE_PRIVATE);
    }

    public static SyncPolicy.SyncRule toSyncRule (String name) {
        try {
            return SyncPolicy.SyncRule.valueOf(name);
        } catch (Exception ex) {
            // For error cases
            return SyncPolicy.SyncRule.FOREGROUND;
        }
    }

    protected void configureSync(Context context)
    {
        if(ruleForQr!= SyncPolicy.SyncRule.FOREGROUND || ruleForCheckoutList!= SyncPolicy.SyncRule.FOREGROUND)
        {
            RestoFlashService.syncAutomatically(context, true);
        }
        else
        {
            RestoFlashService.syncAutomatically(context, false);
        }
    }



}
