import java.rmi.Remote;
import java.rmi.RemoteException;

public interface JogoServer extends Remote {
    public int registra() throws RemoteException;

    public int joga(int id) throws RemoteException;

    public int desiste(int id) throws RemoteException;

    public int finaliza(int id) throws RemoteException;
}
