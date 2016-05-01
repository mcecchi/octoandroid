package com.nairbspace.octoandroid.data.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "PRINTER".
*/
public class PrinterDao extends AbstractDao<Printer, Long> {

    public static final String TABLENAME = "PRINTER";

    /**
     * Properties of entity Printer.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Name = new Property(1, String.class, "name", false, "NAME");
        public final static Property Api_key = new Property(2, String.class, "api_key", false, "API_KEY");
        public final static Property Scheme = new Property(3, String.class, "scheme", false, "SCHEME");
        public final static Property Host = new Property(4, String.class, "host", false, "HOST");
        public final static Property Port = new Property(5, int.class, "port", false, "PORT");
        public final static Property Version_json = new Property(6, String.class, "version_json", false, "VERSION_JSON");
        public final static Property Connection_json = new Property(7, String.class, "connection_json", false, "CONNECTION_JSON");
    };


    public PrinterDao(DaoConfig config) {
        super(config);
    }
    
    public PrinterDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"PRINTER\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"NAME\" TEXT NOT NULL UNIQUE ," + // 1: name
                "\"API_KEY\" TEXT NOT NULL ," + // 2: api_key
                "\"SCHEME\" TEXT NOT NULL ," + // 3: scheme
                "\"HOST\" TEXT NOT NULL ," + // 4: host
                "\"PORT\" INTEGER NOT NULL ," + // 5: port
                "\"VERSION_JSON\" TEXT," + // 6: version_json
                "\"CONNECTION_JSON\" TEXT);"); // 7: connection_json
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"PRINTER\"";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, Printer entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindString(2, entity.getName());
        stmt.bindString(3, entity.getApi_key());
        stmt.bindString(4, entity.getScheme());
        stmt.bindString(5, entity.getHost());
        stmt.bindLong(6, entity.getPort());
 
        String version_json = entity.getVersion_json();
        if (version_json != null) {
            stmt.bindString(7, version_json);
        }
 
        String connection_json = entity.getConnection_json();
        if (connection_json != null) {
            stmt.bindString(8, connection_json);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public Printer readEntity(Cursor cursor, int offset) {
        Printer entity = new Printer( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getString(offset + 1), // name
            cursor.getString(offset + 2), // api_key
            cursor.getString(offset + 3), // scheme
            cursor.getString(offset + 4), // host
            cursor.getInt(offset + 5), // port
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // version_json
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7) // connection_json
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, Printer entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setName(cursor.getString(offset + 1));
        entity.setApi_key(cursor.getString(offset + 2));
        entity.setScheme(cursor.getString(offset + 3));
        entity.setHost(cursor.getString(offset + 4));
        entity.setPort(cursor.getInt(offset + 5));
        entity.setVersion_json(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setConnection_json(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(Printer entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(Printer entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}
