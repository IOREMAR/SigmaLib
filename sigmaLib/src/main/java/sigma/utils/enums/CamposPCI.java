package sigma.utils.enums;

public enum CamposPCI {
    IMPORTE("Importe"),
    PROPINA("Propina"),
    IMPUESTO("Impuesto"),
    CASHBACK("Importe Retiro"),
    CVV("CVV"),
    BITMAP("BITMAP"),
    CP("Codigo Postal");

    private final String campo;

    CamposPCI(final String campoTipo) {
        this.campo = campoTipo;
    }

    public String campo() {
        return campo;
    }
}
