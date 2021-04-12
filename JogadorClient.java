import java.rmi.Remote;
import java.rmi.RemoteException;

public interface JogadorClient extends Remote{
    public void inicia() throws RemoteException;

    public void bonifica() throws RemoteException;

    public void verifica() throws RemoteException;
}