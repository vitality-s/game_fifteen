package fifteen_gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Window15 extends JFrame {

    //Переменная статуса победы
    public static boolean win = false;
    public static int move = 0;
    //Массив цветов для кнопок
    private Color[] color;
    //Основная панель
    JPanel contentPane;
    //Панель с игровым полем
    JPanel panelFieldGame;
    //Стартовая панель
    JPanel panelStart;
    JButton buttonStart;
    //Менеджер компановки элементов
    BorderLayout borderLayout;
    //Текстовая метка с информацией о ходах
    JLabel moveStatus;
    //Массив игровых кнопок
    JButton[][] buttons;
    //Матрица чисел (от 1 до 15)
    int[][] matrix;

    public Window15() {
        //Инициализация полей класса
        color = new Color[16];
        color[0] = Color.WHITE;
        color[1] = Color.BLUE;
        color[2] = Color.CYAN;
        color[3] = Color.getHSBColor(0, 0, 30);
        color[4] = Color.GRAY;
        color[5] = Color.GREEN;
        color[6] = Color.LIGHT_GRAY;
        color[7] = Color.MAGENTA;
        color[8] = Color.ORANGE;
        color[9] = Color.PINK;
        color[10] = Color.RED;
        color[11] = Color.YELLOW;
        color[12] = Color.getHSBColor(270, 170, 200);
        color[13] = Color.getHSBColor(116, 26, 90);
        color[14] = Color.getHSBColor(237, 25, 92);
        color[15] = Color.getHSBColor(150, 110, 67);

        enableEvents(AWTEvent.WINDOW_EVENT_MASK);
        borderLayout = new BorderLayout();
        moveStatus = new JLabel();
        buttons = new JButton[4][4];
        panelFieldGame = new JPanel();
        panelStart = new JPanel(new GridLayout(1, 1));
        buttonStart = new JButton();
        matrix = new int[4][4];

        try {
            initializeContent();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Метод инициализации графического окна
     *
     * @throws Exception
     */
    private void initializeContent() throws Exception {
        contentPane = (JPanel) this.getContentPane();
        contentPane.setLayout(borderLayout);
        this.setSize(new Dimension(270, 339));
        this.setTitle("Пятнашки");
        //Запрет изменения размера окна
        this.setResizable(false);
        //Создание основного меню
        JMenuBar menuBar = new JMenuBar();
        //Создае подменю Игра
        JMenu menuGame = new JMenu("Игра");
        //Создание подменю О программе
        JMenu menuHelp = new JMenu("О программе");
        //Добавление подменю в основное меню
        menuBar.add(menuGame);
        menuBar.add(menuHelp);
        //Установка меню на окно
        this.setJMenuBar(menuBar);
        //Создание элементов подменю Новая Игра и добавляем обработчики событий
        JMenuItem item1 = new JMenuItem("Новая игра");
        JMenuItem item2 = new JMenuItem("Выход");
        //Создание элемента подменю Разработчик с обработчиками событий
        JMenuItem item3 = new JMenuItem("Разработчик");
        //Добавляем обработчики событий по нажатию

        item1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                win = false;
                move = 0;
                newGame();
            }
        });

        item2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);//Выход из системы
            }
        });

        item3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,
                        "Игра разработана: \nИванов Иван Иванович", "Разработчик",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });

        //Добавление элементов подменю в меню
        menuGame.add(item1);
        menuGame.add(item2);
        menuHelp.add(item3);

        panelFieldGame.setLayout(null);
        // Добавление кнопок с установкой слушателя мыши
        int count = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                buttons[i][j] = new JButton("" + count);
                buttons[i][j].setFont(new Font("Times New Roman", Font.ROMAN_BASELINE, 16));
                //Установка обработчики событий мыши
                buttons[i][j].addMouseListener(new myMouseAdapter(i, j));
                //Установка размеров кнопки
                buttons[i][j].setSize(66, 66);
                //Установка расположения кнопки
                buttons[i][j].setLocation(66 * j, 66 * i);
                //Устанока отображения курсора над кнопкой
                buttons[i][j].setCursor(new Cursor(Cursor.HAND_CURSOR));
                //Добавление кнопки на игровую панель
                panelFieldGame.add(buttons[i][j]);
                //Заполнение матрицы значение счетчика count
                matrix[i][j] = count;
                count++;
            }
        }
        //Установка пустого значения текста для первой кнопки
        buttons[0][0].setText(" ");
        //Добавление игровой панели с кнопками
        contentPane.add(panelFieldGame, BorderLayout.CENTER);

        //Установка обрамления строки статуса
        moveStatus.setBorder(BorderFactory.createBevelBorder(0));
        //Установка цвета надписи
        moveStatus.setForeground(Color.ORANGE);
        //Добавление строки статуса (вниз)
        contentPane.add(moveStatus, BorderLayout.SOUTH);

        showDialod("Старт", 80);

        newGame();
    }

    /**
     * Переопределенный метод условия нажания на кнопку закрыть (крестик)
     *
     * @param e
     */
    protected void processWindowEvent(WindowEvent e) {
        super.processWindowEvent(e);
        if (e.getID() == WindowEvent.WINDOW_CLOSING) {
            //Завершение работы приложения
            System.exit(0);
        }
    }

    /**
     * Перемешивание элементов матрицы
     */
    public void shufleMatrix() {
        //Размерность матрицы
        int n = 4;

        for (int i = 0; i < 100; i++) {
            int a = (int) (Math.random() * n);
            int b = (int) (Math.random() * n);
            int a1 = (int) (Math.random() * n);
            int b1 = (int) (Math.random() * n);

            int c = matrix[a][b];
            matrix[a][b] = matrix[a1][b1];
            matrix[a1][b1] = c;
        }
    }

    /**
     * Метод генерации новой игры
     */
    public void newGame() {
        //Перемешивание матрицы
        shufleMatrix();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                //Установка значений чисел и цвета на игровых кнопках
                if (matrix[i][j] != 0) {
                    buttons[i][j].setText("" + matrix[i][j]);
                    buttons[i][j].setBackground(color[matrix[i][j]]);
                } else {
                    buttons[i][j].setText("");
                    buttons[i][j].setBackground(color[matrix[i][j]]);
                }
            }
        }
        moveStatus.setText("Новая игра");
        //Установка видимости игровой панели
        panelFieldGame.setVisible(true);
    }

    /**
     * Метод отображения диалогового информационного окна
     * @param text текст на кнопке
     * @param fontSize размер шрифта на кнопке
     */
    private void showDialod(String text, int fontSize) {
        //Создание даилогового окна
        JDialog jd = new JDialog();
        buttonStart.setFont(new Font("Times New Roman", Font.CENTER_BASELINE, fontSize));
        buttonStart.setForeground(Color.red);
        buttonStart.setText(text);
        panelStart.add(buttonStart);
        buttonStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                jd.setVisible(false);

            }
        });
        jd.add(panelStart);
        jd.setTitle("Пятнашки");
        jd.setMinimumSize(new Dimension(300, 200));
        jd.setModal(true);
        jd.setResizable(false);
        jd.setPreferredSize(new Dimension(300, 200));
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = jd.getSize();
        if (frameSize.height > screenSize.height) {
            frameSize.height = screenSize.height;
        }
        if (frameSize.width > screenSize.width) {
            frameSize.width = screenSize.width;
        }
        jd.setLocation((screenSize.width - frameSize.width) / 2,
                (screenSize.height - frameSize.height) / 2);
        jd.setVisible(true);        
    }

    /**
     * Класс адаптера прослушивающий события мыши над кнопкой
     */
    class myMouseAdapter extends java.awt.event.MouseAdapter {

        int posi, posj;
        int startx = 0;
        int starty = 0;

        /**
         * Конструктор адаптера в зависимости от позиции кнопки
         */
        myMouseAdapter(int posI, int posJ) {
            this.posi = posI;
            this.posj = posJ;
        }

        /**
         * Метод - слушатель нажатой кнопки мыши
         */
        public void mousePressed(MouseEvent e) {
            //Изменение курсова мыши
            buttons[posi][posj].setCursor(new Cursor(Cursor.MOVE_CURSOR));
            //Начальное значение координат переноса кнопки
            startx = e.getX();
            starty = e.getY();
        }

        /**
         * Метод - слушатель при отпускании кнопки мыши
         */
        public void mouseReleased(MouseEvent e) {
            //Изменение курсова мыши
            buttons[posi][posj].setCursor(new Cursor(Cursor.HAND_CURSOR));
            //Конечное значение координат переноса кнопки
            int endx = e.getX();
            int endy = e.getY();
            //Определяем направление переноса кнопки:
            //если абсолютная величина (shiftx) больше абсолютной величины (shifty), 
            //то значит передвигаемся по оси х
            int shiftx = endx - startx;
            int shifty = endy - starty;

            if (Math.abs(shiftx) > Math.abs(shifty)) {
                //Определяем направление движения по оси х
                if (shiftx > 0) {//ВПРАВО

                    //Если это не крайние правые кнопки и правая кнопка пустая, то
                    //меняем местами кнопку с пустой кнопкой
                    if ((posj != 3) && (matrix[posi][posj + 1] == 0)) {
                        matrix[posi][posj + 1] = matrix[posi][posj];
                        matrix[posi][posj] = 0;
                        //Изменение цвета передвинутых кнопок
                        Color background = buttons[posi][posj].getBackground();
                        buttons[posi][posj].setBackground(Color.WHITE);
                        buttons[posi][posj + 1].setBackground(background);

                        buttons[posi][posj].setText("");
                        buttons[posi][posj + 1].setText("" + matrix[posi][posj + 1]);
                        move++;
                        moveStatus.setText("Ход: " + move + ".  Ход возможен");
                        //Проверка на конец игры (победу)
                        win = findWin();
                        System.out.println("win = " + win);
                    } else {
                        moveStatus.setText("Ход: " + move + ".  Ход не возможен");
                    }
                } else {//ВЛЕВО

                    //если это не крайние левые кнопки и левая кнопка пустая, то
                    //меняем местами кнопку с пустой кнопкой
                    if ((posj != 0) && (matrix[posi][posj - 1] == 0)) {
                        matrix[posi][posj - 1] = matrix[posi][posj];
                        matrix[posi][posj] = 0;

                        Color background = buttons[posi][posj].getBackground();
                        buttons[posi][posj].setBackground(Color.WHITE);
                        buttons[posi][posj - 1].setBackground(background);

                        buttons[posi][posj].setText("");
                        buttons[posi][posj - 1].setText("" + matrix[posi][posj - 1]);
                        move++;
                        moveStatus.setText("Ход: " + move + ".  Ход возможен");

                        win = findWin();
                        System.out.println("win = " + win);
                    } else {
                        moveStatus.setText("Ход: " + move + ".  Ход не возможен");
                    }
                }
            } else {
                //Определяем направление движения по оси y
                if (shifty > 0) {//ВНИЗ

                    //если это не крайние нижние кнопки и нижняя кнопка пустая, то
                    //меняем местами кнопку с пустой кнопкой
                    if ((posi != 3) && (matrix[posi + 1][posj] == 0)) {
                        matrix[posi + 1][posj] = matrix[posi][posj];
                        matrix[posi][posj] = 0;

                        Color background = buttons[posi][posj].getBackground();
                        buttons[posi][posj].setBackground(Color.WHITE);
                        buttons[posi + 1][posj].setBackground(background);

                        buttons[posi][posj].setText("");
                        buttons[posi + 1][posj].setText("" + matrix[posi + 1][posj]);

                        move++;
                        moveStatus.setText("Ход: " + move + ".  Ход возможен");
                        win = findWin();
                        System.out.println("win = " + win);
                    } else {
                        moveStatus.setText("Ход: " + move + ".  Ход не возможен");
                    }
                } else {//ВВЕРХ

                    //Если это не крайние верхние кнопки и верхняя кнопка пустая, то
                    //меняем местами кнопку с пустой кнопкой
                    if ((posi != 0) && (matrix[posi - 1][posj] == 0)) {
                        matrix[posi - 1][posj] = matrix[posi][posj];
                        matrix[posi][posj] = 0;

                        Color background = buttons[posi][posj].getBackground();
                        buttons[posi][posj].setBackground(Color.WHITE);
                        buttons[posi - 1][posj].setBackground(background);

                        buttons[posi][posj].setText("");
                        buttons[posi - 1][posj].setText("" + matrix[posi - 1][posj]);

                        move++;
                        moveStatus.setText("Ход: " + move + ".  Ход возможен");
                        win = findWin();
                        System.out.println("win = " + win);
                    } else {
                        moveStatus.setText("Ход: " + move + ".  Ход не возможен");
                    }
                }
            }

            //Если победа (win = true)
            if (win) {
                moveStatus.setText("Победа!");
                //Отображение окна с информацией о победе
                int result = JOptionPane.showConfirmDialog(null,
                        "Вы победили!", "ПОБЕДА!!!", JOptionPane.INFORMATION_MESSAGE);
                if (result == JOptionPane.YES_OPTION) {
                    //Сброс победы и счетчика ходов
                    win = false;
                    move = 0;
                    showDialod("Новая игра", 45);
                    //Генерация новой игры
                    newGame();
                } else {
                    //Скрыть игровую панель
                    panelFieldGame.setVisible(false);
                }
            }
        }

        /**
         * Метод проверки условия окончания игры
         *
         * @return true - если сумма по всем линиям равна 30, иначе - false
         */
        private boolean findWin() {
            //Счетчики сумм по линиям (строкам, столбцам и даигоналям)
            int sumColumn = 0;
            int sumRow = 0;
            int sumDiagLeft = 0;
            int sumDiagRight = 0;
            for (int j = 0; j < 4; j++) {
                sumColumn = 0;
                sumRow = 0;
                for (int i = 0; i < 4; i++) {
                    sumColumn = sumColumn + matrix[i][j];
                    sumRow = sumRow + matrix[j][i];
                }
                //Проверка значения сумм равным 30
                if (sumColumn != 30 || sumRow != 30) {
                    return false;
                }

                sumDiagLeft = sumDiagLeft + matrix[j][j];
                sumDiagRight = sumDiagRight + matrix[j][4 - 1 - j];

            }
            //Проверка значения сумм равным 30
            if (sumDiagLeft != 30 || sumDiagRight != 30) {
                return false;
            }
            return true;
        }
    }
}
