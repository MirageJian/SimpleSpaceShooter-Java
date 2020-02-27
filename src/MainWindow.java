import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import settings.GlobalConst;
import ui.Button;
import util.UnitTests;

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


public class MainWindow {
    public static void main(String[] args) {
        new MainWindow();  //sets up environment
    }

    private static JFrame frame = new JFrame("Simple Space Shooting");   // Change to the name of your game
    private static Model gameWorld = new Model();
    private static Viewer canvas = new Viewer(gameWorld);
    // private static int TargetFPS = 60;
    private static boolean startGame = false;
    private JLabel BackgroundImageForStartMenu;
    private JButton startMenuButton;
    private JButton doublePlayerButton;
    private JLabel endText;
    // Sound file
    private Clip startClip;

    public MainWindow() {
        frame.setSize(GlobalConst.LAYOUT_WIDTH + 14, GlobalConst.LAYOUT_HEIGHT + 37);  // you can customise this later and adapt it to change on size.
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   //If exit // you can modify with your way of quitting , just is a template.
        frame.setResizable(false); // Not resizable
        frame.setLayout(null);
        // Set canvas parameters
        canvas.setBounds(0, 0, GlobalConst.LAYOUT_WIDTH, GlobalConst.LAYOUT_HEIGHT);
        canvas.setBackground(new Color(255, 255, 255)); //white background  replaced by Space background but if you remove the background method this will draw a white screen
        canvas.setVisible(false);   // this will become visible after you press the key.
        // Start menu button
        startMenuButton = new Button("Start Shooting");  // start button
        startMenuButton.addActionListener(e -> startGame());
        startMenuButton.setBounds(GlobalConst.LAYOUT_WIDTH / 2 - 100, GlobalConst.LAYOUT_HEIGHT / 2 - 20, 200, 40);
        // Double player start Button
        doublePlayerButton = new Button("Double Players");
        doublePlayerButton.addActionListener(e -> {
            gameWorld.setP2();
            startGame();
        });
        doublePlayerButton.setBounds(GlobalConst.LAYOUT_WIDTH / 2 - 100, GlobalConst.LAYOUT_HEIGHT / 2 + 40, 200, 40);
        frame.add(doublePlayerButton);
        frame.add(startMenuButton);
        // Game Over tips
        endText = new JLabel("GAME OVER");
        endText.setFont(new Font(endText.getFont().toString(), Font.PLAIN, 36));
        endText.setBounds(GlobalConst.LAYOUT_WIDTH / 2 - 110, GlobalConst.LAYOUT_HEIGHT / 4 - 80, 220, 40);
        endText.setForeground(Color.WHITE);
        endText.setVisible(false);
        frame.add(endText);
        // loading background image Set background
        File BackroundToLoad = new File("res/startscreen.png");  //should work okay on OSX and Linux but check if you have issues depending your eclipse install or if your running this without an IDE
        try {
            // Start screen texture
            BufferedImage myPicture = ImageIO.read(BackroundToLoad);
            BackgroundImageForStartMenu = new JLabel(new ImageIcon(myPicture));
            BackgroundImageForStartMenu.setBounds(0, 0, GlobalConst.LAYOUT_WIDTH, GlobalConst.LAYOUT_HEIGHT);
            frame.add(BackgroundImageForStartMenu);
            // Stream can only have one
            startClip = playOrchestral();
        } catch (Exception e) {
            e.printStackTrace();
        }
        frame.add(canvas);
        frame.setVisible(true);
    }

    //Basic Model-View-Controller pattern
    private static void gameloop() {
        // GAMELOOP
        // controller input  will happen on its own thread
        // So no need to call it explicitly
        // model update
        gameWorld.getGameLogic().doLogic();
        // view update
        canvas.updateview();
        // Both these calls could be setup as  a thread but we want to simplify the game logic for you.
    }
    private void startGame() {
        endText.setVisible(false);
        startMenuButton.setVisible(false);
        doublePlayerButton.setVisible(false);
        BackgroundImageForStartMenu.setVisible(false);
        canvas.setVisible(true);
        // Set some listeners for canvas
        canvas.addKeyListener(Controller.getInstance());    //adding the controller to the Canvas
        canvas.addMouseMotionListener(MouseControl.getInstance());
        canvas.addMouseListener(MouseControl.getInstance());    //adding the Mouse Controller to the Canvas
        canvas.requestFocusInWindow();   // making sure that the Canvas is in focus so keyboard input will be taking in .
        // Reset game every time.
        startClip.stop();
        gameWorld.resetWorld();
        // Use a new Thread for game loop
        Thread thread = new Thread(() -> {
            // not nice but remember we do just want to keep looping till the end.  // this could be replaced by a thread but again we want to keep things simple
            while (true) {
                //swing has timer class to help us time this but I'm writing my own, you can of course use the timer, but I want to set FPS and display it
                long FrameCheck = System.currentTimeMillis() + (long) GlobalConst.INTERVAL_PER_FRAME;
                //wait till next time step
                while (FrameCheck > System.currentTimeMillis()) { }
                // Check whether the game is end or not
                if (!gameWorld.isGameEnd()) {
                    gameloop();
                } else break;
                //UNIT test to see if framerate matches
                UnitTests.CheckFrameRate(System.currentTimeMillis(), FrameCheck, GlobalConst.TARGET_FRAME);
            } // If game ends, show end option
            showEndOption();
        });
        thread.start();
    }

    private void showEndOption() {
        startMenuButton.setVisible(true);
        doublePlayerButton.setVisible(true);
        endText.setVisible(true);
        // Set frame and start
        startClip.setFramePosition(0);
        startClip.start();
    }

