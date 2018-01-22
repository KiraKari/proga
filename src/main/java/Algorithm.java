import java.util.ArrayList;
import java.util.Collections;

public class Algorithm {
    private ArrayList<ArrayList<Integer>> g = new ArrayList<> ();
    private ArrayList<Boolean> visited = new ArrayList<> ();
    private ArrayList<Integer> path = new ArrayList<> ();
    private ArrayList<Integer> optimal_path = new ArrayList<> ();

    private int n = 0;
    private int min_weight = 99999999;
    private int start = 0;
    private int finish = 0;

    private Visualization visual;

    public void start()
    {
        getInput();
        init();
        updateViz();
        DFS(0, -1, n - 1, 0);
        showAnswer();
    }

    private void init(){ //инициализируем path и visited
        for(int i = 0; i < n; i++){
            path.add(-1);
            visited.add(false);
        }
    }

    private void getInput(){
        n = Input.getN();
        g = Input.getG();
    }

    private void updateViz(){ //Рисует окошко с визуализацией
        visual = new Visualization();
        ArrayList<Point> points = getCoordinates();
        for(int i = 0; i < n; i++){
            visual.addVertex(i, (int)(points.get(i).x * 250) + 250, (int)(points.get(i).y * 250) + 250);
        }

        for(int i = 0; i < n; i++){
            for(int j = i; j < n; j++){ //Идем по правой от главной диагонали части матрицы
                if(i == j) continue;
                visual.addEdge(i, j);
            }
        }
    }

    private void DFS(int v, int from, int steps, int weight){ //реализуем перебор
        if (visited.get(v)) return;

        visual.turnOn(from, v);
        visual.changeSumOfWeights(weight, min_weight);

        if(weight > min_weight){
            visual.turnOff(from, v);
            return;
        }

        visited.set(v, true);
        path.set(v, from);
        if(steps == 0 && g.get(v).get(start)!= -1){
            weight += g.get(v).get(start);
            if(weight < min_weight){
                min_weight = weight;
                optimal_path = (ArrayList<Integer>) path.clone(); //Сохраняем найденный путь
                finish = v;
                visual.changeSumOfWeights(weight, min_weight);
            }
        }

        for(int i = 0; i < n; i++){ // Идем по соседям
            if(g.get(v).get(i) == -1) continue; // и проверяем есть ли там путь
            DFS(i, v, steps - 1, weight + g.get(v).get(i));
        }
        visited.set(v, false);

        visual.turnOff(from, v);
    }

    private void showAnswer(){
        ArrayList<Integer> a = get_path();
        for(int i = 0; i < a.size() - 1; i++){
            visual.log(i+" - ");
        }
        visual.log(finish+"\nМинимальный вес "+min_weight+"\n");
    }

    private ArrayList<Integer> get_path()
    {
        ArrayList<Integer> ans = new ArrayList<> ();

        int prev = -1;
        for (int v = finish; v != start; v = optimal_path.get(v)){
            visual.turnOn(prev, v);

            ans.add(v);
            prev = v;
        }

        ans.add(start);
        Collections.reverse(ans); //переворачиваем для полуения пути
        visual.turnOnEdge(ans.get(0), ans.get(1)); //и подсвеиваем его
        for(int i = 0; i < n; i++){
            if(i != 0 && i != n - 1){
                visual.turnOnEdge(ans.get(i - 1), ans.get(i));
            }
            visual.turnOnVertex(ans.get(i));
        }
        visual.turnOnEdge(start, finish);
        return ans;
    }

    private ArrayList<Point> getCoordinates(){ //рассчитываем расположение вершин графа
        ArrayList<Point> coord = new ArrayList<> ();
        double step = (2*Math.PI)/n;
        for(double i = 0; i < 2*Math.PI; i += step){
            double y = Math.sin(i);
            double x = Math.cos(i);
            coord.add(new Point(x, y));
        }
        return coord;
    }
}