package sigma.secciones.operaciones.datos;

;
import com.pagatodo.sigmamanager.Instance.ApiInstance;

import net.fullcarga.android.api.ApiFullcargaAndroid;
import net.fullcarga.android.api.ApiFullcargaAndroidPCI;
import net.fullcarga.android.api.data.respuesta.AbstractRespuesta;
import net.fullcarga.android.api.data.respuesta.RespuestaConsultaX;
import net.fullcarga.android.api.data.respuesta.RespuestaConsultaZ;
import net.fullcarga.android.api.data.respuesta.RespuestaDevolucion;
import net.fullcarga.android.api.data.respuesta.RespuestaListArchivos;
import net.fullcarga.android.api.data.respuesta.RespuestaLogin;
import net.fullcarga.android.api.data.respuesta.RespuestaSaldo;
import net.fullcarga.android.api.data.respuesta.RespuestaUpdate;
import net.fullcarga.android.api.data.respuesta.RespuestaVenta;

import java.util.Date;
import java.util.concurrent.Callable;

import sigma.manager.AppLogger;

public class Transacciones   {

    private static final String TAG = TransactionAction.class.getSimpleName();

    private DatosOperacion datosOperacion;

    public Transacciones(DatosOperacion datosOperacion) {
        this.datosOperacion = datosOperacion;
    }

    public void setDatosOperacion(DatosOperacion datosOperacion) {
        this.datosOperacion = datosOperacion;
    }

    private TransactionAction ejecutarOperacion (){
        switch (datosOperacion.getTipoOperacion()){
            case LOGIN:
                return login(datosOperacion);
            case PCI_LOGIN:
                return loginPCI(datosOperacion);
            case VENTA:
                return venta(datosOperacion);
            case CONSULTA_X:
                return consultaX(datosOperacion);
            case CONSULTA_Z:
                return consultaZ(datosOperacion);
            case SINCRONIZACION:
                return sincronizacion(datosOperacion);
            case PCI_VENTA:
                return ventaPCI(datosOperacion);
            case PCI_DEVOLUCION:
                return devolucionPCI(datosOperacion);
            case PCI_SINCRONIZACION:
                return sincronizacionPCI(datosOperacion);
            case INIT_LIST:
                return initList(datosOperacion);
            case INIT_UPDATE:
                return initUpdate(datosOperacion);
            case ICONOS_LIST:
                return iconosList(datosOperacion);
            case ICONOS_UPDATE:
                return iconosUpdate(datosOperacion);
            case LOGOS_LIST:
                return logosList(datosOperacion);
            case LOGOS_UPDATE:
                return logosUpdate(datosOperacion);
            case SOFTWARE_UPDATE:
                //return softwareUpdate(datosOperacion);
            case SALDO:
                return saldo(datosOperacion);
        }
        return null;
    }

    private TransactionAction login (final DatosOperacion datosOperacion){
        return new TransactionAction() {
            @Override
            public RespuestaLogin execute() {
                return ApiFullcargaAndroid.login(
                        AppLogger.LOGGER,
                        datosOperacion.getIdPeticion(),
                        ApiInstance.getInstance().getDatosSesion(),
                        new Date()
                );
            }
        };
    }

    private TransactionAction loginPCI (final DatosOperacion datosOperacion){
        return new TransactionAction() {
            @Override
            public RespuestaLogin execute() {
                return ApiFullcargaAndroidPCI.loginPCI(
                        AppLogger.LOGGER,
                        datosOperacion.getIdPeticion(),
                        ApiInstance.getInstance().getDatosSesionPCI(),
                        new Date()
                );
            }
        };
    }

    private TransactionAction venta (final DatosOperacion datosOperacion){
        return new TransactionAction() {
            @Override
            public RespuestaVenta execute() {
                return ApiFullcargaAndroid.venta(
                        AppLogger.LOGGER,
                        datosOperacion.getIdPeticion(),
                        ApiInstance.getInstance().getDatosSesion(),
                        new Date(),
                        datosOperacion.getProcod(),
                        datosOperacion.getCierreTurno(),
                        datosOperacion.isNegativo(),
                        "",
                        datosOperacion.getFields()
                );
            }
        };
    }

    private TransactionAction consultaX (final DatosOperacion datosOperacion){
        return new TransactionAction() {
            @Override
            public RespuestaConsultaX execute() {
                return ApiFullcargaAndroid.consultaX(
                        AppLogger.LOGGER,
                        datosOperacion.getIdPeticion(),
                        ApiInstance.getInstance().getDatosSesion(),
                        new Date(),
                        datosOperacion.getProcod(),
                        datosOperacion.isNegativo(),
                        "",
                        datosOperacion.getFields()
                );
            }
        };
    }

    private TransactionAction consultaZ (final DatosOperacion datosOperacion){
        return new TransactionAction() {
            @Override
            public RespuestaConsultaZ execute() {
                return ApiFullcargaAndroid.consultaZ(
                        AppLogger.LOGGER,
                        datosOperacion.getIdPeticion(),
                        ApiInstance.getInstance().getDatosSesion(),
                        new Date(),
                        datosOperacion.getProcod(),
                        datosOperacion.isNegativo(),
                        "",
                        datosOperacion.getFields()
                );
            }
        };
    }

