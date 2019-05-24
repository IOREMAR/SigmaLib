package sigma.general.interfaces;

//import AppLogger;

public interface OnFailureListener {

    void onFailure(Throwable throwable);

    class BasicOnFailureListener implements OnFailureListener {
        final String tag;
        final String text;

        public BasicOnFailureListener(final String tag, final String text) {
            this.tag = tag;
            this.text = text;
        }

        @Override
        public void onFailure(final Throwable throwable) {
//            AppLogger.LOGGER.throwing(tag, 1, throwable, text);
        }
    }
}
