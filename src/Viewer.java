import gameObjects.BulletObject;
import gameObjects.PickupObject;
import gameObjects.PlayerObject;
import gameObjects.enemy.EnemyObject;
import settings.GlobalConst;
import util.GameResource;

import java.awt.*;
import java.awt.geom.AffineTransform;

import javax.swing.JPanel;


/*
 * Created by Abraham Campbell on 15/01/2020.
 *   Copyright (c) 2020  Abraham Campbell

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
   
   (MIT LICENSE ) e.g do what you want with this :-) 
 */
public class Viewer extends JPanel {
    private int CurrentAnimationTime = 0;
    private Model gameWorld;

    public Viewer(Model world) {
        this.gameWorld = world;
    }

    public Viewer(LayoutManager layout) {
        super(layout);
    }

    public Viewer(boolean isDoubleBuffered) {
        super(isDoubleBuffered);
    }

    public Viewer(LayoutManager layout, boolean isDoubleBuffered) {
        super(layout, isDoubleBuffered);
    }

    public void updateview() {
        this.repaint();

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d= (Graphics2D) g;
        CurrentAnimationTime++; // runs animation time step
        //Draw player Game Object
        int x = (int) gameWorld.getPlayer().getCentre().getX();
        int y = (int) gameWorld.getPlayer().getCentre().getY();
        int width = (int) gameWorld.getPlayer().getWidth();
        int height = (int) gameWorld.getPlayer().getHeight();
        Image texture = gameWorld.getPlayer().getTexture();
        //Draw background
        drawBackground(g2d);
        // Laser part
        drawLaser(x, y, g2d);
        gameWorld.pickupList.forEach(p -> drawPickups((int)p.getCentre().getX(), (int)p.getCentre().getY(), p, g2d));
        //Draw Bullets change back
        gameWorld.getBullets().forEach((temp) -> {
            drawBullet((int) temp.getCentre().getX(), (int) temp.getCentre().getY(), temp, g2d);
        });
        //Draw Enemies
        gameWorld.getEnemies().forEach((enemy) -> drawEnemies((int) enemy.getCentre().getX(), (int) enemy.getCentre().getY(), enemy, g2d));
        //Draw player
        drawPlayer(x, y, width, height, texture, g2d);
        //Draw Enemies' Bullets
        gameWorld.eBulletList.forEach((eb) -> drawEBullets((int) eb.getCentre().getX(), (int) eb.getCentre().getY(), eb, g2d));
        // Score UI
        drawScore(g2d);
    }

    private void drawEnemies(int x, int y, EnemyObject enemy, Graphics2D g) {
        //The spirte is 32x32 pixel wide and 4 of them are placed together so we need to grab a different one each time
        //remember your training :-) computer science everything starts at 0 so 32 pixels gets us to 31
        int currentPositionInAnimation = CurrentAnimationTime % 40 / 10 * 32; //slows down animation so every 10 frames we get another frame so every 100ms
        AffineTransform backup = g.getTransform();
        if (enemy.isRotating()) {
            AffineTransform tx = AffineTransform.getRotateInstance(Math.toRadians(CurrentAnimationTime % 360), x, y);
            g.transform(tx);
        }
        g.drawImage(enemy.getTexture(),x - enemy.getWidth() / 2, y - enemy.getHeight() / 2, x + enemy.getWidth() / 2, y + enemy.getHeight() / 2,
                0, 0, enemy.resource.drawWidth , enemy.resource.drawHeight, null);
        g.setTransform(backup);
    }
    private void drawPickups(int x, int y, PickupObject p, Graphics2D g2d) {
        g2d.drawImage(p.getTexture(),x - p.getWidth() / 2, y - p.getHeight() / 2, x + p.getWidth() / 2, y + p.getHeight() / 2,
                0, 0, p.resource.drawWidth , p.resource.drawHeight, null);
    }
    private void drawEBullets(int x, int y, BulletObject eb, Graphics2D g) {
        g.drawImage(eb.getTexture(),x - eb.getWidth() / 2, y - eb.getHeight() / 2, x + eb.getWidth() / 2, y + eb.getHeight() / 2,
                0, 0, eb.resource.drawWidth , eb.resource.drawHeight, null);
    }


