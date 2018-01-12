import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxGraph;
import com.mxgraph.view.mxStylesheet;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Visualization extends JFrame {

    private mxGraph graph;
    private Object parent;

    private ArrayList<Object> vertex = new ArrayList<>();
    private ArrayList<ArrayList<Object>> edge = new ArrayList<>();

    private Map<String, Object> edgeStyle = new HashMap<String, Object>();
    private Map<String, Object> vertexStyle = new HashMap<String, Object>();

    Visualization(){
        graph = new mxGraph();
        parent = graph.getDefaultParent();
        mxGraphComponent graphComponent = new mxGraphComponent(graph);
        getContentPane().add(graphComponent);
        mxStylesheet stylesheet = new mxStylesheet();
        stylesheet.setDefaultEdgeStyle(edgeStyle);
        stylesheet.setDefaultVertexStyle(vertexStyle);
        graph.setStylesheet(stylesheet);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(650, 650);
        setVisible(true);

        for(int i = 0; i < 20; i++){
            edge.add(new ArrayList<>());
            for(int j = 0; j < 20; j++){
                edge.get(i).add(null);
            }
        }
    }

    public void addVertex(int number, int x, int y){
        beginUpdate();
        try{
            //make default style
            vertexStyle.put(mxConstants.STYLE_SHAPE,    mxConstants.SHAPE_CONNECTOR);
            vertexStyle.put(mxConstants.STYLE_STROKECOLOR, "#000000");
            vertexStyle.put(mxConstants.STYLE_FONTCOLOR, "#000000");
            vertexStyle.put(mxConstants.STYLE_LABEL_BACKGROUNDCOLOR, "#ffffff");
            vertex.add(graph.insertVertex(parent, null, Integer.toString(number), x, y, 50, 50,
                    "defaultVertex;fillColor=lightgrey"));
        }
        finally {
            endUpdate();
        }
    }

    public void addEdge(int v1, int v2, int weight, boolean align){ //align=1 => right
        beginUpdate();
        try{
            edgeStyle.put(mxConstants.STYLE_SHAPE,    mxConstants.SHAPE_CONNECTOR);
            edgeStyle.put(mxConstants.STYLE_ENDARROW, mxConstants.ARROW_CLASSIC);
            edgeStyle.put(mxConstants.STYLE_STROKECOLOR, "#000000");
            edgeStyle.put(mxConstants.STYLE_FONTCOLOR, "#000000");
            edgeStyle.put(mxConstants.STYLE_LABEL_BACKGROUNDCOLOR, "#ffffff");

            if(align){
                edgeStyle.put(mxConstants.STYLE_ALIGN, "right");
            } else {
                edgeStyle.put(mxConstants.STYLE_ALIGN, "left");
            }
            edge.get(v1).set(v2, graph.insertEdge(parent, null, Integer.toString(weight), vertex.get(v1), vertex.get(v2)));
        }
        finally {
            endUpdate();
        }
    }

    public void turnOnVertex(int v){

    }

    public void turnOffVertex(int v){

    }

    private void beginUpdate(){
        graph.getModel().beginUpdate();
    }

    private void endUpdate(){
        graph.getModel().endUpdate();
    }

}
