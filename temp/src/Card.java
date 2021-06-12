
import javax.imageio.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

 class Card {
    public int x, y;
    public BufferedImage img;
    public boolean tipRubashka;
    public Image rubashka;
    public int mast;
    public int tipKarta;
    public boolean vibrana;
    public boolean red_karta;

    public Card(String path, Image rubashka, int nom) {
        vibrana = false;
        this.rubashka = rubashka;
        try {
            img = ImageIO.read(new File("C:\\Users\\Kmkt_intel\\Desktop\\solitaire\\temp\\img\\dist\\cardback.png"));
        } catch (IOException ex) {
        }
        x = 30;
        y = 15;
        tipRubashka = true;
        mast = (nom - 1) % 4;
        tipKarta = (nom - 1) / 4;
        red_karta = true;
        if (mast <= 1)
            red_karta = false;
    }

    public void draw(Graphics gr)
  {
    if (tipRubashka==false) {
      gr.drawImage(img,x,y,72,97,null); 
    } else {
      gr.drawImage(rubashka,x,y,72,97,null); 
    }
    if (vibrana==true) {
      gr.setColor(Color.YELLOW);
      gr.drawRect(x,y,72,97);
    }
  }
}