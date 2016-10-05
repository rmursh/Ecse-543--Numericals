import java.util.*;
import java.io.*;


public class NetworkGenerator {

	public double[][] A, E, J, R;
	private int N;
	
	public NetworkGenerator(int N)
	{
		this.A = new double[N*N][2*N*N -2*N+1];
		this.E = new double[2*N*N -2*N+1][1];
		this.J = new double[2*N*N -2*N+1][1];
		this.R = new double[2*N*N -2*N+1][1];
		for(int i =0; i < E.length; i++)
		{
			for (int j =0; j < E[0].length; j++)
			{
				E[i][j] = 0;
				J[i][j] = 0;
				if(i < E[0].length -1 )
				{
					R[i][j] = (float)(1000.0f);
				}
				else
				{
					R[i][j] = (float)(1);
				}	
			}
		}
		this.N = N; 
		generateE();
		generateMatrix();
	}
	
	public double[][] getA()
	{
		return this.A;
	}
	public double[][] getE()
	{
		return this.E;
	}
	public double[][] getJ()
	{
		return this.J;
	}
	public double[][] getR()
	{
		return this.R;
	}
	
	private void generateMatrix()
	{
		int node, above,below, left, right;
		for (int i = 1; i <= this.N; i++)
		{
			for(int j =1; j <= this.N; j++ )
			{
				node = N*(j-1) + i;
				above = node + N*(j-1) - (j-1);
				below = above - 1;
				left = above - N;
				right = above + (N-1);
				
				A[0][2*N*N - 2*N] = -1;
				A[N*N -1][2*N*N - 2*N ] = 1;
				if(j == 1)
				{
					A[node-1][right -1] = 1;
					if(i == 1)
					{
						A[node -1][above -1] = 1;
						
					}
					else if (i == N)
					{
						A[node -1][below -1] = -1;
					}
					else
					{
						A[node -1][above-1] = 1;
						A[node - 1][below -1] = -1;
					}
				}
				else if (j == N)
				{
					A[node-1][left -1] = -1;
					if(i == 1)
					{
						A[node -1][above -1] = 1;
						
					}
					else if (i == N)
					{
						A[node -1][below -1] = -1;
					}
					else
					{
						A[node -1][above-1] = 1;
						A[node - 1][below -1] = -1;
					}
				}
				else
				{
					A[node - 1][left - 1] = - 1;
					A[node - 1][right - 1] =  1;
					if(i == 1)
					{
						A[node -1][above -1] = 1;
						
					}
					else if (i == N)
					{
						A[node -1][below -1] = -1;
					}
					else
					{
						A[node -1][above-1] = 1;
						A[node - 1][below -1] = -1;
					}
				}
			}
		}
	}
	
	private void generateE()
	{
		E[2*N*N -2*N][0] = 1;
	}
	
}
