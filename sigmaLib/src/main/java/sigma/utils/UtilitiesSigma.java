package sigma.utils;

public class UtilitiesSigma {

    private static boolean sIsTablet = false;
    private static int sDecimalesPais = 0;
    private static String sNumeroSerie = "";

    private UtilitiesSigma(){
    }

    public static boolean isTablet() {
        return sIsTablet;
    }

    public static void setIsTablet(boolean isTablet) {
        sIsTablet = isTablet;
    }

    public static int getDecimalesPais() {
        return sDecimalesPais;
    }

    public static void setDecimalesPais(int decimalesPais) {
        sDecimalesPais = decimalesPais;
    }

    public static void setNumeroSerie(String serie) {
        sNumeroSerie = serie;
    }

    public static String getNumeroSerie() {
        return sNumeroSerie;
    }
}
