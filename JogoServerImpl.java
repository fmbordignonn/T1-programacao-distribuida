import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.rmi.registry.LocateRegistry;

public class JogoServerImpl extends UnicastRemoteObject implements JogoServer {

    protected JogoServerImpl() throws RemoteException {
        super();
    }

    private static final long serialVersionUID = 1L;

    private static final int port = 52369;
    private static final String rmiServer = ":" + port + "/JogoServer";

    private static volatile int idGenerator = 0;

    private static Map<Integer, Integer> playerScores;

    public static void main(String[] args) throws RemoteException, InterruptedException {
        if (args.length != 2) {
            // ADICIONAR PARAMETRO PALYERS
            System.out.println("Usage: java AdditionServer <server ip> <players>");
            System.exit(1);
        }

        try {
            System.setProperty("java.rmi.server.hostname", args[0]);
            LocateRegistry.createRegistry(port);
            System.out.println("java RMI registry created.");
        } catch (RemoteException e) {
            System.out.println("java RMI registry already exists.");
        }

        try {
            String server = "rmi://" + args[0] + rmiServer;
            Naming.rebind(server, new JogoServerImpl());
            System.out.println("JogoServer is ready.");
        } catch (Exception e) {
            System.out.println("JogoServer failed: " + e);
        }

        while (true) {

            Thread.sleep(1000);
        }

    }

    @Override
    public int registra() throws RemoteException {
        playerScores.put(idGenerator, 0);
        
        return idGenerator++;
    }

    @Override
    public int joga(int id) throws RemoteException {
        System.out.println(String.format("Jogador ID %d vai jogar....", id));

        

        playerScores.put(id, playerScores.get(id) + 5);

        int playTime = ThreadLocalRandom.current().nextInt(250, 950);

        System.out.println("Jogada vai levar " + playTime + "ms");

        try {
            Thread.sleep(playTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

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