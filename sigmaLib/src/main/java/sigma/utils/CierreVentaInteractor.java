package sigma.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import sigma.manager.localdb.LocalDBContract;

public class CierreVentaInteractor {

    private CierreVentaInteractor() {

    }

    public static List<ModelCierreVenta> callCierreVenta(final Context contexto, final Long stan) {
        final List<ModelCierreVenta> modelo;

        modelo = getCierreVenta(contexto.getContentResolver(), stan);

        return modelo;
    }

    public static String callTotalVenta(final Context contexto, final Long stan) {
        final String total;
        total = getTotalVenta(contexto.getContentResolver(), stan);
        return total;
    }

    public static String getTotalVenta(final ContentResolver contentResolver, final Long stan) {

        String total = "0";
        final String[] projection = {
                LocalDBContract.TransaccionesEntry.COLUMN_IMPORTE
        };
        final String selection = LocalDBContract.TransaccionesEntry.COLUMN_STAN + " = ?";
        final String[] selectionArgs = {stan.toString()};

        final Cursor cursor = contentResolver.query(LocalDBContract.TransaccionesEntry.CONTENT_URI, projection, selection, selectionArgs, null);
        if (cursor.moveToFirst()) {
            total = cursor.getString(0);

        }
        return total;
    }

    public static List<ModelCierreVenta> getCierreVenta(final ContentResolver contentResolver, final Long stan) {
        final List<ModelCierreVenta> models = new ArrayList<>();

        final String[] projection = {
                "t2." + LocalDBContract.CatalogoProductosEntry.COLUMN_DESCRIPCION,
                "t2." + LocalDBContract.CatalogoProductosEntry.COLUMN_PRECIO_VENTA,
                "t1." + LocalDBContract.TransaccionDetalleEntry.COLUMN_AUTORIZACION_PT,
                "t1." + LocalDBContract.TransaccionDetalleEntry.COLUMN_CANTIDAD,
                "t1." + LocalDBContract.TransaccionDetalleEntry.COLUMN_CANTIDAD_PROMO,
                "t1." + LocalDBContract.TransaccionDetalleEntry.COLUMN_DESCUENTO,
                "t1." + LocalDBContract.TransaccionDetalleEntry.COLUMN_FECHA_ACTUALIZACION,
                "t1." + LocalDBContract.TransaccionDetalleEntry.COLUMN_ID_CAMPANA,
                "t1." + LocalDBContract.TransaccionDetalleEntry.COLUMN_ID_PRODUCTO,
                "t1." + LocalDBContract.TransaccionDetalleEntry.COLUMN_ID_VENTA,
                "t1." + LocalDBContract.TransaccionDetalleEntry.COLUMN_ID_VENTA_DETALLE,
                "t1." + LocalDBContract.TransaccionDetalleEntry.COLUMN_IMPORTE_COMPRA,
                "t1." + LocalDBContract.TransaccionDetalleEntry.COLUMN_IMPORTE_VENTA,
                "t1." + LocalDBContract.TransaccionDetalleEntry.COLUMN_IS_PREMIO,
                "t1." + LocalDBContract.TransaccionDetalleEntry.COLUMN_REFERENCIA,
                "t1." + LocalDBContract.TransaccionDetalleEntry.COLUMN_SOBRE_CARGO,

        };
        final String selection = LocalDBContract.TransaccionDetalleEntry.COLUMN_ID_VENTA + " = ?";
        final String[] selectionArgs = {stan.toString()};

        final Cursor cursor = contentResolver.query(LocalDBContract.TransaccionDetalleEntry.CONTENT_URI_LJOIN_CATALOGO_PRODUCTOS, projection, selection, selectionArgs, null);

        while (cursor.moveToNext()) {
            models.add(createModelVenta(cursor));
        }
        return models;
    }

    private static CierreVentaInteractor.ModelCierreVenta createModelVenta(final Cursor cursor) {

        return new CierreVentaInteractor.ModelCierreVenta(cursor);
    }

    public static class ModelCierreVenta {

        private final String descripcion;
        private final String precioVenta;
        private final String autorizacionPt;
        private final String cantidad;
        private final String cantidadPromo;
        private final String descuento;
        private final String fechaActualizacion;
        private final int idCampana;
        private final int idProducto;
        private final int idVenta;
        private final int idVentaDetalle;
        private final String importeCompra;
        private final String importeVenta;
        private final int isPremio;
        private final String referencia;
        private final String sobrecargo;

        public ModelCierreVenta(final Cursor cursor) {

            this.precioVenta = cursor.getString(1);
            this.descripcion = cursor.getString(0);
            this.autorizacionPt = cursor.getString(2);
            this.cantidad = cursor.getString(3);
            this.cantidadPromo = cursor.getString(4);
            this.descuento = cursor.getString(5);
            this.fechaActualizacion = cursor.getString(6);
            this.idCampana = cursor.getInt(7);
            this.idProducto = cursor.getInt(8);
            this.idVenta = cursor.getInt(9);
            this.idVentaDetalle = cursor.getInt(10);
            this.importeCompra = cursor.getString(11);
            this.importeVenta = cursor.getString(12);
            this.isPremio = cursor.getInt(13);
            this.referencia = cursor.getString(14);
            this.sobrecargo = cursor.getString(15);
        }

        public String getDescripcion() {
            return descripcion;
        }

        public String getPrecioVenta() {
            return precioVenta;
        }

        public String getAutorizacionPt() {
            return autorizacionPt;
        }

        public String getCantidad() {
            return cantidad;
        }

        public String getCantidadPromo() {
            return cantidadPromo;
        }

        public String getDescuento() {
            return descuento;
        }

        public String getFechaActualizacion() {
            return fechaActualizacion;
        }

        public int getIdCampana() {
            return idCampana;
        }

        public int getIdProducto() {
            return idProducto;
        }

        public int getIdVenta() {
            return idVenta;
        }

        public int getIdVentaDetalle() {
            return idVentaDetalle;
        }

        public String getImporteCompra() {
            return importeCompra;
        }

        public String getImporteVenta() {
            return importeVenta;
        }

        public int getIsPremio() {
            return isPremio;
        }

        public String getReferencia() {
            return referencia;
        }

        public String getSobrecargo() {
            return sobrecargo;
        }
    }
}
