package sigma.manager;

import net.fullcarga.android.api.bd.sigma.generated.tables.pojos.LiteralesOperacion;
import net.fullcarga.android.api.bd.sigma.generated.tables.pojos.LiteralesTicket;
import net.fullcarga.android.api.bd.sigma.generated.tables.pojos.Logotipos;
import net.fullcarga.android.api.bd.sigma.generated.tables.pojos.Menu;
import net.fullcarga.android.api.bd.sigma.generated.tables.pojos.Operaciones;
import net.fullcarga.android.api.bd.sigma.generated.tables.pojos.Productos;
import net.fullcarga.android.api.bd.sigma.manager.BdSigmaManager;
import net.fullcarga.android.api.formulario.Formulario;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.Collections;
import java.util.List;

import sigma.general.interfaces.OnFailureListener;


public class SigmaBdManager {
    private static final String TAG = SigmaBdManager.class.getName();



    public SigmaBdManager() {}

    public List<Menu> getCategorias(final OnFailureListener sigmaBdInteractorExceptionListener) {
        try (final BdSigmaManager dbManager = StorageUtility.crearConexionSigmaManager()) {
            return dbManager.getMenuItemsFromNivel("");
        } catch (SQLException | NullPointerException exc) {//NOPMD error en sigma
            sigmaBdInteractorExceptionListener.onFailure(exc);
            return Collections.emptyList();
        }
    }

    public List<Menu> getItemsPorNivel(final NivelMenu nivelMenu, final Menu menu, final OnFailureListener onFailureListener) {
        try (final BdSigmaManager dbManager = StorageUtility.crearConexionSigmaManager()) {
            return dbManager.getMenuItemsFromNivel(nivelMenu.getNivelFromMenu(menu));
        } catch (SQLException | NullPointerException exc) {//NOPMD error en sigma
            onFailureListener.onFailure(exc);
            return Collections.emptyList();
        }
    }

    public List<Menu> getItemsPathIconos(final Menu selectedMenu, final NivelMenu menuLevel, final OnFailureListener onFailureListener) {

        try (final BdSigmaManager dbManager = StorageUtility.crearConexionSigmaManager()) {
            switch (menuLevel) {
                case SEGUNDO_NIVEL:
                case TERCER_NIVEL:
                    return dbManager.getMenuItemsFromNivel(menuLevel.getNivelSiguiente().getNivelFromMenu(selectedMenu));
                default:
                    return Collections.emptyList();
            }

        } catch (SQLException | NullPointerException exc) {//NOPMD error en sigma
            onFailureListener.onFailure(exc);
            return Collections.emptyList();
        }
    }

    public String obtenIcono(final Menu menu, final OnFailureListener onFailureListener) {
        try ( BdSigmaManager dbManager = StorageUtility.crearConexionSigmaManager()) {
            return dbManager.getIcono(menu.getIcono() != null ? menu.getIcono() : 0).getRuta();
        } catch (SQLException | NullPointerException exc) {//NOPMD error en sigma
            onFailureListener.onFailure(exc);
            return "";
        }
    }

    public String formatoSaldo(final BigDecimal saldo, final OnFailureListener onFailureListener) {
        try ( BdSigmaManager dbManager = StorageUtility.crearConexionSigmaManager()) {
            return dbManager.getNumberFormat().format(saldo);
        } catch (SQLException | NullPointerException exc) {//NOPMD error en sigma
            onFailureListener.onFailure(exc);
            return " ";
        }
    }

    public NumberFormat getNumberFormat(final OnFailureListener onFailureListener) {
        try ( BdSigmaManager dbManager = StorageUtility.crearConexionSigmaManager()) {
            return dbManager.getNumberFormat();
        } catch (SQLException | NullPointerException exc) {//NOPMD error en sigma
            onFailureListener.onFailure(exc);
            return NumberFormat.getCurrencyInstance();
        }
    }

