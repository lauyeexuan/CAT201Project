import javax.swing.JOptionPane;

public class Tea extends Beverage {
    private boolean sugar;
    private int sugarInt;

    public Tea(String size,int amount,Label lbl) {
        super(size,amount);
        try {
            Object sugarObj = JOptionPane.showConfirmDialog(lbl, "Would you like sugar?", "Sugar", JOptionPane.YES_NO_OPTION);
            String sugarStr = sugarObj.toString();
            sugarInt = Integer.parseInt(sugarStr);
            if(sugarInt==0) {
                sugar=true;
            }
            double price = 0.0;
            if(sugarInt!=-1){
                if (size.equals("Small")) price = 3.0;
                else if (size.equals("Medium")) price = 4.0;
                else price = 5.0;
            }
            if (sugar) price *= 1.25;
            setPrice(price);
        } catch (NullPointerException e) {
        }
    }


    @Override
    public String toString(){
        if(sugarInt<0) return "0 glass(es) of tea";
        if(sugar) return super.toString()+"Tea with sugar";
        else return super.toString()+"Tea";
    }
}
