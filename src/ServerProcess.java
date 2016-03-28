import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerProcess 
{
    public static String requestResult;
    
    public static void createServerThread(String clientCmd, final PrintWriter printToClient) throws IOException
    {
        final Process p = Runtime.getRuntime().exec(clientCmd);
        BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line = null;
        int serverThreadIndex = 0;

        //create server threads
        ServerThread[] serverThread = new ServerThread[75];

        try 
        {
            // Create all the server threads
            while ((line = input.readLine()) != null) 
            {
                // create new server thread
                serverThread[serverThreadIndex] = new ServerThread(serverThreadIndex, clientCmd);
                                
                ServerThread.processCmd(serverThread[serverThreadIndex].clientCmd);

                //send request result to client
                printToClient.println(requestResult);

                System.out.println(requestResult);
                
                serverThreadIndex++;
            }
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        try 
        {
            p.waitFor();
        } 
        catch (InterruptedException ex) 
        {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
