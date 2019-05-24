package sigma.manager;

import com.pagatodo.sigmamanager.Instance.ApiInstance;

import net.fullcarga.android.api.data.StanProvider;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicLong;


/**
 * @author dsoria
 * Mocks usados por la API Sigma para el manejo de stan en la trasacciones de tipo Venta pointY devolucion
 */
public class StanProviderMock implements StanProvider, Serializable {

    protected AtomicLong stanAtomicLong;
    protected long ultimo;

    public StanProviderMock() {
        //Nothing
        //Nothing to do
    }

    @Override
    public long getNextStan() {
        stanAtomicLong = new AtomicLong(Long.parseLong(ApiInstance.getInstance().getStan(), 10));
        ultimo = calcularNext();
        return ultimo;
    }

    private long calcularNext() {
        final long stan = stanAtomicLong.incrementAndGet();
        if (stan == 999999) {
            stanAtomicLong.set(0);
            return stanAtomicLong.get();
        } else {
            return stan;
        }
    }

    @Override
    public long getUltimo() {
        return ultimo;
    }
}
