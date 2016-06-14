// ������̼��������ը�����������뾶�Ķ�������
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
		
		//��ը����
		double W= P1*V*(1-Math.pow(P2/P1,(k-1)/k))/(k-1);
		//W=Math.pow(2, 3);
		System.out.printf("W=%6.4f MJ\n",W/1E6);
		double Wtnt=W/4.52E6;
		System.out.printf("Wtnt=%5.2f\n",Wtnt);
		
		//�����뾶
		double RA=13.6*Math.pow(Wtnt/1000, 0.37);
		System.out.printf("RA=%5.2f\n",RA);
		
		//������
		//����ھ�ΪRA ,�⾶��ΪRB , RB ��ʾ���ڸ�
		//Բ�ܸ�����Ա���������ö�Ĥ����(����) �ĸ���
		//Ϊ0.5 ,��Ҫ��ĳ����������ֵΪ44000Pa
		
		//Set RB
		double RB=13.9;
		double Z=RB*Math.pow(P2/W, 1/3);
		double dPs= 0.137/(Z*Z*Z)+0.119/(Z*Z)+0.269/Z-0.019;
		System.out.printf("dPs=%f\n",dPs);
		
	}

}
