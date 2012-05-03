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
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

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
    public JPanel top;
    public JPanel right;
    public int selectedShowing;
    protected ArrayList<ArrayList> ticketList;
    protected JTextField priceLabel;
    private ArrayList<ArrayList> buttonArray; 
    public ShoppingCart cart;
    public JComboBox ticketCombo;
    public JComboBox showingCombo;
    private JTextArea reciept;
    
    
    BookingPanel(JPanel bookingJPanel){
       this.frame = bookingJPanel;
       center = new JPanel();
       left = new JPanel();
       right = new JPanel();
       top = new JPanel();
       seatingPanel = new JPanel();
       selectedShowing = 1;
       managementPanel = new JPanel(); 
       //Set Layouts
       
       frame.setLayout(new BorderLayout());
       seatingPanel.setLayout(new GridLayout(0,3));
       left.setLayout(new GridLayout(5, 5));
       center.setLayout(new GridLayout(5, 10));
       right.setLayout(new GridLayout(5, 10));
       top.setBackground(Color.lightGray);
       frame.add(top, BorderLayout.NORTH);
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
       this.getShowingList();
    }
    
    public void addSeatingPlanWidgets(){
        center = new JPanel();
        left = new JPanel();
        right = new JPanel();
        left.setLayout(new GridLayout(5, 5));
        center.setLayout(new GridLayout(5, 10));
        right.setLayout(new GridLayout(5, 10));
        seatingPanel.add(left);
        seatingPanel.add(center);
        seatingPanel.add(right);
        
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
    
    public void getShowingList(){
        ResultSet rs = null;
        ArrayList showings = new ArrayList();
        Date showingDate = new Date();
        try{
            rs = new Database().runQuery("SELECT films.title, films.id, showing.id, showing.date_time"
                    + " FROM films, showing"
                    + " WHERE (showing.film = films.id)");
            while(rs.next()){
                ArrayList showing = new ArrayList();
                showing.add(rs.getInt("showing.id"));
                showing.add(rs.getInt("showing.date_time"));
                showing.add(rs.getString("films.title"));
                showings.add(showing);
            }
        }
        catch(Exception e){ 
            System.out.print(e);
        }
        showingCombo = new JComboBox(showings.toArray());
        showingCombo.setActionCommand("showingCombo");
        showingCombo.addActionListener(this);
        top.add(showingCombo);
        
    }
    
    public void redraw(){
        seatingPanel.removeAll();
        try{
            this.createButtonArray();
            this.checkTakenSeats();
        }
        catch(Exception e){
        }
        
        this.addSeatingPlanWidgets();
        this.printSeats();
        seatingPanel.revalidate();
        

    }
    
    private void checkTakenSeats() throws SQLException{
        ResultSet rs = null;
        try{
            rs = new Database().runQuery("SELECT * FROM bookings WHERE "
                    + "showing_id=" + "'" + this.selectedShowing + "'"
                    + " ORDER BY seat_number DESC");
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
            jButton.setPreferredSize(new Dimension(40,10));
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
        ArrayList ticket =  (ArrayList) ticketList.get(ticketCombo.getSelectedIndex());
        setPriceLabelText(ticket.get(2).toString());
        ticketCombo.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                ArrayList ticket =  (ArrayList) ticketList.get(ticketCombo.getSelectedIndex());
                setPriceLabelText(ticket.get(2).toString());
                
            }
        });
        reciept = new JTextArea();
        reciept.setPreferredSize(new Dimension(400, 190));
        reciept.setEditable(false);
        managementPanel.add(reciept);
        //Add Submit Button
        JButton submit = new JButton("Submit");
        
        submit.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                //Confirm
                int n = JOptionPane.showConfirmDialog(
                        null,
                        "Do you want to purchase tickets? \nTotal Cost: £" + cart.calculateTotal(),
                        "Total Cost: £" + cart.calculateTotal(),
                        JOptionPane.YES_NO_OPTION);
                if (n == JOptionPane.YES_OPTION) {
                    cart.saveItems();
                    reciept.setText("");
                    
                }
                        
            }
        });
        managementPanel.add(submit);
    }
    
    public void setPriceLabelText(String price){
        this.priceLabel.setText("£" + price);
        priceLabel.repaint();
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        if(ae.getActionCommand().equals("showingCombo")){
            ArrayList selectedShowing = (ArrayList)showingCombo.getSelectedItem();
            this.selectedShowing = Integer.parseInt(selectedShowing.toArray()[0].toString());
            reciept.setText("");
            cart = new ShoppingCart();
            redraw();
            return;
        }
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
        cart.add(this.selectedShowing, button.getText(), ticketCombo.getSelectedItem().toString(), ticket.get(2).toString());
        reciept.append(("Ticket Type: " + ticketCombo.getSelectedItem().toString()
                + "|| Seat Number: " + button.getText()
                + "|| Ticket Cost: £" + ticket.get(2).toString() + "\n"));
    }
}


