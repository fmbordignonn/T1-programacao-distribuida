import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

public class JogadorClientImpl extends UnicastRemoteObject implements JogadorClient {

    protected JogadorClientImpl() throws RemoteException {
        super();
    }

    private static final long serialVersionUID = 1L;

    private static final int port = 52369;
    private static final String rmiServer = ":" + port + "/JogoServer";
    private static final String rmiClient = ":" + port + "/JogadorClient";

    public static void main(String[] args) {

        //ADICIONAR NUMBER OF PLAYS
		if (args.length != 3) {
			System.out.println("Usage: java AdditionClient <server ip> <client ip> <num of plays>");
			System.exit(1);
		}
	
		try {
			System.setProperty("java.rmi.server.hostname", args[1]);
			LocateRegistry.createRegistry(port);
			System.out.println("java RMI registry created.");
		} catch (RemoteException e) {
			System.out.println("java RMI registry already exists.");
		}

		try {
			String client = "rmi://" + args[1] + rmiClient;
			Naming.rebind(client, new JogadorClientImpl());
			System.out.println("JogadorClient is ready.");
		} catch (Exception e) {
			System.out.println("JogadorClient failed: " + e);
		}

		String remoteHostName = args[0];
		String connectLocation = "rmi://" + remoteHostName + rmiServer;

		JogoServer jogoServer = null;
		try {
			System.out.println("Connecting to server at : " + connectLocation);
			jogoServer = (JogoServer) Naming.lookup(connectLocation);
			System.out.println ("JogoServer is ready.");
		} catch (Exception e) {
			System.out.println ("JogoServer failed: ");
			e.printStackTrace();
		}

		while (true) {
			try {
				jogoServer.registra();
				System.out.println("Calling server to register" );
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException ex) {}
		}
	}
    

    @Override
    public void inicia() throws RemoteException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void bonifica() throws RemoteException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void verifica() throws RemoteException {
        // TODO Auto-generated method stub
        
    }

}