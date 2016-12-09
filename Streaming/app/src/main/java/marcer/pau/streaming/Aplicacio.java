package marcer.pau.streaming;

import android.media.Image;


public class Aplicacio {
    private String name;
    private int identificador;
    private Image imatge;

    public Aplicacio(String name, int identificador, Image imatge) {
        this.name = name;
        this.identificador = identificador;
        this.imatge = imatge;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIdentificador() {
        return identificador;
    }

    public void setIdentificador(int identificador) {
        this.identificador = identificador;
    }

    public Image getImatge() {
        return imatge;
    }

    public void setImatge(Image imatge) {
        this.imatge = imatge;
    }
}
