package fr.restoflash.api.local;

import android.content.Context;

import fr.restoflash.api.local.android.platform.serializer.AndroidSQLPaymentSerializer;
import fr.restoflash.api.local.android.preference.LocalAndroidPreferences;
import fr.restoflash.api.model.Checkout;
import fr.restoflash.api.ConfigurationCore;
import fr.restoflash.api.model.QrCode;
import fr.restoflash.api.model.TokenType;
import fr.restoflash.api.model.interfaces.PaymentInterface;
import fr.restoflash.api.platform.serializer.PaymentsSerializer;
import fr.restoflash.api.platform.sync.SyncPolicy;

import static fr.restoflash.api.platform.sync.SyncPolicy.SyncRule.FOREGROUND;

public class LocalPlatform implements fr.restoflash.api.platform.LocalPlatform {


    @Override
    public PaymentsSerializer getSerializer(Object constructorObject) {
        return new AndroidSQLPaymentSerializer((Context)constructorObject);
    }

    @Override
    public SyncPolicy getSyncStrategy(Object constructorObject) {
        final Context context = (Context)constructorObject;

        SyncPolicy strategy = new SyncPolicy() {
            @Override
            public SyncRule ruleForPayment(PaymentInterface payment, ConfigurationCore currentConfig) {

                if (payment.getTokenType() == TokenType.QRCODE) {
                    boolean bonRepas = false;
                    String bonus = ((QrCode) payment.getToken()).getBonusId();
                    if( bonus != null && !bonus.isEmpty()) {
                        try{
                            bonRepas =  Long.valueOf(((QrCode) payment.getToken()).getBonusId()) < 0;
                        }
                        catch(Throwable e)
                        {
                            e.printStackTrace();
                        }
                    }
                    if (bonRepas)
                        return FOREGROUND;

                    return LocalAndroidPreferences.getPreferences(context).getRuleForQr();

                } else if(payment.getTokenType()==TokenType.CHECKOUT){
                    Checkout sco = (Checkout)payment.getToken();
                    if(sco.isRetrieved())
                    {
                        return LocalAndroidPreferences.getPreferences(context).getRuleForCheckoutList();
                    }
                    else
                    {
                        return FOREGROUND; // checkoutCode toujours foreground
                    }

                }
                return FOREGROUND;
            }

        };
        return strategy;
    }
}
