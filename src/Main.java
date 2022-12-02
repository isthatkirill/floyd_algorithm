class Master {
    public static void main(String[] args)
    {
        ShortestPath shortestPath = new ShortestPath(); //объект класс
        double[][] mainMatrix = new double[20][20]; //исходная матрица из файла
        double[][] intensityMatrix = new double[20][20]; // Матрица интенсивностей трафика в направлениях связи
        int[][] shortRouteMatrix = new int[20][20]; //матрица кратчайших путей
        double[][] loadIntensity = new double[20][20]; // Матрица интенсивностей нагрузок на линии связи (5)

        mainMatrix = shortestPath.readMatrixPath(); //считывание исходной матрицы из файла
        intensityMatrix = shortestPath.readTrafficIntensityMatrix(); // считывание матрицы интенсивностей трафика в направлениях связи (3)
        shortRouteMatrix = shortestPath.floyd(mainMatrix); // печать матриц и возврат функцией матрицы кратчаших путей
        loadIntensity = shortestPath.loadIntensity(intensityMatrix, shortRouteMatrix); // получение матрицы интенсивностей нагрузок на линии связи (5)
        shortestPath.printMatrix(loadIntensity);

    }
}
