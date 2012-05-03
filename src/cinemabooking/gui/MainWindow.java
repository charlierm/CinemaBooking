/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cinemabooking.gui;

import cinemabooking.Film;
import cinemabooking.FilmCollection;
import cinemabooking.Utilities;
import java.awt.Insets;
import java.util.Iterator;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author charlie_r_mills
 */
public class MainWindow extends JFrame{
    private JPanel filmsPanel;
    private JPanel bookingPanel;
    
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
        
        filmsPanel.add(bookingList());
        this.repaint();
    }
    
    public JList filmList(){
        String[] selections = { "Film1", "Film2", "Film3", "Film4" };
        JList list = new JList(selections);
        
        return list;
    }
    
    public void createMenu(){
        JMenuBar menubar = new JMenuBar();
        JMenu menu = new JMenu("File");
        menu.add(new JMenuItem("Save"));
        menu.add(new JMenuItem("Quit"));
        menubar.add(menu);
        this.setJMenuBar(menubar);
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
