package client_app.classes;

import client_app.Network;
import client_app.classes.dtoGames.Single3Int;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;

public class TTTGame extends JPanel implements MouseListener {

    enum GameStatus
    {
        start,
        loop,
        end
    }

    private BufferedImage bg, board, x, o;
    private boolean isPLayerFirrst = false;
    private Network network = new Network();
    private int[][] Array = new int[3][3];
    private JLabel explain = new JLabel();
    private GameStatus status = GameStatus.start;
    private int selectedField = 0;
    private int leftField = 0;
    private String login = "";

    public TTTGame()
    {
        this.addMouseListener(this);
        this.setLayout(null);

        explain.setBounds(0, 425, 500, 50);
        explain.setText("Wybierz strone!");
        explain.setFont(explain.getFont().deriveFont(18.0f));
        explain.setHorizontalAlignment(SwingConstants.CENTER);
        explain.setVerticalAlignment(SwingConstants.CENTER);
        this.add(explain);

        try
        {
            bg = ImageIO.read(new File("data\\bgT.png"));
            board = ImageIO.read(new File("data\\board.png"));
            x = ImageIO.read(new File("data\\x.png"));
            o = ImageIO.read(new File("data\\o.png"));
        }
        catch (Exception e)
        {
            System.err.println(e.getMessage());
        }
    }

