package org.example;

import javax.swing.*;
import java.awt.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Renderizador extends JPanel {
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        FlappyBird.flappyBird.paint(g);
    }

}