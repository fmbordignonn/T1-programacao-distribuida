import java.rmi.RemoteException;

public interface JogadorClient {
    public void inicia() throws RemoteException;

    public void bonifica() throws RemoteException;

    public void verifica() throws RemoteException;
}