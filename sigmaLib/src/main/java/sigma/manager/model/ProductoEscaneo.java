package sigma.manager.model;

import java.math.BigDecimal;

public class ProductoEscaneo {

    private final String nombre;
    private final String precionUnitario;
    private final String precioCompra;
    private final Integer idProducto;
    private final String sku;
    int cantidad = 1;

    public ProductoEscaneo(final String nombre, final String precionUnitario, final Integer idProducto, final String sku, final String precioCompra) {
        this.nombre = nombre;
        this.precionUnitario = precionUnitario;
        this.idProducto = idProducto;
        this.sku = sku;
        this.precioCompra = precioCompra;
    }

    public void setCantidad(final int cantidad) {
        this.cantidad = cantidad;
    }

    public int getCantidad() {
        return cantidad;
    }

    public BigDecimal getPrecioNeto() {
        final BigDecimal precioUnitario = new BigDecimal(precionUnitario);
        return precioUnitario.multiply(new BigDecimal(cantidad));
    }

    public BigDecimal getPrecionUnitario() {
        return new BigDecimal(precionUnitario);
    }

    public String getNombre() {
        return nombre;
    }

    public Integer getIdProducto() {
        return idProducto;
    }

    public String getSku() {
        return sku;
    }

    public String getPrecioCompra() {
        return precioCompra;
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof ProductoEscaneo) {
            return idProducto.equals(((ProductoEscaneo) obj).idProducto);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return idProducto;
    }
}
