

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Client {

	public static void main(String[] args) {
		
		 final String FILEPATH="C:\\Users\\Dell\\Desktop\\Apt\\Lab 7 - Network Progamming\\points.txt";
		 
		 
		try {
			System.out.println("CLIENT SIDE");
			ArrayList<Triangle> Triangles=new ArrayList<Triangle>();
			
			Scanner scanner = new Scanner(new FileReader(FILEPATH)); 
			int numTriangles=Integer.parseInt(scanner.nextLine());
			//read input file
			Point p1=null;
			Point p2=null;
			Point p3=null;
			for(int i=1;i<=4*numTriangles;i++) {
				String input = scanner.nextLine();// get the entire line after the prompt 
				///System.out.println(input);
				String[] numbers = input.split(" "); 
				//read first point
				if(i%4==2) {
					p1=new Point (Float.parseFloat(numbers[0]),Float.parseFloat(numbers[1]));
				}
				//read second Point
				else if(i%4==3) {
					p2=new Point (Float.parseFloat(numbers[0]),Float.parseFloat(numbers[1]));
					
				}
				//read third Point and construct the triangle
				else if(i%4==0) {
					p3=new Point (Float.parseFloat(numbers[0]),Float.parseFloat(numbers[1]));
					Triangle t=new Triangle(p1,p2,p3);
					Triangles.add(t);
				}
			}
			
			scanner.close();
			//to handle EOF Exception
			Triangle temp=null;
			Triangles.add(temp);
			Socket socket = new Socket("localhost", 6666);
			
			ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
			//this scanner s is  to read continue key from user in console (prompt key)
			Scanner s = new Scanner(System.in);
			int i=1;
			for (final Triangle t : Triangles) {
				//prompt a key form user
				System.out.println("Press any key to send the triangle...");
				s.nextLine();
				if(t!=null) {
					System.out.println("Sending the triangle # "+i+"........\n");
					
					System.out.println(t.toString());
					i++;
				}
			
					out.writeObject(t);
					out.flush();
					//take the response from socket input stream to see triangle'type
					if(t!=null) {
					BufferedReader socketReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					String triangleType = socketReader.readLine();
					System.out.println("The triangle Type is " + triangleType+"\n");
					}				
			}
			System.out.print("This Connection has been terminated");
			s.close();
			out.close();
			socket.close();
			
			

		} catch (Exception e) {
			System.out.println(e);
		} 
	}
}
