
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class IHMServerChat {
	private ServerSocket serveursocket;
	List<Socket> listeS = new ArrayList<Socket>();

	public void demarerServeur(int port) {//demarer la socket d'attente
		try {

			serveursocket = new ServerSocket(port);
			while (true) {
				Socket socket = serveursocket.accept(); // creation de socket pour les nouveaux client,
				listeS.add(socket);						//ajout de la socket a la liste des client.
				(new ThreadServer(socket, this)).start(); // creation d'un thread a part pour la gestion client.

			}
		} catch (IOException e) {

		}
	}

	void gererClient(Socket socket) throws IOException, ClassNotFoundException {
		byte[] buffer = new byte[1024];			//preparation de la recuperation du message par le serveur.
	
		InputStream input = socket.getInputStream();
		input.read(buffer);
		String msg = new String(buffer);
		System.out.println(" ca vient de : " + socket.getInetAddress() + ":" + socket.getPort() + "; " + msg); //affichage sur le serveur pour avoir une trace du fonctionnement.

		for (Socket s : listeS) {	//envoie a tout les client les nouveaux message.
			try {

				OutputStream o = s.getOutputStream();
				o.write(buffer);
				
			} catch (IOException e) {	//si il y a une erreur de connexion avec un client
				s.close();	//ferme la socket du client avec l'erreur.
				listeS.remove(s);	//retire le client de la liste
			}
		}

		return;

	}

	public static void main(String[] args) throws IOException, ClassNotFoundException {	//intialisation du serveur
		IHMServerChat Serveur1 = new IHMServerChat();
		System.out.println("le serveur est en marche sur le port " + args[0] + ".");
		Serveur1.demarerServeur(Integer.parseInt(args[0]));

	}
}