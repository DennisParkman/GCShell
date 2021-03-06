import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;

/**
 * @Author: Dennis Parkman and Jake Morris
 * Date: 2/02/21
 * Purpose: To create a custom shell that can run custom commands using API calls to other utilities.
 *
 * How to Use:
 * First, compile the program using:
 * javac GcShell.java
 * java GcShell
 *
 * Then, type in the regular commands as you would in bash or nano.
 * Additional specialty commands include:
 *
 * history - command that allows the user to see a log of their previous commands.
 * example: gcshell> history
 *
 * !! - command that allows the user to see and repeat their perviously used command.
 * example: gcshell> !!
 *
 * quit - command that allows the user to end their use of gcshell and return to normal shell program.
 * example: gcshell> quit
 *
 * Academic Honesty Statement:
 *
 * We are the authors of this project, however we did use the following resources for our project:
 * https://docs.oracle.com/javase/7/docs/api/java/lang/ProcessBuilder.html
 * https://www.geeksforgeeks.org/file-getcanonicalpath-method-in-java-with-examples/
 * https://www.developer.com/java/data/understanding-java-process-and-java-processbuilder.html
 *
 *
 * I discussed this problem with:
 * Nobody
 */
public class GcShell
{
   //Declare and initalize necessay variable to take in and keep a history of commands
    private static final ArrayList<String> history = new ArrayList<>();
    private static final Scanner kb = new Scanner(System.in);

    public static void main(String[] args)
    {
        String line;

        while(true) // while command does not equal "quit"
        {
            //Read in the command from user
            System.out.print("gcshell>");
            line = kb.nextLine();

            try //pass command into static funciton to process the command
            {
               //if "quit" was entered, end the program
                if(!executeCommand(line))
                {
                    return;
                }
            }
            catch(IOException e)// if the command was not valid, catch execeptiion
            {
                System.out.println("Command Not Found");
            }
            catch(InterruptedException e)// if interrupted before process finished, catch exception
            {
                System.out.println("The command was interrupted");
            }

        }
    }
    
   /**
    * This method takes in commands entered into 
    * 
    * @param line - a String containing the name of the process that needs to be executed 
    * along with any other parameters and or options
    * @return boolean - return true if command was sucessful or not found, return false if quit was entered.
    */
    public static boolean executeCommand(String line) throws IOException, InterruptedException
    {
        String[] args = line.split(" ");
        String firstArg = args[0];
        switch (firstArg)
        {
            case "quit":
                //if quit is entered kill loop
                kb.close();
                return false;
            case "history":
                //if history entered, display all text entered into shell
                history.add(line);
                if (history.isEmpty())
                {
                    System.out.println("No commands in history");
                }
                else //print history
                {
                    for (int i = 0; i < history.size(); i++)
                    {
                        System.out.println(i + 1 + ". " + history.get(i));
                    }
                }
                break;
            case "!!":
                //if !! entered, bring up history, if no history display “No commands in history"
                if (history.isEmpty())
                {
                    System.out.println("No commands in history");
                }
                else
                {
                    executeCommand(history.get(history.size() - 1));
                }
                break;
            case "filecopy": // c filecopy
                ArrayList<String> arrayList = new ArrayList<>();
                Collections.addAll(arrayList, args);

                //C:\Users\denni\IdeaProjects\GCShell\src\mybin
                arrayList.set(0, new File(".").getCanonicalPath() + "/mybin/filecopy");

                ProcessBuilder pb = new ProcessBuilder(arrayList);
                pb.inheritIO();
                pb.start().waitFor();
                history.add(line);
                break;
            case "": //Catches if no command was enterd
                break;
            case "java":
                if(args[1].equals("FileCopy"))
                {
                    ArrayList<String> arraylist = new ArrayList<>();
                    Collections.addAll(arraylist, args);

                    //C:\Users\denni\IdeaProjects\GCShell\src\mybin
                    arraylist.add(1, new File(".").getCanonicalPath() + "/mybin");
                    arraylist.add(1, "-cp");


                    ProcessBuilder p = new ProcessBuilder(arraylist);
                    p.inheritIO();
                    p.start().waitFor();
                    history.add(line);
                    break;
                }
            default: //try to run command through API, if command not found execption is thrown
                ProcessBuilder p = new ProcessBuilder(line.split(" "));
                p.inheritIO();
                p.start().waitFor();
                history.add(line);
                break;
        }
        return true;
    }
}
