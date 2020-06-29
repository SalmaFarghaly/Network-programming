import java.io.Serializable;

//Composition relation ship
public class Triangle implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private Point p1;
	private Point p2;
	private Point p3;
	
	public Triangle(Point p1,Point p2,Point p3){
		this.p1=p1;
		this.p2=p2;
		this.p3=p3;
	}
	public Point getP1() {
		return this.p1;
	}
	public Point getP2() {
		return this.p2;
	}
	public Point getP3() {
		return this.p3;
	}
	public void print (){
		System.out.println("Point # 1 ");
		this.p1.print();
		System.out.println("Point # 2 ");
		this.p2.print();
		System.out.println("Point # 3 ");
		this.p3.print();
		
	}
	 public String toString() {
		 return new StringBuffer("First Point : ").append(this.p1.getX()).append(" ").append(this.p1.getY()).append(" ")
	                .append("Second Point: ").append(this.p2.getX()).append(" ").append(this.p2.getY()).append(" ").
	                	append("Third Point: ").append(this.p3.getX()).append(" ").append(this.p3.getY()).toString();
	 }
	

}
