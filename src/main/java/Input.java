import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Input extends JFrame {
    private JTextArea textArea;
    private JButton button;
    private static int n = 0;
    private static ArrayList<ArrayList<Integer>> g;

    public final static int MAX_N = 15;

    private boolean flag = false;

    Input (){
        init();
        setLayout(new BorderLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(300, 300);
        textArea = new JTextArea(18, 15);
        textArea.setText("УДАЛИТЕ ОТСЮДА\nВходные данные подаются в виде:\nn\nM\n\nгде n - количество городов\nа М - матрица смежности\n Например: \nУДАЛИТЕ ДОСЮДА\n3\n-1 1 1\n1 -1 1\n1 1 -1");
        button = new JButton("OK");
        button.addActionListener(new ActionListener() { //Обработчик нажатия
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                onClick();
            }
        });
        getContentPane().add(textArea, BorderLayout.NORTH);
        getContentPane().add(button, BorderLayout.SOUTH);
        setVisible(true);
    }

    void init(){
        g = new ArrayList<>();
        for(int i = 0; i < MAX_N; i++){
            g.add(new ArrayList<>());
            for(int j = 0; j < MAX_N; j++){
                g.get(i).add(-1);
            }
        }
    }

    public boolean getFlag() {
        return flag;
    }

    public static int getN(){
        return n;
    }

    public static ArrayList<ArrayList<Integer>> getG() {
        return g;
    }

    void onClick(){
        String s = textArea.getText();
        String[] str = s.split("\\s");
        try{
            n = Integer.parseInt(str[0]);
            for(int i = 1; i < str.length; i++){
                g.get((i-1) / n).set((i-1) % n, Integer.parseInt(str[i])); //g[(i-1)/n][(i-1)%n] = str[i]
            }
            for(int i = 0; i < g.size(); i++){
                for(int j = 0; j < g.get(i).size(); j++){
                    if((j >= n || i >= n) && g.get(i).get(j) != -1){
                        System.exit(1);
                    }
                    if(i != j && g.get(i).get(j) == -1){
                        System.exit(1);
                    }
                    if(i == j && g.get(i).get(j) != -1){
                        System.exit(1);
                    }
                }
            }
        } catch (NumberFormatException e){
            System.out.println("Неправильный формат ввода");
        } finally {
            showAnswer();
        }
        flag = true;
        setVisible(false);
    }

    void showAnswer(){
        System.out.println(n);
        for(int i = 0; i < g.size(); i++){
            for(int j = 0; j < g.get(i).size(); j++){
                System.out.print(g.get(i).get(j)+" ");
            }
            System.out.println();
        }
    }
}
