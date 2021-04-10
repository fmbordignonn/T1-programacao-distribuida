import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.registry.LocateRegistry;

public class JogoServerImpl extends UnicastRemoteObject implements JogoServer {

    protected JogoServerImpl() throws RemoteException {
        super();
    }

    private static final long serialVersionUID = 1L;

    public static void main(String[] args) throws RemoteException {
		if (args.length != 2) {
            //ADICIONAR PARAMETRO PALYERS
			System.out.println("Usage: java AdditionServer <server ip> <players>");
			System.exit(1);
		}

		try {
			System.setProperty("java.rmi.server.hostname", args[0]);
			LocateRegistry.createRegistry(52369);
			System.out.println("java RMI registry created.");
		} catch (RemoteException e) {
			System.out.println("java RMI registry already exists.");
		}

		try {
			String server = "rmi://" + args[0] + ":52369/Hello";
			Naming.rebind(server, new JogoServerImpl());
			System.out.println("JogoServer is ready.");
		} catch (Exception e) {
			System.out.println("JogoServer failed: " + e);
		}
    }

    @Override
    public int registra() throws RemoteException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int joga(int id) throws RemoteException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int desiste(int id) throws RemoteException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int finaliza(int id) throws RemoteException {
        // TODO Auto-generated method stub
        return 0;
    }

}