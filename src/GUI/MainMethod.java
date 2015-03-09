package GUI;
import javax.swing.JFrame;

public class MainMethod extends JFrame {
	public static final int WIDTH = 720;
	public static final int HEIGHT = 480;
    public MainMethod() {
        add(new Board());

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setTitle("Collision");
        setResizable(false);
        setVisible(true);
    }

    public static void main(String[] args) {
        new MainMethod();
    }
}