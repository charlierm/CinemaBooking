/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cinemabooking.gui;

import cinemabooking.Database;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author charlie_r_mills
 */
public class BookingPanel implements ActionListener{
    
    public JPanel frame;
    public JPanel left;
    public JPanel center;
    public JPanel right;
    
    private ArrayList<ArrayList> buttonArray; 
    
    
    BookingPanel(JPanel bookingJPanel){
       this.frame = bookingJPanel;
       center = new JPanel();
       left = new JPanel();
       right = new JPanel();
       
        
       frame.setLayout(new GridLayout(0, 3));
       frame.add(left);
       frame.add(center);
       frame.add(right);
       
       
       left.setLayout(new GridLayout(5, 5));
       center.setLayout(new GridLayout(5, 10));
       right.setLayout(new GridLayout(5, 10));
       
       JButton testb = new JButton("Test");
       testb.addActionListener(this);
       right.add(testb);
       
       this.createButtonArray();
       try{
            this.checkTakenSeats();
       }
       catch(SQLException e){
           
       }
       this.printSeats();
    }
    
    private void createButtonArray(){
        this.buttonArray = new ArrayList();

        for(int i=0; i<150; i++){
            ArrayList currentButton = new ArrayList();
            currentButton.add(new JButton(Integer.toString(i)));
            currentButton.add(0);
            this.buttonArray.add(currentButton);            
        }
    }
    
    private void checkTakenSeats() throws SQLException{
        ResultSet rs = null;
        try{
            rs = new Database().runQuery("SELECT * FROM bookings ORDER BY seat_number DESC");
        }
        catch(Exception e){
            System.out.print(e);
        }
        Iterator itr = buttonArray.iterator();
        while(itr.hasNext()){
            ArrayList button = (ArrayList) itr.next();
            
            while(rs.next()){
                int seatNumber = rs.getInt("seat_number");
                if(buttonArray.indexOf(button) == seatNumber){
                    button.set(1, 1);
                    this.buttonArray.set(buttonArray.indexOf(button), button);
                }
            }
            rs.first();
        }        
        
    }
    
    public void printSeats(){
        Iterator itr = buttonArray.iterator();
        int i = 0; // Loop count
        while(itr.hasNext()){
            i++;
            ArrayList button = (ArrayList) itr.next();
            JButton jButton = (JButton) button.get(0);
            jButton.addActionListener(this);
            if((Integer)button.get(1) == 1){
                jButton.setEnabled(false);
            }
            if (i<50){
                left.add(jButton);
            }
            else if (i > 50 && i<101){
                center.add(jButton);
            }
            else{
                right.add(jButton);
            }
            
        }
        
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        JButton button = (JButton) ae.getSource();
        button.setEnabled(false);
        frame.repaint();
        
        //Get id of the button
        int buttonId = buttonArray.indexOf(button);
        try{
            new Database().runUpdate("INSERT INTO bookings VALUES("
                    + ""
                    +")");
        }
        catch(Exception e){
            
        }
    }
}


