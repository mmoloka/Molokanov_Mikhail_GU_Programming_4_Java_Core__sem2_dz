package org.example.sem2_dz;

import java.util.Random;
import java.util.Scanner;

public class Game {

    private static final char PLAYER_SYMBOL = 'X';   //  знак хода игрока
    private static final char COMPUTER_SYMBOL = 'O'; //  знак хода компьютера
    private static final char EMPTY_SYMBOL = '•';    //  знак пустого поля
    private static final Scanner SCANNER = new Scanner(System.in);
    private static final Random RANDOM = new Random();

    private static char[][]field;                    // двумерный массив, хранит текущее состояние игрового поля
    private static int fieldSizeX;                   // размер игрового поля по горизонтали
    private static int fieldSizeY;                   // размер игрового поля по вертикали


    public static void main(String[] args) {
        while (true) {
            fieldInitialize();
            fieldPrint();
            while (true){
                playerTurn();
                fieldPrint();
                if(checkGame(PLAYER_SYMBOL, "Ура! Вы победили!"))
                    break;
                computerTurn();
                fieldPrint();
                if(checkGame(COMPUTER_SYMBOL, "Победил компьютер!"))
                    break;
            }
            System.out.println("Желаете сыграть еще раз? (Y - да)");
            if(!SCANNER.next().equalsIgnoreCase("Y"))
                break;
        }
    }

    /**
     * Инициализация игрового поля.
     */
    private static void fieldInitialize() {
        fieldSizeX = 3;                                // устанавливаем размерность игрового поля
        fieldSizeY = 3;

        field = new  char[fieldSizeY][fieldSizeX];
        for (int y = 0; y < fieldSizeY; y++) {         // проходим по всем элементам двумерного массива и
            for (int x = 0; x < fieldSizeX; x++) {     // инициализируем каждый знаком пустого поля '.'
                field[y][x] = EMPTY_SYMBOL;
            }
        }
    }

    /**
     * Отрисовка игрового поля.
     */
    private static void fieldPrint() {
        // Header
        System.out.print("┌ ");
        for (int i = 0; i < fieldSizeX * 2; i++) {
            System.out.print((i % 2 == 0) ? " ┬ " : i / 2 + 1);
        }
        System.out.print(" ┐");
        System.out.println();
        // Body
        for (int j = 0; j < fieldSizeY; j++) {
            System.out.print(j + 1 + "  │ ");
            for (int k = 0; k < fieldSizeX; k++) {
                System.out.print(field[j][k] + " │ ");
            }
            System.out.println();
        }
        // Footer
        System.out.print("└ ");
        for (int m = 0; m < fieldSizeX * 2; m++) {
            System.out.print((m % 2 == 0) ? " ┴ " : "─");
        }
        System.out.print(" ┘");
        System.out.println();
    }

    /**
     * Обработка хода игрока.
     */
    private static void playerTurn() {
        int x, y;
        do {
            System.out.print("Введите координаты хода X и Y (от 1 до 3) через пробел >>> ");
            x = SCANNER.nextInt() - 1;
            y = SCANNER.nextInt() - 1;
        }
        while (!isCellValid(x, y) || !isCellEmpty(x, y));
        field[y][x] = PLAYER_SYMBOL;

    }

    /**
     * Проверка на пустую ячейку.
     * @param x координата ячейки по горизонтали
     * @param y координата ячейки по вертикали
     * @return true если ячейка пустая, иначе false
     */
    static boolean isCellEmpty(int x, int y) {
        return field[y][x] == EMPTY_SYMBOL;
    }

    /**
     * Проверка корректности ввода координат ячейки (исключить выход за размеры игрового поля).
     * @param x координата ячейки по горизонтали
     * @param y координата ячейки по вертикали
     * @return true если ячейка в пределах игрового поля, иначе false
     */
    static boolean isCellValid(int x, int y) {
        return x >= 0 && x < fieldSizeX && y >= 0 && y < fieldSizeY;
    }

    /**
     * Ход компьютера.
     */
    private static void computerTurn() {
        int x, y;
        do {
            x = RANDOM.nextInt(fieldSizeX);
            y = RANDOM.nextInt(fieldSizeY);
        }
        while (!isCellEmpty(x, y));
        field[y][x] = COMPUTER_SYMBOL;
    }

    /**
     * Проверка победы.
     * @param s символ победителя
     * @return true в случае победы игрока c указаным символом, иначе false
     */
    static boolean checkWin(char s) {
        // Проверка по трем горизонталям
        if(field[0][0] == s && field[0][1] == s && field[0][2] == s) return true;
        if(field[1][0] == s && field[1][1] == s && field[1][2] == s) return true;
        if(field[2][0] == s && field[2][1] == s && field[2][2] == s) return true;
        // Проверка по трем вертикалям
        if(field[0][0] == s && field[1][0] == s && field[2][0] == s) return true;
        if(field[0][1] == s && field[1][1] == s && field[2][1] == s) return true;
        if(field[0][2] == s && field[1][2] == s && field[2][2] == s) return true;
        // Проверка по двум диагоналям
        if(field[0][0] == s && field[1][1] == s && field[2][2] == s) return true;
        if(field[0][2] == s && field[1][1] == s && field[2][0] == s) return true;

        return false;
    }

    /**
     * Проверка на ничью.
     * @return true если не осталось пустых ячеек игрового поля, иначе false
     */
    static  boolean checkDraw() {
        for (int y = 0; y < fieldSizeY; y++) {
            for (int x = 0; x < fieldSizeX; x++) {
                if(isCellEmpty(x, y)) return false;
            }
        }
        return true;
    }

    /**
     * Проверка состояния игры.
     * @param s символ победителя
     * @param str выводимая строка в случае победы игрока c указаным символом
     * @return true в случае окончания игры, иначе false
     */
    static boolean checkGame(char s, String str) {
        if(checkWin(s)) {
            System.out.println(str);
            return true;
        }
        if(checkDraw()) {
            System.out.println("Ничья!");
            return true;
        }
        return false; //  Игра продолжается
    }
}
