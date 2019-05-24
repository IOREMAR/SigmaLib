package sigma.utils;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.preference.PreferenceManager;

import com.pagatodo.sigmamanager.Instance.ApiInstance;

import sigma.manager.AppLogger;
import sigma.manager.localdb.LocalDBContract;
import sigma.manager.localdb.OldLocalDBContract;

public class MigracionPreferencias {

    private static final String TAG = MigracionPreferencias.class.getSimpleName();
    private static final String CLAVE_HEX_LOCAL = "30820122300D06092A864886F70D01010105000382010F003082010A02820101008407CC1198356111AFBF64DEB1BBEC31746F1EC59A20D828E3273BBBC5F8740CAB3CCAE1FD3E76F8C858C49666CE5F597302384D0E8AD9A96829280D7C06871EEF0D2D8F47A7965864DE87FC5458DD8AD253E3B54ED1791F89F1EE56B0F8B361D290EBAA4B76F457A673409F3973D4E55CA5A4E20F90287AC4DB9BC6291D727667239A92632F8A6F48C580385F1918A50369883F50090A6EF3D932DE84BD96DA7527F6E9359B7AE8A73A867C02D340E7DA652C2E2C2E96FB06CFDB37EA872FE08B929ED5C6255BA525174ECB0A21CBAECDAA3267F580454001CCEE1ED12DD7F06A0AF67C1C680DC5F703CD0E401EBC289A0AC0B83F4CC127FB6F8E5B146278DB0203010001";
    private final PreferenceManager preferenceManager;
    private int stanNumber;

    public MigracionPreferencias(final PreferenceManager preferenceManager) {
        this.preferenceManager = preferenceManager;
    }

    public void migracion(final String pass) {

//        try {
//
//            final String serialNumber = SerializeUtil.deserialize(SecurityUtil.decrypt(preferenceManager.getValue(PreferenceManager.Preferencia.PREFERENCE_SERIAL_NUMBER, ""), CLAVE_HEX_LOCAL));
//            preferenceManager.setValue(PreferenceManager.Preferencia.NUMERO_SERIE, serialNumber);
//            if (!"".equals(pass) && !pass.isEmpty() && pass.length() == 4) {
//                final String userKey = SerializeUtil.deserialize(SecurityUtil.decrypt(preferenceManager.getValue(PreferenceManager.Preferencia.PREFERENCE_USER_KEY, ""), serialNumber));
//                final String claveTpv = SerializeUtil.deserialize(SecurityUtil.decrypt(preferenceManager.getValue(PreferenceManager.Preferencia.PREFERENCE_POS_KEY, ""), userKey));
//                preferenceManager.setValue(PreferenceManager.Preferencia.CLAVE_TPV, SecurityUtil.encrypt(claveTpv, pass));
//                preferenceManager.setValue(PreferenceManager.Preferencia.ESTADO_REGISTRO, PreferenceManager.EstadosRegistro.REGISTRADO.name());
//            }
//            preferenceManager.deletePreference(PreferenceManager.Preferencia.PREFERENCE_SERIAL_NUMBER);
//            preferenceManager.deletePreference(PreferenceManager.Preferencia.PREFERENCE_INSTALLATION_KEY);
//            preferenceManager.deletePreference(PreferenceManager.Preferencia.PREFERENCE_POS_KEY);
//            preferenceManager.deletePreference(PreferenceManager.Preferencia.PREFERENCE_USER_KEY);
//            preferenceManager.deletePreference(PreferenceManager.Preferencia.STAN);
//        } catch (GeneralSecurityException exc) {
//
//            AppLogger.LOGGER.throwing(TAG, 1, exc, exc.getMessage());
//        }
//
//        getOldTransactionsAsContentValuesArray();
//        preferenceManager.setValue(PreferenceManager.Preferencia.STAN, String.valueOf(stanNumber));
//        StorageUtility.deleteIfExistFile(StorageUtility.getStoragePath());
    }

