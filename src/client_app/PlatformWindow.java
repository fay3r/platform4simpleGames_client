package client_app;

import client_app.classes.dtoPlatform.AccountScoresDto;
import client_app.classes.dtoPlatform.ChangePasswordDto;
import client_app.classes.dtoPlatform.ChatMessage;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class PlatformWindow extends Component implements ActionListener {

    private Network networkApi;
    private List<ChatMessage> chatMessages = new LinkedList<>();
    private List<String> logins;

    private JFrame playerFrame = new JFrame();
    private JButton logoutButton = new JButton();
    private JLabel logoutError = new JLabel("Zamknij otwarte gry!");
    private JLabel hudNick = new JLabel();
    private JButton gamesTab = new JButton("Gry");
    private JButton settingsTab = new JButton("Ustawienia");
    private JButton tableScoreTab = new JButton("Wyniki");
    private JButton chatButton = new JButton("Chat");
    private JButton gameTTTButton = new JButton("<html><center>"+"Kółko Krzyżyk"+"</html");
    private JButton gameShipsButton = new JButton("Statki");
    private JButton gameSPR = new JButton("<html><center>"+"Kamień papier"+"</html");
    private JButton openAdminButton = new JButton("<html><p style=font-size:7px>" +
            "Admin" +
            "</p> </html>");

    private Container gamesCon = new Container();
    private Container scoresCon = new Container();

    //panel administratora
    private JFrame adminPanel = new JFrame("Administracja");
    private JButton adminDeletePlayer = new JButton("Usuń Gracza");
    private JButton adminResetScores = new JButton("Resetuj\n statystyki");
    private JButton adminClearChat = new JButton("Wyczyść chat");
    private JComboBox adminSelectPlayer = new JComboBox();

    //settings
    private Container settingsCon = new Container();
    private JButton settNewPasswordButton = new JButton("Zmień hasło");
    private JLabel settOldPasswordLab = new JLabel("Stare hasło");
    private JLabel settNewPasswordLab = new JLabel("Nowe hasło");
    private JTextField settOldPassword = new JTextField();
    private JTextField settNewPassword = new JTextField();

    //scores
    private JScrollPane scoreScrollPane = new JScrollPane();
    private JTable scoresTable = new JTable();

    //chat
    private JFrame chatFrame = new JFrame("Chat");
    private JTextArea chat = new JTextArea(16,20);
    private JScrollPane chatScrollPane;
    private JTextArea chatNewMessage = new JTextArea(2,20);
    private JButton chatSendMessage = new JButton("Wyślij");

    //gry
    private JFrame gameWindow = new JFrame();
    private GameWindow gamePanel;
    private JLabel errorOpenGame = new JLabel("<html><p style=font-size:15px>Jedna z gier jest już otwarta!</html>");

    PlatformWindow(String nick){
        playerFrame.setSize(600,500);
        playerFrame.setVisible(true);
        playerFrame.setLayout(null);
        playerFrame.setTitle("Game-platform");
        playerFrame.setResizable(false);
        playerFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        playerFrame.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - 800) / 2, (Toolkit.getDefaultToolkit().getScreenSize().height - 360) / 2);
        playerFrame.getContentPane().setBackground(new Color(100, 140, 180));

        adminPanel.setSize(510,160);
        adminPanel.setLayout(null);
        adminPanel.setVisible(false);
        adminPanel.setResizable(false);
        adminPanel.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        adminPanel.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - 800) / 2, (Toolkit.getDefaultToolkit().getScreenSize().height - 670) / 2);
        adminPanel.getContentPane().setBackground(new Color(100, 140, 180));

        chatFrame.setSize(500,600);
        chatFrame.getContentPane().setBackground(new Color(100, 140, 180));
        chatFrame.setLayout(null);
        chatFrame.setVisible(true);
        chatFrame.setResizable(false);
        chatFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        chatFrame.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width-778), (Toolkit.getDefaultToolkit().getScreenSize().height - 360) / 2);

        //player hud
        logoutButton.setBounds(450,20,100, 50);
        logoutButton.setText("Wyloguj");
        logoutButton.addActionListener(this);

        logoutError.setBounds(450,65,160,30);
        logoutError.setVisible(false);
        logoutError.setForeground(Color.red);
        playerFrame.add(logoutError);

        openAdminButton.setBounds(460,90,100,50);
        openAdminButton.addActionListener(this);
        openAdminButton.setVisible(false);

        hudNick.setBounds(50,20, 200,50);
        hudNick.setText(nick);
        hudNick.setFont(hudNick.getFont().deriveFont(18.0f));
        hudNick.setHorizontalAlignment(SwingConstants.CENTER);
        hudNick.setVerticalAlignment(SwingConstants.CENTER);
        hudNick.setBackground(new Color(55, 80, 115));
        hudNick.setOpaque(true);

        gamesTab.setBounds(40,90,100,50);
        gamesTab.addActionListener(this);

        settingsTab.setBounds(300,20,100,50);
        settingsTab.addActionListener(this);

        tableScoreTab.setBounds(180,90,100,50);
        tableScoreTab.addActionListener(this);

        gamesCon.setBounds(20,120,560,330);
        settingsCon.setBounds(20,120,560,330);
        settingsCon.setVisible(false);

        scoresCon.setBounds(5,160,575,330);
        scoresCon.setVisible(false);

        chatButton.setBounds(320,90,100,50);
        chatButton.addActionListener(this);

        gameTTTButton.setBounds(20,50,160,200);
        gameTTTButton.addActionListener(this);

        gameShipsButton.setBounds(200,50,160,200);
        gameShipsButton.addActionListener(this);

        gameSPR.setBounds(380,50,160,200);
        gameSPR.addActionListener(this);

        errorOpenGame.setBounds(150,260,300,50);
        errorOpenGame.setVisible(false);
        errorOpenGame.setForeground(Color.red);
        gamesCon.add(errorOpenGame);
        gamesCon.add(gameSPR);
        gamesCon.add(gameTTTButton);
        gamesCon.add(gameShipsButton);
        gamePanel=new GameWindow(nick);

        playerFrame.add(logoutButton);
        playerFrame.add(hudNick);
        playerFrame.add(gamesTab);
        playerFrame.add(settingsTab);
        playerFrame.add(gamesCon);
        playerFrame.add(settingsCon);
        playerFrame.add(tableScoreTab);
        playerFrame.add(scoresCon);
        playerFrame.add(openAdminButton);
        playerFrame.add(chatButton);

        //panel administratora
        adminResetScores.setBounds(20,60,150,40);
        adminResetScores.addActionListener(this);
        adminClearChat.setBounds(340,60,130,40);
        adminClearChat.addActionListener(this);
        adminDeletePlayer.setBounds(190,60,130,40);
        adminDeletePlayer.addActionListener(this);
        adminSelectPlayer.setBounds(110,10,160,40);

        adminPanel.add(adminResetScores);
        adminPanel.add(adminClearChat);
        adminPanel.add(adminDeletePlayer);
        adminPanel.add(adminSelectPlayer);

        // setting panel
        settOldPasswordLab.setBounds(20,30,100,40);
        settOldPassword.setBounds(20,71,100,30);
        settNewPasswordLab.setBounds(140,30,100,40);
        settNewPassword.setBounds(140,71,100,30);
        settNewPasswordButton.setBounds(260,71,130,40);
        settNewPasswordButton.addActionListener(this);

        settingsCon.add(settNewPassword);
        settingsCon.add(settNewPasswordButton);
        settingsCon.add(settNewPasswordLab);
        settingsCon.add(settOldPassword);
        settingsCon.add(settOldPasswordLab);

        //scores
        scoreScrollPane.setBounds(1,1,573,325);

        scoresTable.setBounds(1,1,573,325);
        scoresTable.setRowSelectionAllowed(false);
        scoresTable.setColumnSelectionAllowed(false);
        scoresTable.setCellSelectionEnabled(false);
        scoresTable.setEnabled(false);
        scoresTable.getTableHeader().setResizingAllowed(false);
        scoresTable.getTableHeader().setReorderingAllowed(false);
        scoreScrollPane.setColumnHeader(new JViewport(){
            @Override
            public Dimension getPreferredSize() {
                Dimension d = super.getPreferredSize();
                d.height = 40;
                return d;
            }
        });
        scoresCon.add(scoreScrollPane);
        scoreScrollPane.setViewportView(scoresTable);
        scoreScrollPane.setEnabled(false);

        //chat
        chat.setEditable(false);
        chatScrollPane = new JScrollPane(chat);
        chatScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        chatScrollPane.setBounds(20,20,460,480);

        chatNewMessage.setBounds(20,510,300,40);
        chatSendMessage.setBounds(340,510,140,40);
        chatNewMessage.setLineWrap(true);
        chatNewMessage.setWrapStyleWord(true);

        chatSendMessage.addActionListener(this);
        chatFrame.add(chatNewMessage);
        chatFrame.add(chatScrollPane);
        chatFrame.add(chatSendMessage);

        ScheduledExecutorService e= Executors.newSingleThreadScheduledExecutor();
        e.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                    loadMessages();
                }
        }, 0, 10, TimeUnit.SECONDS);
        isAdmin();

        gameWindow.setResizable(false);
        gameWindow.setSize(350, 400);
        gameWindow.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - 800) / 2, (Toolkit.getDefaultToolkit().getScreenSize().height - 360) / 2);
        gameWindow.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                gameWindow.setVisible(false);
            }
        });

    }

    @Override
    public void actionPerformed(ActionEvent e) {
            Object source = e.getSource();
        if(source == logoutButton){
            if(!gameWindow.isVisible() && !gamePanel.check()) {
                int response = JOptionPane.showConfirmDialog(this, "Napewno chcesz wylogowac", "Wyloguj", JOptionPane.YES_NO_OPTION);
                if (response == JOptionPane.YES_OPTION) {
                    playerFrame.setVisible(false);
                    adminPanel.setVisible(false);
                    chatFrame.setVisible(false);
                    gameWindow.setVisible(false);
                    new LoginWindow();
                }
            } else{
                logoutError.setVisible(true);
            }

        }
        if(source == gamesTab){
            gamesCon.setVisible(true);
            settingsCon.setVisible(false);
            scoresCon.setVisible(false);
            errorOpenGame.setVisible(false);
        }

        if(source == settingsTab){
            gamesCon.setVisible(false);
            settingsCon.setVisible(true);
            scoresCon.setVisible(false);
        }

        if(source == gameTTTButton ){
            if(!gameWindow.isVisible()) {

                errorOpenGame.setVisible(false);
                gamePanel.start(hudNick.getText(), "TTT");
                gameWindow.getContentPane().removeAll();
                gameWindow.setVisible(true);
                gameWindow.getContentPane().add(gamePanel);
                gameWindow.pack();

                System.out.println(gameWindow.isVisible());
            } else {
                errorOpenGame.setVisible(true);
            }
        }

        if(source == gameShipsButton ) {
            if (!gameWindow.isVisible()) {

                errorOpenGame.setVisible(false);
                gamePanel.start(hudNick.getText(), "Ship");
                gameWindow.getContentPane().removeAll();
                gameWindow.setVisible(true);
                gameWindow.getContentPane().add(gamePanel);
                gameWindow.pack();

                System.out.println(gameWindow.isVisible());
            } else {
                errorOpenGame.setVisible(true);
            }
        }

        if(source == gameSPR ) {
            if (!gameWindow.isVisible()) {
                errorOpenGame.setVisible(false);
                gamePanel.start(hudNick.getText(), "RSP");
                gameWindow.getContentPane().removeAll();
                gameWindow.setVisible(true);
                gameWindow.getContentPane().add(gamePanel);
                gameWindow.pack();

                System.out.println(gameWindow.isVisible());
            } else {
                errorOpenGame.setVisible(true);
            }
        }

        if(source == tableScoreTab){
            gamesCon.setVisible(false);
            settingsCon.setVisible(false);
            scoresCon.setVisible(true);
            scoresTable.removeAll();
            List<AccountScoresDto> temp = networkApi.getScoreTable();
            DefaultTableModel model= new DefaultTableModel(
                    new String[] {
                            "nick","<html> <center>"+ "KK<br>wygrane" +"</html>","<html> <center> "+ "KK<br>przegrane" +"</html>","<html> <center>"+ "Statki<br>wygrane" +"</html>","<html> <center>"+ "Statki<br>przegrane" +"</html>","<html> <center>"+ "PKN<br>wygrane" +"</html>","<html> <center>"+ "PKN<br>przegrane" +"</html>"
                    },0
            );
            scoresTable.setModel(model);

            for(int i=0; i<temp.size();i++){
                AccountScoresDto temp2 = temp.get(i);
                model.addRow(new Object[]{temp2.nick,temp2.tttGamesWon,temp2.tttGamesLost,temp2.shipsGamesWon,temp2.shipsGamesLost,temp2.rpsGamesWon,temp2.rpsGamesLost});
            }
        }

        if(source == chatButton) {
            if (chatFrame.isVisible()) {
                chatFrame.setVisible(false);
            } else {
                chatFrame.setVisible(true);
            }
        }

        if(source == chatSendMessage) {
            if (!chatNewMessage.getText().isBlank()) {
                chatNewMessage.setBackground(Color.white);
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                LocalDateTime now = LocalDateTime.now();
                networkApi.sendMessage(new ChatMessage(hudNick.getText(), chatNewMessage.getText(), dtf.format(now)));
                chatNewMessage.setText("");
                loadMessages();
            } else {
                chatNewMessage.setBackground(Color.red);
            }
        }

        if(source == openAdminButton){
            if(adminPanel.isVisible()==true){
                adminPanel.setVisible(false);
            } else {
                adminSelectPlayer.removeAllItems();
                logins=null;
                adminPanel.setVisible(true);
                logins = networkApi.getLogins();
                for(String x :logins) {
                    if (!x.equals("administrator")) {
                        adminSelectPlayer.addItem(x);
                    }
                }
            }
        }

        if(source == adminClearChat){
            int response = JOptionPane.showConfirmDialog(this,"Napewno chcesz wyczyścić logi czatu?","Chat",JOptionPane.YES_NO_OPTION);
            if( response == JOptionPane.YES_OPTION) {
                networkApi.clearChat();
                loadMessages();
            }
        }

        if(source == adminResetScores){
            int response = JOptionPane.showConfirmDialog(this,"Napewno chcesz zresetowac statystyki gracza "+ logins.get(adminSelectPlayer.getSelectedIndex()) + "?","Wyniki",JOptionPane.YES_NO_OPTION);
            if( response == JOptionPane.YES_OPTION) {
                networkApi.resetScore(logins.get(adminSelectPlayer.getSelectedIndex()+1));
            }
        }

        if(source == adminDeletePlayer){
            int response = JOptionPane.showConfirmDialog(this,"Napewno chcesz usuąć gracza " + logins.get(adminSelectPlayer.getSelectedIndex()+1) + "?","Usuwanie",JOptionPane.YES_NO_OPTION);
            if( response == JOptionPane.YES_OPTION) {
                networkApi.deletePlayer(logins.get(adminSelectPlayer.getSelectedIndex()+1));
                logins=null;
                adminSelectPlayer.removeAllItems();
                logins = networkApi.getLogins();
                for(String x :logins) {
                    if(!x.equals("administrator")) {
                        adminSelectPlayer.addItem(x);
                    }
                }
            }
        }

        if(source == settNewPasswordButton) {
            System.out.println(hudNick.getText() + "    " + settOldPassword.getText() + "    " + settNewPassword.getText());
            if (!settNewPassword.getText().isBlank()) {
                if (networkApi.passwordChange(new ChangePasswordDto(hudNick.getText(), settOldPassword.getText(), settNewPassword.getText()))) {
                    JOptionPane.showMessageDialog(this,
                            "Hasło zostało zmienione",
                            "Hasło",
                            JOptionPane.WARNING_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Hasło nie zostało zmienione",
                            "Hasło",
                            JOptionPane.WARNING_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Nowe hasło nie może być puste!", "Błąd", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    private void loadMessages() {
        chatMessages = networkApi.getChatMessages();
        chat.setText("");
        for (ChatMessage thisOne : chatMessages) {
            System.out.println("wiadomosc");
            chat.setText(chat.getText() + thisOne.toString());
            chat.setFont(chat.getFont().deriveFont(18.0f));
        }
    }

    private void isAdmin() {
        if ("administrator".equals(hudNick.getText().replace(" ", ""))) {
            openAdminButton.setVisible(true);
        }
    }
}
