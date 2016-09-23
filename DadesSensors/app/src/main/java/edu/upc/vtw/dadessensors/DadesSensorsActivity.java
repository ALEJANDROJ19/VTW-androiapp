package edu.upc.vtw.dadessensors;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.nio.ByteBuffer;
import java.util.List;

public class DadesSensorsActivity extends AppCompatActivity implements SensorEventListener {

    //TODO: Modificar variables ja que no totes son necesaries en la app final, pude en un debug mode
    TextView logsensor, dadarebudaaccX,dadarebudaaccY,dadarebudaaccZ,dadarebudagirI,
            dadarebudagirJ,dadarebudagirK;
    //
    SensorManager mSensorManager;
    //TODO: Repasar els sensors que volem usar finalment en la nostre app acc+gyro?
    Sensor mSensorAcc,mSensorGyr;
    //thread que controla l'enviament de dades
    Thread UdpThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dades_sensors);

        //TODO: Revisar segons els sensors escollits
        //TODO: Encapsular en metodes init() com hem vist a damo per portabilitat
        //TODO: Modificar el codi per usar estaticament els sensors destitjats si es troben disponibles
        // i no usar la list en el for.
//        dadarebudaaccX = (TextView) findViewById(R.id.dadarebudaaccX);
//        dadarebudaaccY = (TextView) findViewById(R.id.dadarebudaaccY);
//        dadarebudaaccZ = (TextView) findViewById(R.id.dadarebudaaccZ);
        dadarebudagirI = (TextView) findViewById(R.id.dadarebudagirI);
        dadarebudagirJ = (TextView) findViewById(R.id.dadarebudagirJ);
        dadarebudagirK = (TextView) findViewById(R.id.dadarebudagirK);

        logsensor = (TextView) findViewById(R.id.logsensor);

        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        List<Sensor> llistaSensors = sensorManager.getSensorList(Sensor.TYPE_ALL);

        // Get the sensors to use

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        //mSensorAcc = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorGyr = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        //mSensorManager.registerListener(this,mSensorAcc,SensorManager.SENSOR_DELAY_UI);
        mSensorManager.registerListener(this,mSensorGyr,SensorManager.SENSOR_DELAY_UI);

        for(Sensor sensor: llistaSensors) {
            logsensors(sensor.getName());
        }
    }

    @Override
    public void onPostCreate(Bundle savedInstanceStat){
        super.onPostCreate(savedInstanceStat);
        Runnable UdpRunnable = new UDPHandler(DadesSensorsActivity.this.getBaseContext());
        UdpThread = new Thread(UdpRunnable);
        UdpThread.start();

        //TODO: Repasar el metode isAlive que no se que ace aqui
        UdpThread.isAlive();

        //TODO: Borrar si no es necesari que no ho es; es debug
//        Thread thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                Log.d("DEBUG","Alive");
//            }
//        });
//        thread.start();
        //
//
   }
    //TODO: Metode inecesari millor tractar els sensors de forma estatica
    private void logsensors(String string) {

        logsensor.append(string + "\n");

    }


    @Override
    public void onSensorChanged(SensorEvent event) {

        //TODO: Repasar el meyode dons hem de enviar el sensor escollit posteriorment
        if (event.accuracy == SensorManager.SENSOR_STATUS_UNRELIABLE) {
//
//            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
//                dadarebudaaccX.setText(R.string.act_main_no_acuracy);
//                dadarebudaaccY.setText(R.string.act_main_no_acuracy);
//                dadarebudaaccZ.setText(R.string.act_main_no_acuracy);
//            } else {
                if (event.sensor.getType() == Sensor.TYPE_ORIENTATION || event.sensor.getType() ==  Sensor.TYPE_GYROSCOPE) {
                    dadarebudagirI.setText(R.string.act_main_no_acuracy);
                    dadarebudagirJ.setText(R.string.act_main_no_acuracy);
                    dadarebudagirK.setText(R.string.act_main_no_acuracy);
                }
            //}
        }

//        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
//            dadarebudaaccX.setText("x = " + Float.toString(event.values[0]));
//            dadarebudaaccY.setText("y = " + Float.toString(event.values[1]));
//            dadarebudaaccZ.setText("z = " + Float.toString(event.values[2]));
//            //detectShake(event);
//        } else {
            if (event.sensor.getType() == Sensor.TYPE_ORIENTATION || event.sensor.getType() ==  Sensor.TYPE_GYROSCOPE) {
                dadarebudagirI.setText("x = " + Float.toString(event.values[0]));
                dadarebudagirJ.setText("y = " + Float.toString(event.values[1]));
                dadarebudagirK.setText("z = " + Float.toString(event.values[2]));
                //detectRotation(event);
            }
        //}
    }

    //TODO: falta implementar el metode
    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
