
public class Gasifier {
	double  a=0.3, // 输入煤的灰分，入煤量为 1
			k=0.8, // 煤经过炉，没有被气化的部分。0.8表示 80%的煤没有被气化。
			x=0.98, // 炉出来物，被返回炉入口的比例
			
			b=0.2,  //炉出来物的灰分
			y=0;    //炉出来物的量


	
	double get_b() {
		return a*(1-k*x)/((a+k*(1-a))*(1-x)+a*x*(1-k));
	}

	double get_y(double b) {
		return a/(1-x)/b;
	}
	
	void verify() 
	{
		
		double t=(1-a)+(1-b)*x*y;
		System.out.printf("t=%f\n", t);
		double h=a+b*x*y;
		System.out.printf("h=%f\n", h);
		double t1=k*t;
		System.out.printf("t1=%f\n", t1);
		double y1=h+t1;
		System.out.printf("y1=%f, d=%f\n", y1,y-y1);
		double mei_out=y*(1-x)*(1-b);
		System.out.printf("mei out=%f\n", mei_out);
	}
	
	
	public static void main(String[] args) {
		Gasifier g = new Gasifier();
		g.b=g.get_b();
		g.y=g.get_y(g.b);
		System.out.printf("b,y=%f,%f\n", g.b,g.y);
		g.verify();
	}

	
}
