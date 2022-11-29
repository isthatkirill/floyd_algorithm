import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

class ShortestPath {

    final static int INF = 99999, V = 20; // INF - нет прямого пути, V - количество вершин графа
    double[][] shortDist = new double[20][20];

    //алгоритм флойда
    void floyd(double[][] dist) {
        int t = 1;
        for (int i = 0; i < 20; ++i) {
            for (int j = 0; j < 20; ++j) {
                shortDist[i][j] = t;
                t++;
            }
            t = 1;
        }

        for (int k = 0; k < V; k++) {
            for (int i = 0; i < V; i++) {
                for (int j = 0; j < V; j++) {
                    if (i != j && i != k && j != k) {
                        if (dist[i][k] + dist[k][j] < dist[i][j]) {
                            dist[i][j] = dist[i][k] + dist[k][j];
                            shortDist[i][j] = shortDist[i][k];
                        }
                    }
                }
            }
        }
        System.out.println("Матрица кратчайших дистанций: ");
        printMatrix(dist);
        System.out.println("Матрица кратчайших путей: ");
        printMatrix(shortDist);
    }


    //вывод матриц на экран
    static void printMatrix(double[][] matrix) {
        for (int i = 0; i < V; ++i) {
            for (int j = 0; j < V; ++j) {
                if (matrix[i][j] == INF)
                    System.out.print("INF ");
                else
                    System.out.printf("%9.5f ", matrix[i][j]);
            }
            System.out.println();
        }
    }


    //считывание с файла
    static List<String> readFileContents(String path) {
        try {
            return Files.readAllLines(Path.of(path));
        } catch (IOException e) {
            System.out.println("Файл не существует.");
            return Collections.emptyList();
        }
    }

    //сплит по пробелу, преобразование в Double
    double[][] readM() {
        int j = 0;
        double[][] mas = new double[20][20];
        List<String> mList = readFileContents("resources/matrix.txt");
        if (!mList.isEmpty()) { //проверка на наличие данных в отчете
            for (String line : mList) {  // пропускаем один элемент
                String[] lineContent = line.split(" ");
                for (int i = 0; i < 20; i++) {
                    mas[i][j] = Double.valueOf(lineContent[i]);
                }
                j++;
            }
        }
        return mas;
    }
}