package fr.restoflash.api.local.android.dao;
import java.util.List;
import java.util.ArrayList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.SqlUtils;
import de.greenrobot.dao.internal.DaoConfig;

;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "SERIALIZABLE_PAYMENT".
*/
public class SerializablePaymentDao extends AbstractDao<SerializablePayment, String> {

    public static final String TABLENAME = "SERIALIZABLE_PAYMENT";

    /**
     * Properties of entity SerializablePayment.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property UniqueId = new Property(0, String.class, "uniqueId", true, "UNIQUE_ID");
        public final static Property TokenTypeString = new Property(1, String.class, "tokenTypeString", false, "TOKEN_TYPE_STRING");
        public final static Property CreationDate = new Property(2, java.util.Date.class, "creationDate", false, "CREATION_DATE");
        public final static Property ConfirmDate = new Property(3, java.util.Date.class, "confirmDate", false, "CONFIRM_DATE");
        public final static Property ProcessedDate = new Property(4, java.util.Date.class, "processedDate", false, "PROCESSED_DATE");
        public final static Property Reference = new Property(5, String.class, "reference", false, "REFERENCE");
        public final static Property StatusString = new Property(6, String.class, "statusString", false, "STATUS_STRING");
        public final static Property ResultId = new Property(7, String.class, "resultId", false, "RESULT_ID");
        public final static Property ErrorId = new Property(8, String.class, "errorId", false, "ERROR_ID");
        public final static Property ErrorMessage = new Property(9, String.class, "errorMessage", false, "ERROR_MESSAGE");
        public final static Property TokenText = new Property(10, String.class, "tokenText", false, "TOKEN_TEXT");
        public final static Property AskedAmountCentimes = new Property(11, Integer.class, "askedAmountCentimes", false, "ASKED_AMOUNT_CENTIMES");
        public final static Property AmountToCancelCentimes = new Property(12, Integer.class, "amountToCancelCentimes", false, "AMOUNT_TO_CANCEL_CENTIMES");
        public final static Property AmountCancelledCentimes = new Property(13, Integer.class, "amountCancelledCentimes", false, "AMOUNT_CANCELLED_CENTIMES");
        public final static Property AcceptPartial = new Property(14, Boolean.class, "acceptPartial", false, "ACCEPT_PARTIAL");
        public final static Property LogsString = new Property(15, String.class, "logsString", false, "LOGS_STRING");
    };

    private DaoSession daoSession;


    public SerializablePaymentDao(DaoConfig config) {
        super(config);
    }
    
    public SerializablePaymentDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"SERIALIZABLE_PAYMENT\" (" + //
                "\"UNIQUE_ID\" TEXT PRIMARY KEY NOT NULL ," + // 0: uniqueId
                "\"TOKEN_TYPE_STRING\" TEXT NOT NULL ," + // 1: tokenTypeString
                "\"CREATION_DATE\" INTEGER NOT NULL ," + // 2: creationDate
                "\"CONFIRM_DATE\" INTEGER," + // 3: confirmDate
                "\"PROCESSED_DATE\" INTEGER," + // 4: processedDate
                "\"REFERENCE\" TEXT," + // 5: reference
                "\"STATUS_STRING\" TEXT," + // 6: statusString
                "\"RESULT_ID\" TEXT," + // 7: resultId
                "\"ERROR_ID\" TEXT," + // 8: errorId
                "\"ERROR_MESSAGE\" TEXT," + // 9: errorMessage
                "\"TOKEN_TEXT\" TEXT," + // 10: tokenText
                "\"ASKED_AMOUNT_CENTIMES\" INTEGER," + // 11: askedAmountCentimes
                "\"AMOUNT_TO_CANCEL_CENTIMES\" INTEGER," + // 12: amountToCancelCentimes
                "\"AMOUNT_CANCELLED_CENTIMES\" INTEGER," + // 13: amountCancelledCentimes
                "\"ACCEPT_PARTIAL\" INTEGER," + // 14: acceptPartial
                "\"LOGS_STRING\" TEXT);"); // 15: logsString
        // Add Indexes
        db.execSQL("CREATE INDEX " + constraint + "IDX_SERIALIZABLE_PAYMENT_UNIQUE_ID ON SERIALIZABLE_PAYMENT" +
                " (\"UNIQUE_ID\");");
        db.execSQL("CREATE INDEX " + constraint + "IDX_SERIALIZABLE_PAYMENT_TOKEN_TYPE_STRING ON SERIALIZABLE_PAYMENT" +
                " (\"TOKEN_TYPE_STRING\");");
        db.execSQL("CREATE INDEX " + constraint + "IDX_SERIALIZABLE_PAYMENT_CREATION_DATE ON SERIALIZABLE_PAYMENT" +
                " (\"CREATION_DATE\");");
        db.execSQL("CREATE INDEX " + constraint + "IDX_SERIALIZABLE_PAYMENT_CONFIRM_DATE ON SERIALIZABLE_PAYMENT" +
                " (\"CONFIRM_DATE\");");
        db.execSQL("CREATE INDEX " + constraint + "IDX_SERIALIZABLE_PAYMENT_PROCESSED_DATE ON SERIALIZABLE_PAYMENT" +
                " (\"PROCESSED_DATE\");");
        db.execSQL("CREATE INDEX " + constraint + "IDX_SERIALIZABLE_PAYMENT_STATUS_STRING ON SERIALIZABLE_PAYMENT" +
                " (\"STATUS_STRING\");");
        db.execSQL("CREATE INDEX " + constraint + "IDX_SERIALIZABLE_PAYMENT_RESULT_ID ON SERIALIZABLE_PAYMENT" +
                " (\"RESULT_ID\");");
        db.execSQL("CREATE INDEX " + constraint + "IDX_SERIALIZABLE_PAYMENT_TOKEN_TEXT ON SERIALIZABLE_PAYMENT" +
                " (\"TOKEN_TEXT\");");
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"SERIALIZABLE_PAYMENT\"";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, SerializablePayment entity) {
        stmt.clearBindings();
        stmt.bindString(1, entity.getUniqueId());
        stmt.bindString(2, entity.getTokenTypeString());
        stmt.bindLong(3, entity.getCreationDate().getTime());
 
        java.util.Date confirmDate = entity.getConfirmDate();
        if (confirmDate != null) {
            stmt.bindLong(4, confirmDate.getTime());
        }
 
        java.util.Date processedDate = entity.getProcessedDate();
        if (processedDate != null) {
            stmt.bindLong(5, processedDate.getTime());
        }
 
        String reference = entity.getReference();
        if (reference != null) {
            stmt.bindString(6, reference);
        }
 
        String statusString = entity.getStatusString();
        if (statusString != null) {
            stmt.bindString(7, statusString);
        }
 
        String resultId = entity.getResultId();
        if (resultId != null) {
            stmt.bindString(8, resultId);
        }
 
        String errorId = entity.getErrorId();
        if (errorId != null) {
            stmt.bindString(9, errorId);
        }
 
        String errorMessage = entity.getErrorMessage();
        if (errorMessage != null) {
            stmt.bindString(10, errorMessage);
        }
 
        String tokenText = entity.getTokenText();
        if (tokenText != null) {
            stmt.bindString(11, tokenText);
        }
 
        Integer askedAmountCentimes = entity.getAskedAmountCentimes();
        if (askedAmountCentimes != null) {
            stmt.bindLong(12, askedAmountCentimes);
        }
 
        Integer amountToCancelCentimes = entity.getAmountToCancelCentimes();
        if (amountToCancelCentimes != null) {
            stmt.bindLong(13, amountToCancelCentimes);
        }
 
        Integer amountCancelledCentimes = entity.getAmountCancelledCentimes();
        if (amountCancelledCentimes != null) {
            stmt.bindLong(14, amountCancelledCentimes);
        }
 
        Boolean acceptPartial = entity.getAcceptPartial();
        if (acceptPartial != null) {
            stmt.bindLong(15, acceptPartial ? 1L: 0L);
        }
 
        String logsString = entity.getLogsString();
        if (logsString != null) {
            stmt.bindString(16, logsString);
        }
    }

    @Override
    protected void attachEntity(SerializablePayment entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    /** @inheritdoc */
    @Override
    public String readKey(Cursor cursor, int offset) {
        return cursor.getString(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public SerializablePayment readEntity(Cursor cursor, int offset) {
        SerializablePayment entity = new SerializablePayment( //
            cursor.getString(offset + 0), // uniqueId
            cursor.getString(offset + 1), // tokenTypeString
            new java.util.Date(cursor.getLong(offset + 2)), // creationDate
            cursor.isNull(offset + 3) ? null : new java.util.Date(cursor.getLong(offset + 3)), // confirmDate
            cursor.isNull(offset + 4) ? null : new java.util.Date(cursor.getLong(offset + 4)), // processedDate
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // reference
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // statusString
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // resultId
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // errorId
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // errorMessage
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // tokenText
            cursor.isNull(offset + 11) ? null : cursor.getInt(offset + 11), // askedAmountCentimes
            cursor.isNull(offset + 12) ? null : cursor.getInt(offset + 12), // amountToCancelCentimes
            cursor.isNull(offset + 13) ? null : cursor.getInt(offset + 13), // amountCancelledCentimes
            cursor.isNull(offset + 14) ? null : cursor.getShort(offset + 14) != 0, // acceptPartial
            cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15) // logsString
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, SerializablePayment entity, int offset) {
        entity.setUniqueId(cursor.getString(offset + 0));
        entity.setTokenTypeString(cursor.getString(offset + 1));
        entity.setCreationDate(new java.util.Date(cursor.getLong(offset + 2)));
        entity.setConfirmDate(cursor.isNull(offset + 3) ? null : new java.util.Date(cursor.getLong(offset + 3)));
        entity.setProcessedDate(cursor.isNull(offset + 4) ? null : new java.util.Date(cursor.getLong(offset + 4)));
        entity.setReference(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setStatusString(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setResultId(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setErrorId(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setErrorMessage(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setTokenText(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setAskedAmountCentimes(cursor.isNull(offset + 11) ? null : cursor.getInt(offset + 11));
        entity.setAmountToCancelCentimes(cursor.isNull(offset + 12) ? null : cursor.getInt(offset + 12));
        entity.setAmountCancelledCentimes(cursor.isNull(offset + 13) ? null : cursor.getInt(offset + 13));
        entity.setAcceptPartial(cursor.isNull(offset + 14) ? null : cursor.getShort(offset + 14) != 0);
        entity.setLogsString(cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15));
     }
    
    /** @inheritdoc */
    @Override
    protected String updateKeyAfterInsert(SerializablePayment entity, long rowId) {
        return entity.getUniqueId();
    }
    
    /** @inheritdoc */
    @Override
    public String getKey(SerializablePayment entity) {
        if(entity != null) {
            return entity.getUniqueId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
    private String selectDeep;

    protected String getSelectDeep() {
        if (selectDeep == null) {
            StringBuilder builder = new StringBuilder("SELECT ");
            SqlUtils.appendColumns(builder, "T", getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T0", daoSession.getSerializableTransactionDao().getAllColumns());
            builder.append(" FROM SERIALIZABLE_PAYMENT T");
            builder.append(" LEFT JOIN SERIALIZABLE_TRANSACTION T0 ON T.\"RESULT_ID\"=T0.\"ID\"");
            builder.append(' ');
            selectDeep = builder.toString();
        }
        return selectDeep;
    }
    
    protected SerializablePayment loadCurrentDeep(Cursor cursor, boolean lock) {
        SerializablePayment entity = loadCurrent(cursor, 0, lock);
        int offset = getAllColumns().length;

        SerializableTransaction transactionResult = loadCurrentOther(daoSession.getSerializableTransactionDao(), cursor, offset);
        entity.setTransactionResult(transactionResult);

        return entity;    
    }

    public SerializablePayment loadDeep(Long key) {
        assertSinglePk();
        if (key == null) {
            return null;
        }

        StringBuilder builder = new StringBuilder(getSelectDeep());
        builder.append("WHERE ");
        SqlUtils.appendColumnsEqValue(builder, "T", getPkColumns());
        String sql = builder.toString();
        
        String[] keyArray = new String[] { key.toString() };
        Cursor cursor = db.rawQuery(sql, keyArray);
        
        try {
            boolean available = cursor.moveToFirst();
            if (!available) {
                return null;
            } else if (!cursor.isLast()) {
                throw new IllegalStateException("Expected unique result, but count was " + cursor.getCount());
            }
            return loadCurrentDeep(cursor, true);
        } finally {
            cursor.close();
        }
    }
    
    /** Reads all available rows from the given cursor and returns a list of new ImageTO objects. */
    public List<SerializablePayment> loadAllDeepFromCursor(Cursor cursor) {
        int count = cursor.getCount();
        List<SerializablePayment> list = new ArrayList<SerializablePayment>(count);
        
        if (cursor.moveToFirst()) {
            if (identityScope != null) {
                identityScope.lock();
                identityScope.reserveRoom(count);
            }
            try {
                do {
                    list.add(loadCurrentDeep(cursor, false));
                } while (cursor.moveToNext());
            } finally {
                if (identityScope != null) {
                    identityScope.unlock();
                }
            }
        }
        return list;
    }
    
    protected List<SerializablePayment> loadDeepAllAndCloseCursor(Cursor cursor) {
        try {
            return loadAllDeepFromCursor(cursor);
        } finally {
            cursor.close();
        }
    }
    

    /** A raw-style query where you can pass any WHERE clause and arguments. */
    public List<SerializablePayment> queryDeep(String where, String... selectionArg) {
        Cursor cursor = db.rawQuery(getSelectDeep() + where, selectionArg);
        return loadDeepAllAndCloseCursor(cursor);
    }
 
}
