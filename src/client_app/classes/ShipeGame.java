package client_app.classes;

import client_app.Network;
import client_app.classes.dtoGames.Array2Int;
import client_app.classes.dtoGames.Single2Int;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;

public class ShipeGame extends JPanel implements ActionListener, MouseListener {

    enum GameStatus
    {
        start,
        loop,
        end,
    }

    private BufferedImage radar, sea, bg, ship, shipR, red, green, yellow, fire, blue;
    private JLabel explain = new JLabel();
    private JButton ready = new JButton();
    private JButton clear = new JButton();
    private int count = 0;
    private int Array[][] = new int[10][10];
    private int shipPoint[][] = new int[6][3];
    private GameStatus status = GameStatus.start;
    private int hitPoint[][] = new int[10][10];
    private Point selectedPoint = new Point();

    public ShipeGame()
    {
        this.addMouseListener(this);
        this.setLayout(null);

        ready.setBounds(15, 525, 80, 50);
        ready.setText("Bitwa!");
        ready.addActionListener(this);
        this.add(ready);

        clear.setBounds(100, 525, 130, 50);
        clear.setText("Wyczyść flote");
        clear.addActionListener(this);
        this.add(clear);

        explain.setBounds(250, 525, 550, 50);
        String exp = "<html><center>Za pomocą myszki (LPM i RPM) ustaw swoją flote na mapie.<BR>Daj nam znać kiedy będziesz gotów!</html>";
        explain.setText(exp);
        explain.setFont(explain.getFont().deriveFont(18.0f));
        explain.setHorizontalAlignment(SwingConstants.CENTER);
        explain.setVerticalAlignment(SwingConstants.CENTER);
        this.add(explain);

        lauch.setBounds(50, 525, 100, 50);
        lauch.setText("OGNIA!");
        lauch.addActionListener(this);

        shootText.setBounds(200, 525, 600, 50);
        shootText.setText("Zaznacz miejsce strzału i daj mu popalić!");
        shootText.setFont(shootText.getFont().deriveFont(18.0f));
        shootText.setHorizontalAlignment(SwingConstants.CENTER);
        shootText.setVerticalAlignment(SwingConstants.CENTER);

        gameResult.setBounds(200, 525, 600, 50);
        gameResult.setFont(gameResult.getFont().deriveFont(18.0f));
        gameResult.setHorizontalAlignment(SwingConstants.CENTER);
        gameResult.setVerticalAlignment(SwingConstants.CENTER);

        try
        {
            radar = ImageIO.read(new File("data\\radar.png"));
            sea = ImageIO.read(new File("data\\sea.png"));
            bg = ImageIO.read(new File("data\\bg3.png"));
            ship = ImageIO.read(new File("data\\ship1.png"));
            shipR = ImageIO.read(new File("data\\shipR.png"));
            red = ImageIO.read(new File("data\\red.png"));
            green = ImageIO.read(new File("data\\green.png"));
            yellow = ImageIO.read(new File("data\\yelow.png"));
            fire = ImageIO.read(new File("data\\fire.png"));
            blue = ImageIO.read(new File("data\\blue.png"));

        }
        catch (Exception e)
        {
            System.err.println(e.getMessage());
        }
    }

    public int getPlayerPoint() {
        return PlayerPoint;
    }

    public int getComputerPoint() {
        return ComputerPoint;
    }

    private int PlayerPoint = 0;
    private int ComputerPoint = 0;
    private JButton lauch = new JButton();
    private JLabel shootText = new JLabel();
    private Network network = new Network();
    private boolean playerFirst = true;
    private JLabel gameResult = new JLabel();
    private String login = "";

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        g2.drawImage(bg, 0, 0, 800, 600, null);

        if(status == GameStatus.start)
            drawSelectShip(g2);

