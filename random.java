
	import java.util.ArrayList;
		import java.util.List;


		//taken from from from Adam Jaamour SRPN.java[online], viewed 16/11/18 

		/**
		 * Contains the arrayList in which all the 100 random numbers are stored.
		 *
		 * @author Adam Jaamour
		 * @version 1.0
		 * @release 01/12/2015
		 * @See SRPN.java
		 */

			public class random {

				List<Integer> randomArray;
		    int i = -1; //integer used to push specific element of the arrayList to the stack

		    /**
		     * Constructor. Sets the ArrayList of random numbers.
		     */
		    public random(){
		        randomArray = new ArrayList<Integer>();
		        addSRPNRandom();
		    }

		    /**
		     * returns a random number from they list in the order they were entered. If the end of the list of the
		     * 22 random numbers is reached, then the i is reset to 0 so that the list restarts when r is typed by
		     * the user.
		     *
		     * @return random number.
		     */
		    public Integer listSRPNRandom(){
		        ++i;
		    	if (i == 22){
		            i = 0;
		        }
		        return(randomArray.get(i));
		    }

		    /**
		     * adds the random numbers to the ArrayList.
		     */
		    private void addSRPNRandom(){
		        //SRPNRandom random = new SRPNRandom();

		    	/*I have shortened his code as in Adam's code
		    	 *100 numbers occours at 23 there should be 22 numbers in the list then it will wrap around
		    	 */
		        randomArray.add(1804289383);
		        randomArray.add(846930886);
		        randomArray.add(1681692777);
		        randomArray.add(1714636915);
		        randomArray.add(1957747793);
		        randomArray.add(424238335);
		        randomArray.add(719885386);
		        randomArray.add(1649760492);
		        randomArray.add(596516649);
		        randomArray.add(1189641421);
		        randomArray.add(1025202362);
		        randomArray.add(1350490027);
		        randomArray.add(783368690);
		        randomArray.add(1102520059);
		        randomArray.add(2044897763);
		        randomArray.add(1967513926);
		        randomArray.add(1365180540);
		        randomArray.add(1540383426);
		        randomArray.add(304089172);
		        randomArray.add(1303455736);
		        randomArray.add(35005211);
		        randomArray.add(521595368);

		    }
		}
