
package sigma.manager.localdb;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

public class LocalDbProvider extends ContentProvider {

    private static  UriMatcher URI_MATCHER ;
    private LocalDBAdapter localDBAdapter;

    static final int TRANSACCIONES = 100;
    static final int TRANSACCIONES_DETALLE = 101;
    static final int TRANSACCIONES_DETALLE_JOIN_CATALOGO_PRODUCTOS = 102;
    static final int CATALOGO_PRODUCTOS = 200;

    static final String ERROR_ROW = "Failed to insert row into ";
    static final String ERROR_URI = "Unknown uri: ";

    private static final SQLiteQueryBuilder PRODUCT_QUERIED_QUERY_BUILDER;

    static {
        PRODUCT_QUERIED_QUERY_BUILDER = new SQLiteQueryBuilder();
        PRODUCT_QUERIED_QUERY_BUILDER.setTables(
                LocalDBContract.TransaccionDetalleEntry.TABLE_NAME + " as t1" +
                        " LEFT JOIN " + LocalDBContract.CatalogoProductosEntry.TABLE_NAME + " as t2" +
                        " USING (" + LocalDBContract.TransaccionDetalleEntry.COLUMN_ID_PRODUCTO + ")"
        );
    }

    @Override
    public boolean onCreate() {
        localDBAdapter = new LocalDBAdapter(getContext());
        return true;
    }

    @Nullable
    @Override
    public String getType(@NonNull final Uri uri) {
        switch (getUriMatcher().match(uri)) {
            case TRANSACCIONES:
                return LocalDBContract.TransaccionesEntry.CONTENT_TYPE;
            case TRANSACCIONES_DETALLE:
                return LocalDBContract.TransaccionDetalleEntry.CONTENT_TYPE;
            case TRANSACCIONES_DETALLE_JOIN_CATALOGO_PRODUCTOS:
                return LocalDBContract.TransaccionDetalleEntry.CONTENT_TYPE;
            case CATALOGO_PRODUCTOS:
                return LocalDBContract.CatalogoProductosEntry.CONTENT_TYPE;
            default:
                throw new UnsupportedOperationException(ERROR_URI + uri);
        }
    }

