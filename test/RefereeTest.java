import org.junit.Test;
import org.junit.Assert;

import io.Input;
import io.Output;
import ui.Ui;
import core.Referee;
import core.Field;

public class RefereeTest extends Assert {

	@Test
	public void enterToReferee() {

		final String lineSeparator = System.getProperty("line.separator");

		StringBuilder sbCommands = new StringBuilder(1024);

		{
			sbCommands.append("Classic" + lineSeparator);

			sbCommands.append("Human" + lineSeparator);
			sbCommands.append("Human" + lineSeparator);

			sbCommands.append("1" + lineSeparator + "1" + lineSeparator);
			sbCommands.append("1" + lineSeparator + "3" + lineSeparator);
			sbCommands.append("3" + lineSeparator + "1" + lineSeparator);
			sbCommands.append("3" + lineSeparator + "3" + lineSeparator);
			sbCommands.append("3" + lineSeparator + "2" + lineSeparator);
			sbCommands.append("2" + lineSeparator + "1" + lineSeparator);
			sbCommands.append("2" + lineSeparator + "3" + lineSeparator);
			sbCommands.append("1" + lineSeparator + "2" + lineSeparator);
			sbCommands.append("2" + lineSeparator + "2" + lineSeparator);

			sbCommands.append("y" + lineSeparator);
		}
		{
			sbCommands.append("Custom" + lineSeparator);

			sbCommands.append("2" + lineSeparator);
			sbCommands.append("A" + lineSeparator + "Human" + lineSeparator);
			sbCommands.append("B" + lineSeparator + "Human" + lineSeparator);
			sbCommands.append("3" + lineSeparator + "3" + lineSeparator + " "
					+ lineSeparator + "Bottom Left" + lineSeparator);

			sbCommands.append("1" + lineSeparator + "1" + lineSeparator);
			sbCommands.append("1" + lineSeparator + "3" + lineSeparator);
			sbCommands.append("3" + lineSeparator + "1" + lineSeparator);
			sbCommands.append("3" + lineSeparator + "3" + lineSeparator);
			sbCommands.append("3" + lineSeparator + "2" + lineSeparator);
			sbCommands.append("2" + lineSeparator + "1" + lineSeparator);
			sbCommands.append("2" + lineSeparator + "3" + lineSeparator);
			sbCommands.append("1" + lineSeparator + "2" + lineSeparator);
			sbCommands.append("2" + lineSeparator + "2" + lineSeparator);

			sbCommands.append("y" + lineSeparator);
		}
		{
			sbCommands.append("Custom" + lineSeparator);

			sbCommands.append("2" + lineSeparator);
			sbCommands.append("A" + lineSeparator + "Human" + lineSeparator);
			sbCommands.append("B" + lineSeparator + "Human" + lineSeparator);
			sbCommands.append("3" + lineSeparator + "3" + lineSeparator + "S"
					+ lineSeparator + "Bottom Left" + lineSeparator);

			sbCommands.append("1" + lineSeparator + "1" + lineSeparator);
			sbCommands.append("1" + lineSeparator + "3" + lineSeparator);
			sbCommands.append("3" + lineSeparator + "1" + lineSeparator);
			sbCommands.append("3" + lineSeparator + "3" + lineSeparator);
			sbCommands.append("3" + lineSeparator + "2" + lineSeparator);
			sbCommands.append("2" + lineSeparator + "1" + lineSeparator);
			sbCommands.append("2" + lineSeparator + "3" + lineSeparator);
			sbCommands.append("1" + lineSeparator + "2" + lineSeparator);
			sbCommands.append("2" + lineSeparator + "2" + lineSeparator);

			sbCommands.append("y" + lineSeparator);
		}
		{
			sbCommands.append("Classic" + lineSeparator);

			sbCommands
					.append("Human" + lineSeparator + "Human" + lineSeparator);
			sbCommands.append("step" + lineSeparator);
			sbCommands.append("-1" + lineSeparator);
			sbCommands.append("7" + lineSeparator);
			sbCommands.append("0" + lineSeparator);
			sbCommands.append("1" + lineSeparator + "2" + lineSeparator);
			sbCommands.append("1" + lineSeparator);
			sbCommands.append("step" + lineSeparator);
			sbCommands.append("-2" + lineSeparator);
			sbCommands.append("8" + lineSeparator);
			sbCommands.append("0" + lineSeparator);
			sbCommands.append("2" + lineSeparator);
			sbCommands.append("exit" + lineSeparator);

			sbCommands.append("y" + lineSeparator);
		}
		{
			sbCommands.append("Classic" + lineSeparator);

			sbCommands
					.append("Human" + lineSeparator + "Human" + lineSeparator);
			sbCommands.append("2" + lineSeparator + "2" + lineSeparator);
			sbCommands.append("A" + lineSeparator + "2" + lineSeparator);
			sbCommands.append("2" + lineSeparator + "A" + lineSeparator);
			sbCommands.append("4" + lineSeparator + "4" + lineSeparator);
			sbCommands.append("2" + lineSeparator + "3" + lineSeparator);
			sbCommands.append("1" + lineSeparator + "1" + lineSeparator);
			sbCommands.append("1" + lineSeparator + "3" + lineSeparator);
			sbCommands.append("3" + lineSeparator + "1" + lineSeparator);
			sbCommands.append("step" + lineSeparator + "4" + lineSeparator);
			sbCommands.append("3" + lineSeparator + "3" + lineSeparator);

			sbCommands.append("y" + lineSeparator);
		}
		{
			sbCommands.append("Classic" + lineSeparator);

			sbCommands
					.append("Human" + lineSeparator + "Human" + lineSeparator);
			sbCommands.append("1" + lineSeparator + "1" + lineSeparator);
			sbCommands.append("2" + lineSeparator + "2" + lineSeparator);
			sbCommands.append("1" + lineSeparator + "3" + lineSeparator);
			sbCommands.append("3" + lineSeparator + "2" + lineSeparator);
			sbCommands.append("1" + lineSeparator + "2" + lineSeparator);

			sbCommands.append("y" + lineSeparator);
		}
		{
			sbCommands.append("Custom" + lineSeparator);

			sbCommands.append("-1" + lineSeparator);
			sbCommands.append("0" + lineSeparator);
			sbCommands.append(""
					+ ((Field.getMaxWidth() + 1) * (Field.getMaxHeight() + 2))
					+ lineSeparator);
			sbCommands.append("A" + lineSeparator);

			sbCommands.append("2" + lineSeparator);
			sbCommands.append("A" + lineSeparator + "Human" + lineSeparator);
			sbCommands.append("A" + lineSeparator);
			sbCommands.append("B" + lineSeparator);
			sbCommands.append("Human" + lineSeparator);

			sbCommands.append("" + (Field.getMinWidth() - 1) + lineSeparator);
			sbCommands.append("" + (Field.getMaxWidth() + 1) + lineSeparator);
			sbCommands.append("4" + lineSeparator);
			sbCommands.append("" + (Field.getMinHeight() - 2) + lineSeparator);
			sbCommands.append("" + (Field.getMaxHeight() + 2) + lineSeparator);
			sbCommands.append("5" + lineSeparator);

			sbCommands.append("A" + lineSeparator);
			sbCommands.append("B" + lineSeparator);
			sbCommands.append(" " + lineSeparator);
			sbCommands.append("Bottom Left" + lineSeparator);

			sbCommands.append("exit" + lineSeparator);

			sbCommands.append("y" + lineSeparator);
		}
		{
			sbCommands.append("Classic" + lineSeparator);

			sbCommands.append("Human" + lineSeparator);
			sbCommands.append("AI" + lineSeparator);
			sbCommands.append("exit" + lineSeparator);

			sbCommands.append("y" + lineSeparator);
		}
		{
			sbCommands.append("Classic" + lineSeparator);

			sbCommands.append("AI" + lineSeparator);
			sbCommands.append("AI" + lineSeparator);

			sbCommands.append("n" + lineSeparator);
		}

		Input input = InputTest.getByteInputInstance(sbCommands.toString()
				.getBytes());
		Output output = OutputTest.getByteArrayOutputInstance(1024);

		new Referee(new Ui(input, output));

		// System.out.print(output);
	}

}