    public void start(boolean first, String login) {

        try {
            network.reset();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        for(int i = 0; i<3; i++)
        {
            for( int j = 0; j < 3; j++)
            {
                Array[i][j] = 0;
            }
        }

        isPLayerFirrst = first;
        selectedField = 0;
        leftField = 0;
        this.login = login;
        status = GameStatus.start;
        explain.setText("Wybierz strone!");
    }

    private void drawSelect(Graphics2D g2)
    {
        g2.drawRect(150, 150, 100, 100);
        g2.drawImage(o, 150 + 5, 150+5, 90, 90, null);
        g2.drawRect(260, 150, 100, 100);
        g2.drawImage(x, 260 + 5, 150+5, 90, 90, null);
    }

    private void drawBoard(Graphics2D g2)
    {
        g2.drawImage(board, 100, 50, 300, 300, null);

        for(int i = 0; i < 3; i++)
        {
            for( int j = 0; j < 3; j++)
            {
                if(Array[i][j] == -1)
                {
                    g2.drawImage(x, 100 + 5 + 100 * i, 50 + 5 + 100 * j, 90, 90, null);
                }

                if(Array[i][j] == 1)
                {
                    g2.drawImage(o, 100 + 5 + 100 * i, 50 + 5 + 100* j, 90, 90, null);
                }
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        g2.drawImage(bg, 0, 0, 500, 500, null);

        if(status == GameStatus.start)
            drawSelect(g2);
        else
            drawBoard(g2);

    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
    }

    private void mouseLoop(Point mouse)
    {
        int x = (mouse.x - 100) / 100;
        int y = (mouse.y - 50) / 100;
        if(x >= 0 && x < 3 && y >= 0 && y <= 3)
        {
            System.out.println("Converted mouse position: " + x + " $ " + y);
            if(Array[x][y] == 0)
            {
                playerMove(x, y);
                computerMove();
            }
        }
    }

    private void mouseSelect(Point mouse)
    {
        int x = mouse.x;
        int y = mouse.y;

        if(x > 150 && x < 240 && y > 150 && y < 240)
        {
            System.out.println("Selected x");
            selectedField = 1;
            leftField = -1;
            this.removeAll();
            explain.setText("Zaznacz ZWYCIĘSKIE POLE!");
            this.add(explain);
            if(!isPLayerFirrst) {
                computerMove();
            }
            status = GameStatus.loop;
            repaint();
        }

        if(x > 260 && x < 350 && y > 150 && y < 240)
        {
            System.out.println("Selected y");
            selectedField = -1;
            leftField = 1;
            this.removeAll();
            explain.setText("Zaznacz ZWYCIĘSKIE POLE!");
            this.add(explain);
            if(!isPLayerFirrst) {
                computerMove();
            }
            status = GameStatus.loop;
            repaint();
        }
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

        Point mouse = getMousePosition();
        System.out.println("Raw mouse position: " + mouse);

        if(status != GameStatus.end) {
            if (mouse != null) {
                if (status == GameStatus.start) {
                    mouseSelect(mouse);
                }
                else if (status == GameStatus.loop) {
                    mouseLoop(mouse);
                }
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {
    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(500, 500);
    }

    private void playerMove(int x, int y)
    {
        if(status == GameStatus.end)
        {
            return;
        }
        try {

            if (isFull())
            {
                try{
                    network.gameResult(login, null);
                }
                catch (Exception e) {
                    System.err.println(e.getMessage());
                }
                System.out.println("Koniec gry remis");
                explain.setText("<html><center>Niesamowite. Mamy REMIS!<br>Zamknij okno i spróbuj ponownie</html>");
                status = GameStatus.end;
                return;
            }

            Single3Int single3Int = new Single3Int(x, y, selectedField);
            network.user(single3Int);
            Array = network.result().getArray();

            if(check(selectedField))
            {

                try{
                    network.gameResult(login, "tttwon");
                }
                catch (Exception e) {
                    System.err.println(e.getMessage());
                }

                System.out.println("Koniec gry wygrał gracz");
                explain.setText("<html><center>Wiesz kim jesteś? ZWYCIEZCĄ! Nieźle.<br>Zamknij okno i graj dalej!</html>");
                status = GameStatus.end;
            }

        }
        catch (Exception e)
        {
            System.err.println(e.getMessage());
        }

        repaint();
    }

    private void computerMove()
    {
        if(status == GameStatus.end)
        {
            return;
        }

        if (isFull())
        {
            try{
                network.gameResult(login, null);
            }
            catch (Exception e) {
                System.err.println(e.getMessage());
            }


            System.out.println("Koniec gry remis");
            explain.setText("<html><center>Niesamowite. Mamy REMIS!<br>Zamknij okno i spróbuj ponownie</html>");
            status = GameStatus.end;
            return;
        }

        try
        {
            network.bot(leftField);
            Array = network.result().getArray();
        }
        catch (Exception e)
        {
            System.err.println(e.getMessage());
        }

        if(check(leftField))
        {

            try{
                network.gameResult(login, "tttlost");
            }
            catch (Exception e) {
                System.err.println(e.getMessage());
            }

            System.out.println("Koniec gry wygrał bot");
            explain.setText("<html><center>Coś poszło nie tak :/ Przegrywasz.<br>Zamknij okno i próbuj dalej!</html>");
            status = GameStatus.end;
        }

        repaint();
    }

    public boolean check(int val)
    {
        for(int i = 0; i<3; i++)
        {
            if(Array[i][0] == val && Array[i][1] == val && Array[i][2] == val)
            {
                return true;
            }

            if(Array[0][i] == val && Array[1][i] == val && Array[2][i] == val)
            {
                return true;
            }
        }

        if(Array[0][0] == val && Array[1][1] == val && Array[2][2] == val)
        {
            return true;
        }

        if(Array[2][0] == val && Array[1][1] == val && Array[0][2] == val)
        {
            return true;
        }

        return false;
    }

    public boolean isFull()
    {
        int count = 0;

        for(int i = 0; i <3; i++)
        {
            for(int j = 0; j <3; j++)
            {
                if(Array[i][j] == 0)
                {
                    count++;
                }
            }
        }

        if(count == 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public String gameResult()
    {
        if(isFull())
        {
            return null;
        }
        else if(check(selectedField))
        {
            return "tttwon";
        }
        else
        {
            return "tttlost";
        }
    }

    public boolean isEndGame()
    {
        if(status == GameStatus.end) {
            return true;
        }
        else {
            return false;
        }
    }

}
