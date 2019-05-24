package com.pagatodo.sigmamanager.Instance;


import android.content.Context;

import net.fullcarga.android.api.oper.TipoOperacion;
import net.fullcarga.android.api.sesion.DatosSesion;
import net.fullcarga.android.api.util.HexUtil;

import java.security.PublicKey;

import sigma.secciones.operaciones.datos.DatosOperacion;

public class ApiInstance {

    int contadorinit =0 ;

    private ApiInstance() {
    }

    private static ApiInstance instance;

    public static ApiInstance getInstance() {
        synchronized ( ApiInstance.class ) {
            if ( instance == null ) {
                instance = new ApiInstance();
            }
        }
        return instance;
    }

    public Context getAppcontext() {
        return Appcontext;
    }

    public void setAppcontext(Context appcontext) {
        Appcontext = appcontext;
    }

    private Context Appcontext;

    private String sigmaPath;

    private String AppID;

    public String getAppID() {
        return AppID;
    }

    public void setAppID(String appID) {
        AppID = appID;
    }

    public String getSigmaDBName() {
        return sigmaDBName;
    }

    private PublicKey publicKeyRSA;

    public PublicKey getPublicKeyRSA() {
        return publicKeyRSA;
    }

    public void setPublicKeyRSA(PublicKey publicKeyRSA) {
        this.publicKeyRSA = publicKeyRSA;
    }

    public void setSigmaDBName(String sigmaDBName) {
        this.sigmaDBName = sigmaDBName;
    }

    private String ipServer;

    public String getIpServer() {
        return ipServer;
    }

    public void setIpServer(String ipServer) {
        this.ipServer = ipServer;
    }

    private static Integer puerto;

    public Integer getPuerto() {
        return puerto;
    }

    public void setPuerto(Integer puerto) {
        this.puerto = puerto;
    }


    private int decimales;

    private String  paisCode;


    public int getDecimales() {
        return decimales;
    }

    public void setDecimales(int decimales) {
        this.decimales = decimales;
    }

    public String getPaisCode() {
        return paisCode;
    }

    public void setPaisCode(String paisCode) {
        this.paisCode = paisCode;
    }

    private String nameRsa;

    public String getNameRsaPCI() {
        return nameRsaPCI;
    }

    public void setNameRsaPCI(String nameRsaPCI) {
        this.nameRsaPCI = nameRsaPCI;
    }

    private String nameRsaPCI;

    public String getNameRsa() {
        return nameRsa;
    }

    public void setNameRsa(String nameRsa) {
        this.nameRsa = nameRsa;
    }

    private String NumSerie;

    public String getNumSerie() {
        return NumSerie;
    }

    public void setNumSerie(String numSerie) {
        NumSerie = numSerie;
    }

    private String Stan;

    public String getStan() {
        return Stan;
    }

    public void setStan(String stan) {
        Stan = stan;
    }

    private static String VersionBdApp;

    public static String getVersionBdApp() {
        return VersionBdApp;
    }

    public void setVersionBdApp(String versionBdApp) {
        VersionBdApp = versionBdApp;
    }

    private String sigmaDBName;
    private String logosName;
    private String iconosName;
    private long fileSize;

    public String getLogosName() {
        return logosName;
    }

    public void setLogosName(String logosName) {
        this.logosName = logosName;
    }

    public String getIconosName() {
        return iconosName;
    }

    public void setIconosName(String iconosName) {
        this.iconosName = iconosName;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public String getSigmaPath() {
        return sigmaPath;
    }

    public void setSigmaPath(String sigmaPath) {
        this.sigmaPath = sigmaPath;
    }

    private DatosSesion datosSesion;
    private DatosSesion datosSesionPCI;

    public DatosSesion getDatosSesionPCI() {
        return datosSesionPCI;
    }

    public void setDatosSesionPCI(DatosSesion datosSesionPCI) {
        this.datosSesionPCI = datosSesionPCI;
    }

    private Boolean hasOperacionSiguiente;

    private DatosOperacion operacion;

    public DatosSesion getDatosSesion() {
        return datosSesion;
    }

    public void setDatosSesion(DatosSesion datosSesion) {
        this.datosSesion = datosSesion;
    }

    public Boolean hasOperacionSiguiente() {
        return operacion != null;
    }

    public DatosOperacion getOperacion() {
        return operacion;
    }

    public void setOperacion(DatosOperacion operacion) {
        this.operacion = operacion;
    }

    public void ejecutarOperacion() {
        operacion.realizarOperacion();
    }


    private String getclaveHexLocal ;

    private byte [] ClaveByte;

    private byte [] ClaveBytePCI;

    public String getClaveBytePCI() {
        return HexUtil.hexStringFromBytes( ClaveBytePCI);
    }

    public void setClaveBytePCI(byte[] claveBytePCI) {
        ClaveBytePCI = claveBytePCI;
    }

    public void setClaveByte(byte[] claveByte) {
        ClaveByte = claveByte;
    }

    public String getGetclaveHexLocal() {
        return HexUtil.hexStringFromBytes(ClaveByte);
    }

    public void setGetclaveHexLocal(String getclaveHexLocal) {
        this.getclaveHexLocal = getclaveHexLocal;
    }

    private String versionName;

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }


}
