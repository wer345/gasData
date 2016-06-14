
public class Gas {

	// values in the list
	// Cp_A, Cp_B, Cp_C, Cp_D, 生成焓，生成自由能
	static double[] d_CO = 	{3.376, 0.557,	0,	-0.031 ,-110525, -137169};
	static double[] d_CO2 = {5.457,	1.045,	0,	-1.157 , -393509, 394359};
	static double[] d_H2 = 	{3.249,	0.422,	0,	0.083 ,	0,	0};
	static double[] d_O2 = 	{3.639,	0.506,	0,	-0.227,	0,	0 };
	static double[] d_H2O = {3.47,	1.45,	0,	0.121,-241818, -228572 };
	static double[] d_C = 	{1.771,	0.771,	0,	-0.867 ,0,	0};

	static double R=8.314;
	static double K0=273.15;
	
	double Cp_A,Cp_B,Cp_C,Cp_D, s_H,s_G;
	public void set(double[] d)
	{
		Cp_A=d[0];
		Cp_B=d[1]*1E-3;
		Cp_C=d[2]*1E-6;
		Cp_D=d[3]*1E5;
		s_H=d[4];
		s_G=d[5];
	}
	
	/**
	 * @param T - temperatire in Kelvin
	 * @return  Cp = J/mol
	 */
	public double Cp_T(double T) {
		double Cp=Cp_A+Cp_B*T+Cp_C*T*T+Cp_D/T/T;
		return R*Cp;
	}
	
	/**
	 * @param C - temperatire in C
	 * @return  Cp = J/mol
	 */
	public double Cp_C(double C) {
		return Cp_T(C+K0);
	}
	
	// ICPH
	public double dH_T(double T0,double T) {
		double tao=T/T0;
		double dh=Cp_A*T0*(tao-1)
				+0.5*Cp_B*T0*T0*(tao*tao-1)
				//+h_C*T0*T0*T0*(tao*tao*tao-1)/3
				+Cp_D*(tao-1)/tao/T0;
		double dh1=Cp_A*(T-T0)+0.5*Cp_B*(T*T-T0*T0)+Cp_D*(1/T0-1/T);
		return R*dh;
	}
	
	public double dH_C(double C0,double C) {
		return dH_T(C0+K0,C+K0);
	}
	
	public Gas(String name) throws Exception
	{
		if (name.equals("CO"))
			set(d_CO);
		else if (name.equals("CO2"))
			set(d_CO2);
		else if (name.equals("H2"))
			set(d_H2);
		else if (name.equals("O2"))
			set(d_O2);
		else if (name.equals("H2O"))
			set(d_H2O);
		else if (name.equals("C"))
			set(d_C);
		else {
			throw new Exception();
		}
	}
	
	public static void test1() throws Exception{
		
		Gas g_H2O=new Gas("H2O");
		Gas g_C=new Gas("C");

		Gas g_H2=new Gas("H2");
		Gas g_CO=new Gas("CO");
		
		double Cp_H2O=g_H2O.Cp_C(25);
		double Cp_C=g_C.Cp_C(27);
		double T0=25;
		double T=900;
		
		double dH_H2O=g_H2O.dH_C(T0, T);
		double dH_C=g_C.dH_C(T0, T);
		double dH_H2=g_H2.dH_C(T0, T);
		double dH_CO=g_CO.dH_C(T0, T);
		
		System.out.printf("Cp_H2O at 25C is %f J/K\n",Cp_H2O);
		System.out.printf("Cp_C at 25C is %f J/K\n",Cp_C);
		
		System.out.printf("dH_H2O from 25C to 900Cis %f J\n",dH_H2O);
		System.out.printf("dH_C from 25C to 900Cis %f J\n",dH_C);
		System.out.printf("dH_C+H2O from 25C to 900Cis %f J\n",dH_C+dH_H2O);
		
		System.out.printf("dH_H2 from 25C to 900Cis %f J\n",dH_H2);
		System.out.printf("dH_CO from 25C to 900Cis %f J\n",dH_CO);
		System.out.printf("dH_H2+CO from 25C to 900Cis %f J\n",dH_H2+dH_CO);
		
	}
	

	public static void test2() throws Exception{
		Gas g_CO=new Gas("C");
		Gas g_H2O=new Gas("H2O");
		
		Gas g_H2=new Gas("H2");
		Gas g_CO2=new Gas("CO2");
		double h1=g_CO.s_H+g_H2O.s_H;
		double h2=g_H2.s_H+g_CO2.s_H;
		
		System.out.printf("%f - %f = %f\n",h1,h2,h2-h1);

		double T0=25;
		double T=300;
		double h_CO=g_CO.dH_C(T0, T);
		double h_H2O=g_H2O.dH_C(T0, T);
		
		double h_H2=g_H2.dH_C(T0, T);
		double h_CO2=g_CO2.dH_C(T0, T);
		
		double dh1= h_CO + h_H2O;
		double dh2= h_H2 + h_CO2;
		System.out.printf("%f - %f = %f\n",dh1,dh2,dh2-dh1);
		
	}

	public static void test3() throws Exception{
		Gas g_H2=new Gas("H2");
		Gas g_O2=new Gas("O2");
		Gas g_H2O=new Gas("H2O");
		
		double h1=g_H2.s_H+g_O2.s_H;
		double h2=g_H2O.s_H;
		
		System.out.printf("%f - %f = %f\n",h1,h2,h2-h1);

		double T0=25;
		double T=170;
		double h_H2=g_H2.dH_C(T0, T);
		double h_O2=g_O2.dH_C(T0, T);
		
		double h_H2O=g_H2O.dH_C(T0, T);
		
		
		double dh1= h_H2 + h_O2;
		double dh2= h_H2O;
		
		System.out.printf("%f - %f = %f\n",dh1,dh2,dh2-dh1);
		
		System.out.printf("Total = %f\n",h2-h1 + dh2-dh1);
	}
	
	public static void h_list() throws Exception{
		String [] gs={"C","H2","CO","CO2","H2O","O2"};
		for (String gn:gs) {
			Gas g = new Gas(gn);
			double h0=g.s_H;
			double ht=g.dH_C(25, 400);
			System.out.printf("%3s: h0=%9.0f, ht=%9.1f, h=%9.1f\n",gn,h0,ht,h0+ht);
		}
		
	}
	
	public static void Cp_list() throws Exception{
		Gas co2=new Gas("CO2");
		double molMass=44.01; // g/mol
		for(double t=300;t<=2000;t+=50) {
			double Cp=co2.Cp_T(t);
			Cp=Cp/molMass*1000;
			System.out.printf("T=%3.0f; Cp=%7.4f\n",t,Cp);
		}
	}
	
	public static void main(String[] args) {
		try {
			//test1();
			//test3();
			// h_list();
			Cp_list();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
