package fr.restoflash.api.local.android.dao;

import android.content.Context;
import android.database.ContentObserver;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.Date;

import de.greenrobot.dao.query.CountQuery;
import de.greenrobot.dao.query.DeleteQuery;
import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;
import fr.restoflash.api.model.PaymentStatus;

/**
 * Created by Alex on 25/04/2018.
 */

public class SQLiteManager {



    private final DaoSession daoSession;
    final Query<SerializablePayment> selectAllPaymentsQuery;
    final Query<SerializablePayment> selectUnconfirmedPaymentsQuery;
    final Query<SerializablePayment> selectUnsyncedPaymentsQuery;


    final Query<SerializablePayment> selectPaymentFromTokenTextQuery;
    final CountQuery<SerializablePayment> countUnsyncedPaymentsQuery;
    final CountQuery<SerializablePayment> countDuplicateQuery;
    final Query<SerializablePayment> selectDeletePaymentsQuery;
    final DeleteQuery<SerializablePayment> deletePaymentsQuery;
    final CountQuery<SerializablePayment> countAllQuery;
    final Context context;
    public SQLiteManager(Context context, String dbName, boolean devMode)
    {
        this.context = context;
        DaoMaster.OpenHelper helper = null;
        if(devMode)
        {
            helper = new DaoMaster.DevOpenHelper(context, dbName,null);
        }
        else
        {
            helper = new DaoMaster.OpenHelper(context, dbName, null) {
                @Override
                public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

                }
            };
        }

        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        SerializablePaymentContentProvider.daoSession = daoSession;

        selectAllPaymentsQuery = daoSession.getSerializablePaymentDao().queryBuilder()
                .orderDesc(SerializablePaymentDao.Properties.CreationDate)
                .limit(12)
                .offset(0)
                .build();



        selectUnconfirmedPaymentsQuery = daoSession.getSerializablePaymentDao().queryBuilder()
                .orderAsc(SerializablePaymentDao.Properties.CreationDate)
                .where(SerializablePaymentDao.Properties.StatusString.eq(PaymentStatus.WAIT_CONFIRMATION.name()))
                .build();

        QueryBuilder<SerializablePayment> unsyncedQuery= daoSession.getSerializablePaymentDao()
                .queryBuilder().orderAsc(SerializablePaymentDao.Properties.CreationDate)
                .whereOr(SerializablePaymentDao.Properties.StatusString.eq(PaymentStatus.CONFIRMED.name()),
                         SerializablePaymentDao.Properties.StatusString.eq(PaymentStatus.CANCELED_AFTER_CONFIRMED.name()));

        selectUnsyncedPaymentsQuery =unsyncedQuery.build();


        countUnsyncedPaymentsQuery = unsyncedQuery.buildCount();

        countDuplicateQuery = daoSession.getSerializablePaymentDao().queryBuilder()
                .where(SerializablePaymentDao.Properties.TokenText.eq("<argument>"),
                        SerializablePaymentDao.Properties.StatusString.notEq(PaymentStatus.CANCELED.name()))
                .buildCount();

        selectPaymentFromTokenTextQuery = daoSession.getSerializablePaymentDao().queryBuilder()
                .where(SerializablePaymentDao.Properties.TokenText.eq("<argument>"),
            SerializablePaymentDao.Properties.StatusString.notEq(PaymentStatus.CANCELED.name())).build();

        deletePaymentsQuery = daoSession.getSerializablePaymentDao().queryBuilder()
                .where(SerializablePaymentDao.Properties.CreationDate.le(new Date()))
                .buildDelete();

        selectDeletePaymentsQuery = daoSession.getSerializablePaymentDao().queryBuilder()
                .where(SerializablePaymentDao.Properties.CreationDate.le(new Date()))
                .build();

        countAllQuery = daoSession.getSerializablePaymentDao().queryBuilder().buildCount();
    }

    public Query<SerializablePayment> selectAllPaymentsQuery() {
        return selectAllPaymentsQuery;
    }

    public Query<SerializablePayment> selectUnconfirmedPaymentsQuery() {
        return selectUnconfirmedPaymentsQuery;
    }

    public Query<SerializablePayment> selectUnsyncedPaymentsQuery() {
        return selectUnsyncedPaymentsQuery;
    }

    public CountQuery<SerializablePayment> countUnsyncedPaymentsQuery() {
        return countUnsyncedPaymentsQuery;
    }

    public CountQuery<SerializablePayment> countDuplicateQuery() {
        return countDuplicateQuery;
    }

    public Query<SerializablePayment> getSelectPaymentFromTokenTextQuery() {
        return selectPaymentFromTokenTextQuery;
    }


    public DeleteQuery<SerializablePayment> deletePaymentsQuery()
    {
        return deletePaymentsQuery;
    }

    public Query<SerializablePayment> getSelectDeletePaymentsQuery() {
        return selectDeletePaymentsQuery;
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }

    public void notifyChange(ContentObserver contentObserver)
    {
        Log.i("RestoFlashService", "context.getContentResolver().notifyChange()");
        context.getContentResolver().notifyChange(SerializablePaymentContentProvider.CONTENT_URI, contentObserver,true);
    }

    public CountQuery<SerializablePayment> getCountAllQuery() {
        return countAllQuery;
    }

    public Query<SerializablePayment> getSelectAllPaymentsQuery() {
        return selectAllPaymentsQuery;
    }
}
