import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Random;
import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;

public class JogadorClientImpl extends UnicastRemoteObject implements JogadorClient {

	private static volatile int i;

	protected JogadorClientImpl() throws RemoteException {
		super();
	}

	private static final long serialVersionUID = 1L;

	private static final int port = 52369;
	private static final String rmiServer = ":" + port + "/JogoServer";
	private static final String rmiClient = ":" + port + "/JogadorClient";

	private static boolean gameStarted = false;

	public static void main(String[] args) throws InterruptedException, RemoteException {
		if (args.length != 3) {
			System.out.println("Usage: java AdditionClient <server ip> <client ip> <num of plays>");
			System.exit(1);
		}

		try {
			System.setProperty("java.rmi.server.hostname", args[0]);
			LocateRegistry.createRegistry(port);
			System.out.println("java RMI registry created.");
		} catch (RemoteException e) {
			System.out.println("java RMI registry already exists.");
		}

		i = 0;

		while (true) {
            try {
                String client = "rmi://" + args[1] + rmiClient + i++;

                Naming.bind(client, new JogadorClientImpl());

                System.out.println("JogadorClient is ready.");
                break;
            } catch (AlreadyBoundException e) {
                System.out.println("Já ta bindado no i: " + i);
            } catch (MalformedURLException | RemoteException e) {
                System.out.println("JogadorClient failed");
                e.printStackTrace();
                break;
            }
        }


		String remoteHostName = args[0];
		String connectLocation = "rmi://" + remoteHostName + rmiServer;

		JogoServer jogoServer = null;
		try {
			System.out.println("Connecting to server at : " + connectLocation);
			jogoServer = (JogoServer) Naming.lookup(connectLocation);
			System.out.println("JogoServer is ready.");
		} catch (Exception e) {
			System.out.println("JogoServer failed: ");
			e.printStackTrace();
		}

		int idJogador = 0;

		try {
			System.out.println("Calling server to register player" + i);
			idJogador = jogoServer.registra();
			System.out.println("Player registered");
		} catch (RemoteException e) {
			e.printStackTrace();
		}

		while (!gameStarted) {
			Thread.sleep(1000);
		}

		int pontuacao = 0;
		Random gerador = new Random();
		int desiste;

		for (int i = 0; i < Integer.parseInt(args[2]); i++) {
			pontuacao += jogoServer.joga(idJogador);
			System.out.println(pontuacao + " N jogada " + i);
			desiste = gerador.nextInt(99);

			if (desiste == 0) {
				jogoServer.desiste(idJogador);
				System.exit(1);

			}
		}
		jogoServer.finaliza(idJogador);
		System.exit(1);
	}

	@Override
	public void inicia() throws RemoteException {
		System.out.println("Called back.");
		System.out.println("The game has started!");

		gameStarted = true;
	}

	@Override
	public void bonifica() throws RemoteException {
		System.out.println("Called back.");
		System.out.println("You got a bonus!");
	}

	@Override
	public void verifica() throws RemoteException {
		System.out.println("Called back.");
		System.out.println("Server ping test to verify player!");
	}
}
