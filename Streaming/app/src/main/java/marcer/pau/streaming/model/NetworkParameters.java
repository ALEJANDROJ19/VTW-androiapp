package marcer.pau.streaming.model;

/**
 * Created by ALEJANDRO on 20/12/2016.
 */

public class NetworkParameters {
    private String ip;
    private String port_stream;
    private String port_udpserver;
    private String port_tcpcontrol;
    private String uri;

    private static NetworkParameters networkParameters;
    public static NetworkParameters getInstance() {
        if (networkParameters == null)
            networkParameters = new NetworkParameters();
        return networkParameters;
    }

    private NetworkParameters() {
        this.ip = "192.168.1.33";
        this.port_stream = "8090";
        this.port_udpserver = "21210";
        this.port_tcpcontrol = "21211";
        this.uri = "test1.webm";
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort_stream() {
        return port_stream;
    }

    public void setPort_stream(String port_stream) {
        this.port_stream = port_stream;
    }

    public String getPort_udpserver() {
        return port_udpserver;
    }

    public void setPort_udpserver(String port_udpserver) {
        this.port_udpserver = port_udpserver;
    }

    public String getPort_tcpcontrol() {
        return port_tcpcontrol;
    }

    public void setPort_tcpcontrol(String port_tcpcontrol) {
        this.port_tcpcontrol = port_tcpcontrol;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    @Override
    public String toString() {
        return "NetworkParameters{" +
                "ip='" + ip + '\'' +
                ", port_stream='" + port_stream + '\'' +
                ", port_udpserver='" + port_udpserver + '\'' +
                ", port_tcpcontrol='" + port_tcpcontrol + '\'' +
                '}';
    }
}
