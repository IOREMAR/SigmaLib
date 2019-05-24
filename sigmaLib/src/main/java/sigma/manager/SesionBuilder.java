package sigma.manager;


import android.content.Context;
import android.content.SharedPreferences;

import com.pagatodo.sigmamanager.Instance.ApiInstance;

import net.fullcarga.android.api.bd.sigma.manager.BdSigmaManager;
import net.fullcarga.android.api.constantes.Constantes;
import net.fullcarga.android.api.data.DatosConexion;
import net.fullcarga.android.api.data.DatosTPV;
import net.fullcarga.android.api.sesion.DatosSesion;
import net.fullcarga.android.api.sesion.SessionFactory;
import net.fullcarga.android.api.util.HexUtil;

import java.sql.SQLException;

import static sigma.utils.Constantes.PREFERENCE_SETTINGS;


public final class SesionBuilder {


    private SesionBuilder() {
    }

    public static DatosSesion build(final String claveTpv) throws SQLException {
//        SharedPreferences preferencesdbname = ApiInstance.getInstance().getAppcontext().getSharedPreferences(PREFERENCE_SETTINGS, Context.MODE_PRIVATE);

        if (StorageUtility.validarArchivo(StorageUtility.getSigmaDbPath())) {
            final BdSigmaManager bdSigmaManager = StorageUtility.crearConexionSigmaManager();
            return SessionFactory.crearSesionLocal(
                    bdSigmaManager.crearDatosConexionLocalTrx(),
                    bdSigmaManager.crearDatosConexionLocalDonwload(),
                    bdSigmaManager.crearDatosTPV(ApiInstance.getInstance().getNumSerie(), "1", ApiInstance.getVersionBdApp(), new StanProviderMock()),
                    claveTpv,
                    HexUtil.hex2byte(ApiInstance.getInstance().getGetclaveHexLocal(), Constantes.DEF_CHARSET),
                    ApiInstance.getInstance().getNameRsa());
        } else {
            return SessionFactory.crearSesionLocal(
                    new DatosConexion(ApiInstance.getInstance().getIpServer(), ApiInstance.getInstance().getPuerto(), 5000,60000),
                    new DatosConexion(ApiInstance.getInstance().getIpServer(),ApiInstance.getInstance().getPuerto(), 5000, 60000),
                    new DatosTPV("99999999", ApiInstance.getInstance().getDecimales(), new StanProviderMock(), ApiInstance.getInstance().getNumSerie(), ApiInstance.getInstance().getPaisCode(), "", "", ApiInstance.getVersionBdApp()),
                    claveTpv,
                    HexUtil.hex2byte(ApiInstance.getInstance().getGetclaveHexLocal(), Constantes.DEF_CHARSET),
                    ApiInstance.getInstance().getNameRsa()
            );
        }
    }

    public static DatosSesion buildSessionPci(final String pinPci, final QPOSSessionKeys qposSessionKeys) throws SQLException {
        try (final BdSigmaManager bdSigmaManager = StorageUtility.crearConexionSigmaManager()) {
            final DatosConexion datosConexion = bdSigmaManager.crearDatosConexionPciTrx();
//            final QPosImpl qPos = (QPosImpl) MposApplication.getInstance().getPreferedDongle();

            return SessionFactory.crearSesionPci(
                    datosConexion,
                    ApiInstance.getInstance().getDatosSesion(),
                    pinPci,
                    HexUtil.hex2byte(ApiInstance.getInstance().getClaveBytePCI(), Constantes.DEF_CHARSET),
                    ApiInstance.getInstance().getNameRsaPCI(),
                    qposSessionKeys.getEnDataCardKey(),
                    qposSessionKeys.getEnKcvDataCardKey(),//los 3 primeros bytes
                    qposSessionKeys.getEnPinKey(),
                    qposSessionKeys.getEnPinKcvKey());//los 3 primeros bytes
        }
    }




}
