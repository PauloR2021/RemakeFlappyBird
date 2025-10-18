package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FlappyBird implements ActionListener {
    public static FlappyBird flappyBird;
    public final int LARGURA = 800, ALTURA = 600;
    public Renderizador renderizador;


    public FlappyBird(){

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    public void paint(Graphics g){
        g.setColor(Color.cyan);
        g.fillRect(0,0,LARGURA,ALTURA);
    }

    public static void main(String[] args){
        flappyBird = new FlappyBird();
    }
}
