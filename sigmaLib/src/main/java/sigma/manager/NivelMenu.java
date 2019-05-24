package sigma.manager;

import net.fullcarga.android.api.bd.sigma.generated.tables.pojos.Menu;

//EL PRIMER NIVEL SON LAS CATEGORIAS
public enum NivelMenu {
    SEGUNDO_NIVEL(2),
    TERCER_NIVEL(4),
    CUARTO_NIVEL(6);

    private int nivel;

    private NivelMenu(final int nivel) {
        this.nivel = nivel;
    }

    public String getNivelFromMenu(final Menu menu) {
        return menu.getNivel().substring(0, nivel);
    }

    public NivelMenu getNivelPrevio() {
        if (this.ordinal() > 0) {
            return values()[this.ordinal() - 1];
        }

        return this;
    }

    public boolean tieneNivelPrevio() {
        return this.ordinal() > 0;
    }

    public NivelMenu getNivelSiguiente() {
        if (this.ordinal() < values().length - 1) {
            return values()[this.ordinal() + 1];
        }
        return this;
    }
}
