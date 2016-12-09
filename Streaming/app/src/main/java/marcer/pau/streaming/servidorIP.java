package marcer.pau.streaming;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class servidorIP extends AppCompatActivity {
    private String ip;
    private String port_stream;
    private String port_udpserver;
    private String port_tcpcontrol;

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
                ip = input_ip.getText().toString();
                port_stream = input_stream.getText().toString();
                port_udpserver = input_udp.getText().toString();
                port_tcpcontrol = input_tcp.getText().toString();
                //TODO: notificar (?) || discovery + request apps?
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

    public String getIp() {
        return ip;
    }

    public String getPort_stream() {
        return port_stream;
    }

    public String getPort_udpserver() {
        return port_udpserver;
    }

    public String getPort_tcpcontrol() { return port_tcpcontrol; }
}