    private void getOldTransactionsAsContentValuesArray() {

        final String[] projection = {

                OldLocalDBContract.OldTransaccionesEntry.COLUMN_ID,
                OldLocalDBContract.OldTransaccionesEntry.COLUMN_FECHA,
                OldLocalDBContract.OldTransaccionesEntry.COLUMN_CODIGO_OPER,
                OldLocalDBContract.OldTransaccionesEntry.COLUMN_PRO_COD,
                OldLocalDBContract.OldTransaccionesEntry.COLUMN_DESC_MENU,
                OldLocalDBContract.OldTransaccionesEntry.COLUMN_IMPORTE,
                OldLocalDBContract.OldTransaccionesEntry.COLUMN_MONEDA,
                OldLocalDBContract.OldTransaccionesEntry.COLUMN_IMPORTE_ORIGEN,
                OldLocalDBContract.OldTransaccionesEntry.COLUMN_FEE,
                OldLocalDBContract.OldTransaccionesEntry.COLUMN_REF_LOCAL,
                OldLocalDBContract.OldTransaccionesEntry.COLUMN_STAN,
                OldLocalDBContract.OldTransaccionesEntry.COLUMN_REF_REMOTA,
                OldLocalDBContract.OldTransaccionesEntry.COLUMN_REF_CLIENTE,
                OldLocalDBContract.OldTransaccionesEntry.COLUMN_DESC_PRODUCTO,
                OldLocalDBContract.OldTransaccionesEntry.COLUMN_TURNO,
                OldLocalDBContract.OldTransaccionesEntry.COLUMN_CONTABILIZAR,
                OldLocalDBContract.OldTransaccionesEntry.COLUMN_ZINCRONIZADA,
                OldLocalDBContract.OldTransaccionesEntry.COLUMN_CUENTA,
                OldLocalDBContract.OldTransaccionesEntry.COLUMN_DIA,
                OldLocalDBContract.OldTransaccionesEntry.COLUMN_TOTAL,
                OldLocalDBContract.OldTransaccionesEntry.COLUMN_CASHBACK,
                OldLocalDBContract.OldTransaccionesEntry.COLUMN_IMPUESTO,
                OldLocalDBContract.OldTransaccionesEntry.COLUMN_PROPINA,
                OldLocalDBContract.OldTransaccionesEntry.COLUMN_TIPO_TARJETA
        };

        Cursor cursor = null;

        try {
//            cursor = MposApplication.getInstance().getContentResolver().query(OldLocalDBContract.OldTransaccionesEntry.CONTENT_URI, projection, null, null, null);
        } catch (SQLException e) {
            AppLogger.LOGGER.throwing(TAG, 1,e, e.getMessage());
        }

        final ContentValues contentValues = new ContentValues();

        if (cursor != null && cursor.getCount() > 0) {
            stanNumber = cursor.getCount();
            cursor.moveToFirst();

            while (!cursor.isAfterLast()) {

                contentValues.clear();

                contentValues.put(LocalDBContract.TransaccionesEntry.COLUMN_ID, cursor.getInt(0));
                contentValues.put(LocalDBContract.TransaccionesEntry.COLUMN_FECHA, cursor.getLong(1));
                contentValues.put(LocalDBContract.TransaccionesEntry.COLUMN_CODIGO_OPER, cursor.getString(2));
                contentValues.put(LocalDBContract.TransaccionesEntry.COLUMN_PRO_COD, cursor.getString(3));
                contentValues.put(LocalDBContract.TransaccionesEntry.COLUMN_DESC_MENU, cursor.getString(4));
                contentValues.put(LocalDBContract.TransaccionesEntry.COLUMN_IMPORTE, cursor.getString(5));
                contentValues.put(LocalDBContract.TransaccionesEntry.COLUMN_MONEDA, cursor.getString(6));
                contentValues.put(LocalDBContract.TransaccionesEntry.COLUMN_IMPORTE_ORIGEN, cursor.getString(7));
                contentValues.put(LocalDBContract.TransaccionesEntry.COLUMN_FEE, cursor.getInt(8));
                contentValues.put(LocalDBContract.TransaccionesEntry.COLUMN_REF_LOCAL, cursor.getString(9));
                contentValues.put(LocalDBContract.TransaccionesEntry.COLUMN_STAN, cursor.getInt(10));
                contentValues.put(LocalDBContract.TransaccionesEntry.COLUMN_REF_REMOTA, cursor.getString(11));
                contentValues.put(LocalDBContract.TransaccionesEntry.COLUMN_REF_CLIENTE, cursor.getString(12));
                contentValues.put(LocalDBContract.TransaccionesEntry.COLUMN_DESC_PRODUCTO, cursor.getString(13));
                contentValues.put(LocalDBContract.TransaccionesEntry.COLUMN_TURNO, cursor.getInt(14));
                contentValues.put(LocalDBContract.TransaccionesEntry.COLUMN_CONTABILIZAR, cursor.getInt(15));
                contentValues.put(LocalDBContract.TransaccionesEntry.COLUMN_ZINCRONIZADA, cursor.getInt(16));
                contentValues.put(LocalDBContract.TransaccionesEntry.COLUMN_CUENTA, cursor.getInt(17));
                contentValues.put(LocalDBContract.TransaccionesEntry.COLUMN_DIA, cursor.getInt(18));
                contentValues.put(LocalDBContract.TransaccionesEntry.COLUMN_TOTAL, cursor.getString(19));
                contentValues.put(LocalDBContract.TransaccionesEntry.COLUMN_CASHBACK, cursor.getFloat(20));
                contentValues.put(LocalDBContract.TransaccionesEntry.COLUMN_IMPUESTO, cursor.getFloat(21));
                contentValues.put(LocalDBContract.TransaccionesEntry.COLUMN_PROPINA, cursor.getFloat(22));
                contentValues.put(LocalDBContract.TransaccionesEntry.COLUMN_TIPO_TARJETA, cursor.getInt(23));
                contentValues.put(LocalDBContract.TransaccionesEntry.COLUMN_MEDIO_PAGO, 1);
                ApiInstance.getInstance().getAppcontext().getContentResolver().insert(LocalDBContract.TransaccionesEntry.CONTENT_URI, contentValues);
                cursor.moveToNext();
            }
        }
    }
}
