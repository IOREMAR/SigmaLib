package sigma.general.interfaces;

public interface RetrofitListener<T> extends OnFailureListener, OnSuccessListener<T> {

    void showMessage(final String message);
}