    private Clip playOrchestral() {
        // Sound of Start and End screen
        try {
            // Same as clip, it should be only one
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(new File("sound/space-orchestral.wav")));
            clip.start();
            return clip;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

/*
 * 
 * 

Hand shake agreement 
::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::,=+++
::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::,,,,,,:::::,=+++????
::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::,,,,,,,,,,,,,,:++++????+??
:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::,:,:,,:,:,,,,,,,,,,,,,,,,,,,,++++++?+++++????
::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,=++?+++++++++++??????
:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::,:,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,~+++?+++?++?++++++++++?????
:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::,:::,,,,,,,,,,,,,,,,,,,,,,,,,,,~+++++++++++++++????+++++++???????
::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::,:,,,,,,,,,,,,,,,,,,,,,,:===+=++++++++++++++++++++?+++????????????????
:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::,,,,,,,,,,,,,,,,,,~=~~~======++++++++++++++++++++++++++????????????????
::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::,::::,,,,,,=~.,,,,,,,+===~~~~~~====++++++++++++++++++++++++++++???????????????
:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::,:,,,,,~~.~??++~.,~~~~~======~=======++++++++++++++++++++++++++????????????????II
:::::::::::::::::::::::::::::::::::::::::::::::::::::::,:,,,,:=+++??=====~~~~~~====================+++++++++++++++++++++?????????????????III
:::::::::::::::::::::::::::::::::::::::::::::::::::,:,,,++~~~=+=~~~~~~==~~~::::~~==+++++++==++++++++++++++++++++++++++?????????????????IIIII
::::::::::::::::::::::::::::::::::::::::::::::::,:,,,:++++==+??+=======~~~~=~::~~===++=+??++++++++++++++++++++++++?????????????????I?IIIIIII
::::::::::::::::::::::::::::::::::::::::::::::::,,:+????+==??+++++?++====~~~~~:~~~++??+=+++++++++?++++++++++??+???????????????I?IIIIIIII7I77
::::::::::::::::::::::::::::::::::::::::::::,,,,+???????++?+?+++???7?++======~~+=====??+???++++++??+?+++???????????????????IIIIIIIIIIIIIII77
:::::::::::::::::::::::::::::::::::::::,,,,,,=??????IIII7???+?+II$Z77??+++?+=+++++=~==?++?+?++?????????????III?II?IIIIIIIIIIIIIIIIIIIIIIIIII
::::::::::::::::::::::::::::::,,,,,,~=======++++???III7$???+++++Z77ZDZI?????I?777I+~~+=7+?II??????????????IIIIIIIIIIIIIIIIIIIIII??=:,,,,,,,,
::::::::,:,:,,,,,,,:::~==+=++++++++++++=+=+++++++???I7$7I?+~~~I$I??++??I78DDDO$7?++==~I+7I7IIIIIIIIIIIIIIIIII777I?=:,,,,,,,,,,,,,,,,,,,,,,,,
++=++=++++++++++++++?+????+??????????+===+++++????I7$$ZZ$I+=~$7I???++++++===~~==7??++==7II?~,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
+++++++++++++?+++?++????????????IIIII?I+??I???????I7$ZOOZ7+=~7II?+++?II?I?+++=+=~~~7?++:,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
+?+++++????????????????I?I??I??IIIIIIII???II7II??I77$ZO8ZZ?~~7I?+==++?O7II??+??+=====.,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
?????????????III?II?????I?????IIIII???????II777IIII7$ZOO7?+~+7I?+=~~+???7NNN7II?+=+=++,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
????????????IIIIIIIIII?IIIIIIIIIIII????II?III7I7777$ZZOO7++=$77I???==+++????7ZDN87I??=~,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
IIII?II??IIIIIIIIIIIIIIIIIIIIIIIIIII???+??II7777II7$$OZZI?+$$$$77IIII?????????++=+.,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
IIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII?+++?IIIII7777$$$$$$7$$$$7IIII7I$IIIIII???I+=,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
IIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII???????IIIIII77I7777$7$$$II????I??I7Z87IIII?=,,,,,,,,,,,:,,::,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
777777777777777777777I7I777777777~,,,,,,,+77IIIIIIIIIII7II7$$$Z$?I????III???II?,,,,,,,,,,::,::::::::,,:,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
777777777777$77777777777+::::::::::::::,,,,,,,=7IIIII78ZI?II78$7++D7?7O777II??:,,,:,,,::::::::::::::,:,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
$$$$$$$$$$$$$77=:,:::::::::::::::::::::::::::,,7II$,,8ZZI++$8ZZ?+=ZI==IIII,+7:,,,,:::::::::::::::::,:::,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
$$$I~::::::::::::::::::::::::::::::::::::::::::II+,,,OOO7?$DOZII$I$I7=77?,,,,,,:::::::::::::::::::::,,,:,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
::::::::::::::::::::::::::::::::::::::::::::::::::::::+ZZ?,$ZZ$77ZZ$?,,,,,::::::::::::::::::::::::::,::::,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::I$:::::::::::::::::::::::::::::::::::::::::::,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::,,,:,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::,,,,,,,,,,,,,,,,,,,,,,,,,,,
:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::,,,,,,,,,,,,,,,,,,,,,,,,,,,
:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::,,,,,,,,,,,,,,,,,,,,,,,,,
::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::,,,,,,,,,,,,,,,,,,,,,,
::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::,,,,,,,,,,,,,,,,,,,,,,
                                                                                                                             GlassGiant.com
 * 
 * 
 */