    public List<Operaciones> getOperacionesVisiblesPorProducto(final String producto, final OnFailureListener onFailureListener) {
        try ( BdSigmaManager dbManager = StorageUtility.crearConexionSigmaManager()) {
            return dbManager.getOperacionesVisible(producto);
        } catch (SQLException | NullPointerException exc) {//NOPMD error en sigma
            onFailureListener.onFailure(exc);
            return Collections.emptyList();
        }
    }

    public Operaciones getOperacionPorProducto(final String producto, final String oper, final OnFailureListener onFailureListener) {
        try ( BdSigmaManager dbManager = StorageUtility.crearConexionSigmaManager()) {
            return dbManager.getOperacionProducto(producto, oper);
        } catch (SQLException | NullPointerException exc) {//NOPMD error en sigma
            onFailureListener.onFailure(exc);
            return null;
        }
    }

    public Productos getProducto(final Operaciones operaciones, final OnFailureListener onFailureListener) {
        try ( BdSigmaManager dbManager = StorageUtility.crearConexionSigmaManager()) {
            return dbManager.getProducto(operaciones.getProducto());
        } catch (SQLException | NullPointerException exc) {//NOPMD error en sigma
            onFailureListener.onFailure(exc);
            return null;
        }
    }

    public Productos getProductoxId(final int idProducto, final OnFailureListener onFailureListener) {
        try ( BdSigmaManager dbManager = StorageUtility.crearConexionSigmaManager()) {
            return dbManager.getProductoId(idProducto);
        } catch (SQLException | NullPointerException exc) {//NOPMD error en sigma
            onFailureListener.onFailure(exc);
            return null;
        }
    }

    public LiteralesOperacion getLiteralOperacion(final Operaciones operaciones, final OnFailureListener onFailureListener) {
        try ( BdSigmaManager dbManager = StorageUtility.crearConexionSigmaManager()) {
            return dbManager.getLiteralOperacion(operaciones.getLiteral());
        } catch (SQLException | NullPointerException exc) {//NOPMD error en sigma
            onFailureListener.onFailure(exc);
            return null;
        }
    }

    public Formulario getFormulario(final Operaciones operaciones, final OnFailureListener onFailureListener) {
        try ( BdSigmaManager dbManager = StorageUtility.crearConexionSigmaManager()) {
            if (operaciones.getFormulario() != null) {
                return dbManager.getFormulario(operaciones.getFormulario());
            } else {
                return null;
            }
        } catch (SQLException | NullPointerException | IllegalArgumentException exc) {//NOPMD error en sigma
            onFailureListener.onFailure(exc);
            return null;
        }
    }

    public String getParametroFijo(final String codigo, final OnFailureListener onFailureListener) {
        try ( BdSigmaManager dbManager = StorageUtility.crearConexionSigmaManager()) {
            return dbManager.getParametroFijoValue(codigo);
        } catch (SQLException | NullPointerException exc) {//NOPMD error en sigma
            onFailureListener.onFailure(exc);
            return null;
        }
    }

    public NumberFormat getInputNumberFormat(final OnFailureListener onFailureListener) {
        try ( BdSigmaManager dbManager = StorageUtility.crearConexionSigmaManager()) {
            return dbManager.getInputNumberFormat();
        } catch (SQLException | NullPointerException exc) {//NOPMD error en sigma
            onFailureListener.onFailure(exc);
            return NumberFormat.getCurrencyInstance();
        }
    }

    public Boolean isPCI(final OnFailureListener onFailureListener) {
        try ( BdSigmaManager dbManager = StorageUtility.crearConexionSigmaManager()) {
            return dbManager.isRequiereSesionPCI();
        } catch (SQLException | NullPointerException exc) {//NOPMD error en sigma
            AppLogger.LOGGER.throwing(TAG, 1, exc, "Error si Requiered PCI");
            onFailureListener.onFailure(exc);
            return false;
        }
    }

