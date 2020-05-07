/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.io.*;
import javax.swing.JFileChooser;

/**
 *
 * @author Juan Pablo Castro 2019 GitHub: jpcv222
 */
public class FileManage {

    String ruta;

    public FileManage() {
        ruta = null;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public String calcularRutaArchivo() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.showOpenDialog(fileChooser);
        String result_ruta = null;
        try {
            result_ruta = fileChooser.getSelectedFile().getAbsolutePath();
            this.setRuta(result_ruta);
        } catch (Exception e) {
            //logs.escribirExceptionLogs(Thread.currentThread().getStackTrace()[1].getMethodName() + "// " + e.getMessage() + " " + e.toString());
        }
        return result_ruta;
    }

    public static String selectFile(String ruta_alt) {
        String response;
        if (ruta_alt != null) {
            response = ruta_alt;
        } else {
            response = "Archivo TXT, ruta errónea.";
            //logs.escribirErrorLogs(Thread.currentThread().getStackTrace()[1].getMethodName() + "// Archivo CSV, ruta errónea.");
        }
        return response;
    }

    public static String readFile(String url_file) {
        String response = "error.unknow";
        String archTXT = url_file;

        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;

        try {
            // Apertura del fichero y creacion de BufferedReader para poder
            // hacer una lectura comoda (disponer del metodo readLine()).
            archivo = new File(archTXT);
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);

            // Lectura del fichero
            String linea;
            while ((linea = br.readLine()) != null) {
                System.out.println(linea);
            }
            response = "success";
        } catch (IOException ex) {
            //logs.escribirExceptionLogs(Thread.currentThread().getStackTrace()[1].getMethodName() + "// " + ex.getMessage() + " " + ex.toString());
        } catch (NoClassDefFoundError ex) {
            System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName() + "// " + ex.getMessage() + " " + ex.toString());
            response = "error.noclassdeffounderror";
        } catch (NullPointerException ex) {
            //logs.escribirExceptionLogs(Thread.currentThread().getStackTrace()[1].getMethodName() + "// " + ex.getMessage() + " " + ex.toString());
            response = "error.nullpointerexception";
        } finally {
            // En el finally cerramos el fichero, para asegurarnos
            // que se cierra tanto si todo va bien como si salta 
            // una excepcion.
            try {
                if (null != fr) {
                    fr.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }

        return response;

    }

}
