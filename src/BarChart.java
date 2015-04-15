import java.awt.*;
import java.io.File;
import java.util.Vector;

/**
CST316 #6: This class lacks a constructor of any kind. //DONE
Add one that accepts proper initialization parameters.
Question: (answer in this comment): Why is it unsafe to
not have an explicit constructor?
ANSWER: If you do not have a constructor and someone attempts to create an instance of this class, it could result in uninitialized variables.
 */
@SuppressWarnings("serial")
class BarChart extends Panel
{			   
	private int	barWidth = 20;

	private Vector<Integer>	data;
	private Vector<String>	dataLabels;
	private Vector<Color>	dataColors;
	
	BarChart()
	{
		barWidth = 20;
		data = new Vector<Integer>();
		dataLabels = new Vector<String>();
		dataColors = new Vector<Color>();
	}
	
	BarChart(int barWidthInp, Vector<Integer> dataInp, Vector<String> dataLabelsInp, Vector<Color> dataColorsInp)
	{
		barWidth = barWidthInp;
		data = dataInp;
		dataLabels = dataLabelsInp;
		dataColors = dataColorsInp;
	}

	/**
       CST316 #7: What can go wrong in this paint method? //DONE
       Refactor this method and the class to ensure the Image duke
       is loaded properly. If it is not loaded properly then invalidate
       painting the image object (Defensive Programming: robustness).
       CST316 #8: Design By Contract - what is the assumed precondition //DONE
       of the Vectors in use in the for loop? Add an assert statement
       (or two) to check the precondition.
       CST316 #9: Now apply the Null Object refactoring to ensure there //DONE
       is no way a color can be set to NULL. Make the default color "white".
	 */
	public void paint(Graphics g)
	{
		setSize(200,250);
		File f = new File("duke2.gif");
		if (f.exists())
		{
			Image duke = Toolkit.getDefaultToolkit().getImage("duke2.gif");
			g.drawImage(duke, 80, 10, this);
		}
		assert(data.size() != 0);
		for (int i = 0; i < data.size(); i++)
		{				  
			int yposition = 100+i*barWidth;
			assert(barWidth > 0):"The width of the bars are not visible.";

			if (dataColors.elementAt(i) == null)
			{
				g.setColor(Color.WHITE);
			}
			else
			{
				g.setColor(dataColors.elementAt(i));
			}
			
			int barLength = (data.elementAt(i)).intValue();
			g.fillOval(100, yposition, barLength, barWidth);

			g.setColor(Color.black);
			g.drawString(dataLabels.elementAt(i), 20, yposition+10);
		}
	}

	/*
      CST316 #10: Consider the set methods below, how they are called, and
      the problem posed by issue #9 above. Refactor the code to create a
      "safe zone" that always ensures that the condition you coded in the
      assert for #9 is enforced (thereby rendering that assert unneeded).
	 */
	public void setData(Vector<Integer> dataValues)
	{
		assert(dataValues.size() != 0);
		data = dataValues;
	}

	public void setLabels(Vector<String> labels)
	{
		assert(labels.size() != 0);
		dataLabels = labels;
	}

	public void setColors(Vector<Color> colors)
	{
		assert(colors.size() != 0);
		dataColors = colors;
	}
}