    private TransactionAction sincronizacion (final DatosOperacion datosOperacion){
        return new TransactionAction() {
            @Override
            public RespuestaVenta execute() {
                return ApiFullcargaAndroid.sincronizarVenta(
                        AppLogger.LOGGER,
                        datosOperacion.getIdPeticion(),
                        ApiInstance.getInstance().getDatosSesion(),
                        new Date(),
                        datosOperacion.getStan(),
                        datosOperacion.getProcod(),
                        datosOperacion.getCierreTurno(),
                        datosOperacion.isNegativo()
                );
            }
        };
    }

    private TransactionAction ventaPCI(final DatosOperacion datosOperacion){
        return new TransactionAction() {
            @Override
            public RespuestaVenta execute() {
                return ApiFullcargaAndroidPCI.ventaPCI(AppLogger.LOGGER,
                        datosOperacion.getIdPeticion(),
                        ApiInstance.getInstance().getDatosSesionPCI(),
                        new Date(),
                        datosOperacion.getProcod(),
                        datosOperacion.getCierreTurno(),
                        datosOperacion.isNegativo(),
                        "",
                        datosOperacion.getDatosTarjeta(),
                        datosOperacion.getFields()
                );
            }
        };
    }

    private TransactionAction devolucionPCI (final DatosOperacion datosOperacion){
        return new TransactionAction() {
            @Override
            public RespuestaDevolucion execute() {
                return ApiFullcargaAndroidPCI.devolucionPCI(AppLogger.LOGGER,
                        datosOperacion.getIdPeticion(),
                        ApiInstance.getInstance().getDatosSesionPCI(),
                        new Date(),
                        datosOperacion.getProcod(),
                        datosOperacion.getCierreTurno(),
                        datosOperacion.isNegativo(),
                        "",
                        datosOperacion.getDatosTarjeta(),
                        datosOperacion.getFields()
                );
            }
        };
    }

    private TransactionAction sincronizacionPCI (final DatosOperacion datosOperacion){
        return new TransactionAction() {
            @Override
            public RespuestaVenta execute() {
                
                return ApiFullcargaAndroidPCI.sincronizarVentaPCI(AppLogger.LOGGER,
                        datosOperacion.getIdPeticion(),
                        ApiInstance.getInstance().getDatosSesionPCI(),
                        new Date(),
                        datosOperacion.getStan(),
                        datosOperacion.getProcod(),
                        datosOperacion.getCierreTurno(),
                        datosOperacion.isNegativo(),
                        datosOperacion.getDatosTarjeta()
                );
            }
        };
    }

    private TransactionAction initList (final DatosOperacion datosOperacion){
        return new TransactionAction() {
            @Override
            public RespuestaListArchivos execute()throws Exception {
                return InitResolver.Resolver(datosOperacion);
            }
        };
    }

    private TransactionAction initUpdate (final DatosOperacion datosOperacion){
        return new TransactionAction() {
            @Override
            public RespuestaUpdate execute()throws Exception {
                return InitUpdateResolver.Resolver(datosOperacion);
            }
        };
    }

    private TransactionAction iconosList (final DatosOperacion datosOperacion){
        return new TransactionAction() {
            @Override
            public RespuestaListArchivos execute() throws Exception {
                return IconResolver.Resolver(datosOperacion);
            }
        };
    }

    private TransactionAction iconosUpdate (final DatosOperacion datosOperacion){
        return new TransactionAction() {
            @Override
            public RespuestaUpdate execute() throws Exception {
                return IconUpdateResolver.Resolver(datosOperacion);
            }
        };
    }

    private TransactionAction logosList (final DatosOperacion datosOperacion){
        return new TransactionAction() {
            @Override
            public RespuestaListArchivos execute() {
                return ApiFullcargaAndroid.logoList(
                        AppLogger.LOGGER,
                        datosOperacion.getIdPeticion(),
                        ApiInstance.getInstance().getDatosSesion(),
                        new Date(),
                        datosOperacion.getIconosCRC()
                );
            }
        };
    }

    private TransactionAction logosUpdate (final DatosOperacion datosOperacion){
        return new TransactionAction() {
            @Override
            public RespuestaUpdate execute() throws Exception {
                return ApiFullcargaAndroid.logoUpdate(
                        AppLogger.LOGGER,
                        datosOperacion.getIdPeticion(),
                        ApiInstance.getInstance().getDatosSesion(),
                        new Date(),
                        datosOperacion.getZipName(),
                        datosOperacion.getDescargaListener()
                );
            }
        };
    }

    private TransactionAction saldo (final DatosOperacion datosOperacion){
        return new TransactionAction() {
            @Override
            public RespuestaSaldo execute() throws Exception {
                return ApiFullcargaAndroid.saldo(
                        AppLogger.LOGGER,
                        datosOperacion.getIdPeticion(),
                        ApiInstance.getInstance().getDatosSesion(),
                        new Date()
                );
            }
        };
    }


    public AbstractRespuesta callOperation() throws Exception{
      return    ejecutarOperacion().execute();
    }


    public interface TransactionAction<T extends AbstractRespuesta> {
        T execute() throws Exception;
    }
}
