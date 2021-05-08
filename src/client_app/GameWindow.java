package client_app;

import client_app.classes.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameWindow extends JPanel implements ActionListener
{
    private JLabel startGame = new JLabel();
    private String login ;
    private JButton imStarting = new JButton("Zaczynam!");
    private JButton opponentStarting = new JButton("Dam mu szanse!");
    private JFrame gameFrame = new JFrame();
    private ShipeGame shipGame = new ShipeGame();
    private TTTGame ttt = new TTTGame();
    private RSPGame rsp = new RSPGame();
    private String selectedGame = "";
    private Network network = new Network();
    private JLabel gameName = new JLabel();

    GameWindow(String nick)
    {
        login=nick;
        gameName.setBounds(125,20,150,150);
        this.add(gameName);

        startGame.setBounds(75, 175, 250, 100);
        startGame.setText("<html><center>"+login +", Wybierz kto zaczyna <br> Powodzenia!"+"</html>");
        startGame.setFont(startGame.getFont().deriveFont(18.0f));
        startGame.setHorizontalAlignment(SwingConstants.CENTER);
        startGame.setVerticalAlignment(SwingConstants.CENTER);
        this.add(startGame);

        imStarting.setBounds(100, 280, 200, 50);
        imStarting.removeActionListener(this);
        imStarting.addActionListener(this);

        opponentStarting.setBounds(100, 335, 200, 50);
        opponentStarting.removeActionListener(this);
        opponentStarting.addActionListener(this);

        this.setLayout(null);
        this.add(imStarting);
        this.add(opponentStarting);
        this.setVisible(true);
        this.setBackground(new Color(100, 140, 180));

        gameFrame.setResizable(false);
        gameFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        gameFrame.setVisible(false);
        gameFrame.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - 800) / 2, (Toolkit.getDefaultToolkit().getScreenSize().height - 360) / 2);

        gameFrame.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent event) {
                onEnd();
            }
        });
    }

    public void start(String nick,String gameMode)
    {
        selectedGame=gameMode;
        if(selectedGame=="TTT"){
            opponentStarting.setVisible(true);
            gameName.setText("<html><center><p style=\"font-size:30px\">Kółko krzyżyk</html>");
        }
        if(selectedGame == "Ship") {
            opponentStarting.setVisible(true);
            gameName.setText("<html><center><p style=\"font-size:35px\">Statki</html>");
        }
        if (selectedGame == "RSP") {
            opponentStarting.setVisible(false);
            gameName.setText("<html><center><p style=\"font-size:30px\">Kamień<br>Papier<br>Nożyce</html>");
        }


    }

    @Override
    public void actionPerformed(ActionEvent actionEvent)
    {
        Object source = actionEvent.getSource();
        if((source == imStarting || source == opponentStarting) && !gameFrame.isVisible())
        {
            boolean first=false;
            if(source==imStarting){
                first=true;
            }
            System.out.println("Zaczynamy nowa gre");
            System.out.println("Pierwszy ruch wykona gracz: " + first);

            //to select specyfic game
            switch(selectedGame)
            {
                case "Ship":
                {
                    onStart();
                    shipGame.start(first, login);
                    System.out.println("Start ship game");
                    gameFrame.getContentPane().removeAll();
                    gameFrame.setVisible(true);
                    gameFrame.getContentPane().add(shipGame);
                    gameFrame.pack();
                    break;
                }
                case "TTT":
                {
                    onStart();
                    ttt.start(first, login);
                    System.out.println("Start ttt game");
                    gameFrame.getContentPane().removeAll();
                    gameFrame.setVisible(true);
                    gameFrame.getContentPane().add(ttt);
                    gameFrame.pack();
                    break;
                }
                case  "RSP":
                {
                    //jeszcze nie zroibione
                    onStart();
                    rsp.start(first, login);
                    System.out.println("Start RSP game");
                    gameFrame.getContentPane().removeAll();
                    gameFrame.setVisible(true);
                    gameFrame.getContentPane().add(rsp);
                    gameFrame.pack();
                    break;
                }
            }
        }
    }

    private void onStart()
    {
        //funkcja wołana przy staarcie dowlnej gry
        System.out.println("Zaczynamay nowa gry");

    }
    public boolean check(){
        return gameFrame.isVisible();
    }

    private void onEnd()
    {
        //funkcja wołana na koniec każdej gry
        System.out.println("Konie gry");

        switch (selectedGame)
        {
            case "Ship":
            {
                System.out.println("Ship game result: " + shipGame.gameResult());
                if(shipGame.getPlayerPoint()<18 && shipGame.getComputerPoint()<18) {
                    try {
                        int response = JOptionPane.showConfirmDialog(gameFrame,"Napewno chcesz zamknac? Przegrasz :/ " ,"Close",JOptionPane.YES_NO_OPTION);
                        if( response == JOptionPane.YES_OPTION) {
                            gameFrame.setVisible(false);
                            network.gameResult(login, "shipslost");
                        }
                    } catch (Exception e) {
                        System.err.println(e.getMessage());
                    }
                } else {
                    gameFrame.setVisible(false);
                }
                break;
            }
            case "TTT":
            {
                System.out.println("Ship game result: " + ttt.gameResult());
                if(!ttt.isEndGame()) {

                    try {
                        if (!ttt.gameResult().equals(null)) {
                            int response = JOptionPane.showConfirmDialog(gameFrame,"Napewno chcesz zamknac? Przegrasz :/ " ,"Close",JOptionPane.YES_NO_OPTION);
                            if( response == JOptionPane.YES_OPTION) {
                                gameFrame.setVisible(false);
                                network.gameResult(login, "tttlost");
                            }
                        }
                    } catch (Exception e) {
                        System.err.println(e.getMessage());
                    }
                } else {
                    gameFrame.setVisible(false);
                }
                break;
            }
            case "RSP":
            {
                System.out.println("rps game result: " + rsp.gameResult());
                if(rsp.getCount()<3) {
                    try {
                        int response = JOptionPane.showConfirmDialog(gameFrame,"Napewno chcesz zamknac? Przegrasz :/ " ,"Close",JOptionPane.YES_NO_OPTION);
                        if( response == JOptionPane.YES_OPTION) {
                            network.gameResult(login, "rpslost");
                            gameFrame.setVisible(false);
                        }
                    } catch (Exception e) {
                        System.err.println(e.getMessage());
                    }
                } else {
                    gameFrame.setVisible(false);
                }
                break;
            }
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(400   , 430);
    }
}
