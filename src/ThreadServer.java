import java.io.IOException;
import java.net.Socket;


public class ThreadServer extends Thread{
	private Socket socket;
	private IHMServerChat srv;

	
	
	public ThreadServer(Socket socket,IHMServerChat srv) {
		
		this.socket = socket;
		this.srv=srv;
	
	}

	public void run() {
		while(true){ //reception des message envoyer par les client en permanance
    		try {
				this.srv.gererClient(socket);
			} catch (ClassNotFoundException | IOException e) {
				
				return;
			}
    	}
	}
	
	
}