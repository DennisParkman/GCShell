import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class GCShell
{
    public static void main(String[] args)
    {
        ArrayList<String> history = new ArrayList<>();
        Scanner kb = new Scanner(System.in);
        String line;

        while(true)
        {
            //Read what the user enters on the command line and perform the command.
            System.out.print("gcshell>");
            line = kb.nextLine();

            history.add(line);
            //Hello

            switch (line)
            {
                case "quit":
                    //if quit is entered kill loop
                    kb.close();
                    return;
                case "history":
                    //if history entered, display all text entered into shell
                    for (int i = 0; i < history.size(); i++)
                    {
                        System.out.print(i + 1 + ". " + history.get(i));
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
                        System.out.println(history.get(history.size() - 1));
                    }
                    break;
                default:
                    try
                    {
                        ProcessBuilder p = new ProcessBuilder(line);
                        p.inheritIO();
                        p.directory(new File("bin"));
                        p.start().waitFor();
                    }
                    catch(IOException e)
                    {
                        System.out.println("Command Not Found");
                    }
                    catch(InterruptedException e)
                    {
                        System.out.println("The command was interrupted");
                        e.printStackTrace();
                    }
                    break;
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
}
