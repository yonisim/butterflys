package net;

import java.io.*;
import java.net.Socket;

public class ConnectionHelper
{

    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private Socket socket;

    public ConnectionHelper(Socket socket)
    {
        try
        {
            this.socket = socket;
            System.out.println("creating new connection instance");
            setOos(new ObjectOutputStream(socket.getOutputStream()));
            oos.flush();
            setOis(new ObjectInputStream(socket.getInputStream()));
            System.out.println("connection instance created");
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public Object readMessage()
    {
        System.out.println("Reading message");
		try {
			return ois.readObject();
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
			Thread.currentThread().interrupt();
			return null;
		}
    }

    public boolean isConnected()
    {
        return socket.isConnected();
    }

    public void disconnect()
    {
        try
        {
            socket.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public void sendMessage(Object o)
    {
        System.out.println("Sending message");
        try
        {
            oos.writeObject(o);
        }
        catch(IOException e)
        {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
        System.out.println("Message sent");
    }

    public void close()
    {
        try
        {
            ois.close();
            oos.close();
            socket.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public void flush()
    {
        try
        {
            oos.flush();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public ObjectInputStream getOis()
    {
        return ois;
    }

    public void setOis(ObjectInputStream ois)
    {
        this.ois = ois;
    }

    public ObjectOutputStream getOos()
    {
        return oos;
    }

    public void setOos(ObjectOutputStream oos)
    {
        this.oos = oos;
    }
}