        if(status == GameStatus.loop || status == GameStatus.end)
            drawBoard(g2);

    }

    private void drawSelectShip(Graphics2D g2)
    {
        g2.drawImage(sea, 175, 25, 450, 450, null);

        for(int i = 0; i <count; i++)
        {
            if(shipPoint[i][2] == -1)
            {
                int x = (216 + shipPoint[i][0] * 41) - 41 + 17;
                int y = (66 + shipPoint[i][1] * 41)+ 6;
                g2.drawImage(shipR, x, y, 90, 28, null);
            }
            else if(shipPoint[i][2]== 1)
            {
                int x = 216 + shipPoint[i][0] * 41 + 6;
                int y = 66 + shipPoint[i][1] * 41 - 41 + 16;
                g2.drawImage(ship, x, y, 28, 90, null);
            }
        }
    }

    private void drawBoard(Graphics2D g2)
    {
        g2.drawImage(sea, 25, 75, 350, 350, null);
        g2.drawImage(radar, 425, 75, 350, 350, null);

        //draw shipe
        for(int i = 0; i < count; i++)
        {
            if(shipPoint[i][2]== -1)
            {
                int x = (25+32) + shipPoint[i][0] * 32 - 32 + 3;
                int y = (75+32) + shipPoint[i][1] * 32 + 2;
                g2.drawImage(shipR, x, y, 90, 28, null);
            }
            else if(shipPoint[i][2] == 1)
            {
                int x = (25+32) + shipPoint[i][0] * 32 + 2;
                int y = (75+32) + shipPoint[i][1] * 32  - 32+ 3;
                g2.drawImage(ship, x, y, 28, 90, null);
            }
        }

        //draw selcted
        if(!selectedPoint.equals(new Point(-1, -1)))
        {
            g2.drawImage(yellow, (425+32) + selectedPoint.x * 32 + 2, (75+32)+ selectedPoint.y * 32 + 2, 28, 28, null);
        }

        //draw hitpoitn
        for(int i = 0; i<10; i++)
        {
            for(int j = 0;j < 10; j++)
            {
                if(hitPoint[i][j] == 1)
                {
                    g2.drawImage(green, (425+32) + i * 32 + 2, (75+32)+ j * 32 + 2, 28, 28, null);
                }

                if(hitPoint[i][j] == -1)
                {
                    g2.drawImage(red, (425+32) + i * 32 + 2, (75+32)+ j * 32 + 2, 28, 28, null);
                }
            }
        }

        //draw shipe map
        for(int i = 0; i<10; i++)
        {
            for(int j = 0;j < 10; j++)
            {
                if(Array[i][j] == -1)
                {
                    g2.drawImage(fire, (25+32) + i * 32 + 2, (75+32)+ j * 32 + 2, 28, 28, null);
                }

                if(Array[i][j] == -2)
                {
                    g2.drawImage(blue, (25+32) + i * 32 + 2, (75+32)+ j * 32 + 2, 28, 28, null);
                }
            }
        }
    }

    public void start(boolean start, String login)
    {
        this.removeAll();

        String exp = "<html><center>Za pomocą myszki (LPM i RPM) ustaw swoją flote na mapie.<BR>Daj nam znać kiedy będziesz gotów!</html>";
        explain.setText(exp);

        this.add(explain);
        this.add(clear);
        this.add(ready);

        status = GameStatus.start;
        clear();

        ComputerPoint = 0;
        PlayerPoint = 0;

        selectedPoint.setLocation(-1, -1);

        for(int i = 0; i < 10; i++)
        {
            for(int j  = 0; j < 10; j++)
            {
                hitPoint[i][j] = 0;
            }
        }

        for(int i = 0; i < 6; i++)
        {
            for(int j = 0; j< 3; j++)
            {
                shipPoint[i][j] = -1;
            }
        }

        this.login = login;
        playerFirst = start;
    }

    private boolean playerMove()
    {
        boolean mode = false;
        int x = selectedPoint.x;
        int y = selectedPoint.y;

        if(x != -1 && y != -1) {
            try {
                if (network.sendMissle(x, y)) {
                    hitPoint[x][y] = 1;
                    PlayerPoint++;
                    mode=true;
                } else {
                    hitPoint[x][y] = -1;
                }

                gameStatus();
                repaint();

                selectedPoint.setLocation(-1, -1);

            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        return mode;
    }

    private boolean computerMove()
    {
        boolean mode = false;
        try {
            Single2Int single2Int = network.getMissle();

            int x = single2Int.getX();
            int y = single2Int.getY();

            if(x >= 0 && x <= 9 && y >= 0 && y <= 9)
            {
                if(Array[x][y] == 1)
                {
                    ComputerPoint++;
                    Array[x][y] = -1;
                    mode=true;
                }
                else
                {
                    Array[x][y] = -2;
                }
            }

            gameStatus();
            repaint();
        }
        catch(Exception e)
        {

        }
        return mode;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent)
    {
        Object source = actionEvent.getSource();
        boolean hit = true;

        if(source == clear)
        {
            clear();
        }

        if(source == ready && count == 6)
        {
            ready();
        }

        if(source == lauch  ) {
            if (!selectedPoint.equals(new Point(-1, -1))) {

                hit = playerMove();
                shootText.setText("<html><center>TRAFIONE, sir!<br>Zaznacz miejsce strzału i strzelaj dalej!</html>");
                if (!hit) {
                    shootText.setText("<html><center>Pudło, sir. Ale to jeszcze nie koniec!<br>Strzelaj dalej!</html> ");
                    hit = true;
                    while (hit) {
                        hit = computerMove();
                    }
                }
            } else {
                shootText.setText("<html><center>Nie wiemy gdzie strzelać, sir.<br>Zaznacz miejsce strzału Kapitanie!");
            }
        }
    }

    private void createShip(MouseEvent mouseEvent)
    {
        System.out.println("Raw mouse point: "+ mouseEvent.getPoint());
        Point point = getMousePosition();

        if(point != null)
        {
            if(count < 6) {
                int x = (point.x - 216) / 41;
                int y = (point.y - 66) / 41;

                System.out.println("Converted position: " + x + " $ " + y);

                //button left - ship top
                if (mouseEvent.getButton() == MouseEvent.BUTTON1) {
                    if (x > 0 && x < 9) {
                        if (Array[x - 1][y] != 1 && Array[x][y] != 1 && Array[x + 1][y] != 1) {
                            for (int i = 0; i < 3; i++) {
                                Array[x - 1 + i][y] = 1;
                            }

                            shipPoint[count][0] = x;
                            shipPoint[count][1] = y;
                            shipPoint[count][2] = -1;
                            count++;
                            System.out.println("Update array, actual ship: " +  count);
                        }
                    }
                }

                //button right - ship mid
                if (mouseEvent.getButton() == MouseEvent.BUTTON3) {
                    if (y > 0 && y < 9) {
                        if (Array[x][y - 1] != 1 && Array[x][y] != 1 && Array[x][y + 1] != 1) {
                            for (int i = 0; i < 3; i++) {
                                Array[x][y - 1 + i] = 1;
                            }
                            shipPoint[count][0] = x;
                            shipPoint[count][1] = y;
                            shipPoint[count][2] = 1;
                            count++;
                            System.out.println("Update array, actual ship: " +  count);
                        }
                    }
                }
            }
        }
    }

    private void selectTarget(MouseEvent mouseEvent)
    {
        System.out.println("Raw mouse point: "+ mouseEvent.getPoint());
        Point point = getMousePosition();

        if(point != null)
        {
            int x = (point.x - 425 - 32)/32;
            int y = (point.y - 75 - 32)/32;

            if(x >= 0 && x <= 9 && y >= 0 && y <= 9)
            {
                System.out.println("Converted point: " + x + " $ " + y);
                if(hitPoint[x][y] == 0) {
                    selectedPoint.setLocation(x, y);
                }
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent)
    {

    }

    @Override
    public void mousePressed(MouseEvent mouseEvent)
    {
        if(status == GameStatus.start)
            createShip(mouseEvent);

        if(status == GameStatus.loop)
            selectTarget(mouseEvent);

        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent)
    {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent)
    {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent)
    {

    }

    private void clear()
    {
        for(int i = 0; i < 10; i++)
        {
            for( int j = 0; j < 10; j++)
            {
                Array[i][j] = 0;
            }
        }

        count = 0;

        for(int i = 0; i < 6; i++)
        {
            for(int j = 0; j< 3; j++)
            {
                shipPoint[i][j] = -1;
            }
        }

        this.repaint();
    }

    private void ready()
    {
        this.removeAll();
        this.add(lauch);
        this.add(shootText);

        try {
            network.sendMap(new Array2Int(Array));
            network.generateMap();
        }
        catch(Exception e)
        {
            System.err.println(e.getMessage());
        }

        if(!playerFirst)
        {
            computerMove();
        }

        status = GameStatus.loop;
        this.repaint();
    }

    private void gameStatus()
    {
        if(PlayerPoint == 18) {
            //player win end game
            this.removeAll();
            gameResult.setText("Palyer win game, close window to end game");
            this.add(gameResult);

            try {
                network.gameResult(login, "shipswon");
            }
            catch (Exception e)
            {
                System.err.println(e.getMessage());
            }

            status = GameStatus.end;
            this.repaint();
        }

        if(ComputerPoint == 18) {
            //comouter win
            this.removeAll();
            gameResult.setText("Computer win game, close window to end game");
            this.add(gameResult);

            try
            {
                network.gameResult(login, "shipslost");
            }
            catch (Exception e)
            {
                System.err.println(e.getMessage());
            }

            status = GameStatus.end;
            this.repaint();
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(800, 600);
    }

    public String gameResult()
    {
        if(PlayerPoint == 18 && ComputerPoint == 18)
        {
            return null;
        }
        else if(PlayerPoint == 18)
        {
            return "shipswon";
        }
        else
        {
            return "shipslost";
        }
    }
}
