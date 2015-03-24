package brainfInput;

import java.util.Scanner;

public class brainfInput {

	public static void main(String[] args) {
		Scanner reader = new Scanner(System.in);
		System.out.println("input the line of code: ");
		String input=reader.nextLine();
		Interpreter interpreter = new Interpreter(input);
		interpreter.setCode();
		interpreter.createCellList();
		interpreter.interpret();
		System.out.println("\n---Interpreter done---");
		reader.close();
	}

}
