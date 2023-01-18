import javax.swing.JOptionPane;

public class Water extends Beverage {
    private boolean iced;
    private int iceInt;

    public Water(String size,int amount,Label lbl) {
        super(size,amount);
        try{
            Object iceObj = JOptionPane.showConfirmDialog(lbl, "Would you like ice?","Ice",JOptionPane.YES_NO_OPTION);
            String iceStr = iceObj.toString();
            iceInt = Integer.parseInt(iceStr);
            if(iceInt==0) {
                iced=true;
            }
            double price=0.0;
            if(iceInt!=-1){
                if(size.equals("Small")) price=1.0;
                else if(size.equals("Medium")) price=2.0;
                else price=3.0;
            }
            if(iced) price*=1.25;
            setPrice(price);
        }catch (NullPointerException e) {
        }

    }

    @Override
    public String toString() {
        if(iceInt<0) return "0 glass(es) of water";
        if(iced) return super.toString()+"Water with ice";
        else return super.toString()+"Water";
    }
}