class Master {
    public static void main(String[] args)
    {
        ShortestPath shortestPath = new ShortestPath(); //объект класс
        double[][] graph = new double[20][20]; //матрица
        graph = shortestPath.readM(); //счиитывание мматрицы из файла
        shortestPath.floyd(graph); // поиск и печать

    }
}
