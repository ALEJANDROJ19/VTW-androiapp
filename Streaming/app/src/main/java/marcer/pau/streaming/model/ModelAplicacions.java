package marcer.pau.streaming.model;

import java.util.ArrayList;
import java.util.List;

import marcer.pau.streaming.model.Aplicacio;

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
