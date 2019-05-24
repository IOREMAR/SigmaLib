package sigma.utils;


import net.fullcarga.android.api.bd.sigma.generated.tables.pojos.Menu;

import java.io.Serializable;

public class MenuItemSlider implements Serializable {

private final static  String TAG = MenuItemSlider.class.getSimpleName();

    private String nivel;
    private String texto;
    private String producto;
    private Integer icono;

    private Menu menuItem;

    public MenuItemSlider (){}


    public MenuItemSlider(Menu value) {
        menuItem =new Menu(value);
    }

    public MenuItemSlider(String nivel, String texto, String producto, Integer icono) {
        this.nivel = nivel;
        this.texto = texto;
        this.producto = producto;
        this.icono = icono;
    }


    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public Integer getIcono() {
        return icono;
    }

    public void setIcono(Integer icono) {
        this.icono = icono;
    }

    public Menu getMenuItem() {
        return menuItem;
    }

    public void setMenuItem(Menu menuItem) {
        this.menuItem = menuItem;
    }
}
