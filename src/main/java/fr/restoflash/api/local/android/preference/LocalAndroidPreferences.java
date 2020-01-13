package fr.restoflash.api.local.android.preference;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

import fr.restoflash.api.platform.sync.SyncPolicy;


public class LocalAndroidPreferences {


    private static String preferenceID = "94d45527-4ff9-49d9-b86d-0f1a729cbeb7";
    private static String optionsID = "4b797937-7a11-4502-96a6-e9d129c474c3";



    /*

        FOREGROUND, /// tente de synchroniser immédiatement, si erreur ne retente pas en background
        BACKGROUND, /// le paiement est enregistré et sera remonté en background soit immédiatement soit au retour du réseau
        FAILOVER, ///  tente de synchroniser immédiatement si erreur http, tente de synchroniser en background au retour du réseau

    */

    SyncPolicy.SyncRule ruleForQr = SyncPolicy.SyncRule.BACKGROUND;
    SyncPolicy.SyncRule ruleForCheckoutList = SyncPolicy.SyncRule.FOREGROUND;


    @Expose(serialize = false, deserialize = false)
    SharedPreferences preferences;




    protected LocalAndroidPreferences()
    {
        this.ruleForQr= SyncPolicy.SyncRule.BACKGROUND;
        this.ruleForCheckoutList = SyncPolicy.SyncRule.FOREGROUND;

    }

    public static LocalAndroidPreferences getPreferences(Context context)
    {

        SharedPreferences preferences = context.getSharedPreferences(preferenceID,Context.MODE_PRIVATE);
        if(preferences.contains(optionsID))
        {
            String json = preferences.getString(optionsID, null);
            if(json!=null)
            {
                return  new GsonBuilder()
                        .excludeFieldsWithoutExposeAnnotation()
                        .create().fromJson(json, LocalAndroidPreferences.class);
            }
        }
        LocalAndroidPreferences instance =  new LocalAndroidPreferences();
        instance.preferences = preferences;
        instance.save();
        return instance;

    }

    public void save()
    {
        preferences.edit().putString(optionsID, new Gson().toJson(this, LocalAndroidPreferences.class)).commit();
    }

    public SyncPolicy.SyncRule getRuleForQr() {
        return ruleForQr;
    }

    public void setRuleForQr(SyncPolicy.SyncRule ruleForQr) {
        this.ruleForQr = ruleForQr;
        save();
    }

    public SyncPolicy.SyncRule getRuleForCheckoutList() {
        return ruleForCheckoutList;
    }

    public void setRuleForCheckoutList(SyncPolicy.SyncRule ruleForCheckoutList) {
        this.ruleForCheckoutList = ruleForCheckoutList;
        save();
    }



    public SharedPreferences getPreferences() {
        return preferences;
    }

    public void setPreferences(SharedPreferences preferences) {
        this.preferences = preferences;
    }
}
