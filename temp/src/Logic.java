import javax.imageio.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.io.*;

class Logic {
    public Image rubashka;
    private Stopka[] stopki;
    private boolean pervVidacha;
    public boolean endGame;

    private int nomStopki;
    private int nomKarti;
    private int dx,dy;
    public int oldX,oldY;
    private Timer tmEndGame;

    public Logic() {
        try{
            rubashka = ImageIO.read(new File("C:\\Users\\Kmkt_intel\\Desktop\\solitaire\\temp\\img\\dist\\k0.png"));
        }catch(
        Exception ex)
        {
            System.out.println("Not Upload");
        }stopki=new Stopka[13];for(
        int i = 0;i<13;i++)
        {
            stopki[i] = new Stopka();
        }
        tmEndGame = new Timer (100, new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                for (int i = 2; i <=5; i++) {
                    Card getCard = stopki[i].get(0);
                    stopki[i].add(getCard);
                    stopki[i].remove(0);
                }
            }
        });
        start();
    }
    public void mouseDragged(int mX,int mY)
    {
      if (nomStopki>=0)
      {
        Card getKarta = stopki[nomStopki].get(nomKarti);
        getKarta.x = mX-dx;
        getKarta.y = mY-dy;
        if (getKarta.x<0) getKarta.x = 0;
        if (getKarta.x>720) getKarta.x = 720;
        if (getKarta.y<0) getKarta.y = 0;
        if (getKarta.y>650) getKarta.y = 650;
        int y = 20;
        for (int i = nomKarti+1;i<stopki[nomStopki].size();i++)
        {
          stopki[nomStopki].get(i).x = getKarta.x;
          stopki[nomStopki].get(i).y = getKarta.y+y;
          y+=20;
        }
      }
    }

    public void mousePressed(int mX, int mY) {
        int nom = getNomKolodaPress(mX,mY);
        setVibrana(nom,mX,mY);
    }
    
    private boolean testPerenos(int nom1, int nom2) {
        boolean rez = false;
        Card getKarta1 = stopki[nom1].get(nomKarti);
        Card getKarta2 = null;
        if (stopki[nom2].size() > 0) {
            getKarta2 = stopki[nom2].get(stopki[nom2].size() - 1);
        }
        if ((nom2 >= 2) && (nom2 <= 5)) {
            if (nomKarti == (stopki[nom1].size() - 1)) {
                if (getKarta2 == null) {
                    if (getKarta1.tipKarta == 12)
                        rez = true;
                } else if ((getKarta2.tipKarta == 12) && (getKarta1.mast == getKarta2.mast)
                        && (getKarta1.tipKarta == 0)) {
                    rez = true;
                } else if ((getKarta2.tipKarta >= 0) && (getKarta2.tipKarta < 11)
                        && (getKarta1.mast == getKarta2.mast)) {
                    if ((getKarta2.tipKarta + 1 == getKarta1.tipKarta)) {
                        rez = true;
                    }
                }
                if (rez == true) {
                    getKarta1.x = (110 * (nom2 + 1)) + 30;
                    getKarta1.y = 15;
                    stopki[nom2].add(getKarta1);
                    stopki[nom1].remove(nomKarti);
                    testEndGame();
                }
            }
        }
        if ((nom2 >= 6) && (nom2 <= 12)) {
            int x = 30 + (nom2 - 6) * 110;
            int y = 130;
            if (getKarta2 == null) {
                if (getKarta1.tipKarta == 11)
                    rez = true;
            } else {
                if (getKarta2.tipRubashka == false) {
                    if (getKarta2.tipKarta != 12) {
                        if ((getKarta2.tipKarta == getKarta1.tipKarta + 1)
                                || ((getKarta2.tipKarta == 0) && (getKarta1.tipKarta == 12))) {
                            if (getKarta2.red_karta != getKarta1.red_karta) {
                                y = getKarta2.y + 20;
                                rez = true;
                            }
                        }
                    }
                }
            }
            if (rez == true) {
                for (int i = nomKarti; i < stopki[nom1].size(); i++) {
                    Card getKarta_ = stopki[nom1].get(i);
                    getKarta_.x = x;
                    getKarta_.y = y;
                    stopki[nom2].add(getKarta_);
                    y += 20;
                }
                for (int i = stopki[nom1].size() - 1; i >= nomKarti; i--) {
                    stopki[nom1].remove(i);
                }
            }
        }
        return rez;
    }


    public void mouseDoublePressed(int mX,int mY)
  {
    int nom = getNomKolodaPress(mX,mY);
    if ((nom==1) || ((nom>=6) && (nom<=12)))
    {
      if(stopki[nom].size()>0)
      {
        int nomPosled=stopki[nom].size()-1;
        Card getKarta = stopki[nom].get(nomPosled);
        if ((mY>=getKarta.y) && (mY<=(getKarta.y+97)))
        {
          for (int i=2;i<=5;i++)
          {
            int rez = -1;
            if (stopki[i].size()==0)
            {
              if (getKarta.tipKarta==12)
              {
                rez = i;
              }
            }else {
                int nomPosled2 = stopki[i].size()-1;
                Card getKarta2 = stopki[i].get(nomPosled2);
                if ((getKarta2.tipKarta==12) &&
                  (getKarta.mast == getKarta2.mast) &&
                  (getKarta.tipKarta==0)) {
                  rez = i;
                }
                else if ((getKarta2.tipKarta>=0)&&
                  (getKarta2.tipKarta<11) &&
                  (getKarta.mast == getKarta2.mast)) {
                  if ((getKarta2.tipKarta+1==getKarta.tipKarta)) {
                    rez=i;
                  }
                }
              }
              if (rez>=0) {
                getKarta.x = (110*(rez+1))+30;
                getKarta.y=15;
                stopki[rez].add(getKarta);
                stopki[nom].remove(nomPosled);
                testEndGame();
                break;
              }
            }
          }
        }
      }
      openKarta();
    }
    
    public void mouseReleased(int mX, int mY) {
        int nom = getNomKolodaPress(mX, mY);
        if (nom==0) {
            vidacha();
        }
    }
    public int getNomKolodaPress(int mX, int mY) {
        int nom = 1;
        if ((mY>=15)&&(mY<=(15+97))) {
            if ((mX>=30)&&(mX<=(30+97))) nom = 0;
            if ((mX>=140)&&(mX<=(140+97)))nom = 1;
            if ((mX>=360)&&(mX<=(360+97)))nom = 2;
            if ((mX>=470)&&(mX<=(470+97)))nom = 3;
            if ((mX>=580)&&(mX<=(580+97)))nom = 4;
            if ((mX>=690)&&(mX<=(690+97)))nom = 5;
        } else if ((mY>=130)&&(mY<=(700))) {
            if ((mX>=30)&&(mX<=110*7)) {
                if(((mX-30)%110)<=72) {
                    nom=(mX-30)/110;
                    nom+=6;
                }
            }
        }
        return nom;
    }
    private void vidacha() { 
        if(stopki[0].size()>0)
        {
          int nom;
          if (pervVidacha==true)
          {
            nom = (int)(Math.random()*stopki[0].size());
          }
          else {
            nom = stopki[0].size()-1;
          }
          Card getKarta = stopki[0].get(nom);
          getKarta.tipRubashka = false;
          getKarta.x+=110;
          stopki[1].add(getKarta);
          stopki[0].remove(nom);
        }
        else {
          int nomPosled = stopki[1].size()-1;
          for (int i = nomPosled;i>=0;i--)
          {
            Card getKarta = stopki[1].get(i);
            getKarta.tipRubashka = true;
            getKarta.x-=110;
            stopki[0].add(getKarta);
          }
          stopki[1].clear();
          pervVidacha = false;
        }   
      }
    public void     start() {
        for (int i = 0; i < 13; i++) {
            stopki[i].clear();
        }
        load();
        razdacha();
        endGame = false;
        pervVidacha = true;
        nomKarti = -1;
        nomStopki = -1;
    }
    public void load() {
        for (int i = 1; i < 52; i++) {
            stopki[0].add(new Card("C:\\Users\\Kmkt_intel\\Desktop\\solitaire\\temp\\img\\dist\\k"+i+".png",rubashka,i));
        }
    }
    public void drawKoloda(Graphics gr) {
        if (stopki[0].size() > 0) {
            stopki[0].get(stopki[0].size() - 1).draw(gr);
        }
        for (int i = 2; i <= 5; i++) {
            if (stopki[i].size() > 1) {
                stopki[i].get(stopki[i].size() - 2).draw(gr);
                stopki[i].get(stopki[i].size() - 1).draw(gr);
            } else if (stopki[i].size() == 1) {
                stopki[i].get(stopki[i].size() - 1).draw(gr);
            }
        }
        for (int i = 6;i<13;i++ ) {
            if (stopki[i].size()>0) {
              for (int j =0;j<stopki[i].size();j++) {
                if (stopki[i].get(j).vibrana==true) break;
                stopki[i].get(j).draw(gr);
              }
            }
          }
        if (stopki[1].size()>1) {
            stopki[1].get(stopki[1].size()-2).draw(gr);
            stopki[1].get(stopki[1].size()-1).draw(gr);
        } else if (stopki[1].size()==1) {
            stopki[1].get(stopki[1].size()-1).draw(gr);
        }

        if (nomStopki!=-1) {
            for (int i = nomKarti;i<stopki[nomStopki].size();i++)
            {
              stopki[nomStopki].get(i).draw(gr);
            }
          }
    }
    
    private void razdacha()
    {
      int x = 30;
      for (int i=6;i<13;i++ ) {
        for (int j=6;j<=i ;j++ ) {
          int rnd = (int)(Math.random()*stopki[0].size());
          Card getKarta = stopki[0].get(rnd);
          if (j<i) getKarta.tipRubashka = true;
          else getKarta.tipRubashka = false;
          getKarta.x=x;
          getKarta.y = 130+stopki[i].size()*20;
          stopki[i].add(getKarta);
          stopki[0].remove(rnd);
        }
        x+=110;
      }
    }

    private void testEndGame()
  { 
    if ((stopki[2].size()==13)&&
        (stopki[3].size()==13)&&
            (stopki[4].size() == 13) && (stopki[5].size() == 13)) {
        endGame = true;
        tmEndGame.start();
    }
}

