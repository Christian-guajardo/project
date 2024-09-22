
import java.io.IOException;
import java.io.InputStream;

import java.net.Socket;


public class ThreadClient extends Thread{
	private Socket socket;
	InputStream input ;
	
	ChatController chat;
	byte[] buffer = new byte[1024];
	
	
	public ThreadClient(Socket socket, ChatController chat) throws IOException {
		
		this.socket = socket;
		this.input= this.socket.getInputStream();
		this.chat =chat;
	}


	public void run() {
		while(true){	//reception des message envoyer par le serveur. 
    		try {
    			
    			input.read(buffer);
    			String msg = new String(buffer);
				this.chat.affichage(msg);
				
				
			} catch (IOException e) {
				return;
			}
    	}
	}
	
	
}