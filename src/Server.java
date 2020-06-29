import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.lang.Math; 



public class Server {
	//service for knowing type of triangle
	public static final int PORT=6666;
	
	public static int clientNumber = 0; 
	
	public static void main(String[] args) throws IOException {
		
		System.out.println("The server started .. ");

		//for serving multiple clients at the same time
		//create a thread for each client to serve him
						ServerSocket ss = new ServerSocket(PORT);
						while (true) {
							clientNumber++;
							/*======================================================================*/
							/*SORRY I HAVE MADE A MISTAKE IN THE VIDEO*/
							/*the thread takes CLIENT SOCKET and clientNumber in it's constructor*/
							/*not the SERVER SOCKET as i mentioned in the video*/
							new GetTriangleType(ss.accept(), clientNumber).start();
						}


	}
	
	///=========================GetTriangleType as an enclosing class=========================////
	private static class GetTriangleType extends Thread {
		Socket socket;
		int clientNo;

		public GetTriangleType(Socket s, int clientNo) {
			this.socket = s;
			this.clientNo = clientNo;
			System.out.println("Connection with Client #" + clientNo + "at socket " + socket);
		}

		public void run() {
			try {
				System.out.println("SERVER Thread");
				//read objects from the socket
				ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
				//write type of triangle on socket and sets auto-flush to true
				PrintWriter out = new PrintWriter(this.socket.getOutputStream(), true);
				 Triangle obj;
				 try {
					 //when the server finds Triangle equal to null ,he will understand that client has sent all the triangles he wants to know it's type
					 //and as a result of that he will terminate the connection
					while((obj=(Triangle) ois.readObject())!=null) {
					
						int d1=(int) Math.round(calculateDistanceBetweenPoints(obj.getP1(),obj.getP2()));
						int d2=(int) Math.round(calculateDistanceBetweenPoints(obj.getP3(),obj.getP2()));
						int d3=(int) Math.round(calculateDistanceBetweenPoints(obj.getP1(),obj.getP3()));
						System.out.print( d1+" "+d2+" "+d3+"\n");
						
						if(d1==d2&&d2==d3&&d1==d3)
						{
							out.println("Equilateral");
						}
						else if(d1==d2||d2==d3||d1==d3) {
							out.println("Isosscelces");
						}
						else
						{
							out.println("Scalene");
						}
					 }
				} catch (ClassNotFoundException e) {
			
					System.out.println("ERRR"+e);
					e.printStackTrace();
				}
				out.close();
		        ois.close();
				socket.close();
				System.out.println("Connection with Client #" + this.clientNo + " finished..");
			} catch (IOException e) {
				System.out.println("ERRR"+e);
				e.printStackTrace();
			}
		}
		//function to calculate distance between 2 points
		public double calculateDistanceBetweenPoints(Point p1,Point p2) {       
			return Math.sqrt((p2.getY() - p1.getY()) * (p2.getY() - p1.getY()) + (p2.getX() - p1.getX()) * (p2.getX() - p1.getX()));
		}
	}

}



