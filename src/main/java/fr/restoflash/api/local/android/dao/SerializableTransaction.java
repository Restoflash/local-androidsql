package fr.restoflash.api.local.android.dao;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import fr.restoflash.api.model.Checkout;
import fr.restoflash.api.model.Transaction;
import fr.restoflash.api.model.TransactionType;
import fr.restoflash.api.model.Voucher;

/**
 * Created by Alex on 24/04/2018.
 */

public class SerializableTransaction extends Transaction {

    public SerializableTransaction(String id, String is, Integer totalAmountCentimes,String product, Integer productAmountCentimes, Integer topUpAmountCentimes, String loginPos,
                                   Date scanTime, String reference, String ben, String company, String bonusId, String bonusName, String vouchersJson) {

        setId(id);
        setIs(is);
        setTotalAmountCentimes(totalAmountCentimes);
        setProduct(product);
        setProductAmountCentimes(productAmountCentimes);
        setTopUpAmountCentimes(topUpAmountCentimes);
        setLoginPos(loginPos);
        setScanTime(scanTime);
        setReference(reference);
        setBen(ben);
        setCompany(company);
        setBonusId(bonusId);
        setBonusName(bonusName);
        setVouchersJson(vouchersJson);
    }
    public SerializableTransaction(Transaction transaction)
    {
        setId(transaction.getId());
        setIs(transaction.getIs());
        setTotalAmount(transaction.getTotalAmount());
        setProduct(transaction.getProduct());
        setProductAmount(transaction.getProductAmount());
        setTopUpAmount(transaction.getTopUpAmount());
        setLoginPos(transaction.getLoginPos());
        setScanTime(transaction.getScanTime());
        setReference(transaction.getReference());
        setBen(transaction.getBen());
        setCompany(transaction.getCompany());
        setBonusId(transaction.getBonusId());
        setBonusName(transaction.getBonusName());
        setVouchers(transaction.getVouchers());
    }



    public void  setTotalAmountCentimes(int centimes)
    {
        setTotalAmount(new BigDecimal(centimes)
                .divide(new BigDecimal(100))
                .setScale(2, BigDecimal.ROUND_HALF_UP)
        );
    }
    public int getTotalAmountCentimes()
    {
        return getTotalAmount().multiply(new BigDecimal(100)).intValue();
    }

    public void  setProductAmountCentimes(int centimes)
    {
        setProductAmount(new BigDecimal(centimes)
                .divide(new BigDecimal(100))
                .setScale(2, BigDecimal.ROUND_HALF_UP)
        );
    }
    public int getProductAmountCentimes()
    {
        return getProductAmount().multiply(new BigDecimal(100)).intValue();
    }

    public void  setTopUpAmountCentimes(int centimes)
    {
        setTopUpAmount(new BigDecimal(centimes)
                .divide(new BigDecimal(100))
                .setScale(2, BigDecimal.ROUND_HALF_UP));
    }
    public int getTopUpAmountCentimes()
    {
        return getTopUpAmount().multiply(new BigDecimal(100)).intValue();
    }

    public void setVouchersJson(String vouchersJson)
    {
        Gson gson = new GsonBuilder().create();
        Type voucherListType =  new TypeToken<List<Voucher>>(){}.getType();
        List<Voucher> vouchers = gson.fromJson(vouchersJson,voucherListType);
        setVouchers(vouchers);
    }
    public String getVouchersJson()
    {
        Gson gson = new GsonBuilder().create();
        return gson.toJson(getVouchers());
    }






}
