package sigma.manager.localdb;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static sigma.manager.localdb.LocalDBContract.TransaccionesEntry;
import static sigma.manager.localdb.LocalDBContract.CatalogoProductosEntry;
import static sigma.manager.localdb.LocalDBContract.TransaccionDetalleEntry;



public class LocalDBAdapter extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 16;
    private static final String DATABASE_NAME = "transacciones.db";
    private static final String CREATE_LBL = "CREATE TABLE ";
    private static final String INTEGER_NO_LBL = " INTEGER NOT NULL, ";
    private static final String INTEGER_LBL = " INTEGER, ";
    private static final String TEXT_LBL = " TEXT, ";
    private static final String REAL_LBL = " REAL, ";
    private static final String DROP_TABLE_LBL = "DROP TABLE IF EXISTS ";

    private static final String SQL_CREATE_TRANSACCION_TABLE = CREATE_LBL + TransaccionesEntry.TABLE_NAME + " ( "
            + TransaccionesEntry.COLUMN_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
            + TransaccionesEntry.COLUMN_FECHA + INTEGER_NO_LBL
            + TransaccionesEntry.COLUMN_CODIGO_OPER + " TEXT DEFAULT 'V', "
            + TransaccionesEntry.COLUMN_PRO_COD + TEXT_LBL
            + TransaccionesEntry.COLUMN_DESC_MENU + TEXT_LBL
            + TransaccionesEntry.COLUMN_IMPORTE + TEXT_LBL
            + TransaccionesEntry.COLUMN_MONEDA + TEXT_LBL
            + TransaccionesEntry.COLUMN_IMPORTE_ORIGEN + TEXT_LBL
            + TransaccionesEntry.COLUMN_FEE + INTEGER_LBL
            + TransaccionesEntry.COLUMN_REF_LOCAL + TEXT_LBL
            + TransaccionesEntry.COLUMN_STAN + " INTEGER NOT NULL UNIQUE, "
            + TransaccionesEntry.COLUMN_REF_REMOTA + TEXT_LBL
            + TransaccionesEntry.COLUMN_REF_CLIENTE + TEXT_LBL
            + TransaccionesEntry.COLUMN_DESC_PRODUCTO + TEXT_LBL
            + TransaccionesEntry.COLUMN_TURNO + INTEGER_NO_LBL
            + TransaccionesEntry.COLUMN_CONTABILIZAR + INTEGER_NO_LBL
            + TransaccionesEntry.COLUMN_ZINCRONIZADA + INTEGER_NO_LBL
            + TransaccionesEntry.COLUMN_CUENTA + INTEGER_LBL
            + TransaccionesEntry.COLUMN_DIA + INTEGER_LBL
            + TransaccionesEntry.COLUMN_TOTAL + TEXT_LBL
            + TransaccionesEntry.COLUMN_CASHBACK + REAL_LBL
            + TransaccionesEntry.COLUMN_IMPUESTO + REAL_LBL
            + TransaccionesEntry.COLUMN_PROPINA + REAL_LBL
            + TransaccionesEntry.COLUMN_MEDIO_PAGO + INTEGER_LBL
            + TransaccionesEntry.COLUMN_TIPO_TARJETA + " INTEGER);";

    private static final String SQL_CREATE_ADDRESS_TABLE_CATALOGO_PRODCUTOS = CREATE_LBL + CatalogoProductosEntry.TABLE_NAME + " ( "
            + CatalogoProductosEntry.COLUMN_DESCRIPCION + TEXT_LBL
            + CatalogoProductosEntry.COLUMN_DESVIACION + TEXT_LBL
            + CatalogoProductosEntry.COLUMN_EXISTENCIA + TEXT_LBL
            + CatalogoProductosEntry.COLUMN_FECHA_SYNC + TEXT_LBL
            + CatalogoProductosEntry.COLUMN_ID_CATEGORIA + INTEGER_LBL
            + CatalogoProductosEntry.COLUMN_ID_MARCA + INTEGER_LBL
            + CatalogoProductosEntry.COLUMN_ID_PRODUCTO + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
            + CatalogoProductosEntry.COLUMN_ID_TIPO_PRODUCTO + INTEGER_LBL
            + CatalogoProductosEntry.COLUMN_ID_UNIDAD + INTEGER_LBL
            + CatalogoProductosEntry.COLUMN_MINIMO + INTEGER_LBL
            + CatalogoProductosEntry.COLUMN_PRECIO_COMPRA + TEXT_LBL
            + CatalogoProductosEntry.COLUMN_PRECIO_VENTA + TEXT_LBL
            + CatalogoProductosEntry.COLUMN_PRECIO_VENTA_MAYOREO + TEXT_LBL
            + CatalogoProductosEntry.COLUMN_PROMEDIO + TEXT_LBL
            + CatalogoProductosEntry.COLUMN_SKU + TEXT_LBL
            + CatalogoProductosEntry.COLUMN_SUGERIDO + TEXT_LBL
            + CatalogoProductosEntry.COLUMN_SYNC + INTEGER_LBL
            + CatalogoProductosEntry.COLUMN_TECHO + TEXT_LBL
            + CatalogoProductosEntry.COLUMN_TOTAL_VENTAS + TEXT_LBL
            + CatalogoProductosEntry.COLUMN_DB_STATE + INTEGER_LBL
            + CatalogoProductosEntry.COLUMN_IMAGE_PREVIEW + TEXT_LBL
            + CatalogoProductosEntry.COLUMN_LOGO + TEXT_LBL
            + CatalogoProductosEntry.COLUMN_CUSTOM + " INTEGER);";

    private static final String SQL_CREATE_TRANSACCION_DETALLE_TABLE = CREATE_LBL + TransaccionDetalleEntry.TABLE_NAME + " ( "
            + TransaccionDetalleEntry.COLUMN_AUTORIZACION_PT + TEXT_LBL
            + TransaccionDetalleEntry.COLUMN_CANTIDAD + TEXT_LBL
            + TransaccionDetalleEntry.COLUMN_CANTIDAD_PROMO + TEXT_LBL
            + TransaccionDetalleEntry.COLUMN_DESCUENTO + TEXT_LBL
            + TransaccionDetalleEntry.COLUMN_FECHA_ACTUALIZACION + TEXT_LBL
            + TransaccionDetalleEntry.COLUMN_ID_CAMPANA + INTEGER_LBL
            + TransaccionDetalleEntry.COLUMN_ID_PRODUCTO + INTEGER_LBL
            + TransaccionDetalleEntry.COLUMN_ID_VENTA + INTEGER_LBL
            + TransaccionDetalleEntry.COLUMN_ID_VENTA_DETALLE + INTEGER_LBL
            + TransaccionDetalleEntry.COLUMN_IMPORTE_COMPRA + TEXT_LBL
            + TransaccionDetalleEntry.COLUMN_IMPORTE_VENTA + TEXT_LBL
            + TransaccionDetalleEntry.COLUMN_IS_PREMIO + INTEGER_LBL
            + TransaccionDetalleEntry.COLUMN_REFERENCIA + TEXT_LBL
            + TransaccionDetalleEntry.COLUMN_SOBRE_CARGO + " TEXT);";

    public LocalDBAdapter(final Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(final SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_TRANSACCION_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_TRANSACCION_DETALLE_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_ADDRESS_TABLE_CATALOGO_PRODCUTOS);
    }

    @Override
    public void onUpgrade(final SQLiteDatabase sqLiteDatabase, final int contador, final int contador1) {
        sqLiteDatabase.execSQL(DROP_TABLE_LBL + TransaccionesEntry.TABLE_NAME);
        sqLiteDatabase.execSQL(DROP_TABLE_LBL + TransaccionDetalleEntry.TABLE_NAME);
        sqLiteDatabase.execSQL(DROP_TABLE_LBL + CatalogoProductosEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
