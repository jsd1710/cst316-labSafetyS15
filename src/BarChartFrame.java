import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Vector;
import java.util.Hashtable;

@SuppressWarnings("serial")
class BarChartFrame extends Frame
{
	Hashtable<String, Color> colorMap = new Hashtable<String, Color>();

	protected Vector<Integer>	data;
	protected Vector<String>	labels;
	protected Vector<Color>		colors;

	Choice 	colorSelect;
	TextField	labelSelect;
	TextField	dataSelect;

	BarChart chart;

	class BarChartFrameControl extends WindowAdapter implements ActionListener 
	{
		// CST316 #4: This event handler is not safe as it makes an
		// unchecked assumption about the nature of one of the text field
		// entries. To ensure the user cannot enter a value that causes an
	    // exception, APPLY THE STATE PATTERN to ensure an invalid value
	    // never reaches the handler.
	    //
		// CST316 #5: The line which adds an element to the colors vector //DONE
		// assumes the selected item is available in the colorMap. Add
		// an ASSERT that tests that this assumption is always true and
		// if not prints a message indicating "CST316-2 assert failed"
		public void actionPerformed(ActionEvent e)
		{
			if (e.getSource() instanceof Button)
			{
				labels.addElement(labelSelect.getText());
				data.addElement(new Integer(dataSelect.getText()));
				assert(colorMap.get(colorSelect.getSelectedItem()) == null):"CST316-2 assert failed";
				
				colors.addElement(colorMap.get(colorSelect.getSelectedItem()));

				chart.setData(data);
				chart.setColors(colors);
				chart.setLabels(labels);
				chart.repaint();
			}
			else if (e.getSource() instanceof Menu)
			{
				BarChartFrame.this.dispose();
				System.exit(0);		// only option at this time
			}
		}
	}

	public void initData(String fname) throws MyDataInitializationException
	{
		data = new Vector<Integer>();
		labels = new Vector<String>();
		colors = new Vector<Color>();

		colorMap.put("red", Color.red);
		colorMap.put("green", Color.green);
		colorMap.put("blue", Color.blue);
		colorMap.put("magenta", Color.magenta);
		colorMap.put("gray", Color.gray);
		colorMap.put("orange", Color.orange);

		// CST316: This try/catch block is unsafe. Correct the following issues:
		// #1. The method makes assumptions about the existence and contents of the file.
		//     If the file does not exist or uses an invalid format on a line, it should
		//     skip that line.
		// #2. The method assumes the color read from the file is in the color map. Modify //DONE
		//     so that if the color is not in the color map, it gets mapped to the color
		//     "Color.orange", no matter what the color string says.
		// #3. The method does a poor job of exception handling. Catch each specific type //DONE
		//     of checked exception possible within this block, print out a message
		//     indicating the type of exception caught (e.g. "Caught XXX"), and rethrow a 
		//     new exception called MyDataInitializationException (you have to write this).
		try {
			File f = new File(fname);
			if (f.exists() && !f.isDirectory())
			{
				FileReader bridge = new FileReader(fname);
				StreamTokenizer	tokens = new StreamTokenizer(bridge);
				
				while (tokens.nextToken() != StreamTokenizer.TT_EOF) {
					int number = (int) tokens.nval;
					tokens.nextToken();
					String label = tokens.sval;
					tokens.nextToken();
					Color color = null;
					if (colorMap.get(tokens.sval) == null)
					{
						color = Color.orange;
					}
					else
					{
						color = (Color) colorMap.get(tokens.sval);
					}

					data.addElement(new Integer(number));
					labels.addElement(label);
					colors.addElement(color);
				}
			}
			else
			{
				System.out.println("File " + fname + " does not exist!");
			}
			
		}
		catch (Exception e) {e.printStackTrace();}
	}

	public BarChartFrame(String fname) throws MyDataInitializationException {
		BarChartFrameControl control = new BarChartFrameControl();

		initData(fname);

		setSize(350,350);
		setLayout(new BorderLayout());

		MenuBar mb = new MenuBar();
		Menu file = new Menu("File");
		file.addActionListener(control);
		file.add("Exit");
		mb.add(file);
		setMenuBar(mb);

		chart = new BarChart();

		chart.setData(data);
		chart.setLabels(labels);
		chart.setColors(colors);

		Panel components = new Panel();
		components.setSize(350,50);
		components.setLayout(new FlowLayout());

		colorSelect = new Choice();
		colorSelect.add ("red");
		colorSelect.add("green");
		colorSelect.add("blue");
		colorSelect.add("magenta");
		colorSelect.add("gray");
		colorSelect.add("orange");
		components.add(colorSelect);
		labelSelect = new TextField("label", 10);
		components.add(labelSelect);
		dataSelect = new TextField("data", 5);
		components.add(dataSelect);

		Button button = new Button("Add Data");
		button.addActionListener(control);
		components.add(button);

		setBackground(Color.lightGray);
		add(components, "South");
		add(chart, "North");
		chart.repaint();
		setVisible(true);
	}
}
