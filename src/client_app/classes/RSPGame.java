package client_app.classes;

import client_app.Network;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Random;

public class RSPGame extends JPanel implements MouseListener, ActionListener {

    enum GameStatus {
        select,
        ready,
        wait,
        end
    }

    private BufferedImage bg, r, s, p;
    private boolean isPLayerFirrst = false;
    private Network network = new Network();
    private JLabel explain = new JLabel();
    private GameStatus status = GameStatus.select;
    private JButton ok = new JButton();
    private Random random = new Random();
    private int selected = 0;
    private int point = 0;
    private int count = 0;
    private int computer = 0;
    private String login = "";

    public RSPGame()
    {
        this.addMouseListener(this);
        this.setLayout(null);

        explain.setBounds(182, 425, 318, 50);
        explain.setText("Wybierz karte");
        explain.setFont(explain.getFont().deriveFont(18.0f));
        explain.setHorizontalAlignment(SwingConstants.CENTER);
        explain.setVerticalAlignment(SwingConstants.CENTER);
        this.add(explain);

        ok.setBounds(10,425, 170,50);
        ok.setText("Rzuć karte");
        ok.setVisible(true);
        ok.addActionListener(this);
        this.add(ok);

        try {
            bg = ImageIO.read(new File("data\\bgT.png"));
            r = ImageIO.read(new File("data\\r.png"));
            s = ImageIO.read(new File("data\\s.png"));
            p = ImageIO.read(new File("data\\p.png"));
        }
        catch (Exception e)
        {
            System.err.println(e.getMessage());
        }
    }


    public void start(boolean first, String Login) {

        isPLayerFirrst = first;
        selected = 0;
        point = 0;
        count = 0;
        computer = 0;
        this.login = Login;
        ok.setVisible(true);
        status = GameStatus.select;
        explain.setText("Wybierz karte");
        ok.setText("Rzuć karte");
        explain.setBounds(182, 425, 318, 50);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        g2.drawImage(bg, 0, 0, 500, 500, null);

        if(status == GameStatus.wait || status == GameStatus.end)
        {
            if(selected == 1)
                g2.drawImage(r,100, 70, 100, 100, null);
            if(selected == 2)
                g2.drawImage(s,100, 70, 100, 100, null);
            if(selected == 3)
                g2.drawImage(p,100, 70, 100, 100, null);

            if(computer==1)
                g2.drawImage(r,300, 70, 100, 100, null);
            if(computer==2)
                g2.drawImage(s,300, 70, 100, 100, null);
            if(computer==3)
                g2.drawImage(p,300, 70, 100, 100, null);

        }

        if(selected != 0)
        {
            g2.setColor(Color.RED);
            g2.fillRect(45 + 150 * (selected-1), 225, 110, 110);
        }

        g2.drawImage(r, 50, 230, 100, 100, null);
        g2.drawImage(s, 200, 230, 100, 100, null);
        g2.drawImage(p, 350, 230, 100, 100, null);

    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
    }

    public int getCount() {
        return count;
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

        Point mouse = getMousePosition();
        System.out.println("Raw mouse position: " + mouse);


            if(status != GameStatus.wait ) {
                if (mouse != null) {

                    int x = mouse.x;
                    int y = mouse.y;

                    if (y > 230 && y < 330) {
                        if (x > 50 && x < 150) {
                            System.out.println("Wybrany symbol rock");
                            explain.setText("<html><center>Jeśli jesteś pewien,<br>rzuć karte na stół!</html>");
                            selected = 1;
                            status = GameStatus.ready;
                            repaint();
                        }
                        if (x > 200 && x < 300) {
                            System.out.println("Wybrany symbol scisor");
                            explain.setText("<html><center>Jeśli jesteś pewien,<br>rzuć karte na stół!</html>");
                            selected = 2;
                            status = GameStatus.ready;
                            repaint();
                        }
                        if (x > 350 && x < 450) {
                            System.out.println("Wybrany symbol peper");
                            explain.setText("<html><center>Jeśli jesteś pewien,<br>rzuć karte na stół!</html>");
                            selected = 3;
                            status = GameStatus.ready;
                            repaint();
                        }
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

    public String gameResult() {
        if(point > 0)
        {
            return "rpswon";
        }
        else if(point < 0)
        {
            return "rpslost";
        }
        else
        {
            return null;
        }
    }

    private void gameLoop()
    {
        computer = (random.nextInt(3))+1;

        if(computer == selected) {
            //remise
            point = point + 0;
            explain.setText("<html><center>Myślicie podobnie..<br>Ciekawe..</html>");
            ok.setText("Następna runda");
        }
        else if((computer == 1 && selected == 2) || (computer == 2 && selected == 3) || (computer == 3 && selected == 1)) {
            //remise
            point = point + 1;
            explain.setText("<html><center>Ma lepszą karte,<br>przegrywasz :C</html>");
            ok.setText("Następna runda");
        }
        else {
            point = point - 1;
            explain.setText("<html><center>Nieźle!<br>To Ty jesteś górą tym razem</html>");
            ok.setText("Następna runda");
        }

        count++;
        if(count == 3) {

            ok.setVisible(false);

            try {

                if (point == 0) {
                    explain.setBounds(0, 425, 500, 50);
                    explain.setText("<html><center>Mamy REMIS ! Dobra gra.<br>Zamknij okno i postaraj się bardziej!</html>");
                    network.gameResult(login, null);
                }
                if (point > 0) {
                    explain.setBounds(0, 425, 500, 50);
                    explain.setText("<html><center>Nie miał żadnych szans! WYGRYWASZ!<br>Zamknij okno i może kolejna rundka?</html>");
                    network.gameResult(login, "rpswon");
                }
                if (point < 0) {
                    explain.setBounds(0, 425, 500, 70);
                    explain.setText("<html><center>Coś tu jest nie tak.. on musi oszukiwać! Przegrywasz<br>Zamknij okno i spróbuj ponownie!</html>");
                    network.gameResult(login, "rpslost");
                }
            }
            catch (Exception e)
            {
                System.err.println(e.getMessage());
            }

            status = GameStatus.end;
        }
        else {
            status = GameStatus.wait;
        }

        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

        Object source = actionEvent.getSource();

        if(source == ok) {
            if(status == GameStatus.ready) {
                gameLoop();
            }
            else if(status == GameStatus.wait) {
                selected = 0;
                computer = 0;
                status = GameStatus.select;
                explain.setText("<html><center>Wybierz karte</html>");
                ok.setText("Rzuć karte");
                repaint();
            } else {
                explain.setText("<html><center>No dalej,<br>Najpierw wybierz karte!</html>");
            }
        }
    }
}
