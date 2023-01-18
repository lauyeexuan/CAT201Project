import javax.swing.JOptionPane;

public class Coffee extends Beverage {
    private boolean milk;
    private int milkInt;

    public Coffee(String size,int amount,Label lbl) {
        super(size, amount);
        try {
            Object milkObj=JOptionPane.showConfirmDialog(lbl, "Would you like milk?", "Milk", JOptionPane.YES_NO_OPTION);
            String milkStr = milkObj.toString();
            milkInt = Integer.parseInt(milkStr);
            if(milkInt==0) {
                milk=true;
            }
            double price = 0.0;
            if(milkInt!=-1){
                if (size.equals("Small")) price = 3.5;
                else if (size.equals("Medium")) price = 5;
                else price = 6.5;
            }
            if (milk) price *= 1.5;
            setPrice(price);
        } catch (NullPointerException e) {
        }
    }

    @Override
    public String toString() {
        if(milkInt<0) return "0 glass(es) of coffee";
        if(milk) return super.toString()+" Coffee with milk";
        else return super.toString()+" Coffee";
    }
}
