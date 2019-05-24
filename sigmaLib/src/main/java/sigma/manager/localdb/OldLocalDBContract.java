package sigma.manager.localdb;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

import com.pagatodo.sigmamanager.BuildConfig;
import com.pagatodo.sigmamanager.Instance.ApiInstance;


public class OldLocalDBContract {

    public static final String CONTENT_AUTHORITY = ApiInstance.getInstance().getAppID() + ".oldProvider";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_TRANSACCIONES = "transacciones";

    private OldLocalDBContract() {
    }

    public static final class OldTransaccionesEntry implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_TRANSACCIONES).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TRANSACCIONES;

        public static final String TABLE_NAME = "transacciones";

        public static final String COLUMN_ID = "id";
        public static final String COLUMN_FECHA = "fecha";
        public static final String COLUMN_CODIGO_OPER = "codigooper";
        public static final String COLUMN_PRO_COD = "procod";
        public static final String COLUMN_DESC_MENU = "descmenu";
        public static final String COLUMN_IMPORTE = "importe";
        public static final String COLUMN_MONEDA = "moneda";
        public static final String COLUMN_IMPORTE_ORIGEN = "importeorigen";
        public static final String COLUMN_FEE = "fee";
        public static final String COLUMN_REF_LOCAL = "reflocal";
        public static final String COLUMN_STAN = "stan";
        public static final String COLUMN_REF_REMOTA = "refremota";
        public static final String COLUMN_REF_CLIENTE = "refcliente";
        public static final String COLUMN_DESC_PRODUCTO = "descproducto";
        public static final String COLUMN_TURNO = "turno";
        public static final String COLUMN_CONTABILIZAR = "contabilizar";
        public static final String COLUMN_ZINCRONIZADA = "zincronizada";
        public static final String COLUMN_CUENTA = "cuenta";
        public static final String COLUMN_DIA = "dia";
        public static final String COLUMN_TOTAL = "total";
        public static final String COLUMN_CASHBACK = "cashback";
        public static final String COLUMN_IMPUESTO = "impuesto";
        public static final String COLUMN_PROPINA = "propina";
        public static final String COLUMN_TIPO_TARJETA = "tipo_tarjeta";

        private OldTransaccionesEntry() {
        }

        public static Uri buildAddressUri(final long idProducto) {
            return ContentUris.withAppendedId(CONTENT_URI, idProducto);
        }
    }
}
