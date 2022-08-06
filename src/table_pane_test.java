import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;

public class table_pane_test extends JFrame implements ActionListener {

    //
    //static boolean methodCalled = true;
    static boolean new_inv_flag;
    private JPanel pnl_left, pnl_right;
    private JButton btn_new_inv, btn_del_inv, btn_save, btn_cancel;
    static JTable tbl_invoices_table, tbl_items_table;
    static JTextField tbx_invoice_date, tbx_customer_name;
    private JLabel lbl_invoices_table;
    private JMenuBar mb;
    private JMenu fileMenu;
    private JMenuItem loadFile;
    private JMenuItem saveFile;


    static DefaultTableModel invoices_model, items_model;

    static JLabel lbl_invoice_number_value, lbl_invoice_total;
    static String[][] invoices_arr;
//    static String[][]  temp_invoice_arr;

    static int invoices_tbl_rowcount, items_tbl_rowcount;
    private String[][] row_data;

    private ListSelectionModel listSelectionModel;

    static String[] tbl_invoices_table_cols = {"No.", "Date", "Customer", "Total"};

    static String[] tbl_items_table_cols = {"No.", "Item name", "Item price", "Count", "Item Total"};
    static String[][] tbl_invoices_table_data;
    static String[][] tbl_items_table_data;
    static Object[][] Main_itemsTable;
    static Object[][] Main_invoicesTable;


    public String[][] invoices_arr_filtered;


    public table_pane_test() {
        super("SIG2");


        invoicesTable invoicesTable = new invoicesTable();
        itemsTable itemsTable = new itemsTable();

        new_inv_flag = false;

        invoices_arr = invoicesTable.loadInvoices("src\\InvoiceHeader.csv");
        String[][] items_arr = itemsTable.loadItems("src\\InvoiceLine.csv");


        mb = new JMenuBar();
        fileMenu = new JMenu("File");


        loadFile = new JMenuItem("Load File");
        loadFile.setAccelerator(KeyStroke.getKeyStroke('O', KeyEvent.CTRL_DOWN_MASK));
        //loadFile.addActionListener(this);
        loadFile.setActionCommand("O");

        saveFile = new JMenuItem("Save File");
        saveFile.setAccelerator(KeyStroke.getKeyStroke('S', KeyEvent.CTRL_DOWN_MASK));
        //saveFile.addActionListener(this);
        saveFile.setActionCommand("S");

        setJMenuBar(mb);
        mb.add(fileMenu);
        fileMenu.add(loadFile);
        fileMenu.add(saveFile);


        this.setSize(1000, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);


        lbl_invoices_table = new JLabel("Invoices Table");
        lbl_invoices_table.setBounds(36, 20, 136, 20);
        add(lbl_invoices_table);


        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(36, 42, 410, 380);
        add(scrollPane);


        invoices_model = new DefaultTableModel(invoices_arr, tbl_invoices_table_cols);
        tbl_invoices_table = new JTable(invoices_model);
        //tbl_invoices_table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        //tbl_invoices_table = new JTable(invoices_arr, tbl_invoices_table_cols);
        scrollPane.setViewportView(tbl_invoices_table);

        JButton btn_new_inv = new JButton("Create new invoice");
        btn_new_inv.setBounds(80, 448, 160, 23);
        btn_new_inv.addActionListener(this);
        btn_new_inv.setActionCommand("newInvCmd");
        add(btn_new_inv);

        JButton btn_del_inv = new JButton("Delete invoice");
        btn_del_inv.setBounds(250, 448, 130, 23);
        btn_del_inv.addActionListener(this);
        btn_del_inv.setActionCommand("deleteInvCmd");
        add(btn_del_inv);

        JPanel panel = new JPanel();
        panel.setBorder(new LineBorder(new Color(0, 0, 0)));
        panel.setBounds(15, 11, 446, 479);
        add(panel);


        JLabel lbl_InvoicesNumber = new JLabel("Invoice Number");
        lbl_InvoicesNumber.setBounds(479, 28, 93, 20);
        add(lbl_InvoicesNumber);

        JLabel lbl_invoiceDate = new JLabel("Invoice Date");
        lbl_invoiceDate.setBounds(480, 60, 92, 20);
        add(lbl_invoiceDate);

        JLabel lbl_customerName = new JLabel("Customer Name");
        lbl_customerName.setBounds(479, 94, 93, 20);
        add(lbl_customerName);

        JLabel lbl_invoiceTotal = new JLabel("Invoice Total");
        lbl_invoiceTotal.setBounds(480, 125, 92, 20);
        add(lbl_invoiceTotal);

        lbl_invoice_number_value = new JLabel(" ");
        lbl_invoice_number_value.setBounds(582, 30, 150, 20);
        add(lbl_invoice_number_value);

        lbl_invoice_total = new JLabel(" ");
        lbl_invoice_total.setBounds(582, 125, 93, 20);
        add(lbl_invoice_total);

        tbx_invoice_date = new JTextField();
        tbx_invoice_date.setBounds(582, 60, 294, 20);
        add(tbx_invoice_date);
        tbx_invoice_date.setColumns(10);

        tbx_customer_name = new JTextField();
        tbx_customer_name.setColumns(10);
        tbx_customer_name.setBounds(582, 94, 294, 20);
        add(tbx_customer_name);

        JPanel panel_1 = new JPanel();
        panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
        panel_1.setBounds(471, 20, 446, 146);
        add(panel_1);

        JLabel lbl_invoicesItems = new JLabel("Invoices items");
        lbl_invoicesItems.setBounds(471, 197, 136, 20);
        add(lbl_invoicesItems);

        JScrollPane scrollPane_1 = new JScrollPane();
        scrollPane_1.setBounds(471, 223, 446, 158);
        add(scrollPane_1);


        items_model = new DefaultTableModel(items_arr, tbl_items_table_cols);
        tbl_items_table = new JTable(items_model);
        //tbl_items_table = new JTable(items_arr, tbl_items_table_cols);
        scrollPane_1.setViewportView(tbl_items_table);

        JButton btnSave = new JButton("Save");
        btnSave.setBounds(542, 448, 130, 23);
        btnSave.addActionListener(this);
        btnSave.setActionCommand("saveCmd");
        add(btnSave);

        JButton btn_cancel = new JButton("Cancel");
        btn_cancel.setBounds(712, 448, 130, 23);
        btn_cancel.addActionListener(this);
        btn_cancel.setActionCommand("cancelCmd");
        add(btn_cancel);

        JPanel panel_2 = new JPanel();
        panel_2.setBorder(new LineBorder(new Color(0, 0, 0)));
        panel_2.setBounds(466, 11, 456, 479);
        add(panel_2);


        listSelectionModel = tbl_invoices_table.getSelectionModel();
        listSelectionModel.addListSelectionListener(new MyListSelectionListener());
        tbl_invoices_table.setSelectionModel(listSelectionModel);


        Main_itemsTable = itemsTable.getTableData(tbl_items_table);
        Main_invoicesTable = invoicesTable.getTableData(tbl_invoices_table);
        invoices_tbl_rowcount = tbl_invoices_table.getRowCount();
        items_tbl_rowcount = tbl_items_table.getRowCount();


    }


