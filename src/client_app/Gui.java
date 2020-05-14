package client_app;

import client_app.classes.AccountData;
import client_app.classes.LogginData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Gui implements ActionListener, Runnable {

    private String[] questions = {"Imię psa?",
            "Ulubiona drużyna?",
            "Ulubiony samochód?",
            "Imię ulubionej osoby?"};
    Network networkApi;

    private JFrame loginFrame = new JFrame();
    //login
    private JButton loginButton = new JButton();
    private JLabel nameLabel = new JLabel();
    private JLabel nickLabel = new JLabel("Login");
    private JTextField nickField = new JTextField();
    private JLabel passwordLabel = new JLabel("Hasło");
    private JTextField passwordField = new JTextField();
    private JLabel registerLabel = new JLabel();
    private JButton registerButton = new JButton();
    private JLabel forgetPasswordLabel = new JLabel();
    private JButton forgetPasswordButton = new JButton();
    private JLabel errorField = new JLabel();

    //register
    private JLabel registrationLabel = new JLabel("Rejestracja");
    private JLabel registerNickLabel = new JLabel();
    private JLabel registerNickError = new JLabel();
    private JTextField registerNickField = new JTextField();
    private JLabel registerPasswordLabel = new JLabel();
    private JTextField registerPasswordField = new JTextField();
    private JButton registrationButton = new JButton("Zarejestruj");
    private JButton registrationCancelButton = new JButton("Anuluj");
    private JLabel registerQALabel = new JLabel("Pytanie pomocnicze");
    private JComboBox registerQuestions = new JComboBox(questions);
    private JTextField registerAnswer = new JTextField();

    //forget password
    private JLabel fpLabel = new JLabel("Przypomnij hasło");
    private JLabel fpNickLabel = new JLabel("Login");
    private JTextField fpNickField = new JTextField();
    private JButton fpCheckNick = new JButton("Sprawdź");
    private JLabel fpQuesion = new JLabel();
    private JTextField fpAnswer = new JTextField();
    private JLabel fpNewPasswordLabel = new JLabel("Nowe hasło");
    private JTextField fpNewPasswordField = new JTextField();
    private JButton fpButton = new JButton("Zmień hasło");
    private JButton fpCancelButton = new JButton("Anuluj");

    //player hud
    private JFrame playerFrame = new JFrame();
    private JButton logoutButton = new JButton();
    Container x = new Container();


    Gui() {
        modeMainFrame(0);
        //login
        loginFrame.setSize(350, 360);
        loginFrame.setLayout(null);
        loginFrame.setVisible(true);
        loginFrame.setTitle("GamePlatform");
        loginFrame.setResizable(false);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - 320) / 2, (Toolkit.getDefaultToolkit().getScreenSize().height - 360) / 2);

        playerFrame.setSize(600,500);
        playerFrame.setLayout(null);
        playerFrame.setTitle("Game-platform");
        playerFrame.setResizable(false);
        playerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        nameLabel.setText("Logowanie / Rejestracja");
        nameLabel.setFont(new Font("Arial", Font.BOLD,20));
        nameLabel.setBounds(40, 35, 280 ,25);
        loginFrame.add(nameLabel);

        nickLabel.setBounds(40, 68, 150 ,20);
        nickField.setBounds(40, 90, 150 ,20);
        loginFrame.add(nickLabel);
        loginFrame.add(nickField);

        passwordLabel.setBounds(40, 118, 150 ,20);
        passwordField.setBounds(40, 140, 150 ,20);
        loginFrame.add(passwordField);
        loginFrame.add(passwordLabel);

        loginButton.setText("Zaloguj");
        loginButton.setBounds(200, 90, 100, 70);
        loginFrame.add(loginButton);
        loginButton.addActionListener(this);

        errorField.setBounds(190, 68, 150 ,20);
        errorField.setForeground(Color.red);
        loginFrame.add(errorField);

        forgetPasswordLabel.setText("Zapomniałeś hasła?");
        forgetPasswordLabel.setBounds(40,  190 ,  300,20);
        forgetPasswordButton.setText("Przypomnij!");
        forgetPasswordButton.setBounds(40, 215, 110 ,20);
        forgetPasswordButton.addActionListener(this);
        loginFrame.add(forgetPasswordButton);
        loginFrame.add(forgetPasswordLabel);

        registerLabel.setText("Nie masz konta? Zarejestruj sie juz teraz!");
        registerLabel.setBounds(40,  240 ,  300,20);
        registerButton.setText("Rejestracja");
        registerButton.setBounds(40, 265, 110 ,20);
        registerButton.addActionListener(this);
        loginFrame.add(registerLabel);
        loginFrame.add(registerButton);

        //registration
        registrationLabel.setBounds(40,20,150,20);
        registrationLabel.setFont(new Font("Arial", Font.BOLD,20));
        registerNickLabel.setText("Login");
        registerNickLabel.setBounds(40, 45, 100 ,20);
        registerNickError.setBounds(120, 45, 100 ,20);
        registerNickError.setForeground(Color.red);
        registerNickField.setBounds(40, 67, 150 ,20);

        registerPasswordLabel.setBounds(40, 95, 150 ,20);
        registerPasswordLabel.setText("Hasło");
        registerPasswordField.setBounds(40, 117, 150 ,20);

        registerQALabel.setBounds(40,142,150,20);
        registerQuestions.setBounds( 40, 177, 200,20);
        registerAnswer.setBounds(40,212,200,40);
        //registerAnswer.setLineWrap(true);

        registrationCancelButton.setBounds(40,267,100,30);
        registrationCancelButton.addActionListener(this);
        registrationButton.setBounds(150,267,100,30);
        registrationButton.addActionListener(this);

        loginFrame.add(registrationLabel);
        loginFrame.add(registerNickField);
        loginFrame.add(registerNickLabel);
        loginFrame.add(registerPasswordField);
        loginFrame.add(registerPasswordLabel);
        loginFrame.add(registrationButton);
        loginFrame.add(registrationCancelButton);
        loginFrame.add(registerQuestions);
        loginFrame.add(registerQALabel);
        loginFrame.add(registerAnswer);
        loginFrame.add(registerNickError);

        //forget password

        fpLabel.setBounds(40,20,200,20);
        fpLabel.setFont(new Font("Arial", Font.BOLD,20));
        fpNickLabel.setBounds(40, 45, 150 ,20);
        fpNickField.setBounds(40, 67, 150 ,20);
        fpCheckNick.setBounds(200, 67, 75 ,20);
        fpCheckNick.setMargin(new Insets(10,2,10,2));
        fpCheckNick.addActionListener(this);

        fpQuesion.setBounds(40, 95, 150 ,20);
        fpAnswer.setBounds(40, 117, 150 ,20);

        fpNewPasswordLabel.setBounds(40,142,150,20);
        fpNewPasswordField.setBounds( 40, 167, 150,20);
        fpButton.setBounds(160,212,110,30);
        fpButton.addActionListener(this);
        fpCancelButton.setBounds(49,212,110,30);
        fpCancelButton.addActionListener(this);

        loginFrame.add(fpAnswer);
        loginFrame.add(fpButton);
        loginFrame.add(fpLabel);
        loginFrame.add(fpNewPasswordField);
        loginFrame.add(fpNewPasswordLabel);
        loginFrame.add(fpNickField);
        loginFrame.add(fpNickLabel);
        loginFrame.add(fpQuesion);
        loginFrame.add(fpCancelButton);
        loginFrame.add(fpCheckNick);



        //player hud
        logoutButton.setBounds(40,40,100, 70);
        logoutButton.setText("wyloguj");
        logoutButton.addActionListener(this);
        playerFrame.add(logoutButton);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if(source == loginButton){
            if(networkApi.loginUser(new LogginData(nickField.getText(),passwordField.getText()))){
                loginFrame.setVisible(false);
                playerFrame.setVisible(true);
                errorField.setText("");
            }
            else { errorField.setText("Złe dane logowania"); }
        }
        if(source == logoutButton){
            nickField.setText("");
            passwordField.setText("");
            loginFrame.setVisible(true);
            playerFrame.setVisible(false);
        }
        if(source == registerButton){
            errorField.setText("");
            modeMainFrame(1);

        }
        if(source == forgetPasswordButton){
            modeMainFrame(2);
            fpNickField.setEnabled(true);
            fpAnswer.setEnabled(false);
            fpButton.setEnabled(false);
            fpNewPasswordField.setEnabled(false);
            errorField.setText("");
        }
        if(source == registrationButton){
            if(networkApi.registerUser(new AccountData(registerNickField.getText(),
                                                    registerPasswordField.getText(),
                                                    questions[registerQuestions.getSelectedIndex()],
                                                    registerAnswer.getText()))){
                modeMainFrame(0);
                nickField.setText(registerNickField.getText());
                passwordField.setText("");
                registerNickError.setText("");
            }
            registerNickError.setText("Zajęty login");
        }

        if(source == registrationCancelButton || source == fpCancelButton){
            modeMainFrame(0);
            fpNewPasswordField.setText("");
            fpAnswer.setText("");
            fpNickField.setText("");
            fpQuesion.setText("");

            registerNickError.setText("");
            registerAnswer.setText("");
            registerNickField.setText("");
            registerPasswordField.setText("");
            registerQuestions.setSelectedIndex(0);

            nickField.setText("");
            passwordField.setText("");
        }

        if(source == fpButton){
            modeMainFrame(0);
        }
        if(source == fpCheckNick){
            fpNickField.setEnabled(false);
            fpAnswer.setEnabled(true);
            fpButton.setEnabled(true);
            fpNewPasswordField.setEnabled(true);
        }
    }

    @Override
    public void run() {
        Gui a = new Gui();


    }

    public void modeMainFrame(int mode){
        // 0 - login frame ; 1 - register frame ; 2 - forget password frame
        if(mode==0) {
            changeLoginFrame(true);
            changeRegistrationFrame(false);
            changeForgetPassworFrame(false);

        }
        if(mode==1) {
            changeLoginFrame(false);
            changeRegistrationFrame(true);

        }
        if(mode==2){
            changeLoginFrame(false);
            changeForgetPassworFrame(true);
        }
    }

    public void changeLoginFrame(Boolean mode){
        loginButton.setVisible(mode);
        nameLabel.setVisible(mode);
        nickLabel.setVisible(mode);
        nickField.setVisible(mode);
        passwordLabel.setVisible(mode);
        passwordField.setVisible(mode);
        registerLabel.setVisible(mode);
        registerButton.setVisible(mode);
        forgetPasswordLabel.setVisible(mode);
        forgetPasswordButton.setVisible(mode);
        errorField.setVisible(mode);
    }
    public void changeRegistrationFrame(Boolean mode){
        registrationLabel.setVisible(mode);
        registrationButton.setVisible(mode);
        registrationCancelButton.setVisible(mode);
        registerPasswordField.setVisible(mode);
        registerPasswordLabel.setVisible(mode);
        registerNickField.setVisible(mode);
        registerNickLabel.setVisible(mode);
        registerQALabel.setVisible(mode);
        registerAnswer.setVisible(mode);
        registerQuestions.setVisible(mode);
        registerNickError.setVisible(mode);

    }

    private void changeForgetPassworFrame(boolean mode) {
        fpButton.setVisible(mode);
        fpNewPasswordField.setVisible(mode);
        fpNewPasswordLabel.setVisible(mode);
        fpAnswer.setVisible(mode);
        fpQuesion.setVisible(mode);
        fpNickLabel.setVisible(mode);
        fpNickField.setVisible(mode);
        fpLabel.setVisible(mode);
        fpCancelButton.setVisible(mode);
        fpCheckNick.setVisible(mode);
}


}





