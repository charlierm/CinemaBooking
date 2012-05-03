/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cinemabooking.gui;

import cinemabooking.Booking;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author charlie_r_mills
 */
public class ShoppingCart {
    public ArrayList<ArrayList> items;
    
    ShoppingCart(){
        items = new ArrayList();
    }
    
    public void add(int showingId, int seat, String ticketType, String ticketCost){
        ArrayList item = new ArrayList();
        item.add(showingId);
        item.add(seat);
        item.add(ticketType);
        item.add(ticketCost);
        items.add(item);
    }
    
    public void printItems(){
        Iterator itr = items.iterator();
        int i = 0;
        while(itr.hasNext()){
            ArrayList item = items.get(i);
            System.out.println(item.get(0).toString()
                    + item.get(1).toString()
                    + item.get(2).toString()
                    + item.get(3).toString());
        }
    }
}
