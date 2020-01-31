package fr.restoflash.api.local.android.platform.serializer;

import android.content.Context;
import android.database.ContentObservable;
import android.database.ContentObserver;
import android.database.sqlite.SQLiteDatabase;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.greenrobot.dao.query.CountQuery;
import de.greenrobot.dao.query.DeleteQuery;
import de.greenrobot.dao.query.Query;
import fr.restoflash.api.local.android.dao.DaoMaster;
import fr.restoflash.api.local.android.dao.SQLiteManager;
import fr.restoflash.api.local.android.dao.SerializablePayment;
import fr.restoflash.api.model.PaymentStatus;
import fr.restoflash.api.model.TokenType;
import fr.restoflash.api.model.interfaces.PaymentInterface;
import fr.restoflash.api.platform.serializer.PaymentsSerializer;

/**
 * Created by Alex on 23/04/2018.
 */

public class AndroidSQLPaymentSerializer implements PaymentsSerializer {
    final static boolean DEV_MODE = true;
    final Context context;
    SQLiteManager sqliteManager;
    ContentObserver contentObserver=null;
    final String dbName ;
    final boolean devMode;

    public AndroidSQLPaymentSerializer(Context androidContext, String dbName, boolean devMode) {
        this.context = androidContext;
        this.dbName = dbName;
        this.devMode = devMode;

    }
    public AndroidSQLPaymentSerializer(Context androidContext, String dbName) {
        this(androidContext,dbName,DEV_MODE);
    }
    public AndroidSQLPaymentSerializer(Context androidContext) {
        this(androidContext,"restoflash.db",DEV_MODE);
    }


    @Override
    public void save(PaymentInterface payment) {
        SerializablePayment serializablePayment = new SerializablePayment(payment);
        sqliteManager.getDaoSession().getSerializablePaymentDao().insertOrReplace(serializablePayment);
    }

    @Override
    public PaymentInterface load(String paymentId) {
        return sqliteManager.getDaoSession().getSerializablePaymentDao().load(paymentId);
    }

    @Override
    public List<? extends PaymentInterface> load(int limit, int offset) {
        Query<SerializablePayment> query = sqliteManager.selectAllPaymentsQuery().forCurrentThread();
        query.setLimit(limit);
        query.setOffset(offset);
        return query.list();
    }

    @Override
    public List<? extends PaymentInterface> loadUnconfirmed() {
        return sqliteManager.selectUnconfirmedPaymentsQuery().forCurrentThread().list();
    }

    @Override
    public List<? extends PaymentInterface> loadUnsynced() {
        return sqliteManager.selectUnsyncedPaymentsQuery().forCurrentThread().list();
    }

    @Override
    public long unsyncedPaymentsCount() {
        return  sqliteManager.countUnsyncedPaymentsQuery().forCurrentThread().count();
    }

    @Override
    public PaymentInterface findDuplicate(TokenType type,  String token ) {

        Query<SerializablePayment> countDuplicateQuery = sqliteManager.getSelectPaymentFromTokenTextQuery().forCurrentThread();
        countDuplicateQuery.setParameter(0, token);
        int size = countDuplicateQuery.listLazy().size();
        if (size > 0)
            return countDuplicateQuery.listLazy().get(0);
        return null;
    }

    @Override
    public void update(PaymentInterface paymentInterface) {
        SerializablePayment payment = new SerializablePayment(paymentInterface);
        sqliteManager.getDaoSession().getSerializablePaymentDao().update(payment);
        if(payment.getStatus() == PaymentStatus.CONFIRMED || payment.getStatus() == PaymentStatus.CANCELED_AFTER_CONFIRMED) {
            sqliteManager.notifyChange(contentObserver);
        }
    }

    @Override
    public List<String> pruneBefore(Date date) {
        List<String> retValue = new ArrayList<>();
        Query<SerializablePayment> select = sqliteManager.getSelectDeletePaymentsQuery().forCurrentThread();
        select.setParameter(0,date);
        List<SerializablePayment> list = select.list();
        for(SerializablePayment payment : list)
        {
            retValue.add(payment.getUniqueId());
        }
        DeleteQuery<SerializablePayment> deleteQuery = sqliteManager.deletePaymentsQuery().forCurrentThread();
        deleteQuery.setParameter(0,date);
        deleteQuery.executeDeleteWithoutDetachingEntities();
        return retValue;
    }

    @Override
    public List<String> pruneLimit(long limit) {

        try {
            long count = sqliteManager.getCountAllQuery().count();
            if (count > limit) {
                Query<SerializablePayment> selectQuery = sqliteManager.getSelectAllPaymentsQuery().forCurrentThread();
                selectQuery.setOffset((int) limit);
                selectQuery.setLimit(1);
                SerializablePayment payment = selectQuery.unique();
                if (payment != null) {
                    return pruneBefore(payment.getCreationDate());
                }
            }
        }
        catch (Exception e)
        {

        }
        return  new ArrayList<>();
    }

    @Override
    public void initialize() {
        sqliteManager = new SQLiteManager(context, dbName, devMode);
    }

    @Override
    public void cleanUp() {
        if(sqliteManager!=null)
        {
            sqliteManager.closeDb();
            sqliteManager=null;
        }
    }

    @Override
    public File getLogs() {
        SQLiteDatabase db = sqliteManager.getReadableDatabase();
        File fileDb = new File(db.getPath());
        return fileDb;
    }

    public ContentObserver getContentObserver() {
        return contentObserver;
    }

    public void setContentObserver(ContentObserver contentObserver) {
        this.contentObserver = contentObserver;
    }
}
