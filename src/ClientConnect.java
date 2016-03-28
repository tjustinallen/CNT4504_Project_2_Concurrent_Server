import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientConnect
{
    public static int portNumber; 

    /*
    Creates connection to client
    receives client command request
    sends client command request to ServerProcess.processCmd
    */
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
                            ServerProcess.createServerThread("date", clientOutputStream);
                            break;
                        case "Uptime":
                            ServerProcess.createServerThread("uptime", clientOutputStream);
                            break;
                        //Mac Command for memory
                        //I'm using this to test on my local - T justin
                        case "Memory":
                            ServerProcess.createServerThread("vm_stat", clientOutputStream);
                            break;
                        //Linux Command for memory
                        //Leaving it commented out until the demo - T Justin
//                        case "Memory":
//                            processCmd("free", clientOutputStream);
//                            break;
                        case "Netstat":
                            ServerProcess.createServerThread("netstat", clientOutputStream);
                            break;
                        case "Current Users":
                            ServerProcess.createServerThread("who", clientOutputStream);
                            break;
                        case "Running Processes":
                            ServerProcess.createServerThread("ps -e", clientOutputStream);
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
}