    //transacciones_detalle/join_selected
    @Nullable
    @Override
    public Cursor query(@NonNull final Uri uri, @Nullable final String[] projection, @Nullable final String selection,
                        @Nullable final String[] selectionArgs, @Nullable final String sortOrder) {
        final SQLiteDatabase database = localDBAdapter.getWritableDatabase();
        Cursor retCursor;
        switch (getUriMatcher().match(uri)) {

            case TRANSACCIONES:
                retCursor = database.query(
                        LocalDBContract.TransaccionesEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            case CATALOGO_PRODUCTOS:
                retCursor = database.query(
                        LocalDBContract.CatalogoProductosEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            case TRANSACCIONES_DETALLE:
                retCursor = database.query(
                        LocalDBContract.TransaccionDetalleEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            case TRANSACCIONES_DETALLE_JOIN_CATALOGO_PRODUCTOS:
                retCursor = PRODUCT_QUERIED_QUERY_BUILDER.query(
                        database,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            default:
                throw new UnsupportedOperationException(ERROR_URI + uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }


    @Nullable
    @Override
    public Uri insert(@NonNull final Uri uri, @Nullable final ContentValues values) {

        final SQLiteDatabase database = localDBAdapter.getWritableDatabase();
        Uri returnUri;
        switch (getUriMatcher().match(uri)) {

            case TRANSACCIONES:
                long idTrans = database.insert(
                        LocalDBContract.TransaccionesEntry.TABLE_NAME,
                        null,
                        values
                );
                if (idTrans > 0) {
                    returnUri = LocalDBContract.TransaccionesEntry.buildAddressUri(idTrans);
                } else {
                    throw new android.database.SQLException(ERROR_ROW + uri);
                }
                break;
            case CATALOGO_PRODUCTOS:
                idTrans = database.insert(
                        LocalDBContract.CatalogoProductosEntry.TABLE_NAME,
                        null,
                        values
                );
                if (idTrans > 0) {
                    returnUri = LocalDBContract.CatalogoProductosEntry.buildAddressUri(idTrans);
                } else {
                    throw new android.database.SQLException(ERROR_ROW + uri);
                }
                break;
            case TRANSACCIONES_DETALLE:
                idTrans = database.insert(
                        LocalDBContract.TransaccionDetalleEntry.TABLE_NAME,
                        null,
                        values
                );
                if (idTrans > 0) {
                    returnUri = LocalDBContract.TransaccionDetalleEntry.buildAddressUri(idTrans);
                } else {
                    throw new android.database.SQLException(ERROR_ROW + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException(ERROR_URI + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int bulkInsert(@NonNull final Uri uri, @Nullable final ContentValues[] values) {
        final SQLiteDatabase database = localDBAdapter.getWritableDatabase();
        int returnCount = 0;

        switch (getUriMatcher().match(uri)) {
            case TRANSACCIONES:
                database.beginTransaction();
                try {
                    for (final ContentValues value : values) {
                        database.insert(
                                LocalDBContract.TransaccionesEntry.TABLE_NAME,
                                null,
                                value
                        );
                        returnCount++;
                    }
                    database.setTransactionSuccessful();
                } finally {
                    database.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;
            case CATALOGO_PRODUCTOS:
                database.beginTransaction();
                try {
                    for (final ContentValues value : values) {
                        database.insert(
                                LocalDBContract.CatalogoProductosEntry.TABLE_NAME,
                                null,
                                value
                        );
                        returnCount++;
                    }
                    database.setTransactionSuccessful();
                } finally {
                    database.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;
            case TRANSACCIONES_DETALLE:
                database.beginTransaction();
                try {
                    for (final ContentValues value : values) {
                        database.insert(
                                LocalDBContract.TransaccionDetalleEntry.TABLE_NAME,
                                null,
                                value
                        );
                        returnCount++;
                    }
                    database.setTransactionSuccessful();
                } finally {
                    database.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;
            default:
                return super.bulkInsert(uri, values);
        }
    }

    @Override
    public int delete(@NonNull final Uri uri, @Nullable final String selectionSinValidar, @Nullable final String[] selectionArgs) {
        final SQLiteDatabase database = localDBAdapter.getWritableDatabase();
        int rowsDeleted;
        // this makes delete all rows return the number of rows deleted
        String selection;
        if (selectionSinValidar == null) {
            selection = "1";
        } else {
            selection = selectionSinValidar;
        }
        switch (getUriMatcher().match(uri)) {
            case TRANSACCIONES:
                rowsDeleted = database.delete(LocalDBContract.TransaccionesEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case CATALOGO_PRODUCTOS:
                rowsDeleted = database.delete(LocalDBContract.CatalogoProductosEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case TRANSACCIONES_DETALLE:
                rowsDeleted = database.delete(LocalDBContract.TransaccionDetalleEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException(ERROR_URI + uri);
        }
//         Because a null deletes all rows
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(@NonNull final Uri uri, @Nullable final ContentValues values, @Nullable final String selection,
                      @Nullable final String[] selectionArgs) {
        final SQLiteDatabase database = localDBAdapter.getWritableDatabase();
        int rowsUpdated;

        switch (getUriMatcher().match(uri)) {
            case TRANSACCIONES:
                rowsUpdated = database.update(LocalDBContract.TransaccionesEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            case CATALOGO_PRODUCTOS:
                rowsUpdated = database.update(LocalDBContract.CatalogoProductosEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            case TRANSACCIONES_DETALLE:
                rowsUpdated = database.update(LocalDBContract.TransaccionDetalleEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException(ERROR_URI + uri);
        }
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }

    public static UriMatcher getUriMatcher() {
        if ( URI_MATCHER == null ) {
            URI_MATCHER = buildUriMatcher();
        }
        return URI_MATCHER;
    }

    static UriMatcher buildUriMatcher() {

        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

        matcher.addURI(LocalDBContract.CONTENT_AUTHORITY, LocalDBContract.PATH_TRANSACCIONES, TRANSACCIONES);
        matcher.addURI(LocalDBContract.CONTENT_AUTHORITY, LocalDBContract.PATH_CATALOGO_PRODUCTOS, CATALOGO_PRODUCTOS);
        matcher.addURI(LocalDBContract.CONTENT_AUTHORITY, LocalDBContract.PATH_TRANSACCIONES_DETALLE, TRANSACCIONES_DETALLE);
        matcher.addURI(LocalDBContract.CONTENT_AUTHORITY, LocalDBContract.PATH_TRANSACCIONES_DETALLE + "/" + LocalDBContract.TransaccionDetalleEntry.LJOIN_CATALOGO_PRODUCTOS, TRANSACCIONES_DETALLE_JOIN_CATALOGO_PRODUCTOS);

        return matcher;
    }
}
