package marcer.pau.streaming.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;

import marcer.pau.streaming.FullscreenActivity;
import marcer.pau.streaming.R;
import marcer.pau.streaming.constants.Constants;
import marcer.pau.streaming.model.Aplicacio;
import marcer.pau.streaming.model.ModelAplicacions;
import marcer.pau.streaming.protocol.JSONProtocol;
import marcer.pau.streaming.protocol.TCPControl;

public class SelectorApp extends Activity implements ModelAplicacions.ModelAplicacionsListener, JSONProtocol.JSONProtocolListener{
    private ImageButton app1,app2,app3,app4,app5,app6,app7,app8,app9;
    private List<ImageButton> lapps = new LinkedList<>();
    private Button seleccionar_servidor;
    private ModelAplicacions model;
    private TCPControl tcpControl = new TCPControl(this);

    @Override
    public void onCanviModelAplicacions() {
        reloadModel();
    }

    public void defineixModel(ModelAplicacions model){
        this.model = model;
        model.enregistrarObservador(this);
    }

    private void reloadModel() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selector_app);
        init();
    }

    private void init() {
        app1 = (ImageButton) findViewById(R.id.imageButton1); lapps.add(app1);
        app2 = (ImageButton) findViewById(R.id.imageButton2); lapps.add(app2);
        app3 = (ImageButton) findViewById(R.id.imageButton3); lapps.add(app3);
        app4 = (ImageButton) findViewById(R.id.imageButton4); lapps.add(app4);
        app5 = (ImageButton) findViewById(R.id.imageButton5); lapps.add(app5);
        app6 = (ImageButton) findViewById(R.id.imageButton6); lapps.add(app6);
        app7 = (ImageButton) findViewById(R.id.imageButton7); lapps.add(app7);
        app8 = (ImageButton) findViewById(R.id.imageButton8); lapps.add(app8);
        app9 = (ImageButton) findViewById(R.id.imageButton9); lapps.add(app9);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                app_escollida((ImageButton) view);
            }
        };
        app1.setOnClickListener(onClickListener);
        app2.setOnClickListener(onClickListener);
        app3.setOnClickListener(onClickListener);
        app4.setOnClickListener(onClickListener);
        app5.setOnClickListener(onClickListener);
        app6.setOnClickListener(onClickListener);
        app7.setOnClickListener(onClickListener);
        app8.setOnClickListener(onClickListener);
        app9.setOnClickListener(onClickListener);

        seleccionar_servidor = (Button) findViewById(R.id.buttonConectar);
        seleccionar_servidor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelectorApp.this, servidorIP.class);
//                startActivityForResult(intent,1);
                startActivityForResult(intent,Constants.REQUEST_SERVIDOR_IP);
            }
        });
    }

    private void app_escollida(ImageButton view) {
        //Aplicacio app = model.getListApps().get(lapps.indexOf(view));
        //TODO; Cridar al protocol
        tcpControl.doStartApp(new Aplicacio("APP",4,null),new LinkedList<String>());
        startActivity(new Intent(SelectorApp.this, FullscreenActivity.class));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.REQUEST_SERVIDOR_IP){
            onSelectorIPReturn();
            if (resultCode == Constants.RETURN_CONNECTED) {
                tcpControl.doAppsRequest();
            } else if (resultCode == Constants.RETURN_NOCONNECTED){

            }
        }

    }

    private void onSelectorIPReturn() {
        JSONProtocol.getInstance().registerListener(this);
    }

    @Override
    public void onDiscoveryResponse(String ip, String port) {

    }

    @Override
    public void onAppRequestResponse(List<Aplicacio> listApps) {

    }

    @Override
    public void onStartResponse(String ip, String port, String URI) {
        Toast.makeText(this,"IP: "+ip+"\nPort: "+port+"\nURI: "+URI,Toast.LENGTH_LONG);
    }

    @Override
    public void onStopResponse() {

    }

    @Override
    public void onErrorResponse() {

    }

    //    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.selector_app, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}
