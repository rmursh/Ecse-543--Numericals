import java.util.*;
import java.io.*;

/*
 * Class: CholeskyDecomposition
 * Description: Carries out and tests Cholesky Decomposition on SPD Matrices
 */
public class Assignment1 
{
	public static String pathQ1 = "C:\\Users\\rmursh\\workspace\\Ecse-543--Numericals\\Assignment-1\\Test.xlsx";
	private static int questionNum = 21;
	private static final int J = 0;
	private static final int R = 1;
	private static final int E = 2;
	
	public static double halfBandwidth = 0;
	static ArrayList<Integer> firstNonZero = new ArrayList<Integer>();

	private static final double W = 1.25;
	/*
	 * Name: main()
	 * Parameters: String[]
	 * Output : void
	 * Description: Main function.
	 */
	@SuppressWarnings("deprecation")
	public static void main(String[] args)
	{
		if(questionNum == 1)
		{
		  System.out.println("X1 = \n");
		  matrixPrint(matrixSolver(TestMatrices.test1, TestMatrices.test1B));
		  System.out.println("X2 = \n");
		  matrixPrint(matrixSolver(TestMatrices.test2, TestMatrices.test2B));
		  System.out.println("X3 = \n");
		  matrixPrint(matrixSolver(TestMatrices.test3, TestMatrices.test3B));
		  System.out.println("X4 = \n");
		  matrixPrint(matrixSolver(TestMatrices.test4, TestMatrices.test4B));
		  System.out.println("X5 = \n");
		  matrixPrint(matrixSolver(TestMatrices.test5, TestMatrices.test5B));
		  System.out.println("XManual = \n");
		  matrixPrint(matrixSolver(TestMatrices.testManualMatA, TestMatrices.testManualMatB));
		  
		  ExcelImport worksheet = new ExcelImport(pathQ1);
		  
	      for(int i = 0 ; i < worksheet.getWorkbook().getNumberOfSheets(); i = i+2)
	      {
		      double[][] networks = worksheet.importNetworkBranches(i);
		      double[][] A = worksheet.importNetworkBranches(i+1);
		      double[][] At = matrixTranspose(A);
		      double[][] Y = new double[A[0].length][A[0].length];
		      double[][] Jk = new double[A[0].length][1], Rk = new double[A[0].length][1], Ek = new double[A[0].length][1];
		      for(int j =0; j < Y.length ; j++)
		      {
		    	  for (int k = 0; k < Y[0].length; k++)
		    	  {
		    		  Jk[k][0] = networks[k][J];
		    		  Rk[k][0] = networks[k][R];
		    		  Ek[k][0] = networks[k][E];
		    		  if(j == k)
		    		  {
		    			  Y[j][k] = 1.0/Rk[k][0];
		    		  }
		    		  else
		    		  {
		    			  Y[j][k] = 0;
		    		  }
		    	  } 
		      }
		      
		      double[][] temp = multiplyMatrices(Y,At);
		      double[][] SPD = multiplyMatrices(A,temp);
		      double[][] temp2 = subtractMatrices(Jk,multiplyMatrices(Y,Ek));
		      double[][] B = multiplyMatrices(A,temp2);
		      System.out.println("The node voltages for circuit in ascending order are " + ((i/2)+1) + " are \n");
		      matrixPrint(matrixSolver(SPD,B));
	      }
		}
		else if(questionNum == 2)
		{
			for (int num = 2; num <= 15; num++)
			{
				final long startTime = System.currentTimeMillis();
				NetworkGenerator network = new NetworkGenerator(num);
				double[][] tempA = network.getA();
				double [][] A = new double[tempA.length-1][tempA[0].length];
				for(int i = 0; i < tempA.length - 1; i++)
				{
					for(int j = 0; j < tempA[0].length; j++)
					{
						A[i][j] = tempA[i][j];
					}	
				}
				double[][] At = matrixTranspose(A);
				double[][] Y = new double[A[0].length][A[0].length];
			    double[][] Jk = network.getJ(), Rk = network.getR(), Ek = network.getE();
	
			    for(int j =0; j < Y.length ; j++)
			    {
		    	  for (int k = 0; k < Y[0].length; k++)
		    	  {
		    		  if(j == k)
		    		  {
		    			  Y[j][k] = 1.0/Rk[k][0];
		    		  }
		    		  else
		    		  {
		    			  Y[j][k] = 0;
		    		  }
		    	  } 
			    }
			      double[][] temp = multiplyMatrices(Y,At);
			      double[][] SPD = multiplyMatrices(A,temp);
			      double[][] temp2 = subtractMatrices(Jk,multiplyMatrices(Y,Ek));
			      double[][] B = multiplyMatrices(A,temp2);
			      //System.out.println("The node voltages for circuit in ascending order are " + ((i/2)+1) + " are \n");
			      double[][] Vk = matrixSolver(SPD,B);
			      double R = (1.0f/((Ek[Ek.length - 1][0]/Vk[0][0])-1));
			      final long endTime = System.currentTimeMillis();
	              System.out.println("R is : " + R + " Kohms for a " + num + " by " + num+ " mesh network and time taken is " + ((endTime-startTime)*1000) + " microseconds");
			}
		}
		else if(questionNum == 3)
		{
			
//			//PART A 
//			System.out.println("********** PART A *************");
//			for(double x = 1; x < 2; x += 0.1)
//			{
//				FiniteDifferenceSolver fds = new FiniteDifferenceSolver(0.02);
//				int iterations = fds.solveSOR(x); 
//				double potential = fds.getPotentialAt(0.06, 0.04);
//			    //System.out.println(x + "\t" + iterations);
//				System.out.println("w: " + x + ", iterations: " + iterations + ", potential: " + potential + "\n");
//			}
//			//PART B
//			System.out.println("********** PART B *************");
//			for(double h = 0.02 ; h >= (double)(1/3200); h /= 2 )
//			{
//				FiniteDifferenceSolver fds = new FiniteDifferenceSolver(h);
//				int iterations = fds.solveSOR(W); 
//				double potential = fds.getPotentialAt(0.06, 0.04);
//			    System.out.println(1/h + "\t" + iterations + "\t" + potential);
//			}
//			//PART C
//			System.out.println("********** PART C *************");
//			for(double h = 0.02 ; h > (double)(1/1600); h /= 2 )
//			{
//				FiniteDifferenceSolver fds = new FiniteDifferenceSolver(h);
//				int iterations = fds.solveJacobi(); 
//				double potential = fds.getPotentialAt(0.06, 0.04);
//			    System.out.println(1/h + "\t" + iterations + "\t" + potential);
//			}
			//PART E
			System.out.println("********** PART E *************");
			double[] horizontalLines = new double[10];
			double[] verticalLines = new double[10];
			for (int i = 0; i < 10; i++)
			{
				horizontalLines[i] = (i+1)*0.01; 
				verticalLines[i] = (i+1)*0.01;
			}
			AdvancedDifferenceSolver fds = new AdvancedDifferenceSolver(horizontalLines, verticalLines);
			int iterations = fds.solveSOR(1.25); 
			double potential = fds.getPotentialAt(0.06, 0.04);
			System.out.println("Iterations: " + iterations + ", Potential: " + potential + "\n");
		}
		else if(questionNum == 21)
		{
			for (int num = 2; num <= 15; num++)
			{
				final long startTime = System.currentTimeMillis();
				NetworkGenerator network = new NetworkGenerator(num);
				double[][] tempA = network.getA();
				double [][] A = new double[tempA.length-1][tempA[0].length];
				for(int i = 0; i < tempA.length - 1; i++)
				{
					for(int j = 0; j < tempA[0].length; j++)
					{
						A[i][j] = tempA[i][j];
					}	
				}
				double[][] At = matrixTranspose(A);
				double[][] Y = new double[A[0].length][A[0].length];
			    double[][] Jk = network.getJ(), Rk = network.getR(), Ek = network.getE();
	
			    for(int j =0; j < Y.length ; j++)
			    {
		    	  for (int k = 0; k < Y[0].length; k++)
		    	  {
		    		  if(j == k)
		    		  {
		    			  Y[j][k] = 1.0/Rk[k][0];
		    		  }
		    		  else
		    		  {
		    			  Y[j][k] = 0;
		    		  }
		    	  } 
			    }
			      double[][] temp = multiplyMatrices(Y,At);
			      double[][] SPD = multiplyMatrices(A,temp);
			      
			      double[][] temp2 = subtractMatrices(Jk,multiplyMatrices(Y,Ek));
			      double[][] B = multiplyMatrices(A,temp2);
			      //System.out.println("The node voltages for circuit in ascending order are " + ((i/2)+1) + " are \n");
			      double[][] Vk = sparseMatrixSolver(SPD,B);
			      double R = (1.0f/((Ek[Ek.length - 1][0]/Vk[0][0])-1));
			      final long endTime = System.currentTimeMillis();
	              System.out.println("R is : " + R + " Kohms for a " + num + " by " + num+ " mesh network and time taken is " + ((endTime-startTime)*1000) + " microseconds");
			}
			
		}

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
					sum += outputMatrix[i][j]*outputMatrix[k][j];
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
	
	/*
	 * Name: matrixSolver()
	 * Parameters: double[][], double[][]
	 * Output : double[][]
	 * Description: Solves the matrix equation AX=B using cholesky decomposition 
	 */
	public static double[][] matrixSolver(double[][] A, double[][] B)
	{
		double sum;
		if(B.length != A.length)
		{
			System.out.println("Matrix row dimensions must agree.");
		}
		int ARowDim = A.length;
		int BColDim = B[0].length;
		double[][] X = new double[ARowDim][BColDim];
		double[][] U = choleskyDecompose(A);
		double[][] Ut = matrixTranspose(U);
		double[][] Y = new double[ARowDim][BColDim];
		
		//Solving for U*y= B
		for(int k =0; k < BColDim; k++)
		{
			for(int i = 0; i < ARowDim; i++)
			{
				sum = 0.0; 
				for(int j =0; j < i ; j++)
				{
					sum += U[i][j]*Y[j][k];
				}
			  Y[i][k] = (B[i][k]- sum)/U[i][i]; 
			}
			
			//Solving for Ut*x = Y
			for(int i = ARowDim - 1; i > -1 ; i--)
			{
				sum = 0.0;
				for (int j = i + 1; j < ARowDim; j++)
				{
					sum += Ut[i][j]*X[j][k];
				}
			  X[i][k] = (Y[i][k]- sum)/(Ut[i][i]);	
			}
		}
		return X;
	}
	
	public static double[][] sparseMatrixSolver(double[][] A, double[][] B)
	{
		int ARowDim = A.length;
		int BColDim = B[0].length;
		if(B.length != A.length)
		{
			System.out.println("Matrix row dimensions must agree.");
		}
		double[][] L = computeLMatrix(A);
		double[][] Y = new double[ARowDim][BColDim];
		double[][] X = new double[ARowDim][BColDim];
		for (int k = 0; k < BColDim; k++)
		{
			for (int i = 1; i <= ARowDim  + 1; i++)
			{
				double sum =0;
				for(int j = firstNonZero.get(i-1) + 1; j < i; j++)
				{
					sum += L[i][j]*Y[j-1][k];
				}
				Y[i][k] = (B[i][k]- sum)/L[i][i];
			}
			
			for(int i = ARowDim - 1; i > -1 ; i--)
			{
				double sum = 0;
				for (int j = i + 1; j < ARowDim +1 ;  j++)
				{
					sum += L[j][i]*X[-ARowDim+j-1][k];
				}
			  X[i][k] = (Y[i-1][k]- sum)/(L[i][i]);	
			}
		}
		
		return X;

	}
	
	private static double[][] computeLMatrix(double[][] A)
	{
		matrixPrint(A);
		double[][] L = new double[A.length][A[0].length];
		
		for(int j =0; j < L.length; j++)
		{
			for(int i =0; i < L[0].length; i++)
			{
				if(A[i][j] != 0)
				{
					firstNonZero.add(new Integer(i));
					if((j-i)> halfBandwidth)
					{
						halfBandwidth = j -i ; 
					}
				}
				else
				{
					System.out.println("All Zero Row!!!");
				}
			}
		}
		
	 double rowSquared = 0;
	 for (int j = 1; j < A.length +1; j++)
	 {
		 for(int k = firstNonZero.get(j-1); k <= j-1; k++)
		 {
			 rowSquared += Math.pow(L[j-1][k], 2); 
		 }
		 
		 L[j][j] = Math.sqrt(L[j][j] - rowSquared);
		 
		 double dotProduct = 0;
		 for(int i = j+1; i < A.length +1; i++)
		 {
			 for (int s = Math.max(firstNonZero.get(i-1), firstNonZero.get(j-1)); s < j; s++ )
			 {
				 dotProduct += L[i][s]*L[j][s];
			 }
			 
			 L[i][j] = (L[i][j]-dotProduct)/L[j][j];
			
		 }
		 
		 
	 }
	 return L;
		
		
	}
	
	/*
	 * Name: matrixTranspose()
	 * Parameters: double[][]
	 * Output : double[][]
	 * Description: Transposes a matrix
	 */
	public static double[][] matrixTranspose(double[][] inputMatrix)
	{
		 int m = inputMatrix.length;
		 int n = inputMatrix[0].length;

		 double[][] transposedMatrix = new double[n][m];

		    for(int x = 0; x < n; x++)
		    {
		        for(int y = 0; y < m; y++)
		        {
		            transposedMatrix[x][y] = inputMatrix[y][x];
		        }
		    }
		  return transposedMatrix;
	}
	
	/*
	 * Name: matrixPrint()
	 * Parameters: double[][]
	 * Output : void
	 * Description: Prints a given Matrix
	 */
	public static void matrixPrint(double[][] matrix)
	{
		for (int i = 0; i < matrix.length; i++)
		{
		    for (int j = 0; j < matrix[i].length; j++) 
		    {
		        System.out.print(matrix[i][j] + "  ");
		    }
		    System.out.println();
		    System.out.println();
		}
		System.out.println();
	}
	
	public static double[][] multiplyMatrices(double[][] A, double[][] B) {

        int aRows = A.length;
        int aColumns = A[0].length;
        int bRows = B.length;
        int bColumns = B[0].length;

        if (aColumns != bRows) {
            throw new IllegalArgumentException("A:Rows: " + aColumns + " did not match B:Columns " + bRows + ".");
        }

        double[][] C = new double[aRows][bColumns];
        for (int i = 0; i < aRows; i++) {
            for (int j = 0; j <bColumns; j++) {
                C[i][j] = 0.00000;
            }
        }

        for (int i = 0; i < aRows; i++) { // aRow
            for (int j = 0; j < bColumns; j++) { // bColumn
                for (int k = 0; k < aColumns; k++) { // aColumn
                    C[i][j] += A[i][k] * B[k][j];
                }
            }
        }

        return C;
    }
	
	private static double[][] subtractMatrices(double[][] matrixA, double[][] matrixB) 
	{
		int rows = matrixA.length;

		int cols = matrixA[0].length;

		double[][] sum = new double[rows][cols];

		for (int i = 0; i < rows; i++) 
		{
			for (int j = 0; j < cols; j++)
			{
				sum[i][j] = matrixA[i][j] - matrixB[i][j];

			}
		}

		return sum;
	}

}
