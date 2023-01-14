import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.border.Border;
import java.text.DecimalFormat;
import java.util.stream.*;

public class Label extends JFrame implements ActionListener{
    private static final long serialVersionUID = 1L;
    private JLabel lblSize,lblBev,lblGlass,lblReport,lbljuice,lblcupsize;
    private JComboBox<String> size;
    private JRadioButton rdJuice,rdWater,rdTea,rdCoffee,rdPickup,rdDelivery;
    private ButtonGroup beverageGroup;
    private JTextField txtGlass;
    private JButton btnAdd,btnOrder,btnEdit;
    int amount_of;
    private static final DecimalFormat df = new DecimalFormat("0.00");
    ArrayList<Beverage> list_of_bvr = new ArrayList<Beverage>();

    static String[] report= new String[30];

    static String output="";
    static double pay=0.0;

    static int reportIndex=0;

    public static String[] removeOrder(String[] input, int item) {
            String[] output = new String[input.length - 1];
            if (input.length == 1) {}
            else{
                int i=0;
                while(i<item){
                    output[i]=input[i];
                    ++i;
                }
                for(int j=i; j< input.length-1-i;j++)
                    output[j]=input[j+1];
            }
            return output;
    }

    public Label() {
        setLayout(null);
        setSize(600,400);
        setLocationRelativeTo(null);
        setTitle("Beverage Order System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// exit on closing
        ImageIcon image=new ImageIcon("beverage.png");// icon
        setIconImage(image.getImage());

        getContentPane().setBackground(Color.PINK);
        init();
        btnAdd.addActionListener(this);
        btnEdit.addActionListener(this);
        btnOrder.addActionListener(this);
        setVisible(true);

    }

    public void init() {



        lblSize = new JLabel("Select size:");// a JLabel that labels the comboBox
        lblSize.setSize(250, 50);
        lblSize.setLocation(100, 10);
        add(lblSize);

        String[] sizes = {"Small","Medium","Large"};
        size = new JComboBox<String>(sizes);//
        size.setSelectedIndex(0);//default selection is small
        size.setSize(100, 35);
        size.setLocation(100, 50);
        Border border= BorderFactory.createLineBorder(Color.BLACK);
        size.setBorder(border);
        size.setBackground(new Color(247, 219, 219));
        add(size);

        lblcupsize=new JLabel();
        lblcupsize.setBounds(210,35,100,50);
        lblcupsize.setIcon(resize(new ImageIcon("cupsize4.png"),lblcupsize.getWidth(),lblcupsize.getHeight()));
        add (lblcupsize);


        lblBev = new JLabel("Select which type of beverage you want to order:");//a JLabel that labels the radioButtons
        lblBev.setSize(500, 50);
        lblBev.setLocation(100, 75);
        add(lblBev);

        beverageGroup = new ButtonGroup();

        rdJuice = new JRadioButton("Juice");
        rdJuice.setSize(75, 50);
        rdJuice.setLocation(100, 110);
        rdJuice.setBackground(Color.PINK);
        add(rdJuice);

        lbljuice= new JLabel();

        rdWater = new JRadioButton("Water");
        rdWater.setSize(75, 50);
        rdWater.setLocation(175, 110);
        rdWater.setBackground(Color.PINK);
        add(rdWater);

        rdTea = new JRadioButton("Tea");
        rdTea.setSize(75, 50);
        rdTea.setLocation(250, 110);
        rdTea.setBackground(Color.PINK);
        add(rdTea);

        rdCoffee = new JRadioButton("Coffee");
        rdCoffee.setSize(75, 50);
        rdCoffee.setLocation(325, 110);
        rdCoffee.setBackground(Color.PINK);
        add(rdCoffee);

        beverageGroup.add(rdJuice);
        beverageGroup.add(rdWater);
        beverageGroup.add(rdTea);
        beverageGroup.add(rdCoffee);

        lblGlass = new JLabel("How many glasses:");//a JLabel that labels the Text Field
        lblGlass.setSize(150, 50);
        lblGlass.setLocation(100, 150);
        add(lblGlass);

        txtGlass = new JTextField();//a JTextField to get how many glasses of beverage is the user want
        txtGlass.setSize(70, 25);
        txtGlass.setLocation(220, 165);
        txtGlass.setBackground(new Color	(247, 219, 219));
        add(txtGlass);


        btnAdd = new JButton("Add");
        btnAdd.setSize(120, 40);
        btnAdd.setLocation(100, 230);
        add(btnAdd);

        btnEdit = new JButton("Edit");
        btnEdit.setSize(120, 40);
        btnEdit.setLocation(240, 230);
        btnEdit.setEnabled(false);
        add(btnEdit);

        btnOrder = new JButton("Order");
        btnOrder.setSize(120, 40);
        btnOrder.setLocation(380, 230);
        btnOrder.setEnabled(false);
        add(btnOrder);

        lblReport = new JLabel();//will be showing report of the beverages that added.
        lblReport.setSize(500, 50);
        lblReport.setLocation(100, 270);
        add(lblReport);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String size_of = (String)size.getSelectedItem();
        if(e.getSource().equals(btnAdd)) {
            if( (rdJuice.isSelected() || rdTea.isSelected() || rdCoffee.isSelected() || rdWater.isSelected()) && !(txtGlass.getText().isEmpty()) ) {
                try {
                    amount_of  = Integer.parseInt(txtGlass.getText().trim());
                    Beverage bvg;
                    if(rdJuice.isSelected())  { bvg = new Juice(size_of,amount_of,this); }
                    else if(rdWater.isSelected())  { bvg = new Water(size_of,amount_of,this); }
                    else if(rdTea.isSelected())    { bvg = new Tea(size_of,amount_of,this); }
                    else { bvg = new Coffee(size_of,amount_of,this); }

                    txtGlass.setText(null);
                    list_of_bvr.add(bvg);

                    report[reportIndex] = reportIndex+1+". "+bvg.toString();
                    double totalprice_of_bvg = bvg.getAmount() * bvg.getPrice();
                    pay += totalprice_of_bvg;
                    report[reportIndex] = report[reportIndex] + " - "+totalprice_of_bvg+" TL\n";
                    output = output+report[reportIndex];
                    ++reportIndex;


                    lblReport.setText(bvg.toString()+" added");
                    beverageGroup.clearSelection();
                    btnEdit.setEnabled(true);
                    btnOrder.setEnabled(true);
                }
                catch(NumberFormatException e1) {//if written data in TextField can't be converted to an integer[String,char,double etc...]
                    JOptionPane.showMessageDialog(this, "Enter an integer as amount");
                }
            }
            else { JOptionPane.showMessageDialog(this, "Choose a beverage type and enter an amount");
                //if none of the radio buttons are selected or the textField is empty
            }
        }

        if (e.getSource().equals(btnEdit)) {
//            for(int i=0;i<list_of_bvr.size();i++) {
//                Beverage bvgi = list_of_bvr.get(i);
//                report += i+1+". "+bvgi.toString();
//                double totalprice_of_bvg = bvgi.getAmount() * bvgi.getPrice();
//                pay += totalprice_of_bvg;
//                report = report + " - "+totalprice_of_bvg+" TL\n";
//            }
            Object editObj = JOptionPane.showInputDialog(null, output+"\nRemove which order?", "Edit order", JOptionPane.QUESTION_MESSAGE);
            if(editObj != null) {
                try {
                    String orderEdited = editObj.toString();
                    int orderEditedInt=Integer.parseInt(orderEdited);
                    if (orderEditedInt< 1 || orderEditedInt > list_of_bvr.size()) {
                        JOptionPane.showMessageDialog(this, "Order out of range!");
                    }
                    else{
                        double amountRemoved=list_of_bvr.get(orderEditedInt-1).getAmount() * list_of_bvr.get(orderEditedInt-1).getPrice();
                        pay -= amountRemoved;
                        report =  removeOrder( report, orderEditedInt-1);
//                        for(int i=0;i<report.length;i++)
//                            System.out.println(report[i]);
                        lblReport.setText(list_of_bvr.get(orderEditedInt-1).toString()+" removed");
                        list_of_bvr.remove(orderEditedInt-1);
                    }
                }
                catch(NumberFormatException e1) {//if written data in TextField can't be converted to an integer[String,char,double etc...]
                    JOptionPane.showMessageDialog(this, "Enter an integer as amount");
                }
            }
        }

        if(e.getSource().equals(btnOrder)) {
            String[] values = {"Pick up","Delivery"};
            double deliveryfee=0.00;
            Object selected = JOptionPane.showInputDialog(null, "Order type: Pick up/Delivery", "Selection", JOptionPane.DEFAULT_OPTION, null, values, "0");
            if ( selected != null ){//null if the user cancels.
                String orderType = selected.toString();
                report[reportIndex] = "Order type: "+orderType;
                if (orderType.equals("Delivery")){
                    if (pay<15 && pay!=0){
                        deliveryfee=5;
                    }
                    else{
                        deliveryfee = Double.parseDouble(df.format(pay*0.10));
                    }
                }
                report[reportIndex] = report[reportIndex] + " - " + deliveryfee +" TL\n";
                output = output+report[reportIndex];
                pay = pay+deliveryfee;

                Object promocodeObj =JOptionPane.showInputDialog(null,"Any promo code?", "Promo code", JOptionPane.QUESTION_MESSAGE,null,null,"Press OK if no");
                if(promocodeObj != null) {
                    String promoCode = promocodeObj.toString();
                    if (promoCode.equals("PROMO20")) {
                        report[reportIndex] = report[reportIndex] + "Promotion: 20%\nDiscount: " + df.format(pay * 0.20) + "TL";
                        pay = pay * 0.80;
                    } else if (promoCode.equals("PROMO30")) {
                        report[reportIndex] = report[reportIndex] + "Promotion: 30%\nDiscount: " + df.format(pay * 0.30) + "TL";
                        pay = pay * 0.70;
                    } else {
                        report[reportIndex] = report[reportIndex] + "Discount: 0 TL";
                    }

                    ImageIcon icon = new ImageIcon("order.png");
                    ImageIcon scaledicon=resize(icon,100,100);
                    JOptionPane.showMessageDialog(this, report,"Order Summary",JOptionPane.INFORMATION_MESSAGE,scaledicon);


                    icon=new ImageIcon("checkout.png");
                    scaledicon=resize(icon,100,100);
                    JOptionPane.showMessageDialog(this,"You should pay "+df.format(pay)+" TL","Checkout",JOptionPane.INFORMATION_MESSAGE,scaledicon);
                    lblReport.setText(null);
                    btnEdit.setEnabled(false);
                    btnOrder.setEnabled(false);
                    list_of_bvr.clear();
                    reportIndex=0;// set to zero after every order
                    output="";
                    pay=0;
                }

            }




        }
    }

    public static void main(String[] args) {
        new Label();
    }

    public static ImageIcon resize(ImageIcon im, int w, int h) {
        BufferedImage bi=new BufferedImage(w,h,BufferedImage.TRANSLUCENT);
        Graphics2D gd=(Graphics2D) bi.createGraphics();
        gd.addRenderingHints(new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));
        gd.drawImage(im.getImage(),0,0,w,h,null);
        gd.dispose();
        return new ImageIcon(bi);
    }
}
//haha



