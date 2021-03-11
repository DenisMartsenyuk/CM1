import methods.SimpleIterationMethod;
import models.DoubleMatrix;

import java.io.FileReader;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);

            System.out.println("ОБРАТИТЕ ВНИМАНИЕ! ВСЕ ДРОБНЫЕ ЧИСЛА ВВОДЯТСЯ ЧЕРЕЗ ЗАПЯТУЮ!");
            System.out.println("Каким образом будете вводить данные? Введите 'f' если из файла, 'k' - клавиатуры. ");
            String type = scanner.nextLine();

            if (!type.equals("f") && !type.equals("k")) {
                System.out.println("Выбран несуществующий тип ввода данных.");
                return;
            }

            if (type.equals("f")) {
                System.out.println("Введите путь до файла.");
                String path = scanner.nextLine();
                FileReader fileReader = new FileReader(path);
                scanner.close();
                scanner = new Scanner(fileReader);
            }

            if (type.equals("k"))
                System.out.println("Введите n.");
            Integer n = scanner.nextInt();
            if (n > 20 || n < 0) {
                System.out.println("Некорректное значение n.");
                return;
            }

            if (type.equals("k"))
                System.out.println("Введите точность из промежутка (0, 1).");
            Double accuracy = scanner.nextDouble();
            if (accuracy >= 1 || accuracy <= 0) {
                System.out.println("Некорректное значение точности.");
                return;
            }

            if (type.equals("k"))
                System.out.println("Введите коэфициенты матрицы.");
            DoubleMatrix matrix = new DoubleMatrix(n, n + 1);
            for (int i = 0; i < n; ++i) {
                for (int j = 0; j < n + 1; ++j) {
                    Double number = scanner.nextDouble();
                    matrix.setElement(number, i, j);
                }
            }

            scanner.close();
            SimpleIterationMethod method = new SimpleIterationMethod();
            method.calculation(matrix, accuracy);
        } catch (Exception e) {
            System.out.println("Произошел сбой при чтении данных.");
        }
    }
}
