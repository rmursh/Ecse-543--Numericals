import java.math.*;

public class FiniteDifferenceSolver
{
	private final static double CABLE_HEIGHT = 0.1;
	private final static double CABLE_WIDTH = 0.1;
	private final static double CORE_HEIGHT = 0.02;
	private final static double CORE_WIDTH = 0.04;
	private final static double CORE_POTENTIAL = 110;
	private final static double MIN_RESIDUAL = 0.0001;
	
	private double h;
	private int nodesWide;
	private int nodesHigh;
	public double[][] mesh;
	
	public FiniteDifferenceSolver(double h)
	{
		this.h = h;
		this.nodesWide = (int)(CABLE_WIDTH/h) + 1;
		this.nodesHigh = (int)(CABLE_HEIGHT/h) + 1;
		generateMesh();	
	}
	
	public double[][] getMesh()
	{
		return this.mesh;
	}
	
	public void generateMesh()
	{
		mesh = new double[nodesWide][nodesHigh];
		for(int i = 0; i < mesh.length; i++)
		{
			for(int j =0; j < mesh[0].length; j++)
			{
				if((j <= (int)(CORE_WIDTH/h))&&(i <= (int)(CORE_HEIGHT/h)))
				{
					mesh[i][j] = CORE_POTENTIAL;
				}
				else
				{
					mesh[i][j] = 0;
				}
			}
		}
		
		double rateOfChange = (-CORE_POTENTIAL*this.h)/(CABLE_HEIGHT- CORE_HEIGHT);
		for(int i = (int) ((CORE_HEIGHT/h) + 1); i < nodesHigh -1; i++)
		{
			mesh[i][0]= mesh[i-1][0] + rateOfChange;
		}
		rateOfChange = (-CORE_POTENTIAL*this.h)/(CABLE_WIDTH- CORE_WIDTH);
		
		for(int i = (int) ((CORE_WIDTH/h) + 1); i < nodesWide -1; i++)
		{
			mesh[0][i]= mesh[0][i -1] + rateOfChange;
		}	
	}
	
	public int solveSOR(double w)
	{
		int numIterations = 0;
		//double maximumResidual = computeMaximumResidual(0);
		//System.out.println(maximumResidual);
		double residueOld  = iterateSOR(w);//mesh[(int)(0.06/h)][(int)(0.04/h)];
		System.out.println(residueOld);
		double residueNew = 0;
		while (Math.abs(residueNew - residueOld)> MIN_RESIDUAL)
		{
			residueOld = residueNew;
			residueNew = iterateSOR(w);
			numIterations++;
			//maximumResidual = computeMaximumResidual(maximumResidual);
		}
		return numIterations+1;
	}
	
	public double getPotentialAt(double x, double y)
	{
		int xPoint = (int)(x/h);
		int yPoint = (int)(y/h);
		return mesh[xPoint][yPoint];
	}
	
	private double iterateSOR(double w)
	{
		for (int i = 1; i < nodesHigh - 1; i++)
		{
			for(int j = 1; j < nodesWide -1; j++)
			{
				if((i > (CORE_HEIGHT/h))||(j > (CORE_WIDTH/h)))
				{
					mesh[i][j] = (1-w)*mesh[i][j] 
							   + (w/4)*(mesh[i][j-1]
									   +mesh[i][j+1]
									   +mesh[i-1][j]
									   +mesh[i+1][j]);
				}
			}
		}
		return mesh[(int)(0.06/h)][(int)(0.04/h)];
	}
	private double computeMaximumResidual(double maximumResidual)
	{
		for (int y = 1; y < nodesHigh - 1; y++)
		{
			for(int x = 1; x < nodesWide - 1; x++)
			{
				if((x > (CORE_WIDTH/h))||(y > (CORE_HEIGHT/h)))
				{
					double residual = Math.abs(mesh[y][x-1] 
							                 + mesh[y][x+1]
							                 + mesh[y-1][x]
							                 + mesh[y+1][x]
							                 - 4*mesh[y][x]);
					if(residual > maximumResidual)
					{
						maximumResidual = residual;
					}
				}
			}
		}
		return maximumResidual;	
	}
	
	
}
