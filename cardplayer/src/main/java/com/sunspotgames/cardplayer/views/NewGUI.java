package com.sunspotgames.cardplayer.views;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Insets;
import java.awt.Point;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class NewGUI extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NewGUI frame = new NewGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public NewGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1200, 800);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JLabel i = new JLabel(new ImageIcon("src/main/java/com/sunspotgames/cardplayer/resources/th.gif"));
        JLabel j = new JLabel(new ImageIcon("src/main/java/com/sunspotgames/cardplayer/resources/as.gif"));  
        JLabel k = new JLabel(new ImageIcon("src/main/java/com/sunspotgames/cardplayer/resources/2s.gif"));
        JLabel l = new JLabel(new ImageIcon("src/main/java/com/sunspotgames/cardplayer/resources/5d.gif"));
        JLabel m = new JLabel(new ImageIcon("src/main/java/com/sunspotgames/cardplayer/resources/7d.gif"));
        JLabel n = new JLabel(new ImageIcon("src/main/java/com/sunspotgames/cardplayer/resources/js.gif"));
        
        JPanel contentPanel = new JPanel();
 
        OverlapLayout layout = new OverlapLayout(new Point(35, 0));
        layout.setPopupInsets(new Insets(20, 20, 0, 0));
        
        JPanel panel1 = new JPanel(layout);
 
        panel1.add(i);
        panel1.add(j);
        
        JPanel panel2 = new JPanel(layout);
        
        panel2.add(k);
        panel2.add(l);
        
        JPanel panel3 = new JPanel(layout);
        
        panel3.add(m);
        panel3.add(n);
        
        contentPanel.add(panel1);
        contentPanel.add(panel2);
        contentPanel.add(panel3);
        
        contentPane.add(contentPanel);
		
		setContentPane(contentPane);
	}

}
