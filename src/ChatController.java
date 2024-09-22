import java.io.*;
import java.net.InetAddress;

import java.net.Socket;
import java.net.URL;

import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ChatController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextArea areaDiscussion;

    @FXML
    private TextField entreeAdresseIP;

    @FXML
    private TextField entreeMessage;

    @FXML
    private TextField entreePort;

    @FXML
    private TextField entreePseudo;

    @FXML
    private Label labelEtatConnexion;
    
    OutputStream output;
    InputStream input;
	Socket socket;
	//List<String> myList = new ArrayList<String>();
	
	
    

    @FXML
    void actionBoutonConnexion(ActionEvent event) {		//creation de socket TCP
    	
    	try {
			socket = new Socket(InetAddress.getByName(entreeAdresseIP.getText()),Integer.parseInt(entreePort.getText()));
			output = socket.getOutputStream();
	    	input = socket.getInputStream();
	    	Thread th = new ThreadClient(socket,this); 			//creation de thread pour la gestion de la reception des message
	    	labelEtatConnexion.setText("Connecté");
	    	areaDiscussion.setText("");
	    	th.start();
		} catch (NumberFormatException | IOException e) {
			areaDiscussion.setText(areaDiscussion.getText()+"/!\\ la connexion avec le serveur a échoué\n");
		}
    	
    	
    	
    }

    @FXML
    void actionBoutonDeconnexion(ActionEvent event) throws IOException {		//deconexion
    	socket.close();
    	labelEtatConnexion.setText("déconnecté");
    }

    @FXML
    void actionBoutonEnvoyer(ActionEvent event) {
    	try {
    		if( entreePseudo.getText()!="") 		//verification du pseudo
    		{
    		String chaine = entreePseudo.getText() + "-: "+ entreeMessage.getText()+"\n"; //creation du nouveaux message pour le serveur
    		entreeMessage.setText("");
			output.write(chaine.getBytes());		//envoie au serveur du message
    		}else{
    			//si nonpseudo
    		areaDiscussion.setText(areaDiscussion.getText()+"/!\\ Vous devez avoir un pseudo pour continuer\n");
    		}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			areaDiscussion.setText(areaDiscussion.getText()+"/!\\ la connexion avec le serveur a été intéronput\n");
			labelEtatConnexion.setText("déconnecté");	//deconexion en cas de problème de connexion
		}
    }
    void affichage(String chaine){
    	areaDiscussion.setText(areaDiscussion.getText()+chaine);	//affichage sur la zone de disccusion. (tout les message depuis la connexion du client)
    }

    @FXML
    void initialize() {
    	
        
    }

}
