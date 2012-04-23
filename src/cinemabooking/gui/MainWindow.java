/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cinemabooking.gui;

import cinemabooking.Film;
import cinemabooking.FilmCollection;
import cinemabooking.Utilities;
import java.util.Iterator;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author charlie_r_mills
 */
public class MainWindow extends JFrame{
    
    public MainWindow(){
    
        super("Cinema Booking");
        setVisible(rootPaneCheckingEnabled);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setName("Booking System");
        setTitle("Booking System");
        setSize(800,500);
        
        
     
        JMenuBar menubar = new JMenuBar();
        JMenu menu = new JMenu("File");
        
        menu.add(new JMenuItem("Save"));
        menu.add(new JMenuItem("Quit"));
        
        JLabel booking = new JLabel("booking");
        JLabel films = new JLabel("films");
        
        menubar.add(menu);
        this.setJMenuBar(menubar);
        
        JPanel filmsPanel = new JPanel();
        JPanel bookingPanel = new JPanel();
        
        filmsPanel.add(films);
        bookingPanel.add(booking);
        
        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Films", filmsPanel);
        tabs.addTab("Booking", bookingPanel);
        
        add(tabs);
        
        JButton[] buttons = new JButton[30];
        
        for(int row = 0; row < 30; row++){
            buttons[row] = new JButton("sdfdsf");
            bookingPanel.add(buttons[row]);
        }
        filmsPanel.add(bookingList());
        this.repaint();
    }
    
    public JList filmList(){
        String[] selections = { "Film1", "Film2", "Film3", "Film4" };
        JList list = new JList(selections);
        
        return list;
    }
    
    public JComboBox bookingList(){
        
        String[] selections = new String[new FilmCollection().fetchAll().countFilms()];
        
        FilmCollection films = new FilmCollection().fetchAll();
        System.out.print(films.countFilms());
        
        Iterator itr = films.iterator();
        
        int i = 0;
            
            while(itr.hasNext()){
                Film film = (Film) itr.next();
                selections[i] = film.title;
                i++;
            }
        
        JComboBox combo = new JComboBox(selections);
        
        return combo;
    }
}
