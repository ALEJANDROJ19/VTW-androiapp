package marcer.pau.streaming;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import marcer.pau.streaming.model.NetworkParameters;
import marcer.pau.streaming.protocol.JSONProtocol;

//comments
//actualment el gyro esta hardcoded ens falta una classe externa encarregada
//de comprovar quin sensor vole usar i retornarlo per tal d'usarlo en aquesta classe

public class UDPHandler implements Runnable {

    SensorManager mSensorManager;
    Sensor mGyroscope;
    SensorEventListener mListener;
    Context mContext;
    HandlerThread mHandlerThread;
    //udp data
    private int DestPort;
    private InetAddress Addr;
    //static String SERVERADRRESS = "192.168.1.150";
    private DatagramSocket socket;

    public UDPHandler(Context mContext) {
        this.mContext = mContext;
    }

    public void run(){
        android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);
        //init sensor
        // TODO: Actually only gyroscope support
        mSensorManager = (SensorManager) this.mContext.getSystemService(Context.SENSOR_SERVICE);
        mGyroscope = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        //try to create the udp socket base
        try {
            initUDP();
        } catch (UnknownHostException | SocketException e) {
            e.printStackTrace();
        }

        //create a handler for a new sensor listener thread
        mHandlerThread = new HandlerThread("AccelerometerLogListener");
        mHandlerThread.start();
        Handler handler = new Handler(mHandlerThread.getLooper());

        //the sensor listener thread overrides
        mListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                if (event.sensor.getType() ==  Sensor.TYPE_GYROSCOPE) {
                    //create a new json object to put the sensor data
                    JSONObject data = new JSONObject();
                    try {
                        data = JSONProtocol.getGyroscopeMessage(event.values[0],event.values[1],event.values[2]);
//                        data.put("x", event.values[0]);
//                        data.put("y", event.values[1]);
//                        data.put("z", event.values[2]);
                        //send the object via udp datagram
                        SendUDPDatagram(data);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //debug logger
                    Log.d("UDPHandler", "Message x"+Float.toString(event.values[0])+
                                        "Message y"+Float.toString(event.values[1])+
                                        "Message z"+Float.toString(event.values[2]));
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
            }
        };
        //bind the sensors to the sensor listener and the new thread
        mSensorManager.registerListener(mListener, mGyroscope,
                SensorManager.SENSOR_DELAY_NORMAL, handler);
    }

    private void SendUDPDatagram(JSONObject data) throws IOException {
        //json to byte array using utf-8 charset
        byte[] DatagramBytes  = data.toString().getBytes("utf-8");
        DatagramPacket packet = new DatagramPacket(DatagramBytes, DatagramBytes.length, Addr,
                DestPort);
        socket.send(packet);
    }

    private void initUDP() throws UnknownHostException, SocketException{
//        DestPort = 21210;
        DestPort = Integer.valueOf(NetworkParameters.getInstance().getPort_udpserver());
        //Addr = InetAddress.getByName(SERVERADRRESS);
        Addr = InetAddress.getByName(NetworkParameters.getInstance().getIp());
        socket = new DatagramSocket(8888);
    }

    public void stopUDP() {
        socket.close();
        mSensorManager.unregisterListener(mListener);

    }

//        @NonNull
//        private byte [] float2ByteArray (float value)
//        {
//            return ByteBuffer.allocate(4).putFloat(value).array();
//        }
}
