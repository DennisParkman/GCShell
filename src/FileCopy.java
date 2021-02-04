/**
 * Dennis Parkman
 *   1/27/2021
 *   To copy files from the command line, or copy files and reverse them
 *   FileCopy file1.txt file2.txt
 *   FileCopy -r file1.txt file2.txt
 *   I am the author of the assignment; however, I received help from the following people:
 *   Nobody
 *   I used the following websites as resources:
 *   None
 *   I discussed this problem with:
 *   Nobody
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class FileCopy
{
    public static void main(String[] args)
    {
        boolean reverse;
        String firstFile;
        String secondFile;
        int index;
        // If there are no arguments other than the file name, a file operand is missing
        if(args.length == 0)
        {
            System.out.println("FileCopy:  missing file operand");
            return;
        }

        if(args[0].equals("-r"))
        {
            reverse = true;
            index = 1;
            // If there are no arguments other than the designation [-r], a file operand is missing
            if(args.length == index)
            {
                System.out.println("FileCopy:  missing file operand");
                return;
            }
        }
        else
        {
            reverse = false;
            index = 0;
        }
        // Formatting the file names around an index allows for one set of code to be written
        // rather than two versions for reversed and non-reversed
        firstFile = args[index];
        // Check for destination argument
        if(args.length == index + 1)
        {
            System.out.println("FileCopy:  missing destination file operand after '" + firstFile + "'");
            return;
        }

        secondFile = args[index + 1];
        // Check for extra arguments
        if(args.length > index + 2)
        {
            System.out.println("FileCopy: Invalid use. FileCopy [-r] source destination");
            return;
        }

        File copyFile = new File(firstFile);
        File pasteFile = new File(secondFile);
        Scanner readCopyFile = null;
        // Check for input file and initialize scanner
        try
        {
            readCopyFile = new Scanner(copyFile);
        }
        catch(FileNotFoundException e)
        {
            System.out.println("FileCopy: '" + firstFile + "' : No such file.");
            return;
        }

        StringBuilder finalText = new StringBuilder();
        StringBuilder text;
        // Read each line from the copy file into a StringBuilder, and then append it to the
        // final text to be written to the file after it has possibly been reversed.
        while(readCopyFile.hasNextLine())
        {
            finalText.append("\n");
            text = new StringBuilder(readCopyFile.nextLine());
            // Reverse if necessary
            if(reverse)
            {
                text.reverse();
            }
            finalText.append(text.toString());
        }

        readCopyFile.close();

        finalText.deleteCharAt(0);

        // Write the final string to the output file. This also accounts for errors creating the file,
        // though I can't imagine what would cause such an error other than an illegal windows character
        FileWriter writer;
        try
        {
            copyFile.createNewFile();
            writer = new FileWriter(pasteFile);
            writer.write(finalText.toString());
            writer.close();
        }
        catch(IOException e)
        {
            System.out.println("FileCopy: Error creating file '" + secondFile + "'");
        }
    }
}
