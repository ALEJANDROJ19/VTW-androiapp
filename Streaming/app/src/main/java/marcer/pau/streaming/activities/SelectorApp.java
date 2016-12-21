package marcer.pau.streaming.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.LinkedList;
import java.util.List;

import marcer.pau.streaming.FullscreenActivity;
import marcer.pau.streaming.R;
import marcer.pau.streaming.model.Aplicacio;
import marcer.pau.streaming.model.ModelAplicacions;

public class SelectorApp extends Activity implements ModelAplicacions.ModelAplicacionsListener{
    private ImageButton app1,app2,app3,app4,app5,app6,app7,app8,app9;
    private List<ImageButton> lapps = new LinkedList<>();
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
        app1 = (ImageButton) findViewById(R.id.imageButton1); lapps.add(app1);
        app2 = (ImageButton) findViewById(R.id.imageButton2); lapps.add(app2);
        app3 = (ImageButton) findViewById(R.id.imageButton3); lapps.add(app3);
        app4 = (ImageButton) findViewById(R.id.imageButton4); lapps.add(app4);
        app5 = (ImageButton) findViewById(R.id.imageButton5); lapps.add(app5);
        app6 = (ImageButton) findViewById(R.id.imageButton6); lapps.add(app6);
        app7 = (ImageButton) findViewById(R.id.imageButton7); lapps.add(app7);
        app8 = (ImageButton) findViewById(R.id.imageButton8); lapps.add(app8);
        app9 = (ImageButton) findViewById(R.id.imageButton9); lapps.add(app9);

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
//                startActivityForResult(intent,1);
                startActivity(intent);
            }
        });
    }

    private void app_escollida(ImageButton view) {
        //Aplicacio app = model.getListApps().get(lapps.indexOf(view));
        //TODO; Cridar al protocol
        startActivity(new Intent(SelectorApp.this, FullscreenActivity.class));
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == RESULT_OK) {
//
//        } else {
//        }
//    }

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
