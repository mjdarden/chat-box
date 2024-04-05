package com.mdarden.user.gui;

import com.mdarden.user.networking.Client;

public class ScreenMgr implements Runnable
{
    ChatScreen m_chatScreen = null;
    LoginScreen m_loginScreen = null;
    Client m_client = null;

    public ScreenMgr()
    {
        m_loginScreen = new LoginScreen(this);
    }

    public void login(String username)
    {
        //connect m_client to server
        m_client = new Client();
        if (m_client.connectSocket())
        {
            System.out.println("Successfuly connected to server");
            m_client.sendMessage(username);

            //hide and delete login screen
            m_loginScreen.setVisible(false);
            m_loginScreen.dispose();
            m_loginScreen = null;
    
            //create and show chat screen
            m_chatScreen = new ChatScreen(this);
            m_chatScreen.setVisible(true);
            m_client.addListener(m_chatScreen);
        }
        else
        {
            System.out.println("Failed to connect to server");
        }
    }

    public void sendMessage(String message)
    {
        m_client.sendMessage(message);
    }

    // ----- Runnable -----

    @Override
    public void run()
    {
        m_loginScreen.setVisible(true);
    }
}
