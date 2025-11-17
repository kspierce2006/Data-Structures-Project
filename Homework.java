/**
 * A class for a homework object
 *
 * @author  Kailey Pierce
 * @version for Data Structures
 *
 */

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Homework {
    private String name;
	private String classroom;
    private LocalDate dueDate;
	private int id;

	public Homework(){
		this.name = "";
		this.classroom = "";
		this.dueDate = null;
		this.id = 0;
	}

    /**
 	* Sets all homework information.
 	*
 	* 
	*/

	public void setName(String name) {
		this.name = name;
	}

	public void setClassroom(String classroom) {
		this.classroom = classroom;
	}

	public void setDueDate(String dueDate) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		this.dueDate = LocalDate.parse(dueDate, formatter);
	}

	public void setId(int id) {
		this.id = id;
	}

	/**
 	* Gets all homework information.
 	*
 	* 
	*/

	public String getName() {
		return name;
	}

	public String getClassroom() {
		return classroom;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}
	
	public int getId() {
		return id;
	}

	/**
 	* Displays all homework information.
 	*
 	* 
	*/

	public String toString()
	{
		String fun;
		fun= String.format("%-40s %-30s %-20s %-5d",name,classroom,(dueDate != null ? dueDate.toString() : "N/A"),id);
		return fun;
	}
}