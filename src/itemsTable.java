

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class itemsTable {

    int invNo;
    String itemName;
    int itemPrice;
    int itemQuantity;
    int itemTotal;


    public String invoiceHeaderFile;
    //public int noOfInvoices=0;



    public int getNoOfItems(String invoiceHeaderFile)
    {
        invoiceHeaderFile = "src\\InvoiceLine.csv";
        Path path = Paths.get(invoiceHeaderFile);


        long lines = 0;
        try {
            lines = Files.lines(path).count();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (int) lines;
    }




    public String[][] loadItems(String fileName)
    {

        fileName = "src\\InvoiceLine.csv";
        BufferedReader reader = null;

        String line = "";

        int item_col_num=-1;
        int item_row_num=-1;

        int noOfItemsLines = getNoOfItems(fileName);
        String[][] items_arr = new String[noOfItemsLines][5];
        try{

            reader = new BufferedReader(new FileReader(fileName));
            while((line = reader.readLine()) != null)
            {
                item_row_num=item_row_num+1;
                item_col_num = -1;
                String[] row = line.split(",");

                for(String index : row)
                {
                    item_col_num=item_col_num+1;
                    items_arr[item_row_num][item_col_num]=index;
//                    System.out.println(items_arr[item_row_num][item_col_num]);
                    if (item_col_num==3)
                    {
                        items_arr[item_row_num][4]= String.valueOf(Integer.valueOf(items_arr[item_row_num][3]) * Integer.valueOf(items_arr[item_row_num][2]));
//                        System.out.println(items_arr[item_row_num][4]);
                    }
                }
//                System.out.println();
            }
        }
        catch(Exception e ){

        }
        finally {

        }
        return items_arr;
    }



    public int getItemTotal(int invID)

    {
        itemTotal=0;
        itemsTable itemsTable = new itemsTable();
        String[][] items_arr = itemsTable.loadItems("src\\InvoiceLine.csv");

        for (int x = 0; x <= items_arr.length-1; x++ )
        {
            if ( Integer.valueOf(items_arr[x][0]) == invID)
            {
                itemTotal += Integer.valueOf(items_arr[x][4]);
            }
        }

        return itemTotal;
    }



    public Object[][] getTableData (JTable table) {
        TableModel dtm = table.getModel();
        int nRow = dtm.getRowCount(), nCol = dtm.getColumnCount();
        Object[][] tableData = new Object[nRow][nCol];
        for (int i = 0 ; i < nRow ; i++)
            for (int j = 0 ; j < nCol ; j++)
                tableData[i][j] = dtm.getValueAt(i,j);
        return tableData;


        ////try

    }




    ////////////////////
//    public String[][] loadItems_filtered(int invNo)
//    {
//
//        String fileName = "src\\InvoiceLine.csv";
//        //String file = fileName;
//        BufferedReader reader = null;
//
//        String line = "";
//
//        int item_col_num=-1;
//        int item_row_num=-1;
//
//        int noOfItemsLines = getNoOfItems(fileName);
//        String[][] items_arr = new String[noOfItemsLines][5];
//        try{
//
//            reader = new BufferedReader(new FileReader(fileName));
//            while((line = reader.readLine()) != null)
//            {
//                item_row_num=item_row_num+1;
//                item_col_num = -1;
//                String[] row = line.split(",");
//
//                for(String index : row)
//                {
//                    item_col_num=item_col_num+1;
//                    items_arr[item_row_num][item_col_num]=index;
//                    System.out.println(items_arr[item_row_num][item_col_num]);
//                    if (item_col_num==3)
//                    {
//                        items_arr[item_row_num][4]= String.valueOf(Integer.valueOf(items_arr[item_row_num][3]) * Integer.valueOf(items_arr[item_row_num][2]));
//                        System.out.println(items_arr[item_row_num][4]);
//                    }
//                }
//                System.out.println();
//            }
//        }
//        catch(Exception e ){
//
//        }
//        finally {
//
//        }
//        return items_arr;
//    }


}