    private void drawBackground(Graphics g) {
        int currentPositionInAnimation = CurrentAnimationTime % 1919;
        GameResource bgEffect = gameWorld.bgEffectResource;
        int current2 = CurrentAnimationTime * 20 % bgEffect.drawHeight / 2;
        int current3 = CurrentAnimationTime * 50 % 1000;
        g.drawImage(gameWorld.backgroundResource.imageTexture, 0, 0, GlobalConst.LAYOUT_WIDTH, GlobalConst.LAYOUT_HEIGHT,
                0, 1919 - currentPositionInAnimation, 1080, 3838 - currentPositionInAnimation, null);
        g.drawImage(bgEffect.imageTexture, 0, 0, GlobalConst.LAYOUT_WIDTH, GlobalConst.LAYOUT_HEIGHT,
                0, bgEffect.drawHeight / 2 - current2, bgEffect.drawWidth, bgEffect.drawHeight - current2, null);
//        g.drawImage(gameWorld.bgEffectFResource.imageTexture, 0, 0, 1000, 1000, 0, 1000-current3, 1000, 2000-current3, null);
    }

    private void drawBullet(int x, int y, BulletObject bullet, Graphics g) {
        try {
            //64 by 128 The center is on the middle of top of bullet
            int textureStart = bullet.resource.textureStart;
            g.drawImage(bullet.resource.imageTexture,x-bullet.getWidth()/2 , y,x+bullet.getWidth()/2, y + bullet.getHeight(),
                    textureStart, 0, textureStart + bullet.getWidth(), bullet.resource.drawHeight, null);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void drawLaser(int x, int y, Graphics g) {
        if (gameWorld.laser.isOn) {
            GameResource resource = gameWorld.laser.resource;
            int current = CurrentAnimationTime % 12 * resource.drawWidth;
            float dy1 = y - gameWorld.laser.getHeight();
            // Draw top left from 50
            float reduceHeight = 50;
            if (gameWorld.laser.getContactObject() != null) {
                dy1 = gameWorld.laser.getContactObject().getHeight() / 2f + gameWorld.laser.getContactObject().getCentre().getY();
//                reduceHeight = (y - gameWorld.laser.getHeight() - dy1) / gameWorld.laser.getHeight() * resource.drawHeight;
            }
            g.drawImage(gameWorld.laserResource.imageTexture,
                    x - gameWorld.laser.getWidth() / 2, (int)dy1, x + gameWorld.laser.getWidth() / 2, y,
                    current, (int)reduceHeight, resource.drawWidth + current, resource.drawHeight, null);
        }
    }

    private void drawPlayer(int x, int y, int width, int height, Image texture, Graphics g) {
        try {
            //The spirte is 32x32 pixel wide and 4 of them are placed together so we need to grab a different one each time
            //remember your training :-) computer science everything starts at 0 so 32 pixels gets us to 31
            int currentPositionInAnimation = CurrentAnimationTime % 6 * 125; //slows down animation so every 10 frames we get another frame so every 100ms
            g.drawImage(texture, x - width / 2, y - height / 2, x + width / 2, y + height / 2,
                    currentPositionInAnimation, 0, currentPositionInAnimation + 124, 160, null);
            // Draw shield texture
            if (gameWorld.getPlayer().isShield()) {
                width = width + PlayerObject.S_EXTRA_WIDTH;
                height = height + PlayerObject.S_EXTRA_WIDTH;
                g.drawImage(gameWorld.shieldResource.imageTexture, x - width / 2, y - height / 2, x + width / 2, y + height / 2,
                        0, 0, gameWorld.shieldResource.drawWidth, gameWorld.shieldResource.drawHeight, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //g.drawImage(img, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, observer));
        //Lighnting Png from https://opengameart.org/content/animated-spaceships  its 32x32 thats why I know to increament by 32 each time
        // Bullets from https://opengameart.org/forumtopic/tatermands-art
        // background image from https://www.needpix.com/photo/download/677346/space-stars-nebula-background-galaxy-universe-free-pictures-free-photos-free-images

    }
    private void drawScore(Graphics2D g2d){
//        g2d.drawImage(gameWorld.numbers[0].imageTexture, 0, 0, null);
        int offset = 10;
        int interval = 4;
        int width = 20;
        int height = 30;
        for (int i = 0; i < gameWorld.numbers.length; i++) {
            Image num = gameWorld.numbers[i].imageTexture;
            g2d.drawImage(num, offset + (interval + width) * i, offset, offset + (interval + width) * i + width, offset + height,
                    gameWorld.numbers[i].textureStart, 0,
                    gameWorld.numbers[i].textureStart + gameWorld.numbers[i].drawWidth, gameWorld.numbers[i].drawHeight, null);
        }
    }
}


/*
 * 
 * 
 *              VIEWER HMD into the world                                                             
                                                                                
                                      .                                         
                                         .                                      
                                             .  ..                              
                               .........~++++.. .  .                            
                 .   . ....,++??+++?+??+++?++?7ZZ7..   .                        
         .   . . .+?+???++++???D7I????Z8Z8N8MD7I?=+O$..                         
      .. ........ZOZZ$7ZZNZZDNODDOMMMMND8$$77I??I?+?+=O .     .                 
      .. ...7$OZZ?788DDNDDDDD8ZZ7$$$7I7III7??I?????+++=+~.                      
       ...8OZII?III7II77777I$I7II???7I??+?I?I?+?+IDNN8??++=...                  
     ....OOIIIII????II?I??II?I????I?????=?+Z88O77ZZO8888OO?++,......            
      ..OZI7III??II??I??I?7ODM8NN8O8OZO8DDDDDDDDD8DDDDDDDDNNNOZ= ......   ..    
     ..OZI?II7I?????+????+IIO8O8DDDDD8DNMMNNNNNDDNNDDDNDDNNNNNNDD$,.........    
      ,ZII77II?III??????DO8DDD8DNNNNNDDMDDDDDNNDDDNNNDNNNNDNNNNDDNDD+.......   .
      7Z??II7??II??I??IOMDDNMNNNNNDDDDDMDDDDNDDNNNNNDNNNNDNNDMNNNNNDDD,......   
 .  ..IZ??IIIII777?I?8NNNNNNNNNDDDDDDDDNDDDDDNNMMMDNDMMNNDNNDMNNNNNNDDDD.....   
      .$???I7IIIIIIINNNNNNNNNNNDDNDDDDDD8DDDDNM888888888DNNNNNNDNNNNNNDDO.....  
       $+??IIII?II?NNNNNMMMMMDN8DNNNDDDDZDDNN?D88I==INNDDDNNDNMNNMNNNNND8:..... 
   ....$+??III??I+NNNNNMMM88D88D88888DDDZDDMND88==+=NNNNMDDNNNNNNMMNNNNND8......
.......8=+????III8NNNNMMMDD8I=~+ONN8D8NDODNMN8DNDNNNNNNNM8DNNNNNNMNNNNDDD8..... 
. ......O=??IIIIIMNNNMMMDDD?+=?ONNNN888NMDDM88MNNNNNNNNNMDDNNNMNNNMMNDNND8......
........,+++???IINNNNNMMDDMDNMNDNMNNM8ONMDDM88NNNNNN+==ND8NNNDMNMNNNNNDDD8......
......,,,:++??I?ONNNNNMDDDMNNNNNNNNMM88NMDDNN88MNDN==~MD8DNNNNNMNMNNNDND8O......
....,,,,:::+??IIONNNNNNNDDMNNNNNO+?MN88DN8DDD888DNMMM888DNDNNNNMMMNNDDDD8,.... .
...,,,,::::~+?+?NNNNNNNMD8DNNN++++MNO8D88NNMODD8O88888DDDDDDNNMMMNNNDDD8........
..,,,,:::~~~=+??MNNNNNNNND88MNMMMD888NNNNNNNMODDDDDDDDND8DDDNNNNNNDDD8,.........
..,,,,:::~~~=++?NMNNNNNNND8888888O8DNNNNNNMMMNDDDDDDNMMNDDDOO+~~::,,,.......... 
..,,,:::~~~~==+?NNNDDNDNDDNDDDDDDDDNNND88OOZZ$8DDMNDZNZDZ7I?++~::,,,............
..,,,::::~~~~==7DDNNDDD8DDDDDDDD8DD888OOOZZ$$$7777OOZZZ$7I?++=~~:,,,.........   
..,,,,::::~~~~=+8NNNNNDDDMMMNNNNNDOOOOZZZ$$$77777777777II?++==~::,,,......  . ..
...,,,,::::~~~~=I8DNNN8DDNZOM$ZDOOZZZZ$$$7777IIIIIIIII???++==~~::,,........  .  
....,,,,:::::~~~~+=++?I$$ZZOZZZZZ$$$$$777IIII?????????+++==~~:::,,,...... ..    
.....,,,,:::::~~~~~==+?II777$$$$77777IIII????+++++++=====~~~:::,,,........      
......,,,,,:::::~~~~==++??IIIIIIIII?????++++=======~~~~~~:::,,,,,,.......       
.......,,,,,,,::::~~~~==+++???????+++++=====~~~~~~::::::::,,,,,..........       
.........,,,,,,,,::::~~~======+======~~~~~~:::::::::,,,,,,,,............        
  .........,.,,,,,,,,::::~~~~~~~~~~:::::::::,,,,,,,,,,,...............          
   ..........,..,,,,,,,,,,::::::::::,,,,,,,,,.,....................             
     .................,,,,,,,,,,,,,,,,.......................                   
       .................................................                        
           ....................................                                 
               ....................   .                                         
                                                                                
                                                                                
                                                                 GlassGiant.com
                                                                 */
