import java.lang.Math.*;

public class mathdd {

	public static double f(double x, double b)
	{
		double f;
		f = (57.19140625-(7.5625-b*b)*x*x)/(57.19140625-7.5625*x*x);
		f = Math.sqrt(Math.abs(f));
		return f;
	}
	
	public static double inter(double b)
	{
		int n=10000000;
		double h = 2.75/n;
		double sum = 0;
		for(double xi=0;xi<2.75;xi=xi+h)
		{
			sum += (f(xi,b)+f(xi+h,b))*h/2;
		}
		return sum;
		
	}
	
	
	public static void main(String args[])
	{
		for(double b=0.0; b<0.9;b=b+0.1)
		System.out.println("b="+b+", integer="+inter(b));
		
	}
	
}
