import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

public class ExcelImport {
	
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
	        XSSFSheet sheet = workbook.getSheetAt(0);

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
	 
	  
}
