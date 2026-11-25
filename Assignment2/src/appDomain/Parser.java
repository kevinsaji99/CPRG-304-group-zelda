package appDomain;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

import exceptions.EmptyQueueException;
import implementations.MyQueue;
import implementations.MyStack;
import utilities.Iterator;

public class Parser
{
    private static final String OPENERS = "({[<";
    private static final String CLOSERS = ")}]>";
    private static final String REPORTED = "!";

    private static final boolean IGNORE_ANGLE_OPERATORS = true;

    private MyStack stack;
    private MyQueue errorQ;
    private MyQueue extrasQ;
    private int[] errLines;
    private char[] errChars;
    private int errCount;

    /**
     * Parses a file and returns its syntax errors in line order.
     *
     * @param fileName path of the text file to check
     * @return messages such as {@code Line 4: Syntax error on "]".}; empty if there are no errors
     * @throws IOException if the file cannot be read
     */
    public String[] parse( String fileName ) throws IOException
    {
        stack = new MyStack();
        errorQ = new MyQueue();
        extrasQ = new MyQueue();
        errLines = new int[16];
        errChars = new char[16];
        errCount = 0;

        try( BufferedReader in = new BufferedReader( new FileReader( fileName ) ) )
        {
            String text;
            int lineNo = 0;
            while( ( text = in.readLine() ) != null )
            {
                parseLine( text, ++lineNo );
            }

            discardReported();
            while( !stack.isEmpty() )
            {
                errorQ.enqueue( stack.pop() );
            }
            reconcileQueues();
        }
        catch( EmptyQueueException e )
        {
            throw new IllegalStateException( "Queue was unexpectedly empty.", e );
        }
        return buildMessages();
    }

    private void parseLine( String text, int lineNo ) throws EmptyQueueException
    {
        for( int i = 0; i < text.length(); i++ )
        {
            char c = text.charAt( i );
            if( IGNORE_ANGLE_OPERATORS && isAngleOperator( text, i ) )
            {
                continue;
            }
            if( OPENERS.indexOf( c ) >= 0 )
            {
                stack.push( c + ":" + lineNo );
            }
            else if( CLOSERS.indexOf( c ) >= 0 )
            {
                handleClosing( c, lineNo );
            }
        }
    }

    private void handleClosing( char closer, int lineNo ) throws EmptyQueueException
    {
        if( !stack.isEmpty() && matches( stack.peek(), closer ) )
        {
            stack.pop();
        }
        else if( !errorQ.isEmpty() && matches( errorQ.peek(), closer ) )
        {
            errorQ.dequeue();
        }
        else if( stack.isEmpty() )
        {
            errorQ.enqueue( closer + ":" + lineNo );
        }
        else if( stackHasMatch( closer ) )
        {
            // Openers above the match are reported now; the marked copy lets their closers be skipped later.
            while( !matches( stack.peek(), closer ) )
            {
                String bad = stack.pop();
                record( bad );
                errorQ.enqueue( bad + REPORTED );
            }
            stack.pop();
        }
        else
        {
            extrasQ.enqueue( closer + ":" + lineNo );
        }
    }

    private void discardReported() throws EmptyQueueException
    {
        for( int i = errorQ.size(); i > 0; i-- )
        {
            String entry = errorQ.dequeue();
            if( !entry.endsWith( REPORTED ) )
            {
                errorQ.enqueue( entry );
            }
        }
    }

    private void reconcileQueues() throws EmptyQueueException
    {
        while( !errorQ.isEmpty() && !extrasQ.isEmpty() )
        {
            if( kind( errorQ.peek() ) == kind( extrasQ.peek() ) )
            {
                errorQ.dequeue();
                extrasQ.dequeue();
            }
            else
            {
                record( errorQ.dequeue() );
            }
        }
        while( !errorQ.isEmpty() )
        {
            record( errorQ.dequeue() );
        }
        while( !extrasQ.isEmpty() )
        {
            record( extrasQ.dequeue() );
        }
    }

    private boolean stackHasMatch( char closer )
    {
        Iterator it = stack.iterator();
        while( it.hasNext() )
        {
            if( matches( it.next(), closer ) )
            {
                return true;
            }
        }
        return false;
    }

    private static boolean isAngleOperator( String text, int i )
    {
        char c = text.charAt( i );
        if( c != '<' && c != '>' )
        {
            return false;
        }
        char prev = i > 0 ? text.charAt( i - 1 ) : ' ';
        char next = i + 1 < text.length() ? text.charAt( i + 1 ) : ' ';
        return next == '=' || ( c == '>' && prev == '-' )
                || ( Character.isWhitespace( prev ) && Character.isWhitespace( next ) );
    }

    private static boolean matches( String entry, char closer )
    {
        return entry.charAt( 0 ) == OPENERS.charAt( CLOSERS.indexOf( closer ) );
    }

    private static int kind( String entry )
    {
        int i = OPENERS.indexOf( entry.charAt( 0 ) );
        return i >= 0 ? i : CLOSERS.indexOf( entry.charAt( 0 ) );
    }

    private void record( String entry )
    {
        if( errCount == errLines.length )
        {
            errLines = Arrays.copyOf( errLines, errCount * 2 );
            errChars = Arrays.copyOf( errChars, errCount * 2 );
        }
        errLines[errCount] = Integer.parseInt( entry.substring( 2 ).replace( REPORTED, "" ) );
        errChars[errCount] = entry.charAt( 0 );
        errCount++;
    }

    private String[] buildMessages()
    {
        // Stable insertion sort by line number.
        for( int i = 1; i < errCount; i++ )
        {
            int line = errLines[i];
            char ch = errChars[i];
            int j = i - 1;
            for( ; j >= 0 && errLines[j] > line; j-- )
            {
                errLines[j + 1] = errLines[j];
                errChars[j + 1] = errChars[j];
            }
            errLines[j + 1] = line;
            errChars[j + 1] = ch;
        }

        String[] messages = new String[errCount];
        for( int i = 0; i < errCount; i++ )
        {
            messages[i] = "Line " + errLines[i] + ": Syntax error on \"" + errChars[i] + "\".";
        }
        return messages;
    }
}
