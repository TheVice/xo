import io.Input;
import io.Output;
import ui.Ui;
import core.Referee;

public class Main {

	public static void main(String... args) {

		new Referee(new Ui(new Input(System.in), new Output(System.out)));
	}

}
