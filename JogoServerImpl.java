import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;
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
    private static final String rmiClient = ":" + port + "/JogadorClient";

    private static volatile int idGenerator = 0;

    private static String remoteHostName;

    private static Map<Integer, Integer> playerScores;

    private static Map<Integer, String> clientIPs;

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
            // verificar size
            for (int i = 0; i < clientIPs.size(); i++) {
                // monta a string pra buscar o client na sua interface VALIDAR
                String connectLocation = "rmi://" + clientIPs.get(i) + rmiClient + i;

                JogadorClient jogadorClient = null;

                try {
                    System.out.println("Connecting to client at " + connectLocation);
                    jogadorClient = (JogadorClient) Naming.lookup(connectLocation);

                } catch (Exception ex) {
                    System.err.println("Failed to callback");
                    ex.printStackTrace();
                }

                try {
                    jogadorClient.verifica();
                } catch (RemoteException ex) {
                    System.err.println("An error has occurred while calling verifica() method");
                    ex.printStackTrace();
                }
            }

            Thread.sleep(5000);
        }

    }

    @Override
    public int registra() throws RemoteException {
        try {
            clientIPs.put(idGenerator, getClientHost());
        } catch (ServerNotActiveException ex) {
            ex.printStackTrace();
        }

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
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

        return playerScores.get(id);
    }

    @Override
    public int desiste(int id) throws RemoteException {
        System.out.println(String.format("Jogador ID %s desistiu do jogo, retornando sua pontuação final", id));

        int finalScore = playerScores.get(id);

        playerScores.remove(id);

        System.out.println("Desconectando cliente...");

        return finalScore;
    }

    @Override
    public int finaliza(int id) throws RemoteException {
        System.out.println(String.format("Jogador ID %s finalizou o jogo, retornando sua pontuação final", id));

        int finalScore = playerScores.get(id);

        playerScores.remove(id);

        System.out.println("Desconectando cliente...");

        return finalScore;
    }

}