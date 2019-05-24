package sigma.manager;

import java.util.Arrays;

public class QPOSSessionKeys {

    private byte[] enDataCardKey;
    private byte[] enPinKcvKey;
    private byte[] enPinKey;
    private byte[] rsaReginLen;
    private byte[] enKcvDataCardKey;
    private byte[] rsaReginString;


    public QPOSSessionKeys(final byte[] enDataCardKey, final byte[] enPinKcvKey, final byte[] enPinKey, final byte[] rsaReginLen, final byte[] enKcvDataCardKey, final byte[] rsaReginString) {
        this.enDataCardKey = enDataCardKey.clone();
        this.enPinKcvKey = enPinKcvKey.clone();
        this.enPinKey = enPinKey.clone();
        this.rsaReginLen = rsaReginLen.clone();
        this.enKcvDataCardKey = enKcvDataCardKey.clone();
        this.rsaReginString = rsaReginString.clone();
    }

    public byte[] getEnDataCardKey() {
        return Arrays.copyOf(enDataCardKey, enDataCardKey.length);
    }

    public void setEnDataCardKey(final byte[] enDataCardKey) {
        this.enDataCardKey = enDataCardKey.clone();
    }

    public byte[] getEnPinKcvKey() {

        return Arrays.copyOf(enPinKcvKey, enPinKcvKey.length);
    }

    public void setEnPinKcvKey(final byte[] enPinKcvKey) {

        this.enPinKcvKey = enPinKcvKey.clone();
    }

    public byte[] getEnPinKey() {

        return Arrays.copyOf(enPinKey, enPinKey.length);
    }

    public void setEnPinKey(final byte[] enPinKey) {

        this.enPinKey = enPinKey.clone();
    }

    public byte[] getRsaReginLen() {

        return Arrays.copyOf(rsaReginLen, rsaReginLen.length);
    }

    public void setRsaReginLen(final byte[] rsaReginLen) {

        this.rsaReginLen = rsaReginLen.clone();
    }

    public byte[] getEnKcvDataCardKey() {

        return Arrays.copyOf(enKcvDataCardKey, enKcvDataCardKey.length);
    }

    public void setEnKcvDataCardKey(final byte[] enKcvDataCardKey) {
        this.enKcvDataCardKey = enKcvDataCardKey.clone();
    }

    public byte[] getRsaReginString() {

        return Arrays.copyOf(rsaReginString, rsaReginString.length);
    }

    public void setRsaReginString(final byte[] rsaReginString) {
        this.rsaReginString = rsaReginString.clone();
    }

    @Override
    public final String toString() {
        return super.toString();
    }
}
