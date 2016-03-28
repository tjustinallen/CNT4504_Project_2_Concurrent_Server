//We only need this class because ServerThread uses 
//serverResponse in the Thread object creation

//There's definitely a better way to do this, 
//but I haven't spent enough time to know what it is yet.

//This is only a repurposed Class from Project 1 (Iterative)
//- T Justin


import java.io.*;
public class ServerConnect 
{ 
//    public static String hostName;
//    public static int portNumber; 
//    public static int numClients;

    
    public static String serverResponse(String userChoice, String Preface, boolean ShowResults) throws IOException 
    {
   
//        try (Socket socket = new Socket(hostName, portNumber);               
//            // Get the writer and reader
//            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
//            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream())))
//        {     
//
//            // Send data to the server
//            output.println(userChoice);
            
//            String fromServer;
//            
//            //array serverResult to hold server result value for each client request 
//            String[] serverResult = new String[numClients];
//            
//            while ((fromServer = input.readLine()) != null) 
//            {
//
//                if (ShowResults) 
//                {
//
//                    for(int arrayIndex = 0; arrayIndex < numClients; arrayIndex++)
//                    {
//                        serverResult[arrayIndex] = (Preface + fromServer);
//                        Menu.serverResult[arrayIndex] = serverResult[arrayIndex];
//                    }
//
//                }     
//            } 
//        }
//        catch (UnknownHostException e) 
//        {
//            System.err.println("Client: Don't know about host " + hostName);
//            System.exit(1);
//        }
//        catch (IOException e) 
//        {
//            System.err.println("Client: Couldn't get I/O for the connection to " + hostName);
//            System.exit(1);
//        }
//        
    return "";
    }
}
