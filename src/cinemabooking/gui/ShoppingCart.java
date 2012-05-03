/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cinemabooking.gui;

import cinemabooking.Booking;
import cinemabooking.Database;
import cinemabooking.Utilities;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JTextArea;

/**
 *
 * @author charlie_r_mills
 */
public class ShoppingCart {
    public ArrayList<ArrayList> items;
    
    ShoppingCart(){
        items = new ArrayList();
    }
    
    public void add(int showingId, String seat, String ticketType, String ticketCost){
        ArrayList item = new ArrayList();
        item.add(showingId);
        item.add(seat);
        item.add(ticketType);
        item.add(ticketCost);
        items.add(item);
    }
    
    public void printItems(){
        Iterator itr = items.iterator();
        while(itr.hasNext()){
            ArrayList item = (ArrayList) itr.next();
            System.out.println(item.get(0).toString()
                    + item.get(1).toString()
                    + item.get(2).toString()
                    + item.get(3).toString());
        }
    }
    
    public void saveItems(){
        Iterator itr = items.iterator();
        while(itr.hasNext()){
            ArrayList item = (ArrayList) itr.next();
            try{
            String sql = "INSERT INTO bookings (showing_id, seat_number, reference) VALUES("
                           + Integer.parseInt(item.get(0).toString()) + ", "
                           + item.get(1).toString() + ","
                           + "'" + new Utilities().randomString() + "'"
                           +")";
            
            System.out.println(sql);
            new Database().runUpdate(sql);
        }
        catch(Exception e){
            
        }
        }
        
    }
    
    
    public double calculateTotal(){
        Iterator itr = items.iterator();
        double calc = 0;
        while(itr.hasNext()){
            ArrayList item = (ArrayList) itr.next();
            calc = calc + Double.parseDouble(item.get(3).toString());
        }
        return calc;
    }
}
