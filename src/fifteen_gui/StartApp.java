package fifteen_gui;

import javax.swing.UIManager;
import java.awt.*;

public class StartApp {

    boolean packFrame = false;

    public StartApp() {
        
        Window15 frame = new Window15();

        //Центрирование программы
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = frame.getSize();
        
        if (frameSize.height > screenSize.height) {
            frameSize.height = screenSize.height;
        }
        if (frameSize.width > screenSize.width) {
            frameSize.width = screenSize.width;
        }
        //Установка расположения окна с программой по центру экрана
        frame.setLocation((screenSize.width - frameSize.width) / 2,
                (screenSize.height - frameSize.height) / 2);
        frame.setVisible(true);
    }

    /**
     * Точка входа в программу
     */
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(
                    UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        new StartApp();
    }
}
