/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.othello.ui;

import java.awt.*;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;
import javax.swing.LayoutStyle.ComponentPlacement;

import com.othello.classes.OthelloGame;
import com.othello.entities.Case;
import com.othello.entities.CaseValue;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

/**
 *
 * @author macbookpro
 */
public class Home extends javax.swing.JFrame 
{
    
    private JPanel panel;
    private OthelloGame game;
    String username;
	public Home(String userName) 
	{
        initComponents();
        username = userName;
        game= new OthelloGame();        
        game.setOwner(userName);
        game.setName(userName+" game");
        
        System.out.println("creating a new gamedata : " + game.getName());

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() 
    {

    	setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        
        panel = new JPanel();
        panel.setPreferredSize(new Dimension(700, 400));
        

        GroupLayout layout = new GroupLayout(getContentPane());
        layout.setHorizontalGroup(
        	layout.createParallelGroup(Alignment.TRAILING)
        		.addGroup(Alignment.LEADING, layout.createSequentialGroup()
        			.addContainerGap()
        			.addComponent(panel, GroupLayout.DEFAULT_SIZE, 673, Short.MAX_VALUE)
        			.addContainerGap())
        );
        layout.setVerticalGroup(
        	layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(layout.createSequentialGroup()
        			.addContainerGap()
        			.addComponent(panel, GroupLayout.DEFAULT_SIZE, 330, Short.MAX_VALUE)
        			.addContainerGap())
        );
        panel.setLayout(null);
        
        JLabel lblNewLabel = new JLabel("Bienvenue a Othello");
        lblNewLabel.setFont(new Font("SimSun", Font.BOLD, 16));
        lblNewLabel.setBounds(235, 42, 201, 19);
        panel.add(lblNewLabel);
        
        
        
        JButton newGame = new JButton("Nouveau");
        newGame.addActionListener(new ActionListener() 
        {
        	public void actionPerformed(ActionEvent e) 
        	{
        		JPanel gamep = ChoicePanel();
        		//gamep.revalidate();
        		
        		setContentPane(gamep);
        		pack();
        		
        	}
        });
        newGame.setBounds(258, 110, 137, 23);
        panel.add(newGame);
        
        JButton LoadGame = new JButton("Continue");
        LoadGame.addActionListener(new ActionListener()
        {
        	public void actionPerformed(ActionEvent e)
        	{
        		String name = (String)JOptionPane.showInputDialog("Veuillez inserer le nom du jeu : ");
        		if(name!=null)
        		{
        			
        			game = OthelloGame.loadgame(name);
        			System.out.println("loading game : " + game.getName());
        			
        			Case[][] board = game.getBoardState();
        			
        			dispose();
        			
        			 BoardFrame BF =new BoardFrame(board,game);
                     BF.setVisible(true);
        		}
        			
        		else
        			JOptionPane.showMessageDialog(null, "no game found!");
        		
        		pack();
        	}
        });
        LoadGame.setBounds(258, 164, 137, 23);
        panel.add(LoadGame);
        
        JButton archives = new JButton("Archives");
        archives.setBounds(258, 223, 137, 23);
        archives.addActionListener(new ActionListener() 
        {
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				ArrayList<String> games = OthelloGame.getGames(username);
				
				JPanel gamelist = showGameList(games);
				JFrame f = new JFrame("Game list");
				
				f.add(gamelist);
				f.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
				f.setVisible(true);
				f.pack();
				pack();
			}
		});
        panel.add(archives);
        
        JButton Deconnexion = new JButton("Déconnexion");
        Deconnexion.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		int a = JOptionPane.showConfirmDialog(null, "Est ce que vous êtes sur ?");
                if (a == JOptionPane.YES_OPTION) {
                          dispose();
                          Authentification auth = new Authentification();
                          auth.setVisible(true);
                      }
        	}
        });
        Deconnexion.setBounds(258, 279, 137, 23);
        panel.add(Deconnexion);
        getContentPane().setLayout(layout);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public JPanel ChoicePanel()
    {
    	
		JPanel Gamepanel = new JPanel();
		Gamepanel.setLayout(null);
		Gamepanel.setPreferredSize(new Dimension(550, 350));
		JButton btnNewButton = new JButton("Joueur vs IA");
		btnNewButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{	
				dispose();
				//1 => pvAI
				game.setType(1);
				System.out.println("PvAI choosed : " + game.getType());
				Case[][] defaultBoard = new Case[8][8];
                for (int i = 0; i < 8; i++) 
                {
                    for (int j = 0; j < 8; j++) 
                    {
                        defaultBoard[i][j] = new Case(i,j);
                    }
                }
                // Adding the initial pieces
                defaultBoard[3][3].setValue(CaseValue.WHITE);
                defaultBoard[3][4].setValue(CaseValue.BLACK);
                defaultBoard[4][4].setValue(CaseValue.WHITE);
                defaultBoard[4][3].setValue(CaseValue.BLACK);
                
                BoardFrame BF =new BoardFrame(defaultBoard,game);
                BF.setVisible(true);
			}
		});
		btnNewButton.setBounds(60, 135, 122, 23);
		Gamepanel.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Joueur vs Joueur");
		btnNewButton_1.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				//0 => pvp
				game.setType(0);
				System.out.println("PvP choosed : " + game.getType());

				Case[][] defaultBoard = new Case[8][8];
                for (int i = 0; i < 8; i++) 
                {
                    for (int j = 0; j < 8; j++) 
                    {
                        defaultBoard[i][j] = new Case(i,j);
                    }
                }
                
                // Adding the initial pieces
                defaultBoard[3][3].setValue(CaseValue.WHITE);
                defaultBoard[3][4].setValue(CaseValue.BLACK);
                defaultBoard[4][4].setValue(CaseValue.WHITE);
                defaultBoard[4][3].setValue(CaseValue.BLACK);
                
                BoardFrame BF =new BoardFrame(defaultBoard,game);
                BF.setVisible(true);
                
			}
		});
		btnNewButton_1.setBounds(316, 135, 135, 23);
		Gamepanel.add(btnNewButton_1);
		
		
		/*-----Player vs AI-----*/
		JLabel Image1 = new JLabel("");
		Image1.setIcon(new ImageIcon(getClass().getResource("player.png")));
		Image1.setBounds(23, 47, 72, 77);
		Gamepanel.add(Image1);
	
		JLabel VS1 = new JLabel("");
		VS1.setIcon(new ImageIcon(getClass().getResource("competition.png")));
		VS1.setBounds(105, 79, 46, 45);
		Gamepanel.add(VS1);
		
		JLabel Image1_1 = new JLabel("");
		Image1_1.setIcon(new ImageIcon(getClass().getResource("ai.png")));
		Image1_1.setBounds(161, 47, 72, 77);
		Gamepanel.add(Image1_1);
		
		/*------Player vs Player -----*/

		JLabel Image2 = new JLabel("");
		Image2.setBounds(284, 47, 72, 77);
		Image2.setIcon(new ImageIcon(getClass().getResource("player.png")));
		Gamepanel.add(Image2);
		
		JLabel Image2_1 = new JLabel("");
		Image2_1.setBounds(366, 90, 46, 14);
		Image2_1.setIcon(new ImageIcon(getClass().getResource("competition.png")));
		Gamepanel.add(Image2_1);
		
		JLabel Image2_3 = new JLabel("");
		Image2_3.setBounds(411, 47, 64, 77);
		Image2_3.setIcon(new ImageIcon(getClass().getResource("player.png")));
		Gamepanel.add(Image2_3);
		
		JButton retour = new JButton("revient au menu");
		retour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				setContentPane(panel);
        		pack();
			}
		});
		retour.setBounds(23, 268, 128, 23);
		Gamepanel.add(retour);
		return Gamepanel;
    }
    
    private JPanel showGameList(ArrayList<String> games)
    {
    	 // Frame initiallization 
        JPanel gamelistPanel = new JPanel(); 
        gamelistPanel.setLayout(new BorderLayout());
        
        // Data to be displayed in the JTable 
        String[][] data = new String[games.size()][2];
        
        for(int i=0; i<games.size();i++)
        {
        	//game name
        	data[i][0] = games.get(i);
        	
        	if(OthelloGame.isWon(data[i][0]))
        		data[i][1] = "WIN"; //gamestate
        	else
        		data[i][1] = "LOOSE";
        }
       
        // Column Names 
        String[] columnNames = { "Game Name", "Game State"}; 
  
        // Initializing the JTable 
        JTable j = new JTable(data, columnNames); 
        
        // adding it to JScrollPane 
        JScrollPane sp = new JScrollPane(j); 
        gamelistPanel.add(sp,BorderLayout.CENTER); 
        // Frame Size 
        gamelistPanel.setSize(500, 200); 
        // Frame Visible = true 
        gamelistPanel.setVisible(true); 
        
        return gamelistPanel;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

       
    }

   
    // End of variables declaration//GEN-END:variables
}