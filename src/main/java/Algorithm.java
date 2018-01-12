import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Scanner;

public class Algorithm {

    private ArrayList<ArrayList<Integer>> g = new ArrayList<>();
    private ArrayList<Boolean> visited = new ArrayList<>();
    private ArrayList<Integer> path = new ArrayList<>();
    private ArrayList<Integer> optimal_path = new ArrayList<>();

    private int n = 0;
    private int min_weight = 99999999;
    private int start = 0;
    private int finish = 0;

    private Visualization visual;

    Algorithm(Visualization visual){
        this.visual = visual;
    }

    private void DFS(int v, int from, int steps, int weight)
    {
        if (visited.get(v))
        {
            return;
        }


        if(min_weight < weight)
        {
            return;
        }

        if (steps == 0 && g.get(v).get(start)!= -1)
        {
            weight += g.get(v).get(start);
            if(weight < min_weight){
                min_weight = weight;
                optimal_path = (ArrayList<Integer>) path.clone();
            }
            finish = v;
        }

        visited.set(v, true);
        path.set(v, from);

        for(int i = 0; i < n; i++)
        {
            if(g.get(v).get(i) == -1)
            {
                continue;
            }
            DFS(i, v, steps - 1, weight + g.get(v).get(i));
        }
        visited.set(v, false);
    }


    private ArrayList<Integer> get_path()
    {
        ArrayList<Integer> ans = new ArrayList<>();
        for (int v = finish; v != start; v = optimal_path.get(v))
        {
            ans.add(v);
        }
        ans.add(start);
        Collections.reverse(ans);
        return ans;
    }

    public void start(){
        getInput();
        updateViz();
        DFS(0, -1, n - 1, 0);
        showAnswer();
    }

    private void updateViz(){
        ArrayList<Point> points = getCoordinates();
        for(int i = 0; i < n; i++){
            visual.addVertex(i, (int)(points.get(i).x * 250) + 250, (int)(points.get(i).y * 250) + 250);
        }

        for(int i = 0; i < n; i++){
            for(int j = i; j < n; j++){
                if(i == j) continue;
                visual.addEdge(i, j, g.get(i).get(j), true);
            }
        }


        for(int i = 0; i < n; i++){
            for(int j = 0; j < i; j++){
                visual.addEdge(i, j, g.get(i).get(j), false);
            }
        }
    }

    private void getInput(){
        System.out.println("Введите количество городов: ");
        Scanner cin = new Scanner(System.in);
        n = cin.nextInt();
        System.out.println("Введите матрицу смежности: ");
        for(int i = 0; i < n; i++) {
            g.add(new ArrayList<>());
            path.add(-1);
            visited.add(false);
            for (int j = 0; j < n; j++) {
                int k = cin.nextInt();
                g.get(i).add(k);
            }
        }
    }

    private void showAnswer(){
        ArrayList<Integer> a = get_path();
        for(int i : a){
            System.out.print(i+" ");
        }
        System.out.println("\n"+min_weight);
    }

    private ArrayList<Point> getCoordinates(){
        ArrayList<Point> coord = new ArrayList<>();
        double step = (2*Math.PI)/n;
        for(double i = 0; i < 2*Math.PI; i += step){
            double y = Math.sin(i);
            double x = Math.cos(i);
            coord.add(new Point(x, y));
        }
        return coord;
    }
}



/*
TESTS:

 3
 -1 50 100
 999 -1 15
 188 13 -1

 5
 -1	34	76	45	49
 64	-1	34	85	435
 64	5	-1	9999	3
 1	5	4	-1	8
 45	9	1	5	-1

 0 - 4 - 3 - 2 - 1
 77

 5
 -1	34	76	45	49
 64	-1	0	85	435
 64	5	-1	9999	0
 0	5	4	-1	8
 45	9	1	5	-1

 0 - 4 - 3 - 2 - 1
 39

 6
 -1	34	76	45	49 3
 64	-1	0	85	435 5
 64	5	-1	9999	0 8
 0	5	4	-1	8 5
 45	9	1	5	-1 4
 12 32 54 239 438 -1

 0 - 5 - 1 - 2 - 4 - 3
 40

 7
 -1	34	76	45	49 3 5
 64	-1	0	85	435 5 1
 64	5	-1	9999	0 8 2
 0	5	4	-1	8 5 5
 45	9	1	5	-1 4 4
 12 32 54 239 438 -1 2
 456 7 4 7 4 9 -1

 0 - 6 - 5 - 4 - 3 - 2 - 1
 17

 */