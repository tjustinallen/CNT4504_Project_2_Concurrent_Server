import java.io.*;

public class Server 
{
    public static void main(String[] args) throws IOException
    {
        //Validation for portNumber
        if (args.length != 1)
        {
            System.err.println("Usage: java Server <port number>");
            System.exit(1);
        }
        
        //parse input args onload for portNumber
        int portNumber = Integer.parseInt(args[0]);

        ClientConnect.portNumber = portNumber;
        
        ClientConnect clientConnect = new ClientConnect();
        clientConnect.clientConnect(); 
    }
}