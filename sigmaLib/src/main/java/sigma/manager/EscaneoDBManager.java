package sigma.manager;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Locale;


import sigma.dbgen.tables.pojos.Transacciones;
import sigma.manager.localdb.LocalDBContract;
import sigma.manager.localdb.LocalDBContract.TransaccionDetalleEntry;
import sigma.manager.localdb.LocalDBContract.CatalogoProductosEntry;
import sigma.manager.localdb.LocalDBContract.TransaccionesEntry;
import sigma.manager.model.ProductoEscaneo;
import sigma.manager.model.TransaccionEscaneo;



public final class EscaneoDBManager {
    private static final String CODIGO_GRANEL = "20";
    private static final String NUMERO_AGENTE = "12227";

    private EscaneoDBManager() {
        throw new IllegalStateException("Utility class");
    }

    public static ProductoEscaneo getProducto(final ContentResolver contentResolver, final String sku) {
        final String[] projection = {
                CatalogoProductosEntry.COLUMN_DESCRIPCION,
                CatalogoProductosEntry.COLUMN_PRECIO_VENTA,
                CatalogoProductosEntry.COLUMN_ID_PRODUCTO,
                CatalogoProductosEntry.COLUMN_SKU,
                CatalogoProductosEntry.COLUMN_PRECIO_COMPRA
        };
        final String selection = CatalogoProductosEntry.COLUMN_SKU + "=?";
        final String[] selectionArgs = {sku};
        final Cursor cursor = contentResolver.query(CatalogoProductosEntry.CONTENT_URI, projection, selection, selectionArgs, null);
        if (cursor.moveToFirst()) {
            return new ProductoEscaneo(
                    cursor.getString(0),
                    cursor.getString(1),
                    cursor.getInt(2),
                    cursor.getString(3),
                    cursor.getString(4)
            );
        }
        return null;
    }

    public static List<ProductoEscaneo> getProducts(final ContentResolver contentResolver) {
        final String[] projection = {
                CatalogoProductosEntry.COLUMN_DESCRIPCION,
                CatalogoProductosEntry.COLUMN_PRECIO_VENTA,
                CatalogoProductosEntry.COLUMN_ID_PRODUCTO,
                CatalogoProductosEntry.COLUMN_SKU,
                CatalogoProductosEntry.COLUMN_PRECIO_COMPRA
        };
        final List<ProductoEscaneo> models = new ArrayList<>();
        final Cursor cursor = contentResolver.query(CatalogoProductosEntry.CONTENT_URI, projection, null, null, null);
        while (cursor.moveToNext()) {
            models.add(
                    createProductoEscaneoModel(
                            cursor.getString(0),
                            cursor.getString(1),
                            cursor.getInt(2),
                            cursor.getString(3),
                            cursor.getString(4)
                    )
            );
        }
        return models;
    }

    private static ProductoEscaneo createProductoEscaneoModel(final String var1, final String var2, final int var3, final String var4, final String var5) {

        return new ProductoEscaneo(var1, var2, var3, var4, var5);
    }

    public static String getNewSKU(final ContentResolver contentResolver) {
        final String[] projection = {
                CatalogoProductosEntry.COLUMN_SKU
        };
        final String selection = LocalDBContract.CatalogoProductosEntry.COLUMN_SKU + " LIKE '20%'";
        final String sortOrder = CatalogoProductosEntry.COLUMN_SKU + " DESC LIMIT 1";
        final Cursor cursor = contentResolver.query(CatalogoProductosEntry.CONTENT_URI, projection, selection, null, sortOrder);
        if (cursor.moveToFirst()) {
            final String lastSku = cursor.getString(0);
            cursor.close();
            final String skuConsecutivo = lastSku.substring(lastSku.length() - 6, lastSku.length());
            final int consecutivo = Integer.valueOf(skuConsecutivo) + 1;
            return CODIGO_GRANEL + NUMERO_AGENTE + String.format(Locale.getDefault(), "%06d", consecutivo);
        } else {
            return CODIGO_GRANEL + NUMERO_AGENTE + String.format(Locale.getDefault(), "%06d", 0);
        }
    }

    public static Uri agregarProduct(final ContentResolver contentResolver, final String nombre, final String precioVenta, final String precioCompra, final String sku) {
        final ContentValues contentValues = new ContentValues();
        contentValues.put(CatalogoProductosEntry.COLUMN_DESCRIPCION, nombre);
        contentValues.put(CatalogoProductosEntry.COLUMN_PRECIO_VENTA, precioVenta);
        contentValues.put(CatalogoProductosEntry.COLUMN_PRECIO_COMPRA, precioCompra);
        contentValues.put(CatalogoProductosEntry.COLUMN_SKU, sku);

        return contentResolver.insert(CatalogoProductosEntry.CONTENT_URI, contentValues);
    }

