package brainfInput;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Interpreter {
	String codeLine = "";
	ArrayList<CodeChar> code = new ArrayList<CodeChar>();
	ArrayList<Integer> cellList = new ArrayList<Integer>();
	String validChars = "+-[]<>,.";
	int currentCell = 0;
	int currentChar=0;
	ArrayList<Integer> loopList = new ArrayList<Integer>();
	
	public Interpreter(String input) 
	{
		codeLine = input;
		loopList.add(0);
	}
	
	public void setCode() 
	{
		for(int k=0;k<codeLine.length();k++){
			if(validChars.contains(Character.toString(codeLine.charAt(k)))){
				code.add(new CodeChar(codeLine.charAt(k)));
			}
		}
		String listString = code.stream().map(Object::toString).collect(Collectors.joining(""));
		System.out.println("code inputted:\n" + listString);
		System.out.println("code output: ");
	}

	public void createCellList() 
	{
		int k = 0;
		while(k<256)
		{
			cellList.add(0);
			k++;
		}
	}
	
	public void interpret() 
	{
		for(int k=0;k<code.size();k++)
		{
			switch(code.get(k).code)
			{
				case ',':comma(); break;
				case '.':period(); break;
				case '+':plus(); break;
				case '-':minus(); break;
				case '>':forward(); break;
				case '<':back(); break;
				case '[':k=open(k); break;
				case ']':k=close(k); break;
				default: break;
			}
		}
	}
	
	//takes in input for the current cell
	public void comma(){
		@SuppressWarnings("resource")
		Scanner reader = new Scanner(System.in);
		System.out.println("input the character: ");
		char input=reader.nextLine().charAt(0);
		cellList.set(currentCell, Character.getNumericValue(input));
	}
	
	//outputs the character of the current cell
	public void period(){
		if(cellList.get(currentCell)>9)
			System.out.print(Character.toChars(cellList.get(currentCell)));
		else
			System.out.println(cellList.get(currentCell));
	}
	
	//increases the current cell's contained number by one
	public void plus(){
		if(cellList.get(currentCell)<1023)
			cellList.set(currentCell, cellList.get(currentCell)+1);
		else
			cellList.set(currentCell, -1024);
	}
	
	//reduces the current cell's contained number by one
	public void minus(){
		if(cellList.get(currentCell)>-1024)
			cellList.set(currentCell, cellList.get(currentCell)-1);
		else
			cellList.set(currentCell, 1023);
	}

	//moves forward one cell
	public void forward(){
		currentCell++;
	}

	//moves back one cell
	public void back(){
		currentCell--;
	}
	
	//starts a loop.
	public int open(int k){
		if(cellList.get(currentCell)!=0)
			loopList.add(k);
		else
			return jump(k);
		return k;
	}

	//end of that loop. if the number in the cell isn't 0, restart loop.
	public int close(int k){
		//if the current cell is 0, delete the current loop from the list and move on.
		if(cellList.get(currentCell)==0){
			loopList.remove(loopList.size()-1);
			return k;
		}
		//else, return to the beginning of the loop
		int newK = k;
		newK=loopList.get(loopList.size()-1);
		//ends up going to the line right after the last open bracket.
		return newK;
	}
	
	public int jump(int k){
		int newK = k;
		for(int z=k;z<code.size();z++)
		{
			if(code.get(k).code==']'){
				newK=z;
				break;
			}
		}
		return newK;
	}
	
	//pretty much just a char, but works with the stream concatenator.
	public class CodeChar
	{
		char code = ' ';
		public CodeChar(char codeChar)
		{
			code = codeChar;
		}
		
		public String toString() {
			return Character.toString(code);
		}
	}
}
