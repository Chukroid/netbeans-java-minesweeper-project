/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.buscaminas;

import java.awt.Color;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import java.util.Random;
import javax.swing.JOptionPane;

/**
 *
 * @author HP}
 */
public class Buscaminas {
    
    public static int clamp(int number, int min, int max) {
        if (number < min) {
            return min;
        }
        else if (number > max) {
            return max;
        }
        else {
            return number;
        }
    }
    
    public static int[] obtenernumerosalrededor(int x, int y, int numrow){
        int listanum[] = new int[8];
        int max = 100000;
        int upperrow = clamp(y-1,0,max);
        int lowerrow = y+1;
        int index = x - (y*numrow);
        
        
        
        int u2 = (upperrow*numrow) + index;
        int l2 = (lowerrow*numrow) + index;
        int u1 = clamp(u2-1, ((y-1)*numrow)+1, max);
        int u3 = clamp(u2+1, u2+1, ((y-1)*numrow)+numrow);
        int m1 = clamp(x-1, (y*numrow)+1, max);
        int m3 = clamp(x+1, x+1, (y*numrow)+numrow);
        int l1 = clamp(l2-1, ((y+1)*numrow)+1, max);
        int l3 = clamp(l2+1, l2+1, ((y+1)*numrow)+numrow);
        listanum[0] = u1;
        listanum[1] = u2;
        listanum[2] = u3;
        listanum[3] = m1;
        listanum[4] = m3;
        listanum[5] = l1;
        listanum[6] = l2;
        listanum[7] = l3;
        return listanum;
    }

    public static void main(String[] args) {
        int numBotones = 100;
        int numBombas = 10;
        int numfila = 10;
        
        JButton listabotones[]= new JButton[numBotones];
        Random rand = new Random();
        
        JFrame frame = new JFrame("Busca Minas");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize((54*numfila), (57*numfila)+50);
        frame.setLayout(null);
        
        int x = 0;
        int y = 0;
        /* crear los botones */
        for(int i = 1;i<=numBotones;i++){
            
            if (i>1){ 
                x++;
            }
            if(x==numfila){
                x = 0;
                y++;
            }
            
            JButton boton = new JButton();
            boton.putClientProperty("Bomba", false);
            boton.putClientProperty("Activado", false);
            boton.putClientProperty("Id", i-1);
            boton.putClientProperty("Row", y);
            boton.setBackground(Color.lightGray);
            listabotones[i-1] = boton;
            
            
            boton.addActionListener((ActionEvent e) -> {
                Boolean tipo = (Boolean)boton.getClientProperty("Bomba");
                Boolean initactivado = (Boolean)boton.getClientProperty("Activado");
                if((initactivado == false)){
                    if(tipo == false){
                    
                    int xfinal = (int)boton.getClientProperty("Id");
                    int yfinal = (int)boton.getClientProperty("Row");
                    int[] cosas = obtenernumerosalrededor(xfinal,yfinal,numfila);
                    
                    boton.putClientProperty("Activado", true);
                    boton.setBackground(Color.green);
                    int bombas = 0;
                    for (int j = 0; j <= 7;j++){
                        if ((cosas[j] <= (numBotones-1)) && (cosas[j] >= 0)){
                            JButton btnseleccionado = listabotones[cosas[j]];
                            Boolean activadobtnselecionado = (Boolean)btnseleccionado.getClientProperty("Activado");
                            if (activadobtnselecionado == false){
                                Boolean esbomba = (Boolean)btnseleccionado.getClientProperty("Bomba");
                                if ((esbomba==true) && (activadobtnselecionado == false)){
                                    bombas++;
                                }
                            }
                        }
                    }
                    if (bombas>0){
                        boton.setText(Integer.toString(bombas));
                    }
                }else{
                    boton.setBackground(Color.red);
                    int result = JOptionPane.showOptionDialog(null, "Tocaste una bomba y perdiste", "Perdiste!", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new Object[]{"OK"}, null);
                    if (result == JOptionPane.OK_OPTION) {
                    System.exit(0);
                }
                        }
                }
            });
            
            
            boton.setBounds(x*50, y*50, 49, 49);
            frame.add(boton);
        }
        
        /* elegir cuales botones serian bombas */
        for (int i = 0; i <= numBombas; i++){
            int numrandom = rand.nextInt(numBotones);
            listabotones[numrandom].putClientProperty("Bomba", true);
        }
        
        
        
        frame.setVisible(true);
    }
}
