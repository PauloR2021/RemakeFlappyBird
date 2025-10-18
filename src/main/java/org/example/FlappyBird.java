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
        Timer timer = new Timer(20,this); // Timer para Atualizar o Game

        renderizador = new Renderizador();
        rand = new Random();

        jFrame.add(renderizador);
        jFrame.setTitle("Flappy Bird");
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setSize(LARGURA,ALTURA);
        jFrame.addKeyListener(this);
        jFrame.setResizable(false);
        jFrame.setVisible(true);

        bird = new Rectangle(LARGURA / 2 - 10, ALTURA / 2 - 10, 20,20);
        pipes = new ArrayList<>();

        addPipe(true);
        addPipe(true);
        addPipe(true);
        addPipe(true);

        timer.start();

    }

    /*
    * Método para Criar os Canos ativos na Tela
    * Paramentro Start indica se é a primeira vez que os canos estão sendo adicionados
    */

    public void addPipe(boolean start){
        //Espaçamento e tamanho dos Canos
        int space = 300; //Defini a distancia vertical
        int width = 100; //Largura de Cada Cano
        int height = 50 + rand.nextInt(300); //Define uma altura aleatoria para os canoss

        //Quando o jogo Começa
        if(start){
            //Cria os canos de cima e de baixo
            pipes.add(new Rectangle(LARGURA + width + pipes.size() * 300, ALTURA - height - 120, width, height));
            pipes.add(new Rectangle(LARGURA + width + (pipes.size() - 1) * 300, 0, width, ALTURA - height - space));
        }else {
            pipes.add(new Rectangle(pipes.get(pipes.size() - 1).x + 600, ALTURA - height - 120, width, height));
            pipes.add(new Rectangle(pipes.get(pipes.size() - 1).x, 0, width, ALTURA - height - space));
        }
    }

    public void jump(){
        if(gameOver){
            bird = new Rectangle(LARGURA / 2 - 10, ALTURA / 2 - 10,20,20);
            pipes.clear();
            yMotion = 0;
            score = 0;

            addPipe(true);
            addPipe(true);
            addPipe(true);
            addPipe(true);
            gameOver = false;
        }

        if(!started){
            started = true;
        } else if (!gameOver) {
            if(yMotion > 0){
                yMotion = 0;
            }
            yMotion -= 10;
        }
    }

    /*
    * Criando o Loop Principal do Game, que atualiza a lógica do jogo a cada fração de segundo
    * Ele é chamado automaticamente por um timer que roda a cada 20 milissegundos
    **/
    @Override
    public void actionPerformed(ActionEvent e) {
        //Controla a Velocidade e Contagem dos Frames
        int speed = 10; //Velocidade dos Canos
        ticks++; //Contador de Frames


        //Movimenta os Canos, a cada Pipe é um cano verde
        if(started){
            for (int i = 0; i< pipes.size(); i++){
                Rectangle pipe = pipes.get(i);
                pipe.x -= speed;
            }

            //Gravidade do Passaro
            if(ticks % 2 == 0 && yMotion < 15){
                yMotion += 2;
            }

            //Remoção e Criação de Novos Canos
            for (int i = 0; i < pipes.size(); i++){
                Rectangle pipe = pipes.get(i);

                if(pipe.x + pipe.width < 0){
                    pipes.remove(pipe);
                    if(pipe.y == 0){
                        addPipe(false);
                    }

                }
            }

            bird.y += yMotion; //Move o Passaro Verticalmente

            //Pontuação e Colisão
            for(Rectangle pipe : pipes){
                if(pipe.y == 0 && bird.x + bird.width / 2 > pipe.x + pipe.width / 2 - 10 &&
                    bird.x + bird.width / 2 < pipe.x + pipe.width / 2 + 10){
                    score++;
                }

                if(pipe.intersects(bird)){
                    gameOver = true;
                    bird.x = pipe.x - bird.width;
                }
            }

            //Limites da Tela
            if(bird.y > ALTURA - 120 || bird.y < 0){
                gameOver = true;
            }

            if(bird.y + yMotion >= ALTURA - 120){
                bird.y = ALTURA - 120 - bird.height;
            }
        }
        renderizador.repaint(); //Faz a Atualização do Desenho na Tela

    }

    /*
    * Metodo responsavel por desenha tudo na tela do Jogo
    * Ele é chamado toda vez que a tela precisa ser atualizada
    */
    public void paint(Graphics g){
        //Desenha o Fundo do Céu
        g.setColor(Color.cyan);
        g.fillRect(0,0,LARGURA,ALTURA);

        //Desenha o Chão
        g.setColor(Color.orange);
        g.fillRect(0, ALTURA - 120, LARGURA,120);

        //Desenha o Gramado sobre o Chão
        g.setColor(Color.green);
        g.fillRect(0, ALTURA - 120, LARGURA, 20);

        //Desenha o Pássaro
        g.setColor(Color.red);
        g.fillRect(bird.x,bird.y,bird.width,bird.height);

        //Desenha o Cano
        for(Rectangle pipe : pipes){
            desenharCano(g, pipe);
        }

        //Defininfdo o Estilo do Texto - Placar e Mensagem
        g.setColor(Color.white);
        g.setFont(new Font("Arial",Font.BOLD,60));

        //Menssagem do Jogo
        if(!started){
            g.drawString("Pressione Enter",180,ALTURA /2 -50);
        }

        //Mensagem Game Over
        if(gameOver){
            g.drawString("Game Over !", 250,ALTURA / 2 - 50);
        }

        //Menssagem com o Pontos
        if(!gameOver && started){
            g.drawString(String.valueOf(score), LARGURA /2 - 25 , 100);
        }
    }
    /*Método para Auxilia o Desenho do Cano*/
    public void desenharCano(Graphics g, Rectangle pipe){
        //Definindo a Cor dos Canos
        g.setColor(Color.green.darker());
        g.fillRect(pipe.x,pipe.y, pipe.width,pipe.height);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
    

    }




    public static void main(String[] args){
        flappyBird = new FlappyBird();
    }





}
