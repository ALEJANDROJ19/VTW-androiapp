package marcer.pau.streaming.model;

import android.graphics.Bitmap;
import android.media.Image;


public class Aplicacio {
    private String name;
    private int identificador;
    private Bitmap imatge;

    public Aplicacio(String name, int identificador, Bitmap imatge) {
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

    public Bitmap getImatge() {
        return imatge;
    }

    public void setImatge(Bitmap imatge) {
        this.imatge = imatge;
    }
}
