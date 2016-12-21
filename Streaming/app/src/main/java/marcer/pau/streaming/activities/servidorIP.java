package marcer.pau.streaming.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import marcer.pau.streaming.R;
import marcer.pau.streaming.model.NetworkParameters;

public class servidorIP extends AppCompatActivity {
    private EditText input_ip, input_stream, input_udp, input_tcp;
    private TextView label_connected;
    private Boolean connected;

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

        ok_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Canviar tots els valors i retornar a la Activity Anterior
                NetworkParameters.getInstance().setIp(input_ip.getText().toString());
                NetworkParameters.getInstance().setPort_stream(input_stream.getText().toString());
                NetworkParameters.getInstance().setPort_udpserver(input_udp.getText().toString());
                NetworkParameters.getInstance().setPort_tcpcontrol(input_tcp.getText().toString());
                finish();
            }
        });

        test_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Test discovery and set if connected

                //End
                if(connected) {
                    label_connected.setText(LABEL_CONNECTED);
                    label_connected.setTextColor(getResources().getColor(R.color.green));
                } else {
                    label_connected.setText(LABEL_NOT_CONNECTED);
                    label_connected.setTextColor(getResources().getColor(R.color.red));
                }
            }
        });

    }
}
