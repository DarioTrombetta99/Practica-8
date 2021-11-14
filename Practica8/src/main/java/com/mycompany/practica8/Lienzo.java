/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.practica8;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/**
 *
 * @author Dario Trombetta y Piotr Pietruszynzky
 */
public class Lienzo extends javax.swing.JPanel {

    private BufferedImage image = null;
    private BufferedImage displayed = null;
    private File file = null;
    
    public Lienzo() {
    }
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(displayed,0,0,null);
    }
    
    void setFile(File f){
        file = f;
        try {
                URL url = new URL("file:///"+file.getAbsolutePath());
                System.out.println(""+url);
            image = ImageIO.read(url);      
            displayed = image;
            this.setPreferredSize(getPreferredSize());
        } catch (MalformedURLException ex) {
            Logger.getLogger(Lienzo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Lienzo.class.getName()).log(Level.SEVERE, null, ex);
        }
        repaint();
    }
    @Override
    public Dimension getPreferredSize() {
        if (image == null) {
             return new Dimension(280, 308);
        } else {
           return new Dimension(image.getWidth(null), image.getHeight(null));
       }
    }
    
    private Mat umbralizar(Mat imagen_original, Integer umbral) {
        // crear dos imágenes en niveles de gris con el mismo
        // tamaño que la original
        Mat imagenGris = new Mat(imagen_original.rows(),imagen_original.cols(),CvType.CV_8U);
        Mat imagenUmbralizada = new Mat(imagen_original.rows(),imagen_original.cols(),CvType.CV_8U);
        // convierte a niveles de grises la imagen original
        Imgproc.cvtColor(imagen_original,imagenGris,Imgproc.COLOR_BGR2GRAY);
        // umbraliza la imagen:
        // - píxeles con nivel de gris > umbral se ponen a 1
        // - píxeles con nivel de gris <= umbra se ponen a 0
        Imgproc.threshold(imagenGris,imagenUmbralizada,umbral,255,Imgproc.THRESH_BINARY);
        return imagenUmbralizada;
    }
    
    public void threshold(File file, Integer umbral) {
        Mat image_original = Imgcodecs.imread(file.getAbsolutePath());
        displayed = (BufferedImage) HighGui.toBufferedImage(umbralizar(image_original,umbral));
    }
        
    public void save(File outputFile) throws IOException{
        ImageIO.write(displayed, "png",outputFile);
    }
    
    public void exit(){
        Graphics g = this.getGraphics();
        g.clearRect(0, 0, displayed.getWidth(), displayed.getHeight());
        image=null;
        displayed = image;
    }
    
    public boolean imageNull(){
        return image == null; 
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setMaximumSize(new java.awt.Dimension(1024, 768));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
