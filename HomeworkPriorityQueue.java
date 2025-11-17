/**
 * A class for maintaining a priority queue of homework
 *
 * @author  Kailey Pierce
 * @version for Data Structures
 *
 */

import java.util.Scanner;
import java.util.ArrayList;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Locale;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class HomeworkPriorityQueue {

    public static final String DATAFILE= "homeworklist.txt";
	public static final int MAXHW= 10000;

    public static void main(String[] args) {
        Homework [] homework = new Homework[MAXHW];
		int numberOfEntries = loadHomework(homework);
        quickSort(homework, 0, numberOfEntries - 1);
        int choice;

		System.out.println("Number of entries read from data file: "+numberOfEntries);
		do { 
			choice= getMenuChoice();
			if (choice==1)
				numberOfEntries = enterHomework(homework, numberOfEntries);
			else if (choice==2)
				numberOfEntries = deleteHomework(homework, numberOfEntries);
			else if (choice==3)
				displayAll(homework, numberOfEntries);
			else if (choice==4)
				searchByClass(homework, numberOfEntries);
			else if (choice==5)
				searchByDueDate(homework, numberOfEntries);
            else if (choice==6)
                displayUpcoming(homework, numberOfEntries);
		} while (choice!=0);
		System.out.println("\nEXITING");
     }

    /**
	 * Displays menu and get's user's selection
	 *
	 * @return the user's menu selection
	*/
	public static int getMenuChoice() {
		Scanner kb= new Scanner(System.in);
		int choice;	 // user's selection

		System.out.println("\n\n");
		System.out.print("------------------------------------\n");
		System.out.print("[1] New homework\n");
		System.out.print("[2] Finished homework\n");
		System.out.print("[3] List all homework\n");
		System.out.print("[4] Search by class\n");
		System.out.print("[5] Search by due date\n");
        System.out.print("[6] List upcoming homework\n");
		System.out.print("---\n");
		System.out.print("[0] Exit Program\n");
		System.out.print("------------------------------------\n");
		do {
			System.out.print("Your choice: ");
			choice= kb.nextInt();
		} while (choice < 0 || choice > 6);

		return choice;
	}

    /**
	* Load homework list from the data file into the array
	*
	* @param homework an array to hold the list of homeworks
	* @return the number of homeworks loaded
	*/
	public static int loadHomework(Homework[] homework){
		int k = 0;
		try{
			Scanner hwFile = new Scanner(new FileInputStream(DATAFILE));
			while (hwFile.hasNextLine()){
				Homework hw = new Homework();     // Create a new Movie object

				hw.setName(hwFile.nextLine());
				hw.setClassroom(hwFile.nextLine());
				hw.setDueDate(hwFile.nextLine());

				hw.setId(k);

				homework[k] = hw;
				k = k +1;
			}
		}
		catch (FileNotFoundException e){
			System.out.println(e);
		}
		return k;
	}

    /**
     * Quick sort algorithm that sorts a Homework array by date
     * 
     * @param array the homework array
     * @param low the low value of the array
     * @param high the high value of the array
     */
    public static void quickSort(Homework[] array, int low, int high) {
        if(low<high){
            int p=partition(array, low, high);
            quickSort(array, low, p-1);
            quickSort(array, p+1, high);
        }
    }

    /**
     * Partition portion of the Quick sort algorithm
     * 
     * @param array the homework array
     * @param low the low value of the array
     * @param high the high value of the array
     */
    public static int partition(Homework[] array, int low, int high){
        Homework temp;
        Homework pivot = array[high];
        int i = low - 1;

        for(int j = low; j < high; j++){
            if (array[j].getDueDate().isBefore(pivot.getDueDate())) {
                i++;
                temp = array[i];
                array[i] = array[j];
                array[j] = temp;
            }
        }
        temp = array[i+1];
        array[i+1] = array[high];
        array[high] = temp;
        return i + 1;
    }

	/**
	 * Allow user to enter a new homework assignment.
	 *
	 * @param homework an array to hold the list of homeworks
	 * @return the new homework count
	*/
    public static int enterHomework(Homework [] homework, int n)
	{
		Scanner kb= new Scanner(System.in);
		Homework hw = new Homework();     // Create a new Homework object
		System.out.print("Enter the homework name: ");
		hw.setName(kb.nextLine());
		System.out.print("Enter the class that the homework was assigned in: ");
		hw.setClassroom(kb.nextLine());
		System.out.print("Enter the date the homework is due (mm/dd/yyyy): ");
		hw.setDueDate(kb.nextLine());

		homework[n] = hw;
        quickSort(homework, 0, n);
		return n+1;
	}

    /**
	 * Displays all homework information.
	 *
	 * @param homework array of homework information
	 * @param n the actual number of homework assignments currently in the array
	*/
	public static void displayAll(Homework [] homework, int n) {
		System.out.println("------------------------------------------------------------------------------------------------");
		System.out.printf("%-40s %-30s %-20s %-10s\n","NAME","CLASS","DUE DATE","ID");
        System.out.println("------------------------------------------------------------------------------------------------");
		for (int i=0; i<n; i++)
			System.out.println(homework[i]);
	}

    /**
	 * Searches the list of homeworks by due date
	 *
	 * @param homework an array to hold the list of homework
	 * @param n the actual number of homework assignments currently in the array
	*/
	public static void searchByDueDate(Homework [] homework, int n)
	{
		String dueDate;   //allow user to search movies by year
		Scanner kb = new Scanner(System.in);
		System.out.print("Enter a due date (mm/dd/yyyy): ");
		dueDate = kb.nextLine();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		LocalDate searchDate = LocalDate.parse(dueDate, formatter);

		System.out.println("------------------------------------------------------------------------------------------------");
		System.out.printf("%-40s %-30s %-20s %-10s\n","NAME","CLASS","DUE DATE","ID");
        System.out.println("------------------------------------------------------------------------------------------------");
		for (int i=0; i<n; i++){
			if (homework[i].getDueDate().isEqual(searchDate)){
				System.out.println(homework[i]);
			}
		}
	}

    /**
	 * Searches the list of homeworks by class name
	 *
	 * @param homework an array to hold the list of homework
	 * @param n the actual number of homework assignments currently in the array
	*/
    public static void searchByClass(Homework [] homework, int n) {
		String searchClass;   //allow user to search homework assignments by class name
		Scanner kb = new Scanner(System.in);
		System.out.print("Enter a class name: ");
		searchClass = kb.nextLine();
		String lowerCaseName = searchClass.toLowerCase();     //making the user input lower case
		
		System.out.println("------------------------------------------------------------------------------------------------");
		System.out.printf("%-40s %-30s %-20s %-10s\n","NAME","CLASS","DUE DATE","ID");
        System.out.println("------------------------------------------------------------------------------------------------");
		for (int i=0; i<n; i++){
            if (homework[i] != null) {
			    String lowerCaseNameList = homework[i].getName().toLowerCase();    //making the current title lower case
			    if (lowerCaseNameList.contains(lowerCaseName)){
				    System.out.println(homework[i]);
                }
            }
		}
	}

    /**
	 * Searches the list of homeworks by name and allows the user to delete one
	 *
	 * @param homework array of homework information
	 * @param n the actual number of homework assignments currently in the array
	*/
	public static int deleteHomework(Homework [] homework, int n) {
        searchByName(homework, n);
        Scanner kb = new Scanner(System.in);
        System.out.print("Enter the id of the homework you have finished: ");
        int deleteId = kb.nextInt();
        
        int indexToDelete = -1;
        for (int i = 0; i < n; i++) {
            if (homework[i].getId() == deleteId) {
                indexToDelete = i;
                break;
            }
        }

        if (indexToDelete == -1) {
            System.out.println("Homework with that ID not found.");
            return n;
        }

        for (int i = indexToDelete; i < n - 1; i++) {
            homework[i] = homework[i + 1];
            homework[i].setId(i);
        }

        homework[n - 1] = null;
        n--; 

        quickSort(homework, 0, n - 1);

        return n;
    }

    /**
     * helper method for the delet method
     */
    public static void searchByName(Homework [] homework, int n)
	{
		String name;
		Scanner kb = new Scanner(System.in);
		System.out.print("Enter the homework name: ");
		name = kb.nextLine();
		String lowerCaseName = name.toLowerCase();     //making the user input lower case

		System.out.println("------------------------------------------------------------------------------------------------");
		System.out.printf("%-40s %-30s %-20s %-10s\n","NAME","CLASS","DUE DATE","ID");
        System.out.println("------------------------------------------------------------------------------------------------");
		for (int i=0; i<n; i++){
            if (homework[i] != null) {
			    String lowerCaseNameList = homework[i].getName().toLowerCase();    //making the current title lower case
			    if (lowerCaseNameList.contains(lowerCaseName)){
				    System.out.println(homework[i]);
                }
            }
		}
	}

    /**
	 * Displays upcoming homework information.
	 *
	 * @param homework array of homework information
	 * @param n the actual number of homework assignments currently in the array
	*/
	public static void displayUpcoming(Homework [] homework, int n) {
        LocalDate today = LocalDate.now();
		System.out.println("------------------------------------------------------------------------------------------------");
		System.out.printf("%-40s %-30s %-20s %-10s\n","NAME","CLASS","DUE DATE","ID");
        System.out.println("------------------------------------------------------------------------------------------------");
		for (int i=0; i<n; i++){
            if (homework[i] != null) {
                LocalDate due = homework[i].getDueDate();

                if(due.isAfter(today)) {
				    System.out.println(homework[i]);
                }
            }
		}
	}
}