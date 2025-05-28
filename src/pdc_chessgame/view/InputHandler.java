/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pdc_chessgame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 *
 * @author Andrew & Finlay
 */
public class InputHandler
{
    JPanel posParent;
            
    public InputHandler(JPanel parent) {
        
        this.posParent = parent;

    }
    
    public String getStringInput(String title, String prompt, String inputRequirements) 
    {
        final JDialog dialog = new JDialog((Frame) null, title, true);
        final JTextField textField = new JTextField(20);
        
        final String[] input = new String[1];
        final boolean[] submitted = new boolean[1];
        

        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> 
        {
            input[0] = textField.getText();
            submitted[0] = true;
            dialog.dispose();
        });

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> 
        {
            dialog.dispose();
        });

        textField.addActionListener(e -> 
        {
            okButton.doClick();
        });

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(new Color(50,50,50));
        panel.setForeground(new Color(126,204,255));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.add(new JLabel(prompt), BorderLayout.NORTH);
        panel.add(textField, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(50,50,50));
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        dialog.getContentPane().add(panel);
        //dialog.setUndecorated(true);
        dialog.setBackground(new Color(50,50,50));
        dialog.setForeground(new Color(126,204,255));
        dialog.pack();
        dialog.setLocationRelativeTo(posParent);  // Center on parent
        dialog.setVisible(true);

        return submitted[0] ? input[0] : null;
        //return JOptionPane.showInputDialog(null, prompt, title);
    }

    public boolean confirmAction(String message) 
    {
        int result = JOptionPane.showConfirmDialog(
                null,
                message,
                "Please Confirm",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );
        return result == JOptionPane.YES_OPTION;
    }

    // Optional: A manual popup window example if needed
    public void showPopupFrame(String title, String message) 
    {
        JFrame frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(300, 150);
        frame.setLocationRelativeTo(null); // center the popup

        JLabel label = new JLabel(message, SwingConstants.CENTER);
        JButton okButton = new JButton("OK");

        okButton.addActionListener(e -> frame.dispose());

        frame.setLayout(new BorderLayout());
        frame.add(label, BorderLayout.CENTER);
        frame.add(okButton, BorderLayout.SOUTH);

        frame.setVisible(true);
    }
}