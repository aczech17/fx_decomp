package fx_decomp;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import superdecompressor.wordtree.Node;
import superdecompressor.wordtree.Tree;

public class TreeVisualization extends Application
{

    private static final int WIDTH = 800; // Szerokość okna
    private static final int HEIGHT = 600; // Wysokość okna
    private static final int LEVEL_HEIGHT = 80; // Wysokość poziomu drzewa
    private static final int NODE_RADIUS = 20; // Promień węzła

    private static final Color NODE_COLOR = Color.rgb(0, 150, 0); // Kolor węzłów
    private static final Color LINE_COLOR = Color.BLACK; // Kolor linii

    private superdecompressor.wordtree.Tree tree; // Przykładowe drzewo

    public TreeVisualization(superdecompressor.wordtree.Tree tree)
    {
        this.tree = tree;
    }

    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage)
    {
        primaryStage.setTitle("Drzewo Huffmana");


        Group root = new Group();
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        drawTree(gc, tree.getRoot(), WIDTH / 2, 50, WIDTH / 4);

        root.getChildren().add(canvas);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    private void drawTree(GraphicsContext gc, Node node, double x, double y, double offsetX)
    {
        if (node == null) return;

        gc.setFill(NODE_COLOR);
        gc.fillOval(x - NODE_RADIUS, y - NODE_RADIUS, 2 * NODE_RADIUS, 2 * NODE_RADIUS);

        gc.setStroke(LINE_COLOR);
        if (node.getLeft() != null)
        {
            double childX = x - offsetX;
            double childY = y + LEVEL_HEIGHT;
            gc.strokeLine(x, y, childX, childY);
            drawTree(gc, node.getLeft(), childX, childY, offsetX / 2);
        }
        if (node.getRight() != null)
        {
            double childX = x + offsetX;
            double childY = y + LEVEL_HEIGHT;
            gc.strokeLine(x, y, childX, childY);
            drawTree(gc, node.getRight(), childX, childY, offsetX / 2);
        }
    }

}
