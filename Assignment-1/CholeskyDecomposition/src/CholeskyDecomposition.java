import java.util.*;
/*
 * Class: CholeskyDecomposition
 * Description: Carries out and tests Cholesky Decomposition on SPD Matrices
 */
public class CholeskyDecomposition 
{
	/*
	 * Name: main()
	 * Parameters: String[]
	 * Output : void
	 * Description: Main function.
	 */
	public static void main(String[] args)
	{
	  double[][] decomposedMatrix =	choleskyDecompose(TestMatrices.test2);
      System.out.println(Arrays.deepToString(matrixSolver(TestMatrices.test1, TestMatrices.test2B))+ " \n");
      System.out.println(Arrays.deepToString(matrixTranspose(TestMatrices.test2))+ " \n");
	}
	
	/*
	 * Name: choleskyDecompose()
	 * Parameters: double[][]
	 * Output : double[][]
	 * Description: Takes a 2D Matrix inputMatrix and does Cholesky Decomposition on it to 
	 * 				produce a lower triangular matrix output. 
	 */
	public static double[][] choleskyDecompose(double[][] inputMatrix)
	{
		int lengthOfMatrix = inputMatrix.length;
		double[][] outputMatrix = new double[lengthOfMatrix][lengthOfMatrix]; 
		for(int i = 0; i < lengthOfMatrix;i++)
		{
			for(int k = 0; k < (i+1); k++)
			{
				double sum = 0;
				for(int j = 0; j < k; j++)
				{
					sum += outputMatrix[i][j] * outputMatrix[k][j];
				}
				if (i==k)
				{
					outputMatrix[i][k] = Math.sqrt(inputMatrix[i][i] - sum);
				}
				else
				{
					outputMatrix[i][k]= (1.0 / outputMatrix[k][k] * (inputMatrix[i][k] - sum));
				}	
			}
		}
		return outputMatrix;
	}
	public static double[][] matrixSolver(double[][] A, double[][] B)
	{
		if(B.length != A.length)
		{
			System.out.println("Matrix row dimensions must agree.");
		}
		int aRowDimension = A.length;
		int bColumnDimension = B[0].length;
		double[][] X = new double[aRowDimension][bColumnDimension];
		double[][] decomposedMatrix = choleskyDecompose(A);
	    for (int k = 0; k < aRowDimension; k++)
	    {
	          for (int i = k+1; i < aRowDimension; i++)
	          {
	             for (int j = 0; j < bColumnDimension; j++)
	             {
	                X[i][j] -= X[k][j]*decomposedMatrix[i][k];
	             }
	          }
	     }
		
//		 for (int k = 0; k < aRowDimension; k++) 
//		 {
//	         for (int i = k+1; i < aRowDimension; i++) 
//	         {
//	            for (int j = 0; j < bColumnDimension; j++) 
//	            {
//	               X[i][j] -= X[k][j]*A[i][k];
//	            }
//	         }
//	      } 
//		 for (int k = aRowDimension-1; k >= 0; k--)
//		 {
//	         for (int j = 0; j < bColumnDimension; j++) 
//	         {
//	            X[k][j] /= A[k][k];
//	         }
//	         for (int i = 0; i < k; i++) 
//	         {
//	            for (int j = 0; j < aRowDimension; j++) 
//	            {
//	               X[i][j] -= X[k][j]*A[i][k];
//	            }
//	         }
//	      }
		return X;
	}

	public static double[][] matrixTranspose(double[][] inputMatrix)
	{
		 int m = inputMatrix.length;
		 int n = inputMatrix[0].length;

		 double[][] trasposedMatrix = new double[n][m];

		    for(int x = 0; x < n; x++)
		    {
		        for(int y = 0; y < m; y++)
		        {
		            trasposedMatrix[x][y] = inputMatrix[y][x];
		        }
		    }

		  return trasposedMatrix;
	}
}
