import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerThread extends Thread 
{   
    public int serverThreadID;
    public String clientCmd;
    public static String requestResult;
    
    public ServerThread(int serverN, String cmd) 
    {
        serverThreadID = serverN;
        clientCmd = cmd;
        requestResult = "Request has not been anwered yet";
    }
    
    public static void processCmd(String clientCmd) throws IOException
    {
//        final Process process = Runtime.getRuntime().exec(clientCmd);
        final Process process = Runtime.getRuntime().exec(clientCmd);

        requestResult = process.toString();

        ServerProcess.requestResult = requestResult;
                                            
        try
        {
            process.waitFor();
        } 
        catch (InterruptedException ex)
        {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
