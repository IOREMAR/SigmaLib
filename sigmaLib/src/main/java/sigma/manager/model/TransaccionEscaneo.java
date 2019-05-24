package sigma.manager.model;

import java.io.Serializable;

public class TransaccionEscaneo implements Serializable {
    private long fecha;
    private String codigoOper;
    private String proCod;
    private String descMenu;
    private String importe;
    private String moneda;
    private String importeOrigen;
    private int fee;
    private String refLocal;
    private long stan;
    private String refRemota;
    private String refCliente;
    private String descProducto;
    private int turno;
    private int contabilizar;
    private int zincronizada;
    private int cuenta;
    private int dia;
    private String total;
    private String cashBack;
    private String impuesto;
    private String propina;
    private String tipoTarjeta;
    private String medioPago;
    private String cantidadPagada;

    public long getFecha() {
        return fecha;
    }

    public void setFecha(final long fecha) {
        this.fecha = fecha;
    }

    public String getCodigoOper() {
        return codigoOper;
    }

    public void setCodigoOper(final String codigoOper) {
        this.codigoOper = codigoOper;
    }

    public String getProCod() {
        return proCod;
    }

    public void setProCod(final String proCod) {
        this.proCod = proCod;
    }

    public String getDescMenu() {
        return descMenu;
    }

    public void setDescMenu(final String descMenu) {
        this.descMenu = descMenu;
    }

    public String getImporte() {
        return importe;
    }

    public void setImporte(final String importe) {
        this.importe = importe;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(final String moneda) {
        this.moneda = moneda;
    }

    public String getImporteOrigen() {
        return importeOrigen;
    }

    public void setImporteOrigen(final String importeOrigen) {
        this.importeOrigen = importeOrigen;
    }

    public int getFee() {
        return fee;
    }

    public void setFee(final int fee) {
        this.fee = fee;
    }

    public String getRefLocal() {
        return refLocal;
    }

    public void setRefLocal(final String refLocal) {
        this.refLocal = refLocal;
    }

    public long getStan() {
        return stan;
    }

    public void setStan(final long stan) {
        this.stan = stan;
    }

    public String getRefRemota() {
        return refRemota;
    }

    public void setRefRemota(final String refRemota) {
        this.refRemota = refRemota;
    }

    public String getRefCliente() {
        return refCliente;
    }

    public void setRefCliente(final String refCliente) {
        this.refCliente = refCliente;
    }

    public String getDescProducto() {
        return descProducto;
    }

    public void setDescProducto(final String descProducto) {
        this.descProducto = descProducto;
    }

    public int getTurno() {
        return turno;
    }

    public void setTurno(final int turno) {
        this.turno = turno;
    }

    public int getContabilizar() {
        return contabilizar;
    }

    public void setContabilizar(final int contabilizar) {
        this.contabilizar = contabilizar;
    }

    public int getZincronizada() {
        return zincronizada;
    }

    public void setZincronizada(final int zincronizada) {
        this.zincronizada = zincronizada;
    }

    public int getCuenta() {
        return cuenta;
    }

    public void setCuenta(final int cuenta) {
        this.cuenta = cuenta;
    }

    public int getDia() {
        return dia;
    }

    public void setDia(final int dia) {
        this.dia = dia;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(final String total) {
        this.total = total;
    }

    public String getCashBack() {
        return cashBack;
    }

    public void setCashBack(final String cashBack) {
        this.cashBack = cashBack;
    }

    public String getImpuesto() {
        return impuesto;
    }

    public void setImpuesto(final String impuesto) {
        this.impuesto = impuesto;
    }

    public String getPropina() {
        return propina;
    }

    public void setPropina(final String propina) {
        this.propina = propina;
    }

    public String getTipoTarjeta() {
        return tipoTarjeta;
    }

    public void setTipoTarjeta(final String tipoTarjeta) {
        this.tipoTarjeta = tipoTarjeta;
    }

    public String getMedioPago() {
        return medioPago;
    }

    public void setMedioPago(final String medioPago) {
        this.medioPago = medioPago;
    }

    public void setCantidadPagada(final String cantidadPagada) {
        this.cantidadPagada = cantidadPagada;
    }

    public String getCantidadPagada() {
        return cantidadPagada;
    }
}
