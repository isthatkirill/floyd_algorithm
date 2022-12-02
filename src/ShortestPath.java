import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

class ShortestPath {

    final static int INF = 99999, V = 20; // INF - нет прямого пути, V - количество вершин графа
    int[][] shortDist = new int[20][20];

    //алгоритм флойда
    int[][] floyd(double[][] dist) {
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
        printMatrixInt(shortDist);
        return shortDist;
    }


    //вывод матриц на экран
    void printMatrix(double[][] matrix) {
        System.out.println();
        for (int i = 0; i < V; ++i) {
            for (int j = 0; j < V; ++j) {
                if (matrix[i][j] == INF)
                    System.out.print("INF ");
                else
                    System.out.printf("%11.5f ", matrix[i][j]);
            }
            System.out.println();
        }
    }

    void printMatrixInt(int[][] matrix) {
        System.out.println();
        for (int i = 0; i < V; ++i) {
            for (int j = 0; j < V; ++j) {
                if (matrix[i][j] == INF)
                    System.out.print("INF ");
                else
                    System.out.printf("%9d ", matrix[i][j]);
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

    //считывание исходной матрицы
    double[][] readMatrixPath() {
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

    //считывание матрицы интенсивностей трафика в направлениях связи
    double[][] readTrafficIntensityMatrix() { //посчитана в экселе
        int j = 0;
        double[][] mas = new double[20][20];
        List<String> trafficList = readFileContents("resources/trafficIntensivity.txt");
        if (!trafficList.isEmpty()) { //проверка на наличие данных в отчете
            for (String line : trafficList) {  // пропускаем один элемент
                String[] lineContent = line.split(" ");
                for (int i = 0; i < 20; i++) {
                    mas[i][j] = Double.valueOf(lineContent[i]);
                }
                j++;
            }
        }
        return mas;
    }


    double[][] loadIntensity(double[][] intensities, int[][] routes) {
        double[][] mas = new double[20][20];
        for (int i = 0; i < 20; ++i) {
            for (int j = 0; j < 20; ++j) {
                mas[i][routes[i][j] - 1] += intensities[i][j];
                for (int k = routes[i][j] - 1; k != j; k = routes[k][j] - 1) {
                    mas[(int)k][routes[k][j] - 1] += intensities[i][j];
                }
            }
        }
        return mas;
    }


    double[][] streamMatrix(double[][] intensities) { //матрица потоков (6)
        double p_max = 0.02;
        double[][] mas = new double[20][20];
        for (int i = 0; i < 20; ++i) {
            for (int j = 0; j < 20; ++j) {
                int v = 0;
                if (intensities[i][j] != 0) {
                    v = 1;
                    double p = 1;
                    double y = intensities[i][j];
                    double numerator = y;
                    double sum = y;
                    while (p > p_max) {
                        ++v; // прибавляем единицу к v каждую итерацию
                        numerator = (numerator / v) * y; //считаем числитель (перезаписываем)
                        sum += numerator; //считаем сумму (знаменатель)
                        p = numerator / sum; //находим p - если (p <= p_max) - цикл заканчивается, требование к качеству обслуживания выполнено
                    }
                }
                mas[i][j] = v;
            }
        }
        return mas;
    }
}