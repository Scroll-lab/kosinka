import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class Panel extends JPanel {
    private Timer tmDraw;
    private JButton btn1, btn2;
    private Image background;
    private Logic newGame;
    
    public class myMouse1 implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
       
        }

        @Override
        public void mousePressed(MouseEvent e) {
            if (newGame.endGame == false) {
                int mX = e.getX();
                int mY = e.getY();
                if ((e.getButton() == 1) && (e.getClickCount() == 2)) {
                    newGame.mousePressed(mX, mY);
                } else if ((e.getButton() == 1) && (e.getClickCount() == 2)) {
                    newGame.mouseDoublePressed(mX, mY);
                }
            }

        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if (newGame.endGame == false) {
                int mX = e.getX();
                int mY = e.getY();
                if (e.getButton() == 1) {
                    newGame.mouseReleased(mX, mY);
                }
            }

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }

    }

    public class myMouse2 implements MouseMotionListener {

        @Override
        public void mouseDragged(MouseEvent e) {
            if (newGame.endGame == false) {
                int mX = e.getX();
                int mY = e.getY();
                newGame.mouseDragged(mX, mY);
            }

        }

        @Override
        public void mouseMoved(MouseEvent e) {

        }
    }

    public Panel() {
        
        addMouseListener(new myMouse1());
        addMouseMotionListener(new myMouse2());
        newGame = new Logic();
        try {
            background = ImageIO.read(new File("C:\\Users\\Kmkt_intel\\Desktop\\solitaire\\temp\\img\\dist\\background.png"));
        } catch (Exception ex) {
            System.out.println("NOT UPLOAD");
        }
        setLayout(null);
        btn1 = new JButton();
        btn1.setText("New Game");
        btn1.setForeground(Color.BLUE);
        btn1.setFont(new Font("serif", 0, 20));
        btn1.setBounds(820, 50, 150, 50);
        btn1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                newGame.start();
            }
        });
        add(btn1);

        btn2 = new JButton();
        btn2.setText("Exit");
        btn2.setForeground(Color.BLUE);
        btn2.setFont(new Font("serif", 0, 20));
        btn2.setBounds(820, 150, 150, 50);
        btn2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                System.exit(0);
            }
        });
        add(btn2);

        tmDraw = new Timer(20, new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                repaint();
            }
        });
        tmDraw.start();

        
    }

    public void paintComponent(Graphics gr)
    {
      super.paintComponent(gr);
      gr.drawImage(background,0,0,1000,700,null);
      gr.setColor(Color.WHITE);
      for (int i=0;i<7;i++)
      {
        if(i!=2) gr.drawRect(30+i*110,15,72,97);
      }
      for (int i=0;i<7;i++)
      {
        gr.drawRect(30+i*110,130,72,97);
      }
      newGame.drawKoloda(gr);
    }
}