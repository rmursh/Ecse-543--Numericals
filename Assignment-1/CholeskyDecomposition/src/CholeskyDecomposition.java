import java.util.*;

public class CholeskyDecomposition {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
      System.out.println("Hello World \n");
      double[][] test1 = {{3.75774013057833, 0.699305179151236, 0.212029932955794},
				{0.699305179151236, 3.17118668781156, 0.491484536490249},
				{0.212029932955794, 0.491484536490249, 3.04617139063115}};
		System.out.println(Arrays.deepToString(choleskyDecompose(test1))+ " \n");
		double[][] test2 = {{18, 22, 54, 42},
						{22, 70, 86, 62},
						{54, 86, 174, 134},
						{42, 62, 134, 106}};
		System.out.println(Arrays.deepToString(choleskyDecompose(test2))+" \n");
	}
	
	public static double[][] choleskyDecompose(double[][] a){
		int m = a.length;
		double[][] l = new double[m][m]; //automatically initialzed to 0's
		for(int i = 0; i< m;i++){
			for(int k = 0; k < (i+1); k++){
				double sum = 0;
				for(int j = 0; j < k; j++){
					sum += l[i][j] * l[k][j];
				}
				l[i][k] = (i == k) ? Math.sqrt(a[i][i] - sum) : (1.0 / l[k][k] * (a[i][k] - sum));
			}
		}
		return l;
	}
	

}
