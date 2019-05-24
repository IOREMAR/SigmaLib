package sigma.manager;

import android.database.Cursor;

public class Model {

    private String codigoOper ;
    private Long fecha;
    private String refLocal;
    private String refCliente;
    private String importe;
    private String descProducto;

    public Model(final Cursor cursor) {

        this.codigoOper = cursor.getString(0);
        this.fecha = cursor.getLong(1);
        this.refLocal = cursor.getString(2);
        this.refCliente = cursor.getString(3);
        this.importe = cursor.getString(4);
        this.descProducto = cursor.getString(5);
    }


    public  String getCodigoOper() {
        return codigoOper;
    }

    public  Long getFecha() {
        return fecha;
    }

    public  String getRefLocal() {
        return refLocal;
    }

    public  String getRefCliente() {
        return refCliente;
    }

    public  String getImporte() {
        return importe;
    }

    public  String getDescProducto() {
        return descProducto;
    }
}