    public static void main(String[] args) {
        new table_pane_test().setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {


        switch (e.getActionCommand()) {

            case "saveCmd":
                updateInvoice();
                break;

            case "cancelCmd":
                cancelUpdate();
                break;

            case "deleteInvCmd":
                deleteInvCmd();
                break;

            case "newInvCmd":
                newInvCmd();
                break;

        }
    }

    class MyListSelectionListener implements ListSelectionListener {

        @Override
        public void valueChanged(ListSelectionEvent e) {

            new_inv_flag = false;
            if (tbl_invoices_table.getSelectedRow() == -1) {
                System.out.println("no row selected");
            } else {
                System.out.println("row selected");
                int row = tbl_invoices_table.getSelectedRow();
                String selectedInvID = (String) tbl_invoices_table.getValueAt(row, 0);
                TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(items_model);
                tbl_items_table.setRowSorter(sorter);
                sorter.setRowFilter(RowFilter.regexFilter(selectedInvID, 0));


                row = tbl_invoices_table.getSelectedRow();
                String selectedItem = String.valueOf(tbl_invoices_table.getValueAt(row, 0));
                lbl_invoice_number_value.setText(String.valueOf(tbl_invoices_table.getValueAt(row, 0)));
                tbx_invoice_date.setText(String.valueOf(tbl_invoices_table.getValueAt(row, 1)));
                tbx_customer_name.setText(String.valueOf(tbl_invoices_table.getValueAt(row, 2)));
                lbl_invoice_total.setText(String.valueOf(tbl_invoices_table.getValueAt(row, 3)));


            }


//            if (methodCalled == true)
//            {
//
//            }


//
//            {
//                //DefaultTableModel modelz = new DefaultTableModel(Main_itemsTable, tbl_items_table_cols);
//               // items_model = new DefaultTableModel(Main_itemsTable, tbl_items_table_cols);
//
//
//                //tbl_items_table.setModel(items_model);
//                tbl_items_table.setModel(new DefaultTableModel(Main_itemsTable, tbl_items_table_cols));
//                tbl_items_table.revalidate();
//


////            System.out.println("valueChanged: " + e.toString());

//
//                //            lbl_invoice_number_value.setText("sdrrsd");
//                System.out.println(selectedItem);
//
//                itemsTable itemsTable = new itemsTable();
//                Object[][] itemsTable_unfiltered = itemsTable.getTableData(tbl_items_table);
//
//
//                //System.out.println(itemsTable_unfiltered[4][2]);
//                int rowCount = 0;
//                for (int row_num = 0; row_num < itemsTable_unfiltered.length; row_num++) {
////                System.out.println(itemsTable_unfiltered[row_num][0]);
//                    System.out.println("unfiltered : " + String.valueOf(itemsTable_unfiltered[row_num][0] + "--VS--" + selectedItem));
//                    if (String.valueOf(itemsTable_unfiltered[row_num][0]).equals(selectedItem)) {
//                        rowCount++;
//                    }
//                }
////
////            System.out.println(rowCount);
//                int row_num_filtered = 0;
//                String[][] itemsTable_filtered = new String[rowCount][5];
//                for (int row_num = 0; row_num < itemsTable_unfiltered.length; row_num++) {
//                    if ((itemsTable_unfiltered[row_num][0]).equals(selectedItem)) {
//                        for (int y = 0; y <= 4; y++) {
//                            System.out.println(itemsTable_unfiltered[row_num][y]);
//                            itemsTable_filtered[row_num_filtered][y] = (String) itemsTable_unfiltered[row_num][y];
//
//                        }
//                        row_num_filtered++;
//                    }
//                }
//
//
//                Object updatemodel[][] = itemsTable_filtered;
//
//
//                tbl_items_table.setModel(new DefaultTableModel(updatemodel, tbl_items_table_cols));
//                tbl_items_table.revalidate();
//
//
//            }
        }
    }

    static void updateInvoice() {


        if (new_inv_flag == false) {

            for (int counter = 0; counter < Main_invoicesTable.length; counter++) {
                if ((Main_invoicesTable[counter][0]).equals(lbl_invoice_number_value.getText())) {
                    Object cell_content_name = new Object();
                    Object cell_content_date = new Object();
                    cell_content_name = tbx_customer_name.getText();
                    cell_content_date = tbx_invoice_date.getText();
                    tbl_invoices_table.setValueAt(cell_content_name, counter, 2);
                    tbl_invoices_table.setValueAt(cell_content_date, counter, 1);
                    break;
                }
            }
        }
        else // new customer
        {
            invoices_model.addRow(new String[]{ lbl_invoice_number_value.getText()  , tbx_invoice_date.getText() ,tbx_customer_name.getText(),"0"});
            new_inv_flag = false;

        }
    }

    static void cancelUpdate() {

        for (int counter = 0; counter < invoices_arr.length; counter++) {
            if ((invoices_arr[counter][0]).equals(lbl_invoice_number_value.getText())) {
                tbx_customer_name.setText(invoices_arr[counter][2]);
                tbx_invoice_date.setText(invoices_arr[counter][1]);
                break;
            }
        }
    }

    static void deleteInvCmd() {

        lbl_invoice_number_value.setText("");
        tbx_invoice_date.setText("");
        tbx_customer_name.setText("");
        lbl_invoice_total.setText("");

//        int temp_row_counter=-1;
//        String[][] temp_invoice_arr = new String[invoices_arr.length-1][4];
//        for (int row_counter = 0; row_counter < invoices_arr.length;row_counter++)
//        {
//            temp_row_counter++;
//            if (invoices_arr[row_counter][0] != lbl_invoice_number_value.getText() )
//            {
//                for (int col_counter = 0; col_counter < 4; col_counter++) {
//                    temp_invoice_arr[temp_row_counter][col_counter] = invoices_arr[row_counter][col_counter];
//                    System.out.println(temp_invoice_arr[temp_row_counter][col_counter]);
//                    System.out.println(invoices_arr[row_counter][col_counter]);
//                }
//            }
//            else {
//                temp_row_counter = temp_row_counter-1;
//            }
//        }
//
//        Object updatemodel[][] = temp_invoice_arr;
//        tbl_invoices_table.setModel(new DefaultTableModel(updatemodel,tbl_invoices_table_cols));
//        tbl_invoices_table.revalidate();
//
//
//
//

        //DefaultTableModel model = (DefaultTableModel) tbl_invoices_table.getModel();
//        if (tbl_invoices_table.getSelectedRow() == 0)
//        {
//            System.out.println("Select row");
//        }

//        System.out.println(tbl_invoices_table.getSelectedRow() );

//                DefaultTableModel model = (DefaultTableModel) tbl_items_table.getModel();
//
        // remove items first
//        for (int counter = items_model.getRowCount(); counter >1; counter--)
//            if (tbl_items_table[0][0]=="3")
//
//            {
//
//            }

//        methodCalled = false;


        int row = tbl_invoices_table.getSelectedRow();
        int col = tbl_invoices_table.getSelectedColumn();
        String selectedItem = String.valueOf(tbl_invoices_table.getValueAt(row, 0));


        //items_model.getDataVector().removeIf(items_model.getValueAt(0,0).equals("0"));

//        Object del_row= new Object();
//        del_row =  invoices_model.row getValueAt(0,0) ;
//        System.out.println(invoices_model.getValueAt(0,0));
//        invoices_model.removeRow((int) del_row);
//        //items_model.removeRow(items_model.getValueAt(1,1) );
//

//////////////////////////////////////////OKKKKK
        for (int x = items_model.getRowCount() - 1; x >= 0; x--) {
            System.out.println("Row ID : " + x);
            String y = items_model.getValueAt(x, 0).toString();

            String selected = selectedItem;
            if (selected.equals(y)) {
                items_model.removeRow(x);
            }
        }
        tbl_items_table.setModel(items_model);
        tbl_items_table.revalidate();
        tbl_invoices_table.setRowSelectionAllowed(false);
        //    tbl_invoices_table.clearSelection();
        invoices_model.removeRow(row);
        //  invoices_model.removeRow(row);


//////////////////////////////////////////OKKKKK

//            String y = items_model.getValueAt(1,0).toString();
//
//            String selected = selectedItem;
//            //System.out.println("counter : " + items_model.getValueAt(x,0) + " Selected Item : "+ selectedItem + " >>> " + items_model.getValueAt(x,0).equals(selectedItem));
//            if (selected.equals(y) );
//            {
//                items_model.removeRow(1);
//            }
//
//        tbl_items_table.setModel(items_model);
//        tbl_items_table.revalidate();


        /////////// OK2


//        for (int i = items_model.getRowCount() - 1; i >= 0; --i) {
//            for (int j = items_model.getColumnCount() - 1; j >= 0; --j) {
//                if (items_model.getValueAt(i, j).equals(value)) {
//                    // what if value is not unique?
//                    return i;
//                }
//            }
//        }


        // invoices_model.removeRow(row);
        //invoices_model.removeRow(Integer.valueOf(selectedItem));

//        Main_itemsTable = itemsTable.getTableData(tbl_items_table);
//        Main_invoicesTable = invoicesTable.getTableData(tbl_invoices_table);
//        for (int counter = items_tbl_rowcount-1 ; counter >=0; counter--)
//        {
//            System.out.println(counter);
//            if ((tbl_items_table.getValueAt(counter-1,0).equals(selectedItem) ))
//            {
//               items_model.removeRow(counter);
//               //removeRow(counter);
//            }
//        }


        //    methodCalled = true;

//
//        invoices_model.removeRow(0);
//        items_model.removeRow(0);
//        items_model.removeRow(0);
//        items_model.removeRow(0);
//        items_model.removeRow(0);
//        items_model.removeRow(0);
//        tbl_items_table.setModel(items_model);
//
//
//
////
//        tbl_invoices_table.setRowSelectionAllowed(false);// clearSelection();
////
////        //tbl_invoices_table.getSelectionModel().removeSelectionInterval(0, 1);
//        invoices_model.removeRow(0);
//        tbl_invoices_table.setRowSelectionAllowed(true);
//
//        methodCalled = true;
//        //        Object[][] items_model_arr;
////


//      for (int counter = items_model.getRowCount(); counter >1; counter--)
//            if (tbl_items_table [0][0]=="3")
//
//            {
//
//            }

//
//        tbl_invoices_table.setRowSelectionInterval(0,0);
//        items_model.removeRow(3);
//        items_model.removeRow(2);


        //tbl_items_table.revalidate();
//        items_model.removeRow(2);

//        int z = tbl_invoices_table.getSelectedRow();
//        invoices_model.removeRow(z);
//        tbl_invoices_table.
        //model.removeRow(Integer.valueOf(1 ));

    }

    static void newInvCmd() {

        int maxInvId = 0;

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();

        for (int counter = 0; counter < invoices_model.getRowCount() ; counter++) {
            if (Integer.valueOf((String) invoices_model.getValueAt(counter,0)) > maxInvId)
            {
                maxInvId = Integer.valueOf((String) invoices_model.getValueAt(counter,0));
            }
        }
        System.out.println(maxInvId);
        lbl_invoice_number_value.setText(String.valueOf(maxInvId+1)   ) ;
        lbl_invoice_total.setText("0") ;
        tbx_invoice_date.setText(String.valueOf(java.time.LocalDate.now()));
        tbx_customer_name.setText("");
        new_inv_flag = true;




    }
}



