import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientConnect
{
    public static String userChoice;
    public static int portNumber; 
    public static int numSpawns;

    public void clientConnect() throws IOException 
    {        
        try (ServerSocket serverSocket = new ServerSocket(portNumber, 100);)
        {
            System.out.println("Listening to port " + portNumber + "...\n");

            String inputLine;
            
            while (true)
            {
                try (Socket clientSocket = serverSocket.accept();
                    PrintWriter clientOutputStream = new PrintWriter(clientSocket.getOutputStream(), true);
                    BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));)
                {
                    inputLine = input.readLine();

                    switch (inputLine)
                    {
                        case "Date/Time":                          
                            ProcessCmd("date", clientOutputStream);
                            break;
                        case "Uptime":
                            ProcessCmd("uptime", clientOutputStream);
                            break;
                        //Mac Command for memory
                        //I'm using this to test on my local - T justin
                        case "Memory":
                            ProcessCmd("vm_stat", clientOutputStream);
                            break;
                        //Linux Command for memory
                        //Leaving it commented out until the demo - T Justin
//                        case "Memory":
//                            ProcessCmd("free", clientOutputStream);
//                            break;
                        case "Netstat":
                            ProcessCmd("netstat", clientOutputStream);
                            break;
                        case "Current Users":
                            ProcessCmd("who", clientOutputStream);
                            break;
                        case "Running Processes":
                            ProcessCmd("ps -e", clientOutputStream);
                            break;
                        //Should never get to this point because Menu class is doing validation    
                        default:
                            clientOutputStream.println("Could not run procees, " + inputLine);
                            break;
                    
                    //end swtich
                    }
                
                //end try
                } 
                catch (IOException e)
                {
                    System.out.println("Exception caught when trying to handle client request");
                    System.out.println(e.getMessage());
                }

            //end while
            }
        
        //end try
        } 
        catch (IOException e)
        {
            System.out.println("Exception caught when trying to listen on port " + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }
        
    public static boolean ProcessCmd(String cmd, final PrintWriter pw) throws IOException
    {
        final Process p = Runtime.getRuntime().exec(cmd);
        BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line = null;
        
        // Set number of completes runs to zero
        ServerThread.numFinished = new AtomicInteger(0);
        
        //create server threads
        try 
        {
            ServerThread[] spawns = new ServerThread[numSpawns]; 

            // Create all the server threads
            for(int x = 0; x < numSpawns; x++) 
            { 
                // create new server thread
                spawns[x] = new ServerThread(x, System.nanoTime(), userChoice );
            }

            for (int x =0; x < numSpawns; x++) 
            {
                // start this thread (calls RUN)
                spawns[x].start();
            }

            // loop until all client threads are done
            while (ServerThread.getNumFinished() < numSpawns)
            {
                Thread.sleep(5000);
            }

            long topEndTime = System.nanoTime();

            // This ensures all thread have really finished writing to the console
            Thread.sleep(5000);

            for(int x = 0; x < numSpawns; x++) 
            {                   
                long time = spawns[x].endTime/1000;                        
            }
            
            while ((line = input.readLine()) != null)
            {
                //Print Results to Server Program output
                System.out.println("     Server echo: " + line);
                pw.println(line);
            }
                      
        //end try
        }
        catch (IOException ex)
        {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch(InterruptedException iee) 
        {
            iee.printStackTrace();;
            System.exit(1);
        }
        try
        {
            p.waitFor();
        } 
        catch (InterruptedException ex)
        {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

    return true;
    //end Process()
    }
}