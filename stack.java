import java.util.Stack;

//create a stack class because I use its push and pop with my functions in the run class.
public class stack {
	Stack<Integer> stack = new Stack<Integer>();

//Constructor
	stack(){
		this.count = 0;
		this.store1 = 0;
		this.store2 = 0;
	}

	/**
	 * keeping count of how many elements are popped from the stack
	 */
	 public int count;

	 /**
	  * first integer to be popped from the stack
	  */
		public int store1;

		/**
		 * second integer to be popped from the stack
		 */
		public int store2;

 	/**
	 * pop the stack
	 */
	public void popStack() {
	for(int i = 0; i<2; i++) {

		if(!stack.isEmpty()&&count == 0){
			store1 = stack.pop();
			count+=1;
			}

			else if(!stack.isEmpty()&&count == 1) {
				store2 = stack.pop();
				count+=1;
			}

			else if(count == 0) {

				System.out.println("Stack empty.");
				count = 0;

				return;
			}

			else if(count == 1) {

				System.out.println("Stack underflow.");
				count = 0;

				double numberToBeAddedBackToStack = (double)store1;

				pushstack(numberToBeAddedBackToStack);
				return;
			}
		}
	}


	/**
	 * Displays the stack through the use of a reversed array
	 */
	public void displayStack(){

			Stack<Integer> clone = (Stack<Integer>)stack.clone();
			Stack<Integer> stackLocal = clone;

		int max = 1000;
 		int[]array = new int[max];
 		int numberOfElements = 0;

 		if(stackLocal.isEmpty()) {
 			//prints out the minimum integer if the stack is empty
 			System.out.println(-2147483648);
 			return;
 		}

 		while(!stackLocal.isEmpty()) {
 			array[numberOfElements] = (stackLocal.pop());
 			numberOfElements+=1;
 		}


 		for(int i = numberOfElements-1; i>=0; i--) {
 			System.out.println(array[i]);
 		}

 	}

	 /**
	  * converts value to an integer(truncates the double to avoid wrap around)
	  * then pushes the result to the stack and resets count
	  * @param value
	  */
	public void pushstack(double value){
		int result = (int)value;

		stack.push(result);
		count = 0;

     }

	 /**
	  * checks if there were enough elements popped from the stack before processing an operation
	  * @return
	  * true if enough elements have been popped from the stack, false otherwise
	  */
	public boolean enoughElements() {
		 if(count == 2) {
			 return true;
		 }

		 else {
			 return false;
		 }
	 }
}
