import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class test {


    public static void main(String[] args) {

        String fileName = "src\\InvoiceLine.csv";
        //String file = fileName;
        BufferedReader reader = null;

        String line = "";

        int item_col_num=-1;
        int item_row_num=-1;
        // String item_cell_value[][] = new String[2][4];

        int noOfInvoiceslines = getNoOfItems(fileName);
        String[][] item_cell_value = new String[noOfInvoiceslines][5];
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
                    item_cell_value[item_row_num][item_col_num]=index;
                    System.out.println(item_cell_value[item_row_num][item_col_num]);
                    if (item_col_num==3)
                    {
                        item_cell_value[item_row_num][4]= String.valueOf(Integer.valueOf(item_cell_value[item_row_num][3]) * Integer.valueOf(item_cell_value[item_row_num][2]));
                        System.out.println(item_cell_value[item_row_num][4]);
                    }
                }
                System.out.println();
            }

        }
        catch(Exception e ){


        }
        finally {

        }





    }

    public static int getNoOfItems(String invoiceHeaderFile)
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
}
