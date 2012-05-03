/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cinemabooking.gui;

import cinemabooking.Film;
import cinemabooking.FilmCollection;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import javax.imageio.ImageIO;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import java.net.URL;
import javax.swing.*;

/**
 *
 * @author charlie_r_mills
 */
class FilmPanel implements ActionListener{
    public JPanel frame;
    public String selectedFilm;
    public JComboBox filmList;

    public FilmPanel(JPanel filmsPanel) {
        frame = filmsPanel;
        this.addElements();
    }
    
    public JComboBox filmList(){
        String[] selections = new String[new FilmCollection().fetchAll().countFilms()];
        FilmCollection films = new FilmCollection().fetchAll();
        Iterator itr = films.iterator();
        int i = 0;
            while(itr.hasNext()){
                Film film = (Film) itr.next();
                selections[i] = film.title;
                i++;
            }
        JComboBox combo = new JComboBox(selections);
        combo.addActionListener(this);
        filmList = combo;
        selectedFilm = combo.getSelectedItem().toString();
        return combo;
    }
    
    public void addElements(){
        frame.add(filmList());
        description();
    }
    
    public void description(){
        Image image = null;
        Film film = new Film();
        film.fromTitle(selectedFilm);
        JTextArea description = new JTextArea(film.getFromFlixster().toArray()[0].toString());
        description.setPreferredSize(new Dimension(1200, 120));
        description.setLineWrap(true);
        description.setEditable(false);
        frame.add(description);
        
        try{
            image = ImageIO.read(new URL(film.getFromFlixster().toArray()[1].toString()));
        }
        catch(Exception e){
            System.err.print(e);
        }
        
        JLabel imageLabel = new JLabel(new ImageIcon(image));
        frame.add(imageLabel);
    }
    
    public void redraw(){
        frame.removeAll();
        frame.add(filmList);
        this.description();
        frame.revalidate();
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        JComboBox filmList = (JComboBox) ae.getSource();
        selectedFilm = filmList.getSelectedItem().toString();
        redraw();
    }
}
