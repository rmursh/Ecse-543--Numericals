import java.math.*;

public class AdvancedDifferenceSolver {
	private final static double CABLE_HEIGHT = 0.1;
	private final static double CABLE_WIDTH = 0.1;
	private final static double CORE_HEIGHT = 0.02;
	private final static double CORE_WIDTH = 0.04;
	private final static double CORE_POTENTIAL = 110;
	private final static double MIN_RESIDUAL = 0.0001;
	
	private double h;
	public double[][] mesh;
	private double[] horizontalLines, verticalLines;
	
	public AdvancedDifferenceSolver(double[] horizontalLines, double[] verticalLines)
	{
		this.horizontalLines = horizontalLines;
		this.verticalLines = verticalLines;
		generateMesh();	
	}
	
	public double[][] getMesh()
	{
		return this.mesh;
	}
	
	public void generateMesh()
	{
		mesh = new double[horizontalLines.length][verticalLines.length];
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
		
		double rateOfChange = (-CORE_POTENTIAL)/(CABLE_HEIGHT- CORE_HEIGHT);
		for(int i = 0; i < horizontalLines.length; i++)
		{
			if(horizontalLines[i] > CORE_HEIGHT)
			{
				mesh[i][0]= CORE_POTENTIAL + rateOfChange*(horizontalLines[i] - CORE_HEIGHT);
			}
		}
		
		rateOfChange = (-CORE_POTENTIAL)/(CABLE_WIDTH- CORE_WIDTH);
		
		for(int i = 0; i < verticalLines.length; i++)
		{
			if(verticalLines[i] > CORE_WIDTH)
			{
				mesh[0][i]= CORE_POTENTIAL + rateOfChange*(verticalLines[i] - CORE_WIDTH);
			}
		}	
	}
	
	public int solveSOR(double w)
	{
		int numIterations = 0;
		//double maximumResidual = computeMaximumResidual(0);
		//System.out.println(maximumResidual);
		//double residueOld  = iterateSOR(w);//mesh[(int)(0.06/h)][(int)(0.04/h)];
		while (computeMaximumResidual()> MIN_RESIDUAL)
		{
			//residueOld = residueNew;
			iterateSOR(w);
			numIterations++;
			//maximumResidual = computeMaximumResidual(maximumResidual);
		}
		return numIterations+1;
	}
	
	
	public double getPotentialAt(double x, double y)
	{
		int xPoint = (int) verticalLines[(int) x];
		int yPoint = (int)verticalLines[(int) y];
		return mesh[xPoint][yPoint];
	}
	
	private void iterateSOR(double w)
	{
		for (int y = 1; y < horizontalLines.length - 1; y++)
		{
			for(int x = 1; x < verticalLines.length -1; x++)
			{
				if((horizontalLines[x] > (CORE_HEIGHT))||(verticalLines[x] > (CORE_WIDTH)))
				{
					double temp1 = verticalLines[x] - verticalLines[x-1];
					double temp2 = horizontalLines[y+1] - horizontalLines[y];
					double temp3 = verticalLines[x+1] - verticalLines[x];
					double temp4 = horizontalLines[y] - horizontalLines[y-1];
					
					mesh[y][x] = ((mesh[y][x-1]/(temp1*(temp1+temp3))) 
							   + (mesh[y][x+1]/(temp3*(temp1+temp3))) 
							   + (mesh[y-1][x]/(temp4*(temp2+temp4))) 
							   + (mesh[y+1][x]/(temp2*(temp2+temp4))))
							   /((1/(temp1*temp3))+(1/(temp2*temp4)));
				}
			}
		}
	}
	

	private double computeMaximumResidual()
	{
		double maximumResidual =0;
		for (int y = 1; y < horizontalLines.length - 1 - 1; y++)
		{
			for(int x = 1; x < verticalLines.length - 1; x++)
			{
				if((horizontalLines[x] > (CORE_HEIGHT))||(horizontalLines[x] > (CORE_HEIGHT)))
				{
					double temp1 = verticalLines[x] - verticalLines[x-1];
					double temp2 = horizontalLines[y+1] - horizontalLines[y];
					double temp3 = verticalLines[x+1] - verticalLines[x];
					double temp4 = horizontalLines[y] - horizontalLines[y-1];
					
					double residual = (mesh[y][x-1]/(temp1*(temp1+temp3)) + mesh[y][x+1]/(temp3*(temp1+temp3)) + mesh[y-1][x]/(temp4*(temp2+temp4)) + mesh[y+1][x]/(temp2*(temp2+temp4))) - (1/(temp1*temp3) + 1/(temp2*temp4))*mesh[y][x];
					residual = Math.abs(residual);
					if (residual > maximumResidual)
					{
						maximumResidual = residual;
					}

				}
			}
		}
		return maximumResidual;

	}
	
	
}
