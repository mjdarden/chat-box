package com.mdarden.user.networking;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Properties;

public class Client
{
    private String m_ip;
    private int m_port;
    private Socket m_socket = null;
    private boolean m_isRunning = false;
    private BufferedReader m_bufferedInputReader = null;
    private BufferedWriter m_bufferedOutputWriter = null;
    private ArrayList<IMessageReceivedListener> messageListeners = new ArrayList<IMessageReceivedListener>();
    //private final int TIMEOUT = 30000; // 30 seconds

    public Client()
    {
        try
        {
            loadConfigFile();

            createSocket();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public boolean connectSocket()
    {
        try
        {
            m_socket.connect(new InetSocketAddress(m_ip, m_port));
            if (m_socket.isConnected())
            {
                m_socket.setReuseAddress(true);
                m_bufferedInputReader = new BufferedReader(new InputStreamReader(m_socket.getInputStream()));
                m_bufferedOutputWriter = new BufferedWriter(new OutputStreamWriter(m_socket.getOutputStream()));
                startReceieveThread();
                return true;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return false;
    }

    public void sendMessage(String message)
    {
        if (message != null)
        {
            try
            {
                System.out.println("Sending message: ." + message + ".");
                m_bufferedOutputWriter.write(message);
                m_bufferedOutputWriter.newLine();
                m_bufferedOutputWriter.flush();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    public void addListener(IMessageReceivedListener listener)
    {
        messageListeners.add(listener);
    }

    public void removeListener(IMessageReceivedListener listener)
    {
        messageListeners.remove(listener);
    }

    private void notifyListeners(String message)
    {
        for (IMessageReceivedListener listener : messageListeners)
        {
            if (listener != null)
            {
                listener.messageReceived(message);
            }
        }
    }

    private void loadConfigFile()
    {
        try
        {
            Properties props = new Properties();
            props.load(getClass().getResourceAsStream( "/META-INF/client.properties" ));
            m_ip = props.getProperty("ip", "127.0.0.1");
            m_port = Integer.parseInt(props.getProperty("port", "10001"));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void createSocket()
    {
        try
        {
            m_socket = new Socket();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void startReceieveThread()
    {
        Thread receiveMessageThread = new Thread(new ReceiveMessageRunnable());
        receiveMessageThread.setDaemon(false);
        receiveMessageThread.start();
    }

    private void stop()
    {
        try
        {
            if (m_bufferedInputReader != null)
            {
                m_bufferedInputReader.close();
            }
            if (m_bufferedOutputWriter != null)
            {
                m_bufferedOutputWriter.close();
            }
            if (m_socket != null)
            {
                m_socket.close();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private class ReceiveMessageRunnable implements Runnable
    {
        @Override
        public void run()
        {
            try
            {
                m_isRunning = true;
                while (m_isRunning)
                {
                    // Wait for response
                    String response = m_bufferedInputReader.readLine();
                    notifyListeners(response);

                    if (response.equalsIgnoreCase("disconnectFromServer"));
                    {
                        m_isRunning = false;
                    }
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            finally
            {
                stop();
            }
        }
    }
}
