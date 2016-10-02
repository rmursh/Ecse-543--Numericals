import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

public class ExcelImport {
	public static int NETWORK_BOOK = 0;
	public static int MATRIX_BOOK = 1;
	private String path;
	private XSSFWorkbook workbook;
	private FileInputStream file;
	private FormulaEvaluator evaluator;
	
	public ExcelImport(String filePath)
	{
		this.path = filePath; 
		try
		{
			this.file = new FileInputStream(new File(this.path));	
			this.workbook = new XSSFWorkbook(file);
			this.evaluator = workbook.getCreationHelper().createFormulaEvaluator();
		}
    	catch (Exception e)
    	{
	        e.printStackTrace();
	    }

	}
	
	public XSSFWorkbook getWorkbook()
	{
		return this.workbook;
	}
	
	private void endExcel()
	{
		try
		{
			this.file.close();
		}
		catch (Exception e)
		{
			 e.printStackTrace();
		}
	}
	
    public void printExcelContent()
    {
	        //Get first/desired sheet from the workbook
	        XSSFSheet sheet = workbook.getSheetAt(NETWORK_BOOK);

	        //Iterate through each rows one by one
	        Iterator<Row> rowIterator = sheet.iterator();
	        while (rowIterator.hasNext())
	        {
	            Row row = rowIterator.next();
	            //For each row, iterate through all the columns
	            Iterator<Cell> cellIterator = row.cellIterator();

	            while (cellIterator.hasNext()) 
	            {
	                Cell cell = cellIterator.next();
	                CellValue cellValue = evaluator.evaluate(cell);
	                //Check the cell type and format accordingly
	                switch (cellValue.getCellType()) 
	                {
	                    case Cell.CELL_TYPE_NUMERIC:
	                        System.out.print(cell.getNumericCellValue() + "\t");
	                        break;
	                    case Cell.CELL_TYPE_STRING:
	                        System.out.print(cell.getStringCellValue() + "\t");
	                        break;
	                    default:
	                    	System.out.print(cell.toString() + "\t");
	                    	break;
	                }
	            }
	            System.out.println("");
	        }     
	        endExcel();
    }
    
    public double[][] importNetworkBranches(int matrix)
    { 
    	XSSFSheet sheet;
    	int colNum;  
    	if((matrix%2) == 0)
    	{
    		sheet = workbook.getSheetAt(matrix);
    		colNum = sheet.getRow(0).getLastCellNum();
    	}
    	else
    	{
    		sheet = workbook.getSheetAt(matrix);
    		colNum = sheet.getRow(0).getLastCellNum();
    	}
    	int rowNum = sheet.getLastRowNum() + 1;

    	double[][] data = new double[rowNum][colNum];
	    for (int i = 0; i < rowNum; i++)
	    {
	        //get the row
	        XSSFRow row = sheet.getRow(i);
	            for (int j = 0; j < colNum; j++)
	            {
	                //this gets the cell and sets it as blank if it's empty.
	            	XSSFCell cell = row.getCell(j);
	            	CellValue cellValue = evaluator.evaluate(cell);
	                double value = cell.getNumericCellValue();                             
	                data[i][j] = value;
	            }            
	     }
    	endExcel();
    	return data;

    	
    }
	 
	  
}