private void openKarta() {
    for (int i = 6; i <= 12; i++) {
        if (stopki[i].size() > 0) {
            int nomPosled = stopki[i].size() - 1;
            Card getKarta = stopki[i].get(nomPosled);
            if (getKarta.tipRubashka == true)
                getKarta.tipRubashka = false;
        }
    }
}

private void setVibrana(int nom, int mX,int mY) {
    if ((nom>=1) && (nom<=5))
    {
      if (stopki[nom].size()>0)
      {
        int nomPosled= stopki[nom].size()-1;
        Card getKarta = stopki[nom].get(nomPosled);
        getKarta.vibrana = true;
        nomKarti = nomPosled;
        nomStopki = nom;
        dx = mX-getKarta.x;
        dy = mY-getKarta.y;
        oldX = getKarta.x;
        oldY = getKarta.y;
      }
    }
    else if ((nom>=6) && (nom<=12)) {
        if (stopki[nom].size()>0) {
          int nomPosled = stopki[nom].size()-1;
          Card getKarta = stopki[nom].get(nomPosled);
          int nomVibrana = -1;
          if ((mY>=getKarta.y) && (mY<=(getKarta.y+97))) {
            nomVibrana = nomPosled;
          }
          else if (mY < getKarta.y)  {
            nomVibrana = (mY-130)/20;
            if (stopki[nom].get(nomVibrana).tipRubashka==true) {
              nomVibrana = -1;
            }
          }
          if (nomVibrana!=-1) {
            Card getKartaVibrana = stopki[nom].get(nomVibrana);
            if (getKartaVibrana.tipRubashka == false) {
                getKartaVibrana.vibrana = true;
                nomKarti = nomVibrana;
                nomStopki = nom;
                dx = mX-getKartaVibrana.x;
                dy = mY-getKartaVibrana.y;
                oldX = getKartaVibrana.x;
                oldY = getKartaVibrana.y;
              }
          }
        }
      }   
    }
}
