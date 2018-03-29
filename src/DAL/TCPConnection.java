package DAL;

public class TCPConnection {
    private static TCPConnection ourInstance = new TCPConnection();



    public static TCPConnection getInstance() {
        return ourInstance;
    }

    private TCPConnection() {
    }
}