    public static boolean insertTransaccion(final ContentResolver contentResolver, final List<ProductoEscaneo> productoEscaneoList, final TransaccionEscaneo transaccionEscaneo) {
        final ContentValues transaccionCV = new ContentValues();
        transaccionCV.put(TransaccionesEntry.COLUMN_FECHA, transaccionEscaneo.getFecha());
        transaccionCV.put(TransaccionesEntry.COLUMN_CODIGO_OPER, transaccionEscaneo.getCodigoOper());
        transaccionCV.put(TransaccionesEntry.COLUMN_PRO_COD, transaccionEscaneo.getProCod());
        transaccionCV.put(TransaccionesEntry.COLUMN_DESC_MENU, transaccionEscaneo.getDescMenu());
        transaccionCV.put(TransaccionesEntry.COLUMN_IMPORTE, transaccionEscaneo.getImporte());
        transaccionCV.put(TransaccionesEntry.COLUMN_MONEDA, transaccionEscaneo.getMoneda());
        transaccionCV.put(TransaccionesEntry.COLUMN_IMPORTE_ORIGEN, transaccionEscaneo.getImporteOrigen());
        transaccionCV.put(TransaccionesEntry.COLUMN_FEE, transaccionEscaneo.getFee());
        transaccionCV.put(TransaccionesEntry.COLUMN_REF_LOCAL, transaccionEscaneo.getRefLocal());
        transaccionCV.put(TransaccionesEntry.COLUMN_STAN, transaccionEscaneo.getStan());
        transaccionCV.put(TransaccionesEntry.COLUMN_REF_REMOTA, transaccionEscaneo.getRefRemota());
        transaccionCV.put(TransaccionesEntry.COLUMN_REF_CLIENTE, transaccionEscaneo.getRefCliente());
        transaccionCV.put(TransaccionesEntry.COLUMN_DESC_PRODUCTO, transaccionEscaneo.getDescProducto());
        transaccionCV.put(TransaccionesEntry.COLUMN_TURNO, transaccionEscaneo.getTurno());
        transaccionCV.put(TransaccionesEntry.COLUMN_CONTABILIZAR, transaccionEscaneo.getContabilizar());
        transaccionCV.put(TransaccionesEntry.COLUMN_ZINCRONIZADA, transaccionEscaneo.getZincronizada());
        transaccionCV.put(TransaccionesEntry.COLUMN_CUENTA, transaccionEscaneo.getCuenta());
        transaccionCV.put(TransaccionesEntry.COLUMN_DIA, transaccionEscaneo.getDia());
        transaccionCV.put(TransaccionesEntry.COLUMN_TOTAL, transaccionEscaneo.getTotal());
        transaccionCV.put(TransaccionesEntry.COLUMN_CASHBACK, transaccionEscaneo.getCashBack());
        transaccionCV.put(TransaccionesEntry.COLUMN_IMPUESTO, transaccionEscaneo.getImpuesto());
        transaccionCV.put(TransaccionesEntry.COLUMN_PROPINA, transaccionEscaneo.getPropina());
        transaccionCV.put(TransaccionesEntry.COLUMN_TIPO_TARJETA, transaccionEscaneo.getTipoTarjeta());
        transaccionCV.put(TransaccionesEntry.COLUMN_MEDIO_PAGO, transaccionEscaneo.getMedioPago());
        contentResolver.insert(TransaccionesEntry.CONTENT_URI, transaccionCV);
        inserTransaccionDetalle(contentResolver, transaccionEscaneo.getStan(), productoEscaneoList);

        return true;
    }

