package sigma.manager;

import net.fullcarga.android.api.bd.sigma.generated.tables.pojos.EmvAids;
import net.fullcarga.android.api.bd.sigma.generated.tables.pojos.EmvAidsPerfil;
import net.fullcarga.android.api.bd.sigma.generated.tables.pojos.EmvMonedas;
import net.fullcarga.android.api.bd.sigma.generated.tables.pojos.PerfilesEmv;

import java.io.Serializable;

public class PerfilEmvApp implements Serializable {

    private final EmvAids emvAids;
    private final EmvAidsPerfil emvAidsPerfil;
    private final PerfilesEmv perfilesEmv;
    private final EmvMonedas emvMonedas;
    private final EmvMonedas emvCashBackMonedas;

    public PerfilEmvApp(final EmvAids emvAids, final EmvAidsPerfil emvAidsPerfil, final PerfilesEmv perfilesEmv, final EmvMonedas emvMonedas) {
        this.emvAids = emvAids;
        this.emvAidsPerfil = emvAidsPerfil;
        this.perfilesEmv = perfilesEmv;
        this.emvMonedas = emvMonedas;
        this.emvCashBackMonedas = null;
    }

    public PerfilEmvApp(final EmvAids emvAids, final EmvAidsPerfil emvAidsPerfil, final PerfilesEmv perfilesEmv, final EmvMonedas emvMonedas, final EmvMonedas emvCashBackMonedas) {
        this.emvAids = emvAids;
        this.emvAidsPerfil = emvAidsPerfil;
        this.perfilesEmv = perfilesEmv;
        this.emvMonedas = emvMonedas;
        this.emvCashBackMonedas = emvCashBackMonedas;
    }

    public EmvAids getEmvAids() {
        return emvAids;
    }

    public EmvAidsPerfil getEmvAidsPerfil() {
        return emvAidsPerfil;
    }

    public PerfilesEmv getPerfilesEmv() {
        return perfilesEmv;
    }

    public EmvMonedas getEmvMonedas() {
        return emvMonedas;
    }

    public EmvMonedas getEmvCashBackMonedas() {
        return emvCashBackMonedas;
    }
}