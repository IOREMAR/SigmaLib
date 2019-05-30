package sigma.secciones.operaciones.datos;

//import android.text.TextUtils;

import com.pagatodo.sigmamanager.Instance.ApiInstance;

import net.fullcarga.android.api.data.DataOpTarjeta;
import net.fullcarga.android.api.data.respuesta.AbstractRespuesta;
import net.fullcarga.android.api.data.respuesta.Respuesta;
import net.fullcarga.android.api.exc.RespuestaException;
import net.fullcarga.android.api.listener.DescargaListener;
import net.fullcarga.android.api.oper.TipoOperacion;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import sigma.general.interfaces.InteractorListener;
import sigma.manager.AppLogger;
import sigma.manager.StanProviderMock;

public class DatosOperacion {

    private static final String TAG = DatosOperacion.class.getSimpleName();

    private final String idPeticion;

    private TipoOperacion tipoOperacion;

    private final Respuesta respuestaAnterior;

    private InteractorListener<Object> listener;

    private String procod;

    private List<String> fields;

    private int cierreTurno;

    private final boolean negativo = false;

    private DataOpTarjeta datosTarjeta;

    private Long stan;

    private BigDecimal importe;

    private String refCliente;

    private String refLocal;

    private String zipName ;

    private DescargaListener descargaListener;

    private Map<String, Long> iconosCRC;

    public DatosOperacion(String idPeticion, TipoOperacion tipoOperacion, Respuesta respuestaAnterior, InteractorListener<Object> listener, String procod, List<String> fields) {
        this.idPeticion = idPeticion;
        this.tipoOperacion = tipoOperacion;
        this.respuestaAnterior = respuestaAnterior;
        this.listener = listener;
        this.procod = procod;
        this.fields = fields;
    }

    public String getZipName() {
        return zipName;
    }

    public Map<String, Long> getIconosCRC() {
        return iconosCRC;
    }

    public void setIconosCRC(Map<String, Long> iconosCRC) {
        this.iconosCRC = iconosCRC;
    }

    public void setZipName(String zipName) {
        this.zipName = zipName;
    }

    public DescargaListener getDescargaListener() {
        return descargaListener;
    }

    public void setDescargaListener(DescargaListener descargaListener) {
        this.descargaListener = descargaListener;
    }

    public TipoOperacion getTipoOperacion() {
        return tipoOperacion;
    }

    public Respuesta getRespuestaAnterior() {
        return respuestaAnterior;
    }

    public InteractorListener<Object> getListener() {
        return listener;
    }

    public void setListener(final InteractorListener<Object> listener) {
        this.listener = listener;
    }

    public String getProcod() {
        return procod;
    }

    public void setTipoOperacion(final TipoOperacion tipoOperacion) {
        this.tipoOperacion = tipoOperacion;
    }

    public String getIdPeticion() {
        return idPeticion;
    }

    public void setProcod(final String procod) {
        this.procod = procod;
    }

    public String getRefLocal() {
        return refLocal;
    }

    public void setRefLocal(final String refLocal) {
        this.refLocal = refLocal;
    }

    public BigDecimal getImporte() {
        return importe;
    }

    public void setImporte(final BigDecimal importe) {
        this.importe = importe;
    }

    public List<String> getFields() {
        return fields;
    }

    public void setFields(final List<String> fields) {
        this.fields = fields;
    }

    public int getCierreTurno() {
        return cierreTurno;
    }

    public boolean isNegativo() {
        return negativo;
    }

    public DataOpTarjeta getDatosTarjeta() {
        return datosTarjeta;
    }

    public void setDatosTarjeta(final DataOpTarjeta datosTarjeta) {
        this.datosTarjeta = datosTarjeta;
    }

    public String getRefCliente() {
        return refCliente;
    }

    public void setRefCliente(final String refCliente) {
        this.refCliente = refCliente;
    }

    public Long getStan() {
        return stan;
    }

    public void setStan(final Long stan) {
        this.stan = stan;
    }

    public void realizarOperacion (){
        Thread transactionTask = new Thread()  {
            @Override
            public void run() {
                try {
                    final Transacciones transacciones = new Transacciones(DatosOperacion.this);

                    Object respuesta =  transacciones.callOperation();
                    if (respuesta instanceof AbstractRespuesta) {
                        if (((AbstractRespuesta)respuesta).getOperacionSiguiente().isOperNextRequired()) {
                            AbstractRespuesta respuestaOperacion = ((AbstractRespuesta) respuesta);
                            ApiInstance.getInstance().setOperacion(new DatosOperacion("", DatosOperacion.this.tipoOperacion.equals(TipoOperacion.LOGIN) ? TipoOperacion.INIT_LIST : respuestaOperacion.getOperacionSiguiente().getTipoOperacionNext(), null, listener, "", Collections.EMPTY_LIST));
                        }else {
                            ApiInstance.getInstance().setOperacion(null);
                        }
                    } else {
                        throw new Throwable("Ocurrió un Error, Intente de Nuevo");
                    }
                    listener.onResponse(respuesta);
                } catch ( Throwable  thrExe ){
                    AppLogger.LOGGER.throwing(TAG,1,thrExe,thrExe.getMessage());
                    listener.onResponse(thrExe);
                }

            }
        };
        transactionTask.start();

    }

    @Override
    public String toString() {
        return "DatosOperacion{" +
                "idPeticion='" + idPeticion + '\'' +
                ", datosSesion=" + ApiInstance.getInstance().getDatosSesion() +
                ", tipoOperacion=" + tipoOperacion +
                ", respuestaAnterior=" + respuestaAnterior +
                ", listener=" + listener +
                ", procod='" + procod + '\'' +
                ", fields=" +  getJoinedFields() +
                ", cierreTurno=" + cierreTurno +
                ", negativo=" + negativo +
                '}';
    }

    private String getJoinedFields() {
        final StringBuilder builder = new StringBuilder();

        if (fields != null && fields.size() > 0) {
            final int size = fields.size();

            int iteration = 1;

            for (final String item: fields){
                builder.append(item);

                if (iteration < size) {
                    builder.append(", ");
                    iteration++;
                }
            }
        }

        return builder.toString();
    }

    private void stan(){
        final Long stan = new StanProviderMock().getNextStan();
    }

}
