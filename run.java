import java.util.Stack;

//This class is responsible for storing the method capable of reading input

 public class run {

	//constructor
	run(){
	srpnStack = new stack();
	randi = new random();
	Instructions = "";
	flag = false;
	octalFlag=false;
  	hash="";

	}

	private stack srpnStack;
	private random randi;
	private String Instructions;
	private String hash;

	/*A flag to determine if hash has been activated or not*/
	private boolean flag;
	private boolean octalFlag;

	/**
	 * result must be a double to avoid wrap around as a double stores more bits than an int
	 */
	private double result;

	/**
	 * A command that checks the string input and iterates through the characters of the string and
	 * decides what operations to pe performed on the characters
	 * @param s
	 * the string being passed in
	 */

	public void processCommand(String s) {

		for(int j=0; j<s.length(); j++) {


			if(s.length()==0) {
				System.out.println();
			}

			if (s.charAt(j) == '='&&flag == false) {
				//clones the stack because I don't want to pop elements from the real stack
				Stack<Integer> clone2 = (Stack<Integer>)srpnStack.stack.clone();
				Stack<Integer> clone = clone2;
				Stack<Integer> stackLocal = clone;

				if(!stackLocal.isEmpty()){
					System.out.println(stackLocal.pop());
				}

        else {
					System.out.println("Stack empty.");
				}
			}

			else if ((s.charAt(j) == '+')&&flag == false) {
				srpnStack.popStack();

				if(srpnStack.enoughElements()) {
					result = (double)srpnStack.store2+(double)srpnStack.store1;
					srpnStack.pushstack(result);
					resetResult();
				}

			}

			//This is to change between an octal number including negative octal numbers
			else if((s.charAt(j) == '0'&&flag == false&&j!=s.length()-1&&storesNumber(s.charAt(j+1))&&(Instructions.equals("-")||Instructions.equals("")))) {
				octalFlag = true;
			}


			else if (s.charAt(j) == '-'&&flag == false) {

        /*add the negative sign to the instructions to enable the program to distinguish between negative numbers
				 * and the negative operator
				 */
				Instructions = Instructions+"-";

				if(j == s.length()-1) {
					srpnStack.popStack();

					if(srpnStack.enoughElements()) {
						result = (double)srpnStack.store2-(double)srpnStack.store1;

						srpnStack.pushstack(result);
						resetString();
						resetResult();
					}
				}
			}


			else if (s.charAt(j) == '*'&&flag == false) {
				srpnStack.popStack();

				if(srpnStack.enoughElements()) {
					result = (double)srpnStack.store2*(double)srpnStack.store1;
					srpnStack.pushstack(result);
					resetResult();
				}
			}


			else if (s.charAt(j) == '%'&&flag == false) {
				srpnStack.popStack();

				if(srpnStack.enoughElements()) {
					result = (double)srpnStack.store2%(double)srpnStack.store1;
					srpnStack.pushstack(result);
					resetResult();
				}
			}

			else if (s.charAt(j) == '/'&&flag==false) {
				srpnStack.popStack();

				if(srpnStack.enoughElements()) {
					//in the instance that a number was divided by 0 push the popped numbers back into the stack and continue
					if(srpnStack.store1 == 0) {
						System.out.println("Divide by 0.");
						srpnStack.count = 0;
						srpnStack.pushstack((double)srpnStack.store2);
						srpnStack.pushstack((double)srpnStack.store1);
						continue;
					}
					//store in double to perform saturation. A double holds more bits than int therefore higher value
					 result = (double)srpnStack.store2/(double)srpnStack.store1;
					 srpnStack.pushstack(result);
					 resetResult();
				}
			}

			else if (s.charAt(j) == '^'&&flag == false) {
				srpnStack.popStack();

				if(srpnStack.enoughElements()) {
					//in the instance that a number has a negative power, push the popped numbers back into the stack and continue
					if(srpnStack.store1<0) {
						System.out.println("Negative power.");
						srpnStack.pushstack((double)srpnStack.store2);
						srpnStack.pushstack((double)srpnStack.store1);
						continue;
					}
					result = Math.pow((double)srpnStack.store2,(double)srpnStack.store1);
					srpnStack.pushstack(result);
					resetResult();
				}
			}


			//if a 'd' is received display the stack
			else if(s.charAt(j) == 'd'&&flag == false) {
				srpnStack.displayStack();
			}

			// if there is a space in-between characters interpret the instructions
			else if(s.charAt(j) == ' '&&flag == false){

				if(Instructions.equals("")) {
					continue;
				}

				else if(Instructions.equals("-")) {
					srpnStack.popStack();

					if(srpnStack.enoughElements()) {
						result = (double)srpnStack.store2-(double)srpnStack.store1;
						srpnStack.pushstack(result);
						resetString();
						resetResult();
					}
				}

				else {
					readingInput(Instructions);
				}
			}



			/*if an 'r' is received calculate the random number using the random class and push the result to the stack.
			 * This random functionality was taken from  Adam Jaamour, 01/12/2015. Random.java[online], from https://github.com/Adamouization/SRPN
			 * accessed 18/11/18
			 */

			else if(s.charAt(j) == 'r'&&flag == false) {

				double randomNumberGen = (double)randi.listSRPNRandom();
				boolean stackOverflow = checkStackOverflow(srpnStack);

				if(!stackOverflow) {
					srpnStack.pushstack(randomNumberGen);
				}

				else
					System.err.println("Stack overflow.");
			}

			//if a hash is received if the conditions inside are met ignore characters after the hash if the flag is false
			else if(s.charAt(j) == '#') {
				hash=hash+"#";

				//if the character before the hash was a space change the flag
				if(j!=0&&s.charAt(j-1) == ' '){
					if(flag==true){
						flag=false;
					}

					else {
						flag=true;
					}
				}

				//if the character after the hash was a space change the flag
				else if(j != s.length()-1&&s.charAt(j+1) == ' '){

					if(flag == true){
						flag = false;
					}

					else {
						flag = true;
					}
				}

				//if the only character in the line is a hash change the flag
				else if(j == s.length()-1&&hash.equals("#")) {

					if(flag == true){
						flag = false;
						hash = "";
					}

					else {
						flag = true;
						hash = "";
					}
				}
				else {System.out.println("Unrecognised operator or operand "+"\""+s.charAt(j) + "\""+".");}

			}

			/*if the character stores a number add that number to the instructions
			 * only read the instructions if it is the only thing in the line otherwise continue
			 * as the instructions will be read when there is a space
			 */
			else if(storesNumber(s.charAt(j))&&flag == false) {
				String charAsString = Character.toString(s.charAt(j)) ;
				Instructions = Instructions+charAsString;

				if(j == s.length()-1) {
					readingInput(Instructions);
				}
			}


			else {
				if(flag == false) {
					System.out.println("Unrecognised operator or operand "+"\""+s.charAt(j) + "\""+".");
				}

			}
		}
		//reset the hash string after each line
		hash = "";
	}


	/**
	 * determines if the character is received is a number or not
	 * @param number
	 * 	The character being read
	 * @return
	 * 	true if the character is a number, false otherwise
	 */
	private boolean storesNumber(char number) {
		switch(number) {
			case '1': return true;
			case '2': return true;
			case '3': return true;
			case '4': return true;
			case '5': return true;
			case '6': return true;
			case '7': return true;
			case '8': return true;
			case '9': return true;
			case '0': return true;
			default: return false;
		}
	}

	void printUnderflow() {
		System.out.println("Stack underflow.");
	}

  /**
	 * resets the instruction string
	 */
	void resetString(){
		Instructions = "";
	}

	/**
	 * resets the result of an operation after it has been pused to the stack
	 */
	void resetResult() {
		result = 0;
	}

	/**
	 * Takes in the instruction and converts it to an integer and pushes to the stack if the stack isn't full
	 * @param instructionreading
	 * A string that could be an octal number depending on the flag.
	 * if the length of the number of non-zero digits is greater than 21
	 */
	public void readingInput(String instructionreading){
		boolean stackOverflow = checkStackOverflow(srpnStack);

		if(!stackOverflow) {
			if(octalFlag!=true) {

			double inputNumber = Double.parseDouble(Instructions);
			srpnStack.pushstack(inputNumber);
			resetString();
			}

			else {
        /*lenght of octal string can't be over 22 because it will cause an error because 0 isn't read
        Therefore there the lenght cant exceed 21*/

        if(Instructions.length()>21&&!Instructions.contains("8")&&!Instructions.contains("9")) {
					Instructions="-1";
					Long inputNumber =  Long.parseLong(Instructions);
					double octalNum = (double)inputNumber;
					srpnStack.pushstack(octalNum);
					resetString();
					octalFlag=false;
				}

				else if(!Instructions.contains("8")&&!Instructions.contains("9")) {
					Long inputNumber =  Long.parseLong(Instructions,8);
					double octalNum = (double)inputNumber;
					srpnStack.pushstack(octalNum);
					resetString();
					octalFlag=false;
					}
        
				else {
					double inputNumber = Double.parseDouble(Instructions);
					srpnStack.pushstack(inputNumber);
					resetString();
				}
				
			}
		}

		else
		System.err.println("Stack overflow.");
		resetString();
	}

	/**
	 * checks whether the stack is full or not
	 * @param srpnStack
	 * @return
	 * true if it is full and false otherwise
	 */
	private boolean checkStackOverflow(stack srpnStack){
		if(srpnStack.stack.size()>=23) {
			return true;
		}

		else{
			return false;
		}
	}
}