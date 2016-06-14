// 二氧化碳储罐物理爆炸能量及波及半径的定量评价
// http://www.cntcw.com/Doc/WebNote/201110/Y2011M10D31H10m20s48/u4E8Cu6C27u5316u78B3u50A8u7F50u7269u7406u7206u70B8u80FDu91CFu53CAu6CE2u53CAu534Au5F84u7684u5B9Au91CFu8BC4u4EF7.pdf

public class CO2Energy {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		double 
			P1=5.733E6,
			P2=101325,
			k=1.293,
			V=18;
		
		//P1=15E6;V=0.05;
		
		//爆炸能量
		double W= P1*V*(1-Math.pow(P2/P1,(k-1)/k))/(k-1);
		//W=Math.pow(2, 3);
		System.out.printf("W=%6.4f MJ\n",W/1E6);
		double Wtnt=W/4.52E6;
		System.out.printf("Wtnt=%5.2f\n",Wtnt);
		
		//死亡半径
		double RA=13.6*Math.pow(Wtnt/1000, 0.37);
		System.out.printf("RA=%5.2f\n",RA);
		
		//重伤区
		//起点内径为RA ,外径记为RB , RB 表示处于该
		//圆周附近人员因冲击波作用耳膜破裂(重伤) 的概率
		//为0.5 ,它要求的冲击波超出峰值为44000Pa
		
		//Set RB
		double RB=13.9;
		double Z=RB*Math.pow(P2/W, 1/3);
		double dPs= 0.137/(Z*Z*Z)+0.119/(Z*Z)+0.269/Z-0.019;
		System.out.printf("dPs=%f\n",dPs);
		
	}

}
