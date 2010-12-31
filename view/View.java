package view;

public class View {

	/**
	 * The view object.
	 */
	private static View view = new View();

	/**
	 * The window that shows the graph.
	 */
	private Window window;

	public View() {
		window = new Window("Base stations model");
		window.setLocationRelativeTo( null );
		window.pack();
		window.setVisible(true);
	}

	/**
	 * Get the view object.
	 * @return The view object.
	 */
	public static View getView() {
		return view;
	}

	/**
	 * Initialize the layout.
	 * This command should be called at the first time to start initializing the layout.
	 */
	public void initialize() {}
}