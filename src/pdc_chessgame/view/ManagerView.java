/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pdc_chessgame.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author ARKen
 */
public class ManagerView extends JPanel
{
    private JTextArea moveHistoryArea;

    public ManagerView()
    {
        this.setBackground(new Color(30, 30, 30));
        this.setBorder(null);
        this.setLayout(new BorderLayout());

        moveHistoryArea = new JTextArea();
        moveHistoryArea.setEditable(false);
        moveHistoryArea.setBackground(new Color(40, 40, 40));
        moveHistoryArea.setForeground(Color.WHITE);
        moveHistoryArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        moveHistoryArea.setLineWrap(true);
        moveHistoryArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(moveHistoryArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED); //autoscroll (:

        this.add(scrollPane, BorderLayout.CENTER);
    }

    public void updateMoveHistory(String history) 
    {
        moveHistoryArea.setText(history);
        moveHistoryArea.setCaretPosition(moveHistoryArea.getDocument().getLength());
    }
}