package sigma.manager;

import android.content.ContentResolver;
import android.database.Cursor;
import static sigma.manager.localdb.LocalDBContract.TransaccionesEntry;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class ReporteVentasInteractor {

    private static final Map<TipoReporte, String> selectionMap;

    static {
        selectionMap = new HashMap<>();
        selectionMap.put(TipoReporte.REPORTE_VENTAS, TransaccionesEntry.COLUMN_MEDIO_PAGO+"==1");
        selectionMap.put(TipoReporte.REPORTE_PCI, TransaccionesEntry.COLUMN_MEDIO_PAGO+"=?");
        selectionMap.put(TipoReporte.REPORTE_PCI_DIA, TransaccionesEntry.COLUMN_MEDIO_PAGO+"=? AND " + TransaccionesEntry.COLUMN_TURNO + "=? AND " + TransaccionesEntry.COLUMN_CODIGO_OPER + "='V'");
    }

    public enum TipoReporte{
        REPORTE_VENTAS, REPORTE_PCI, REPORTE_PCI_DIA;
    }

    private static final String[] PROJECTION = {
            TransaccionesEntry.COLUMN_CODIGO_OPER,
            TransaccionesEntry.COLUMN_FECHA,
            TransaccionesEntry.COLUMN_REF_LOCAL,
            TransaccionesEntry.COLUMN_REF_CLIENTE,
            TransaccionesEntry.COLUMN_IMPORTE,
            TransaccionesEntry.COLUMN_DESC_PRODUCTO
    };

    private ReporteVentasInteractor() {

    }

    public static List<Model> getVentas(final ContentResolver contentResolver, TipoReporte tipoReporte, String[] selectionArguments) {

        final List<Model> models = new ArrayList<>();

        String selection = selectionMap.get(tipoReporte);

        if (selectionArguments!=null) {
            for (String arg: selectionArguments) {
                selection = selection.replaceFirst("\\?", arg);
            }
        }

        final Cursor cursor = contentResolver.query(TransaccionesEntry.CONTENT_URI, PROJECTION, null, null, TransaccionesEntry.COLUMN_FECHA + " DESC");
        while (cursor != null && cursor.moveToNext()) {
            models.add(createNewModelGetVentas(
                    cursor
            ));
        }

        return models;
    }

    private static Model createNewModelGetVentas(final Cursor cursor) {
        return new Model(cursor);
    }


}

