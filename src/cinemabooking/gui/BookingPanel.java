/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cinemabooking.gui;

import cinemabooking.Database;
import cinemabooking.Utilities;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import javax.swing.*;

/**
 *
 * @author charlie_r_mills
 */
public class BookingPanel implements ActionListener{
    
    public JPanel seatingPanel;
    public JPanel managementPanel;
    public JPanel frame;
    public JPanel left;
    public JPanel center;
    public JPanel right;
    protected ArrayList<ArrayList> ticketList;
    protected JTextField priceLabel;
    private ArrayList<ArrayList> buttonArray; 
    public ShoppingCart cart;
    public JComboBox ticketCombo;
    
    
    BookingPanel(JPanel bookingJPanel){
       this.frame = bookingJPanel;
       center = new JPanel();
       left = new JPanel();
       right = new JPanel();
       seatingPanel = new JPanel();
       managementPanel = new JPanel(); 
       //Set Layouts
       frame.setLayout(new BorderLayout());
       seatingPanel.setLayout(new GridLayout(0, 3));
       left.setLayout(new GridLayout(5, 5));
       center.setLayout(new GridLayout(5, 10));
       right.setLayout(new GridLayout(5, 10));
       frame.add(seatingPanel, BorderLayout.CENTER);
       managementPanel.setBackground(Color.lightGray);
       managementPanel.setPreferredSize(new Dimension(100,200));
       //Add Panels
       frame.add(managementPanel, BorderLayout.SOUTH);
       seatingPanel.add(left);
       seatingPanel.add(center);
       seatingPanel.add(right);
             
       
       this.createButtonArray();
       try{
            this.checkTakenSeats();
       }
       catch(SQLException e){
           
       }
       this.printSeats();
       this.managementPanel();
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
            jButton.setPreferredSize(new Dimension(10,20));
            if((Integer)button.get(1) == 1){
                jButton.setEnabled(false);
            }
            if (i<51){
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
    
    public void managementPanel(){
        int ticketTypeCount = 0;
        ResultSet rs = null;
        

        try{
            rs = new Database().runQuery("SELECT * FROM tickets");     
        }
        catch(Exception e){
            System.err.println(e);
        } 
        
        ArrayList<String> ticketListArray = new ArrayList();
        ticketList = new ArrayList();
        
        try{
            while(rs.next()){
                ArrayList ticket = new ArrayList();
                ticket.add(rs.getInt("id"));
                ticket.add(rs.getString("type"));
                ticket.add(rs.getFloat("price"));
                ticketListArray.add(rs.getString("type"));
                ticketList.add(ticket);
            }
        }
        catch(Exception e){
            
        }
        
        //add action listener
        ticketCombo = new JComboBox(ticketListArray.toArray());
        managementPanel.add(new JLabel("Please Select Ticket Type"));
        managementPanel.add(ticketCombo);
        priceLabel = new JTextField();
        managementPanel.add(priceLabel);
        ticketCombo.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                ArrayList ticket =  (ArrayList) ticketList.get(ticketCombo.getSelectedIndex());
                setPriceLabelText(ticket.get(2).toString());
                
            }
        });
    }
    
    public void setPriceLabelText(String price){
        this.priceLabel.setText("£" + price);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        //Check to see if cart is empty
        if (cart == null){
            cart = new ShoppingCart();
        }
        JButton button = (JButton) ae.getSource();
        button.setEnabled(false);
        frame.repaint();
        
        //get cost of ticket
        ArrayList ticket =  (ArrayList) ticketList.get(ticketCombo.getSelectedIndex());
        
        
        //Add to shopping cart
        cart.add(1, buttonArray.indexOf(button), ticketCombo.getSelectedItem().toString(), ticket.get(2).toString());
        cart.printItems();
        //Get id of the button
        int buttonId = buttonArray.indexOf(button);
        try{
            String sql = "INSERT INTO bookings (showing_id, seat_number, reference) VALUES("
                           + "1,"
                           + button.getText() + ","
                           + "'" + new Utilities().randomString() + "'"
                           +")";
            System.out.println(sql);
            new Database().runUpdate(sql);
        }
        catch(Exception e){
            
        }
    }
}


