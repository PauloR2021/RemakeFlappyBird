package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Random;

public class FlappyBird implements ActionListener, KeyListener {
    public static FlappyBird flappyBird; //Chamando A Classe
    public final int LARGURA = 800, ALTURA = 600; //Definindo Largura e Altura
    public Renderizador renderizador; //Chamando o Renderizador

    public Rectangle bird; //Definindo o Personagem
    public ArrayList<Rectangle> pipes; //Criando os Obstaculos
    public int ticks, yMotion, score; //Criando os Metodos de Pontuação
    public boolean gameOver, started; //Criando os Metodos de Game Over e Inicio
    public Random rand; //Criando Biblioteca Random


    public FlappyBird(){

        JFrame jFrame = new JFrame();
        Timer timer = new Timer(20,this);

        renderizador = new Renderizador();
        rand = new Random();

        jFrame.add(renderizador);
        jFrame.setTitle("Flappy Bird");
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setSize(LARGURA,ALTURA);
        jFrame.addKeyListener(this);
        jFrame.setResizable(false);
        jFrame.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    public void paint(Graphics g){
        g.setColor(Color.cyan);
        g.fillRect(0,0,LARGURA,ALTURA);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }


    public static void main(String[] args){
        flappyBird = new FlappyBird();
    }





}