    public Boolean isTransporte(final OnFailureListener onFailureListener) {
        try ( BdSigmaManager dbManager = StorageUtility.crearConexionSigmaManager()) {
            return   false;
//                    !dbManager.getParametroFijoValue("0085").equals("");
        } catch (SQLException | NullPointerException exc) {//NOPMD error en sigma
            AppLogger.LOGGER.throwing(TAG, 1, exc, "Error, no tiene Trasporte");
            onFailureListener.onFailure(exc);
            return false;
        }
    }

    public String getHeaderTicket(final Operaciones operaciones, final OnFailureListener onFailureListener) {
        try ( BdSigmaManager dbManager = StorageUtility.crearConexionSigmaManager()) {
            return operaciones.getTicketCabecera() != null ? dbManager.getPlantillaTicket(operaciones.getTicketCabecera()) : "";
        } catch (SQLException | NullPointerException exc) {//NOPMD error en sigma
            AppLogger.LOGGER.throwing(TAG, 1, exc, "Error al obtener header ticket");
            onFailureListener.onFailure(exc);
            return null;
        }
    }

    public String getFooterTicket(final Operaciones operaciones, final OnFailureListener onFailureListener) {
        try ( BdSigmaManager dbManager = StorageUtility.crearConexionSigmaManager()) {
            return operaciones.getTicketPie() != null ? dbManager.getPlantillaTicket(operaciones.getTicketPie()) : "";
        } catch (SQLException | NullPointerException exc) {//NOPMD error en sigma
            AppLogger.LOGGER.throwing(TAG, 1, exc, "Error al obtener footer ticket");
            onFailureListener.onFailure(exc);
            return null;
        }
    }

    public LiteralesTicket getLiteralTicket(final int literalTicket, final OnFailureListener onFailureListener) {
        try ( BdSigmaManager dbManager = StorageUtility.crearConexionSigmaManager()) {
            return dbManager.getLiteralTicket(literalTicket);
        } catch (SQLException | NullPointerException exc) {//NOPMD error en sigma
            AppLogger.LOGGER.throwing(TAG, 1, exc, "Error al obtener literal ticket");
            onFailureListener.onFailure(exc);
            return null;
        }
    }

    public String getLiteralMoneda(final OnFailureListener onFailureListener) {
        try ( BdSigmaManager dbManager = StorageUtility.crearConexionSigmaManager()) {
            return dbManager.getParametroFijoValue("0021");
        } catch (SQLException | NullPointerException exc) {//NOPMD error en sigma
            AppLogger.LOGGER.throwing(TAG, 1, exc, "Error al obtener literal Moneda");
            onFailureListener.onFailure(exc);
            return null;
        }
    }

    public String getDongleID(final OnFailureListener onFailureListener) {
        try ( BdSigmaManager dbManager = StorageUtility.crearConexionSigmaManager()) {
            return dbManager.getSerieDongle();
        } catch (SQLException | NullPointerException exc) {//NOPMD error en sigma
            AppLogger.LOGGER.throwing(TAG, 1, exc, "Error al obtener DongleId");
            onFailureListener.onFailure(exc);
            return null;
        }
    }


    public String getDescMenu (final String prodCod, final OnFailureListener onFailureListener){
        try ( BdSigmaManager dbManager = StorageUtility.crearConexionSigmaManager()) {
            return dbManager.getMenuForProducto(prodCod).getTexto();
        } catch (SQLException | NullPointerException exc) {//NOPMD error en sigma
            AppLogger.LOGGER.throwing(TAG, 1, exc, "Error al obtener DongleId");
            onFailureListener.onFailure(exc);
            return null;
        }

    }


    public String getLogRuta(final Operaciones operaciones , final OnFailureListener onFailureListener  ){

        try ( BdSigmaManager dbManager = StorageUtility.crearConexionSigmaManager())
        {

            if(getProducto(operaciones,onFailureListener).getLogotipo() != null ){
                final Logotipos logotipo  = dbManager.getLogotipo(getProducto(operaciones,onFailureListener).getLogotipo());
                return logotipo.getRuta();
            }else {
                return "";
            }
        }
        catch ( SQLException exc ){
            onFailureListener.onFailure(exc);
            return "";
        }

    }


}
