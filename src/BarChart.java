import java.awt.*;
import java.util.Vector;

/**
CST316 #6: This class lacks a constructor of any kind. 
Add one that accepts proper initialization parameters.
Question: (answer in this comment): Why is it unsafe to
not have an explicit constructor?
 */
@SuppressWarnings("serial")
class BarChart extends Panel
{			   
	private int	barWidth = 20;

	private Vector<Integer>	data;
	private Vector<String>	dataLabels;
	private Vector<Color>	dataColors;

	/**
       CST316 #7: What can go wrong in this paint method?
       Refactor this method and the class to ensure the Image duke
       is loaded properly. If it is not loaded properly then invalidate
       painting the image object (Defensive Programming: robustness).
       CST316 #8: Design By Contract - what is the assumed precondition
       of the Vectors in use in the for loop? Add an assert statement
       (or two) to check the precondition.
       CST316 #9: Now apply the Null Object refactoring to ensure there
       is no way a color can be set to NULL. Make the default color "white".
	 */
	public void paint(Graphics g)
	{
		setSize(200,250);
		Image duke = Toolkit.getDefaultToolkit().getImage("duke2.gif");
		g.drawImage(duke, 80, 10, this);

		for (int i = 0; i < data.size(); i++)
		{				  
			int yposition = 100+i*barWidth;

			g.setColor(dataColors.elementAt(i));

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
		data = dataValues;
	}

	public void setLabels(Vector<String> labels)
	{
		dataLabels = labels;
	}

	public void setColors(Vector<Color> colors)
	{
		dataColors = colors;
	}
}
