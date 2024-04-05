package com.mdarden.user.gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.mdarden.user.networking.IMessageReceivedListener;

public class ChatScreen extends Screen implements IMessageReceivedListener
{
    private JPanel bottomPannel = null;
    private JTextArea receivedMessages = null;
    private JScrollPane scrollArea = null;

    public ChatScreen(ScreenMgr mgr)
    {
        super(mgr, "Chat Screen", BoxLayout.Y_AXIS);

        receivedMessages = new JTextArea();
        receivedMessages.setLineWrap(true);
        receivedMessages.setEditable(false);
        scrollArea = new JScrollPane(receivedMessages);
        scrollArea.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollArea.setPreferredSize(new Dimension(350, 500));

        bottomPannel = new JPanel(new FlowLayout());

        button = new JButton("Send");
        button.addActionListener(this);
        bottomPannel.add(button);

        textField = new JTextField();
        textField.setPreferredSize(new Dimension(250, 40));
        bottomPannel.add(textField);

        addComponent(0, scrollArea);
        addComponent(1, bottomPannel);

        addComponentsToScreen();
    }

    // ----- IMessageReceivedListener -----

    @Override
    public void messageReceived(String message)
    {
        // Display message received
        System.out.println(message);
    }

    // ----- ActionListener -----

    @Override
    public void actionPerformed(ActionEvent event)
    {
        if (event.getSource() == button)
        {
            String message = textField.getText();
            if (!message.isEmpty())
            {
                System.out.println("Sending message: " + message);
                windowMgr.sendMessage(message);
            }
        }
    }
}
