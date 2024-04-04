package com.mdarden.host;

import javax.swing.SwingUtilities;

import com.mdarden.host.gui.MainWindow;
import com.mdarden.host.networking.Server;

public class App
{
    public static void main(String[] args) throws Exception
    {
        System.out.println("Running....");
        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                MainWindow mainWindow = new MainWindow();
                mainWindow.show();
            }
        });
        Server server = new Server();
        Thread myThread = new Thread(server);
        myThread.setDaemon(false);
        myThread.start();
    }
}
