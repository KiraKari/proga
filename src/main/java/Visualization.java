import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxGraph;
import com.mxgraph.view.mxStylesheet;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Visualization extends JFrame
{
    private mxGraph graph;
    private Object parent;
    private mxGraphComponent graphComponent;
    private mxStylesheet stylesheet;

    private JScrollPane scrollPane;
    private JTextArea textArea;
    private JSlider slider;
    private JLabel sumWeightLabel;

    private ArrayList<Object> vertex = new ArrayList<> ();
    private ArrayList<ArrayList<Object>> edge = new ArrayList<> ();

    Visualization()
    {
        setLayout(new BorderLayout());

        textArea = new JTextArea(20, 20); //размер панельки справа
        textArea.setEditable(false);
        scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        getContentPane().add(scrollPane, BorderLayout.EAST);

        sumWeightLabel = new JLabel("Сумма весов: ; Минимальная сумма весов: ");
        getContentPane().add(sumWeightLabel, BorderLayout.SOUTH);

        slider = new JSlider();
        slider.setMinorTickSpacing(20);
        slider.setPaintTicks(true);
        slider.setValue(5);

        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                Algorithm.speed = slider.getValue()*100;
            }
        });
        getContentPane().add(slider, BorderLayout.NORTH);

        graph = new mxGraph();
        parent = graph.getDefaultParent();
        graphComponent = new mxGraphComponent(graph); //чтобы прикрепить к jframe
        getContentPane().add(graphComponent, BorderLayout.CENTER);
        stylesheet = new mxStylesheet();
        initVertexTurnOn();
        initVertexTurnOff();
        initEdgeTurnOn();
        initEdgeTurnOff();
        graph.setStylesheet(stylesheet);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(950, 650);
        setVisible(true);

        for(int i = 0; i < 20; i++)
        {
            edge.add(new ArrayList<> ());
            for(int j = 0; j < 20; j++)
            {
                edge.get(i).add(null);
            }
        }
    }

    public void log(String s)
    {
        textArea.append(s);
        textArea.setCaretPosition(textArea.getDocument().getLength()); //чтобы панелька справа спускалась вниз
    }

    public void changeSumOfWeights(int current, int min)
    {
        sumWeightLabel.setText("Сумма весов: "+current+"; Минимальная сумма весов: "+min);
    }

    public void addVertex(int number, int x, int y)
    {
        beginUpdate();
        try{
            vertex.add(graph.insertVertex(parent, null, Integer.toString(number), x, y, 50, 50));
            System.out.println(number+" vertex has been added");
            log(number+" вершина была добавлена\n");
        }
        finally {
            endUpdate();
        }
    }

    public void addEdge(int v1, int v2)
    {
        beginUpdate();
        try{
            edge.get(v1).set(v2, graph.insertEdge(parent, null, "", vertex.get(v1), vertex.get(v2)));
        }
        finally {
            endUpdate();
        }
    }

    public void turnOnVertex(int v)
    {
        System.out.println("Turn on "+v+" vertex");
        log("Пришли в "+v+" вершину\n");
        graph.setCellStyle("turnVertexOn", new Object[]{vertex.get(v)});
        graphComponent.refresh();
    }

    public void turnOffVertex(int v)
    {
        System.out.println("Turn off "+v+" vertex");
        log("Вышли из "+v+" вершины\n");
        graph.setCellStyle("turnVertexOff", new Object[]{vertex.get(v)});
        graphComponent.refresh();
    }

    public void turnOnEdge(int v1, int v2)
    {
        if(v1 == -1) return;

        if(v1 > v2)
        {
            int b = v1;
            v1 = v2;
            v2 = b;
        }

        System.out.println("Turn on from "+v1+" to "+v2);
        graph.setCellStyle("turnEdgeOn", new Object[]{edge.get(v1).get(v2)});
        graphComponent.refresh();
    }

    public void turnOffEdge(int v1, int v2)
    {
        if(v1 == -1) return;

        if(v1 > v2)
        {
            int b = v1;
            v1 = v2;
            v2 = b;
        }

        System.out.println("Turn off from "+v1+" to "+v2);
        graph.setCellStyle("turnEdgeOff", new Object[]{edge.get(v1).get(v2)});
        graphComponent.refresh();
    }

    private void beginUpdate()
    {
        graph.getModel().beginUpdate();
    }

    private void endUpdate()
    {
        graph.getModel().endUpdate();
    }

    // ниже играюсь со шрифтами
    private void initVertexTurnOn()
    {
        Map<String, Object> vertexStyle = new HashMap<> ();
        vertexStyle.put(mxConstants.STYLE_VERTICAL_ALIGN, mxConstants.ALIGN_MIDDLE);
        vertexStyle.put(mxConstants.STYLE_PERIMETER, mxConstants.PERIMETER_ELLIPSE);
        vertexStyle.put(mxConstants.STYLE_PERIMETER_SPACING, 10);
        vertexStyle.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_ELLIPSE);
        vertexStyle.put(mxConstants.STYLE_RESIZABLE, 0);
        vertexStyle.put(mxConstants.STYLE_MOVABLE, 0);
        vertexStyle.put(mxConstants.STYLE_FONTCOLOR, "#000000");
        vertexStyle.put(mxConstants.STYLE_LABEL_BACKGROUNDCOLOR, "#ffffff");
        vertexStyle.put(mxConstants.STYLE_FILLCOLOR, "#ff0000");
        stylesheet.putCellStyle("turnVertexOn", vertexStyle);
    }

    private void initVertexTurnOff()
    {
        Map<String, Object> vertexStyle = new HashMap<> ();
        vertexStyle.put(mxConstants.STYLE_VERTICAL_ALIGN, mxConstants.ALIGN_MIDDLE);
        vertexStyle.put(mxConstants.STYLE_PERIMETER, mxConstants.PERIMETER_ELLIPSE);
        vertexStyle.put(mxConstants.STYLE_PERIMETER_SPACING, 10);
        vertexStyle.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_ELLIPSE);
        vertexStyle.put(mxConstants.STYLE_RESIZABLE, 0);
        vertexStyle.put(mxConstants.STYLE_MOVABLE, 0);
        vertexStyle.put(mxConstants.STYLE_FONTCOLOR, "#000000");
        vertexStyle.put(mxConstants.STYLE_LABEL_BACKGROUNDCOLOR, "#ffffff");
        vertexStyle.put(mxConstants.STYLE_FILLCOLOR, "#A9A9A9");
        stylesheet.putCellStyle("turnVertexOff", vertexStyle);
        stylesheet.setDefaultVertexStyle(vertexStyle);
    }

    private void initEdgeTurnOn()
    {
        Map<String, Object> edgeStyle = new HashMap<> ();
        edgeStyle.put(mxConstants.STYLE_MOVABLE, 0);
        edgeStyle.put(mxConstants.STYLE_RESIZABLE, 0);
        edgeStyle.put(mxConstants.STYLE_SHAPE,    mxConstants.SHAPE_CONNECTOR);
        edgeStyle.put(mxConstants.STYLE_ENDARROW, mxConstants.NONE);
        edgeStyle.put(mxConstants.STYLE_STROKECOLOR, "#ff0000");
        edgeStyle.put(mxConstants.STYLE_FONTCOLOR, "#000000");
        edgeStyle.put(mxConstants.STYLE_LABEL_BACKGROUNDCOLOR, "#ffffff");
        edgeStyle.put(mxConstants.STYLE_FILLCOLOR, "#ffffff");
        stylesheet.putCellStyle("turnEdgeOn", edgeStyle);
    }

    private void initEdgeTurnOff()
    {
        Map<String, Object> edgeStyle = new HashMap<> ();
        edgeStyle.put(mxConstants.STYLE_MOVABLE, 0);
        edgeStyle.put(mxConstants.STYLE_RESIZABLE, 0);
        edgeStyle.put(mxConstants.STYLE_SHAPE,    mxConstants.SHAPE_CONNECTOR);
        edgeStyle.put(mxConstants.STYLE_ENDARROW, mxConstants.NONE);
        edgeStyle.put(mxConstants.STYLE_STROKECOLOR, "#000000");
        edgeStyle.put(mxConstants.STYLE_FONTCOLOR, "#000000");
        stylesheet.putCellStyle("turnEdgeOff", edgeStyle);
        stylesheet.setDefaultEdgeStyle(edgeStyle);
    }
}