    public static boolean insertTransaccion(final ContentResolver contentResolver, final Transacciones transaccionEscaneo) {
        final ContentValues transaccionCV = new ContentValues();
        transaccionCV.put(TransaccionesEntry.COLUMN_FECHA, transaccionEscaneo.getFecha());
        transaccionCV.put(TransaccionesEntry.COLUMN_CODIGO_OPER, transaccionEscaneo.getCodigooper());
        transaccionCV.put(TransaccionesEntry.COLUMN_PRO_COD, transaccionEscaneo.getProcod());
        transaccionCV.put(TransaccionesEntry.COLUMN_DESC_MENU, transaccionEscaneo.getDescmenu());
        transaccionCV.put(TransaccionesEntry.COLUMN_IMPORTE, transaccionEscaneo.getImporte());
        transaccionCV.put(TransaccionesEntry.COLUMN_MONEDA, transaccionEscaneo.getMoneda());
        transaccionCV.put(TransaccionesEntry.COLUMN_IMPORTE_ORIGEN, transaccionEscaneo.getImporteorigen());
        transaccionCV.put(TransaccionesEntry.COLUMN_FEE, transaccionEscaneo.getFee());
        transaccionCV.put(TransaccionesEntry.COLUMN_REF_LOCAL, transaccionEscaneo.getReflocal());
        transaccionCV.put(TransaccionesEntry.COLUMN_STAN, transaccionEscaneo.getStan());
        transaccionCV.put(TransaccionesEntry.COLUMN_REF_REMOTA, "");
        transaccionCV.put(TransaccionesEntry.COLUMN_REF_CLIENTE, "");
        transaccionCV.put(TransaccionesEntry.COLUMN_DESC_PRODUCTO, transaccionEscaneo.getDescproducto());
        transaccionCV.put(TransaccionesEntry.COLUMN_TURNO, transaccionEscaneo.getTurno());
        transaccionCV.put(TransaccionesEntry.COLUMN_CONTABILIZAR, transaccionEscaneo.getContabilizar());
        transaccionCV.put(TransaccionesEntry.COLUMN_ZINCRONIZADA, transaccionEscaneo.getZincronizada());
        transaccionCV.put(TransaccionesEntry.COLUMN_CUENTA, transaccionEscaneo.getCuenta());
        transaccionCV.put(TransaccionesEntry.COLUMN_DIA, transaccionEscaneo.getFecha());
        transaccionCV.put(TransaccionesEntry.COLUMN_TOTAL, transaccionEscaneo.getImporte());
        transaccionCV.put(TransaccionesEntry.COLUMN_CASHBACK, "");
        transaccionCV.put(TransaccionesEntry.COLUMN_IMPUESTO, "");
        transaccionCV.put(TransaccionesEntry.COLUMN_PROPINA, "");
        transaccionCV.put(TransaccionesEntry.COLUMN_TIPO_TARJETA, "");
        transaccionCV.put(TransaccionesEntry.COLUMN_MEDIO_PAGO, transaccionEscaneo.getTipoTarjeta());
        contentResolver.insert(TransaccionesEntry.CONTENT_URI, transaccionCV);
        inserTransaccionDetalle(contentResolver, transaccionEscaneo.getStan().longValue(), Collections.<ProductoEscaneo>emptyList());

        return true;
    }

    public static int inserTransaccionDetalle(final ContentResolver contentResolver, final Long stan, final List<ProductoEscaneo> productoEscaneoList) {
        final List<ContentValues> contentValues = new ArrayList<>();
        for (final ProductoEscaneo productoEscaneo : productoEscaneoList) {
            contentValues.add(createModelProdcutoEscaneo(productoEscaneo, stan));
        }

        return contentResolver.bulkInsert(TransaccionDetalleEntry.CONTENT_URI, contentValues.toArray(new ContentValues[contentValues.size()]));
    }

    private static ContentValues createModelProdcutoEscaneo(final ProductoEscaneo productoEscaneo, final Long stan) {

        final ContentValues transaccionDetalleCV = new ContentValues();
        transaccionDetalleCV.put(TransaccionDetalleEntry.COLUMN_AUTORIZACION_PT, "");
        transaccionDetalleCV.put(TransaccionDetalleEntry.COLUMN_CANTIDAD, productoEscaneo.getCantidad());
        transaccionDetalleCV.put(TransaccionDetalleEntry.COLUMN_CANTIDAD_PROMO, "0");
        transaccionDetalleCV.put(TransaccionDetalleEntry.COLUMN_DESCUENTO, "0");
        transaccionDetalleCV.put(TransaccionDetalleEntry.COLUMN_FECHA_ACTUALIZACION, Calendar.getInstance().getTimeInMillis());
        transaccionDetalleCV.put(TransaccionDetalleEntry.COLUMN_ID_CAMPANA, 0);
        transaccionDetalleCV.put(TransaccionDetalleEntry.COLUMN_ID_PRODUCTO, productoEscaneo.getIdProducto());
        transaccionDetalleCV.put(TransaccionDetalleEntry.COLUMN_ID_VENTA, stan);
        transaccionDetalleCV.put(TransaccionDetalleEntry.COLUMN_IMPORTE_COMPRA, "0");
        transaccionDetalleCV.put(TransaccionDetalleEntry.COLUMN_IMPORTE_VENTA, productoEscaneo.getPrecioNeto().toString());
        transaccionDetalleCV.put(TransaccionDetalleEntry.COLUMN_IS_PREMIO, 0);
        transaccionDetalleCV.put(TransaccionDetalleEntry.COLUMN_REFERENCIA, productoEscaneo.getSku());

        return transaccionDetalleCV;
    }

    public static int update(final ContentResolver contentResolver, final ProductoEscaneo producto, final String nuevoPrecioVenta, final String nuevoPrecioCompra) {
        final ContentValues transaccionCV = new ContentValues();
        transaccionCV.put(CatalogoProductosEntry.COLUMN_PRECIO_VENTA, nuevoPrecioVenta);
        transaccionCV.put(CatalogoProductosEntry.COLUMN_PRECIO_COMPRA, nuevoPrecioCompra);
        return contentResolver.update(CatalogoProductosEntry.CONTENT_URI, transaccionCV, "id_producto=" + producto.getIdProducto(), null);
    }
}
