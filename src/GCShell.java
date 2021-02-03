import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class GCShell
{
    private static final ArrayList<String> history = new ArrayList<>();
    private static final Scanner kb = new Scanner(System.in);

    public static void main(String[] args)
    {
        String line;

        while(true)
        {
            //Read what the user enters on the command line and perform the command.
            System.out.print("gcshell>");
            line = kb.nextLine();

            try
            {
                if(!executeCommand(line))
                {
                    return;
                }
            }
            catch(IOException e)
            {
                System.out.println("Command Not Found");
            }
            catch(InterruptedException e)
            {
                System.out.println("The command was interrupted");
            }

        }
        // create while loop that prints gcshell and asks for commands
        //in loop
        //Read what the user enters on the command line and perform the command.
        //save command in history
        //Create a child process to execute the command entered
        //if quit is entered kill loop
        //if !! entered, bring up history, if no history display “No commands in history"
        //if history entered, display all text entered into shell

    }

    public static boolean executeCommand(String line) throws IOException, InterruptedException
    {
        switch (line)
        {
            case "quit":
                //if quit is entered kill loop
                kb.close();
                return false;
            case "history":
                //if history entered, display all text entered into shell
                if (history.isEmpty())
                {
                    System.out.println("No commands in history");
                }
                else
                {
                    for (int i = 0; i < history.size(); i++)
                    {
                        System.out.println(i + 1 + ". " + history.get(i));
                    }
                }

                history.add(line);
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
            default:
                ProcessBuilder p = new ProcessBuilder(line.split(" "));
                p.inheritIO();
                p.start().waitFor();
                history.add(line);
                break;
        }
        return true;
    }
}
