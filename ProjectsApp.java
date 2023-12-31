package projects;

import java.util.List;
import java.util.Scanner;
import java.util.Objects;
import java.math.BigDecimal;
import projects.entity.Project;
import projects.exception.DbException;
import projects.service.ProjectService;

/**
 * This is a menu-driven application
 */

public class ProjectsApp {
	private Scanner scanner = new Scanner(System.in);
	private ProjectService projectService = new ProjectService();

			

//formatter: off
	private List<String> operations = List.of(
			"1) Add a project"
			);
//formatter :on
	
/**
 * Entry point for Java application
 * @param args Unused.
 */
	
public static void main(String[] args) {
	new ProjectsApp().processUserSelections();	
	
/**
 * This method will print the operation, gets a user menu selection, and then perform the chosen operation.
 * It will continue to repeat until the user requests that it terminates. 
 */
}
	private void processUserSelections() {
		boolean done = false;
		while(!done) {
			try {
				int selection = getUserSelection();
				
				switch(selection) {
				case -1:
				done = exitMenu();
				break;
				
				case 1:
					createProject();
					break;
				
				default:
					System.out.println("\n" + selection + " is not a valid selection. Try again.");
					break;
					
				}
			}
		catch(Exception e) {
			System.out.println("\nError: " + e + "Try again.");
		}
		}
	}
	
	/**
	 * Gathers the user input for a row, then calls the project service to create the row
	 */
	
	private void createProject() {
		String projectName = getStringInput("Enter the project name");
		BigDecimal estimatedHours = getDecimalInput("Enter the estimated hours");
		BigDecimal actualHours = getDecimalInput("Enter the actual hours");
		Integer difficulty = getIntInput("Enter the project difficulty (1-5)");
		String notes = getStringInput("Enter the prject notes");
		
		Project project = new Project();
		
		project.setProjectName(projectName);
		project.setEstimatedHours(estimatedHours);
		project.setActualHours(actualHours);
		project.setDifficulty(difficulty);
		project.setNotes(notes);
		
		Project dbProject = projectService.addProject(project);
		System.out.println("You have successfully created project: " + dbProject);
	}
	
	/**
	 * Collects the user's input from the console and then converts it to a BigDecimal.
	 * @param prompt This is the prompt that will display on the console.
	 * @return If successful, will return a BigDecimal value
	 * @throws If there is an error that occurs converting the number to a BigDecimal, then a DbException is thrown.
	 */
	private BigDecimal getDecimalInput(String prompt) {
		String input = getStringInput(prompt);
		
		if(Objects.isNull(input)) {
			return null;
			
		}
		try {
			return new BigDecimal(input).setScale(2);
		} 
		catch(NumberFormatException e) {
			throw new DbException(input + " is not a valid dcimal number.");
		}
	}
	
	/**
	 * Called when the user wants to exit the application. It prints and returns to terminate the app.
	 * @return
	 */
	private boolean exitMenu() {
		System.out.println("Exiting the menu.");
		return true;
	}
	/**
	 * This method will print the available menu selections. It will then get the user's menu selection from 
	 * the console and convert it to an int.
	 * @return It will return the menu selection as in int or -1 if nothing is selected.
	 */
	
	private int getUserSelection() {
		printOperations();
		Integer input = getIntInput("Enter a menu selection");
		return Objects.isNull(input) ? -1 : input;
		}
	
	/*
	 * Will print a prompt on the console and then collect the user's input from the console.
	 * It will then convert the input to an Integer.
	 */
	
	private Integer getIntInput(String prompt) {
	String input = getStringInput(prompt);
	
	if(Objects.isNull(input)) {
		return null;
	}
	try {
		return Integer.valueOf(input);
	}
	catch(NumberFormatException e) {
		throw new DbException(input + " is not a valid number.");
		
	}
}

	/*
	 * It will print a prompt on the console and then collect the user's input from the console.
	 * If the user enters nothing, null is returned. Otherwise, the input is trimmed and returned.
	 */
private String getStringInput(String prompt) {
	System.out.print(prompt + ": ");
	String input = scanner.nextLine();
	
	return input.isBlank() ? null : input.trim();
	}


private void printOperations() {
	System.out.println("\nThese are the available selections. Press the Enter key to quit:");
	
	operations.forEach(line -> System.out.println("  " + line));
}
}





