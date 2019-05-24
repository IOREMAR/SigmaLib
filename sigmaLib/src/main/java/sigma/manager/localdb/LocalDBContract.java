package sigma.manager.localdb;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

import com.pagatodo.sigmamanager.BuildConfig;
import com.pagatodo.sigmamanager.Instance.ApiInstance;


public class LocalDBContract {

    public static final String CONTENT_AUTHORITY = ApiInstance.getInstance().getAppID();
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_TRANSACCIONES = "transacciones";
    public static final String PATH_TRANSACCIONES_DETALLE = "transacciones_detalle";
    public static final String PATH_CATALOGO_PRODUCTOS = "catalogo_productos";

    private LocalDBContract() {
    }

    public static final class TransaccionesEntry implements BaseColumns {
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
        public static final String COLUMN_MEDIO_PAGO = "medio_pago";

        private TransaccionesEntry() {
        }

        public static Uri buildAddressUri(final long idProducto) {
            return ContentUris.withAppendedId(CONTENT_URI, idProducto);
        }
    }

    public static final class TransaccionDetalleEntry implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_TRANSACCIONES_DETALLE).build();

        public static final String LJOIN_CATALOGO_PRODUCTOS = "left_join_catalogo_productos";
        public static final Uri CONTENT_URI_LJOIN_CATALOGO_PRODUCTOS = BASE_CONTENT_URI.buildUpon().
                appendPath(PATH_TRANSACCIONES_DETALLE).appendPath(LJOIN_CATALOGO_PRODUCTOS).build();


        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TRANSACCIONES_DETALLE;


        public static final String TABLE_NAME = "transaccion_detalle";

        public static final String COLUMN_AUTORIZACION_PT = "autorizacion_pt";
        public static final String COLUMN_CANTIDAD = "cantidad";
        public static final String COLUMN_CANTIDAD_PROMO = "cantidad_promo";
        public static final String COLUMN_DESCUENTO = "descuento";
        public static final String COLUMN_FECHA_ACTUALIZACION = "fecha_actualizacion";
        public static final String COLUMN_ID_CAMPANA = "id_campana";
        public static final String COLUMN_ID_PRODUCTO = "id_producto";
        public static final String COLUMN_ID_VENTA = "id_venta";
        public static final String COLUMN_ID_VENTA_DETALLE = "id_venta_detalle";
        public static final String COLUMN_IMPORTE_COMPRA = "importe_compra";
        public static final String COLUMN_IMPORTE_VENTA = "importe_venta";
        public static final String COLUMN_IS_PREMIO = "is_premio";
        public static final String COLUMN_REFERENCIA = "referencia";
        public static final String COLUMN_SOBRE_CARGO = "sobre_cargo";

        private TransaccionDetalleEntry() {
        }

        public static Uri buildAddressUri(final long idProducto) {
            return ContentUris.withAppendedId(CONTENT_URI, idProducto);
        }
    }

    public static final class CatalogoProductosEntry implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_CATALOGO_PRODUCTOS).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_CATALOGO_PRODUCTOS;

        public static final String TABLE_NAME = "catalogo_productos";

        public static final String COLUMN_DESCRIPCION = "descripcion";
        public static final String COLUMN_DESVIACION = "desviacion";
        public static final String COLUMN_EXISTENCIA = "existencia";
        public static final String COLUMN_FECHA_SYNC = "fecha_sync";
        public static final String COLUMN_ID_CATEGORIA = "id_categoria";
        public static final String COLUMN_ID_MARCA = "id_marca";
        public static final String COLUMN_ID_PRODUCTO = "id_producto";
        public static final String COLUMN_ID_TIPO_PRODUCTO = "id_tipo_producto";
        public static final String COLUMN_ID_UNIDAD = "id_unidad";
        public static final String COLUMN_MINIMO = "minimo";
        public static final String COLUMN_PRECIO_COMPRA = "precio_compra";
        public static final String COLUMN_PRECIO_VENTA = "precio_venta";
        public static final String COLUMN_PRECIO_VENTA_MAYOREO = "precio_venta_mayoreo";
        public static final String COLUMN_PROMEDIO = "promedio";
        public static final String COLUMN_SKU = "sku";
        public static final String COLUMN_SUGERIDO = "sugerido";
        public static final String COLUMN_SYNC = "sync";
        public static final String COLUMN_TECHO = "techo";
        public static final String COLUMN_TOTAL_VENTAS = "total_ventas";
        public static final String COLUMN_DB_STATE = "db_state";
        public static final String COLUMN_IMAGE_PREVIEW = "image_preview";
        public static final String COLUMN_LOGO = "logo";
        public static final String COLUMN_CUSTOM = "custom";

        private CatalogoProductosEntry() {
        }

        public static Uri buildAddressUri(final long idProducto) {
            return ContentUris.withAppendedId(CONTENT_URI, idProducto);
        }
    }
}
