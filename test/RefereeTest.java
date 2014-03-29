
import org.junit.Test;
import org.junit.Assert;

import io.Input;
import io.Output;

import ui.Ui;
import core.Referee;

public class RefereeTest extends Assert {

	@Test
	public void enterToReferee() {

		final String lineSeparator = System.getProperty("line.separator");

		StringBuilder sbCommands = new StringBuilder(1024);
		sbCommands.append("Classic" + lineSeparator);

		sbCommands.append("AI" + lineSeparator);
		sbCommands.append("AI" + lineSeparator);

		sbCommands.append("y" + lineSeparator);
		sbCommands.append("Classic" + lineSeparator);

		sbCommands.append("AI" + lineSeparator);
		sbCommands.append("AI" + lineSeparator);

		sbCommands.append("n" + lineSeparator);

		Input input = InputTest.getByteInputInstance(sbCommands.toString());
		Output output = OutputTest.getByteArrayOutputInstance(1024);

		new Referee(new Ui(input, output));

		// System.out.print(output);
	}

}
