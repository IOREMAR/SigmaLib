package sigma.manager.localdb;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class OldLocalDbProvider extends ContentProvider {

    private static final UriMatcher URI_MATCHER = buildUriMatcher();
    private OldLocalDBAdapter oldLocalDBAdapter;

    static final int TRANSACCIONES = 100;

    static final String ERROR_ROW = "Failed to insert row into ";
    static final String ERROR_URI = "Unknown uri: ";



    @Override
    public boolean onCreate() {
        oldLocalDBAdapter = new OldLocalDBAdapter(getContext());
        return true;
    }

    @Nullable
    @Override
    public String getType(@NonNull final Uri uri) {

        if (URI_MATCHER.match(uri) == TRANSACCIONES) {

            return OldLocalDBContract.OldTransaccionesEntry.CONTENT_TYPE;
        } else {

            throw new UnsupportedOperationException(ERROR_URI + uri);
        }

    }

    //transacciones_detalle/join_selected
    @Nullable
    @Override
    public Cursor query(@NonNull final Uri uri, @Nullable final String[] projection, @Nullable final String selection,
                        @Nullable final String[] selectionArgs, @Nullable final String sortOrder) {

        final SQLiteDatabase database = oldLocalDBAdapter.getWritableDatabase();
        Cursor retCursor;

        if  (URI_MATCHER.match(uri) == TRANSACCIONES) {

            retCursor = database.query(
                    OldLocalDBContract.OldTransaccionesEntry.TABLE_NAME,
                    projection,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    sortOrder
            );
        } else {

                throw new UnsupportedOperationException(ERROR_URI + uri);
        }

        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }


    @Nullable
    @Override
    public Uri insert(@NonNull final Uri uri, @Nullable final ContentValues values) {
        final SQLiteDatabase database = oldLocalDBAdapter.getWritableDatabase();
        Uri returnUri;
        if (URI_MATCHER.match(uri) == TRANSACCIONES) {

            final long idTrans = database.insert(
                    OldLocalDBContract.OldTransaccionesEntry.TABLE_NAME,
                    null,
                    values
            );

            if (idTrans > 0) {

                returnUri = OldLocalDBContract.OldTransaccionesEntry.buildAddressUri(idTrans);
            } else {

                throw new android.database.SQLException(ERROR_ROW + uri);
            }
        } else {

            throw new UnsupportedOperationException(ERROR_URI + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int bulkInsert(@NonNull final Uri uri, @Nullable final ContentValues[] values) {

        final SQLiteDatabase database = oldLocalDBAdapter.getWritableDatabase();
        int returnCount = 0;

        if  (URI_MATCHER.match(uri) == TRANSACCIONES) {

            database.beginTransaction();
            try {

                for (final ContentValues value : values) {

                    database.insert(
                            OldLocalDBContract.OldTransaccionesEntry.TABLE_NAME,
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
        } else {

            return super.bulkInsert(uri, values);
        }
    }

    @Override
    public int delete(@NonNull final Uri uri, @Nullable final String selectionSinValidar, @Nullable final String[] selectionArgs) {
        final SQLiteDatabase database = oldLocalDBAdapter.getWritableDatabase();
        int rowsDeleted;
        // this makes delete all rows return the number of rows deleted
        String selection;
        if (selectionSinValidar == null) {
            selection = "1";
        } else {
            selection = selectionSinValidar;
        }
        if (URI_MATCHER.match(uri) == TRANSACCIONES) {

            rowsDeleted = database.delete(OldLocalDBContract.OldTransaccionesEntry.TABLE_NAME, selection, selectionArgs);
        } else {

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
        final SQLiteDatabase database = oldLocalDBAdapter.getWritableDatabase();
        int rowsUpdated;

        if (URI_MATCHER.match(uri) == TRANSACCIONES) {

            rowsUpdated = database.update(OldLocalDBContract.OldTransaccionesEntry.TABLE_NAME, values, selection, selectionArgs);
        } else {

            throw new UnsupportedOperationException(ERROR_URI + uri);
        }

        if (rowsUpdated != 0) {

            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsUpdated;
    }

    static UriMatcher buildUriMatcher() {

        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

        matcher.addURI(OldLocalDBContract.CONTENT_AUTHORITY, OldLocalDBContract.PATH_TRANSACCIONES, TRANSACCIONES);

        return matcher;
    }
}
