package com.sunspotgames.cardplayer.views;

import java.awt.Component;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class overlapper extends JFrame {
	 
    overlapper() {
 
        JLabel k = new JLabel(new ImageIcon("src/main/java/com/sunspotgames/cardplayer/resources/th.gif"));
        JLabel q = new JLabel(new ImageIcon("src/main/java/com/sunspotgames/cardplayer/resources/as.gif"));
        
        
 
        OverlapLayout layout = new OverlapLayout(new Point(35, 0));
        layout.setPopupInsets(new Insets(20, 20, 0, 0));
        JPanel panel = new JPanel(layout);
 
        panel.add(k);
        panel.add(q);
        add(panel);
    }
 
    public static void main(String[] args) {
        overlapper gui = new overlapper();
        gui.setSize(500, 400);
        gui.setVisible(true);
 
    }
 
}