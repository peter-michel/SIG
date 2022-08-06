import javax.swing.*;
import javax.swing.table.TableModel;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

public class invoicesTable {

    int invNo;
    Date invDate;
    String invCust;
    int invTotal;


    public String invoiceHeaderFile;
    //public int noOfInvoices=0;



    public int getNoOfInvoices(String invoiceHeaderFile)
    {
        Path path = Paths.get(invoiceHeaderFile);
        long lines = 0;
        try {
          lines = Files.lines(path).count();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (int) lines;
    }


    public String[][] loadInvoices(String fileName)
    {

        itemsTable itemsTable = new itemsTable();
        String[][] items_arr = itemsTable.loadItems("src\\InvoiceLine.csv");
//        System.out.println(items_arr.length);


        BufferedReader reader = null;
        String line = "";

        int inv_col_num=-1;
        int inv_row_num=-1;


        int noOfInvoiceslines = getNoOfInvoices(fileName);
        String[][] inv_arr = new String[noOfInvoiceslines][4];
        try{



            reader = new BufferedReader(new FileReader(fileName));
            while((line = reader.readLine()) != null)
            {
                inv_row_num=inv_row_num+1;
                inv_col_num = -1;
                String[] row = line.split(",");

                for(String index : row)
                {
                    inv_col_num=inv_col_num+1;
                    inv_arr[inv_row_num][inv_col_num]=index;

                    // Getting total of items as per invoice ID
                    if (inv_col_num==2)
                    {
                        int y = Integer.valueOf(inv_arr[inv_row_num][0]);

                        inv_arr[inv_row_num][3] =  String.valueOf(itemsTable.getItemTotal(y));
                    }

                }
//                System.out.println();
            }

        }
        catch(Exception e ){


        }
        finally {

        }

        return inv_arr;
    }


    public Object[][] getTableData (JTable table) {
        TableModel dtm = table.getModel();
        int nRow = dtm.getRowCount(), nCol = dtm.getColumnCount();
        Object[][] tableData = new Object[nRow][nCol];
        for (int i = 0; i < nRow; i++)
            for (int j = 0; j < nCol; j++)
                tableData[i][j] = dtm.getValueAt(i, j);
        return tableData;
    }

}
