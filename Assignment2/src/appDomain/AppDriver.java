package appDomain;

import java.io.File;
import java.io.IOException;

public class AppDriver
{
    private static final String USAGE = "Usage: java -jar Parser.jar <file>";

    /**
     * Validates the argument, parses the file and prints the results.
     *
     * @param args exactly one file path
     */
    public static void main( String[] args )
    {
        if( args.length != 1 )
        {
            System.out.println( args.length == 0 ? "Error: no file was given."
                    : "Error: expected one file but got " + args.length
                      + " arguments. Put paths with spaces in double quotes." );
            System.out.println( USAGE );
            return;
        }

        File file = new File( args[0].trim() );
        String error = validate( file );
        if( error != null )
        {
            System.out.println( "Error: " + error );
            return;
        }

        try
        {
            printResults( file.getName(), new Parser().parse( file.getPath() ) );
        }
        catch( IOException e )
        {
            System.out.println( "Error: could not read \"" + file.getPath() + "\": " + e.getMessage() );
        }
        catch( RuntimeException e )
        {
            System.out.println( "Error: parsing stopped unexpectedly: " + e );
        }
    }

    private static String validate( File file )
    {
        if( !file.exists() )
        {
            return "file \"" + file.getPath() + "\" does not exist.";
        }
        if( file.isDirectory() )
        {
            return "\"" + file.getPath() + "\" is a folder, not a file.";
        }
        if( !file.canRead() )
        {
            return "file \"" + file.getPath() + "\" cannot be read.";
        }
        return null;
    }

    private static void printResults( String name, String[] errors )
    {
        if( errors.length == 0 )
        {
            System.out.println( "File: " + name + " has no errors." );
            return;
        }
        System.out.println( "Errors in file " + name + ":" );
        for( String error : errors )
        {
            System.out.println( error );
        }
    }
}

