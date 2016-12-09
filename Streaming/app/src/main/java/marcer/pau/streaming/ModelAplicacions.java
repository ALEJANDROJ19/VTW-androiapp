package marcer.pau.streaming;

import android.media.Image;

import java.util.ArrayList;
import java.util.List;

public class ModelAplicacions {
    private List<Aplicacio> listApps;
    private ModelAplicacionsListener observador;

    public ModelAplicacions() {
        listApps = new ArrayList<>();
    }

    public interface ModelAplicacionsListener {
        void onCanviModelAplicacions();
    }

    private void avisarObservador() {
        if(observador != null)
            observador.onCanviModelAplicacions();
    }

    public void enregistrarObservador(ModelAplicacionsListener observador){
        this.observador = observador;
    }

    ////

    public void afegirAPP(Aplicacio aplicacio) {
        listApps.add(aplicacio);
        avisarObservador();
    }

    public List<Aplicacio> getListApps() {
        return listApps;
    }
}
