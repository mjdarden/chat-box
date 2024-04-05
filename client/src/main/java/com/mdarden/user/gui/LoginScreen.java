package com.mdarden.user.gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JTextField;

public class LoginScreen extends Screen
{
    public LoginScreen(ScreenMgr mgr)
    {
        super(mgr, "Login Screen", BoxLayout.X_AXIS);

        button = new JButton("Login");
        button.addActionListener(this);
        addComponent(0, button);

        textField = new JTextField();
        textField.setPreferredSize(new Dimension(250, 40));
        addComponent(1, textField);

        addComponentsToScreen();
    }

    // ----- ActionListener -----

    @Override
    public void actionPerformed(ActionEvent event)
    {
        if (event.getSource() == button)
        {
            String username = textField.getText();
            if (!username.isEmpty())
            {
                String registarationMessage = "username:" + username;
                System.out.println("login message: ." + registarationMessage + ".");
                windowMgr.login(registarationMessage);
            }
        }
    }
}
