package com.mdarden.user;

import javax.swing.SwingUtilities;

import com.mdarden.user.gui.ScreenMgr;

public class App
{
    public static void main(String[] args) throws Exception
    {
        SwingUtilities.invokeLater(new ScreenMgr());
    }
}
