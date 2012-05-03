/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cinemabooking.gui;

import cinemabooking.Film;
import cinemabooking.FilmCollection;
import cinemabooking.Utilities;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author charlie_r_mills
 */
public class MainWindow extends JFrame implements ActionListener{
    private JPanel filmsPanel;
    private JPanel bookingPanel;
    private BookingPanel booking;
    
    public MainWindow(){
    
        super("Cinema Booking");
        setVisible(rootPaneCheckingEnabled);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setName("Booking System");
        setTitle("Booking System");
        setSize(1350,500);
        
        
        this.createMenu();
        this.filmsPanel = new JPanel();
        this.bookingPanel = new JPanel();
        
        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Films", filmsPanel);
        tabs.addTab("Booking", bookingPanel);
        
        add(tabs);
        
        new BookingPanel(this.bookingPanel);
        new FilmPanel (this.filmsPanel);
        
        this.repaint();
    }
    
    public void createMenu(){
        JMenuBar menubar = new JMenuBar();
        JMenu menu = new JMenu("File");
        menu.add(new JMenuItem("Quit"));
        JMenuItem clear = new JMenuItem("Clear Current Booking");
        clear.addActionListener(this);
        menu.add(clear);
        menubar.add(menu);
        this.setJMenuBar(menubar);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        bookingPanel.removeAll();
        bookingPanel.repaint();
        booking = new BookingPanel(this.bookingPanel);
    }
}
