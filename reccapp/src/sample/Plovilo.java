package sample;

public class Plovilo {

    //varijable
    private String imePlovila;
    protected static int duljinaPlovila;
    private String vrstaPlovila;
    private String imeIPrezimeVlasnika;
    private String vez;

    // SET METHODS
    public void setImePlovila(String imePlovila){
        this.imePlovila = imePlovila;
    }

    public void setDuljinaPlovila(int duljinaPlovila) {
        this.duljinaPlovila = duljinaPlovila;
    }

    public void setVrstaPlovila(String vrstaPlovila) {
        this.vrstaPlovila = vrstaPlovila;
    }

    public void setImeIPrezimeVlasnika(String imeIPrezimeVlasnika) {
        this.imeIPrezimeVlasnika = imeIPrezimeVlasnika;
    }

    public void setVez(String vez) {
        this.vez = vez;
    }

    // GET METHODS
    public String getImePlovila() {
        return imePlovila;
    }

    public int getDuljinaPlovila() {
        return duljinaPlovila;
    }

    public String getVrstaPlovila() {
        return vrstaPlovila;
    }

    public String getImeIPrezimeVlasnika() {
        return imeIPrezimeVlasnika;
    }

    public String getVez() {
        return vez;
    }
}
