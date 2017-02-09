package marcer.pau.streaming.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import marcer.pau.streaming.R;
import marcer.pau.streaming.constants.Constants;
import marcer.pau.streaming.model.Aplicacio;
import marcer.pau.streaming.model.NetworkParameters;
import marcer.pau.streaming.protocol.JSONProtocol;
import marcer.pau.streaming.protocol.TCPControl;

public class servidorIP extends AppCompatActivity implements JSONProtocol.JSONProtocolListener{
    private EditText input_ip, input_stream, input_udp, input_tcp;
    private TextView label_connected;
    private Boolean connected = false;
    private static TCPControl tcpControl;

    private static final String LABEL_CONNECTED = "Conectat";
    private static final String LABEL_NOT_CONNECTED = "No conectat";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servidor_ip);
        init();
    }

    private void init() {
        input_ip = (EditText) findViewById(R.id.inputIP);
        input_stream = (EditText) findViewById(R.id.inputStreamPort);
        input_udp = (EditText) findViewById(R.id.inputUDPPort);
        input_tcp = (EditText) findViewById(R.id.inputTCPPort);
        Button ok_button = (Button) findViewById(R.id.ok_ip_selector);
        Button test_button = (Button) findViewById(R.id.button_test);
        label_connected = (TextView) findViewById(R.id.tv_conexio);

        tcpControl = new TCPControl(this);

        populateFields();

        ok_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Canviar tots els valors i retornar a la Activity Anterior
                NetworkParameters.getInstance().setIp(input_ip.getText().toString());
                NetworkParameters.getInstance().setPort_stream(input_stream.getText().toString());
                NetworkParameters.getInstance().setPort_udpserver(input_udp.getText().toString());
                NetworkParameters.getInstance().setPort_tcpcontrol(input_tcp.getText().toString());
                if(connected) setResult(Constants.RETURN_CONNECTED);
                else setResult(Constants.RETURN_NOCONNECTED);
                finish();
            }
        });

        test_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NetworkParameters.getInstance().setIp(input_ip.getText().toString());
                NetworkParameters.getInstance().setPort_stream(input_stream.getText().toString());
                NetworkParameters.getInstance().setPort_udpserver(input_udp.getText().toString());
                NetworkParameters.getInstance().setPort_tcpcontrol(input_tcp.getText().toString());
                tcpControl.doDiscovery();
                changeConnectedState(false);
            }
        });

        //Enregistrar obs
        JSONProtocol.getInstance().registerListener(this);

    }

    private void changeConnectedState(boolean connected) {
        if(connected) {
            label_connected.setText(LABEL_CONNECTED);
            label_connected.setTextColor(getResources().getColor(R.color.green));
            this.connected = true;
        } else {
            label_connected.setText(LABEL_NOT_CONNECTED);
            label_connected.setTextColor(getResources().getColor(R.color.red));
            this.connected = false;
        }
    }

    @Override
    public void onDiscoveryResponse(String ip, String port) {
        Toast.makeText(this,"IP: "+ip+"\nPort: "+port,Toast.LENGTH_LONG).show();
        changeConnectedState(true);
    }

    @Override
    public void onAppRequestResponse(List<Aplicacio> listApps) {

    }

    @Override
    public void onStartResponse(String ip, String port, String URI) {

    }

    @Override
    public void onStopResponse() {

    }

    @Override
    public void onErrorResponse() {
        Toast.makeText(this,"ERROR Recived!",Toast.LENGTH_SHORT).show();
    }

    private void populateFields(){
        input_ip.setText(NetworkParameters.getInstance().getIp());
        input_stream.setText(NetworkParameters.getInstance().getPort_stream());
        input_udp.setText(NetworkParameters.getInstance().getPort_udpserver());
        input_tcp.setText(NetworkParameters.getInstance().getPort_tcpcontrol());
    }
}
