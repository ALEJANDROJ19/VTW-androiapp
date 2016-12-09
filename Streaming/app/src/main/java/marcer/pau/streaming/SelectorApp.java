package marcer.pau.streaming;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.List;

public class SelectorApp extends Activity implements ModelAplicacions.ModelAplicacionsListener{
    private ImageButton app1,app2,app3,app4,app5,app6,app7,app8,app9;
    private List<ImageButton> lapps;
    private Button seleccionar_servidor;
    private ModelAplicacions model;

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
        app1 = (ImageButton) findViewById(R.id.imageButton1); lapps.add(1,app1);
        app2 = (ImageButton) findViewById(R.id.imageButton2); lapps.add(2,app2);
        app3 = (ImageButton) findViewById(R.id.imageButton3); lapps.add(3,app3);
        app4 = (ImageButton) findViewById(R.id.imageButton4); lapps.add(4,app4);
        app5 = (ImageButton) findViewById(R.id.imageButton5); lapps.add(5,app5);
        app6 = (ImageButton) findViewById(R.id.imageButton6); lapps.add(6,app6);
        app7 = (ImageButton) findViewById(R.id.imageButton7); lapps.add(7,app7);
        app8 = (ImageButton) findViewById(R.id.imageButton8); lapps.add(8,app8);
        app9 = (ImageButton) findViewById(R.id.imageButton9); lapps.add(9,app9);

        app1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                app_escollida((ImageButton) view);
            }
        });
        app2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                app_escollida((ImageButton) view);
            }
        });
        app3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                app_escollida((ImageButton) view);
            }
        });
        app4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                app_escollida((ImageButton) view);
            }
        });
        app5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                app_escollida((ImageButton) view);
            }
        });
        app6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                app_escollida((ImageButton) view);
            }
        });
        app7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                app_escollida((ImageButton) view);
            }
        });
        app8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                app_escollida((ImageButton) view);
            }
        });
        app9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                app_escollida((ImageButton) view);
            }
        });

        seleccionar_servidor = (Button) findViewById(R.id.buttonConectar);
        seleccionar_servidor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelectorApp.this, servidorIP.class);
                startActivity(intent);
            }
        });
    }

    private void app_escollida(ImageButton view) {
        Aplicacio app = model.getListApps().get(lapps.indexOf(view));
        //TODO; Fer alguna cossa amb això
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
