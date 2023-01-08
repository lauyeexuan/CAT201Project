import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.Border;


public class Label extends JFrame implements ActionListener{
    private static final long serialVersionUID = 1L;
    private JLabel lblSize,lblBev,lblGlass,lblReport,lbljuice,lblcupsize;
    private JComboBox<String> size;
    private JRadioButton rdJuice,rdWater,rdTea,rdCoffee,rdPickup,rdDelivery;
    private ButtonGroup beverageGroup;
    private JTextField txtGlass;
    private JButton btnAdd,btnOrder;
    int amount_of;
    ArrayList<Beverage> list_of_bvr = new ArrayList<Beverage>();

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
        txtGlass.setSize(50, 25);
        txtGlass.setLocation(220, 165);
        txtGlass.setBackground(new Color	(247, 219, 219));
        add(txtGlass);



        btnAdd = new JButton("Add");
        btnAdd.setSize(120, 40);
        btnAdd.setLocation(100, 230);
        add(btnAdd);

        btnOrder = new JButton("Order");
        btnOrder.setSize(120, 40);
        btnOrder.setLocation(280, 230);
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
                    lblReport.setText(bvg.toString()+" added");
                    beverageGroup.clearSelection();
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
        if(e.getSource().equals(btnOrder)) {
            String report = "";
            double pay=0.0;
            for(int i=0;i<list_of_bvr.size();i++) {
                Beverage bvgi = list_of_bvr.get(i);
                report += bvgi.toString();
                double totalprice_of_bvg = bvgi.getAmount() * bvgi.getPrice();
                pay += totalprice_of_bvg;
                report = report + " - "+totalprice_of_bvg+" TL\n";
            }

            String[] values = {"Pick up","Delivery"};
            double deliveryfee=0.0;
            Object selected = JOptionPane.showInputDialog(null, "Order type: Pick up/Delivery", "Selection", JOptionPane.DEFAULT_OPTION, null, values, "0");
            if ( selected != null ){//null if the user cancels.
                String orderType = selected.toString();
                report = report + "\n"+"Order type: "+orderType;
                if (orderType.equals("Delivery")){
                    if (pay<15 && pay!=0){
                        deliveryfee=5;
                    }
                    else{
                        deliveryfee = pay*0.1;
                    }
                }
                report = report + " - " + deliveryfee +" TL\n";
                pay = pay+deliveryfee;
            }
            else{
                System.out.println("User cancelled");
            }

            JOptionPane.showMessageDialog(this, report,"Order Summary",JOptionPane.INFORMATION_MESSAGE);
            JOptionPane.showMessageDialog(this,"You should pay "+pay+" TL","Checkout",JOptionPane.INFORMATION_MESSAGE);
            lblReport.setText(null);
            btnOrder.setEnabled(false);
            list_of_bvr.clear();
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



