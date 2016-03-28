import java.io.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ServerThread extends Thread 
{
    //Static makes it one integer for entire class vs one for each instance.
    //Needed if you don't want the number reset with each instance creation.
    public static AtomicInteger numFinished = new AtomicInteger(0); 
    
    private int serverThreadNum;
    private long startTime;
    public long endTime;
    private String userChoice;
    
    public ServerThread(int serverN, long startTime, String userChoice) 
    {
        serverThreadNum = serverN;
        this.startTime = startTime;
        this.userChoice = userChoice;
    }
    
    public static int getNumFinished() 
    {
        return numFinished.get();
    }
    
    @Override
    public void run() 
    {
        try
        {
            startTime = System.nanoTime();
            
            ServerConnect.serverResponse(userChoice, "Server Thread " + serverThreadNum + ": ", false );
        
            endTime = System.nanoTime()-startTime;
            
            // Indicate the client has finished
            numFinished.incrementAndGet(); 
        }
        catch (IOException ioe) 
        {
            System.exit(1);
        }
    }
}
