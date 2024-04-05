package com.mdarden.user.gui;

import java.awt.Component;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

public abstract class Screen extends JFrame implements ActionListener
{
    protected JButton button = null;
    protected JTextField textField = null;
    protected ScreenMgr windowMgr = null;
    private ArrayList<Component> componentList = new ArrayList<Component>();

    protected  Screen(ScreenMgr mgr, String screenTitle, int axis)
    {
        windowMgr = mgr;
        this.setTitle(screenTitle);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLayout(new BoxLayout(this.getContentPane(), axis));
    }

    protected void addComponent(int addIndex, Component component)
    {
        componentList.add(addIndex, component);
    }

    protected void addComponentsToScreen()
    {
        for (Component component : componentList)
        {
            if (component != null)
            {
                this.add(component);
            }
        }
        this.pack();
        this.setLocationRelativeTo(null);
    }

    public int getNumComponents()
    {
        return componentList.size();
    }
}
