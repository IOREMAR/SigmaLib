package sigma.manager;

import java.util.Map;

public final class DecodeData {

    private Map<String, String> data;

    public Map<String, String> getData() {
        return data;
    }

    public void setData(final Map<String, String> data) {
        this.data = data;
    }

    private DecodeData() {

    }

    public static DecodeData newInstance(final Map<String, String> data) {

        final DecodeData decodeData = new DecodeData();

        decodeData.setData(data);

        return decodeData;
    }

    public String getExpiryDate() {
        return data.get("expiryDate");
    }

    public String getTrackksn() {
        return data.get("trackksn");
    }

    public String getServiceCode() {
        return data.get("serviceCode");
    }

    public String getIccdata() {
        return data.get("iccdata");
    }

    public String getMaskedPAN() {
        return data.get("maskedPAN");
    }

    public String getPinBlock() {
        return data.get("pinBlock");
    }

    public String getPinRandomNumber() {
        return data.get("pinRandomNumber");
    }

    public String getPsamNo() {
        return data.get("psamNo");
    }

    public String getCardSquNo() {
        return data.get("cardSquNo");
    }

    public String getIccCardAppexpiryDate() {
        return data.get("iccCardAppexpiryDate");
    }

    public String getEncPAN() {
        return data.get("encPAN");
    }

    public String getPosID() {
        return data.get("posID");
    }

    public String getCardholderName() {
        return data.get("cardholderName");
    }

    public String getFormatID() {
        return data.get("formatID");
    }

    public String getEncTracks() {
        return data.get("encTracks");
    }

    public String getPinKsn() {
        return data.get("pinKsn");
    }

    public String getTrack1Length() {
        return data.get("track1Length");
    }

    public String getTrack2Length() {
        return data.get("track2Length");
    }

    public String getTrack3Length() {
        return data.get("track3Length");
    }

    public String getEncTrack1() {
        return data.get("encTrack1");
    }

    public String getEncTrack2() {
        return data.get("encTrack2");
    }

    public String getEncTrack3() {
        return data.get("encTrack3");
    }

    public String getKsn() {
        return data.get("ksn");
    }

    public String getPartialTrack() {
        return data.get("partialTrack");
    }

    public String getTrackRandomNumber() {
        return data.get("trackRandomNumber");
    }
}
