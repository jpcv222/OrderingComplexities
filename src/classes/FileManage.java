/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JFileChooser;

/**
 *
 * @author Juan Pablo Castro 2019 GitHub: jpcv222
 */
public class FileManage {

    String ruta;
    String file_content;
    String directorioRaiz = System.getProperty("user.dir");
    String outputDir = directorioRaiz + "/files/output/";

    public FileManage() {
        ruta = null;
        file_content = null;
    }

    public String getFile_content() {
        return file_content;
    }

    public void setFile_content(String file_content) {
        this.file_content = file_content;
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

    public static ArrayList<Object> readFile(String url_file) {
        ArrayList<Object> result = new ArrayList<>();
        String response = "error.unknow";
        ArrayList<String> content = new ArrayList<>();
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
                content.add(linea);
            }

            response = "success";
        } catch (IOException ex) {
            //logs.escribirExceptionLogs(Thread.currentThread().getStackTrace()[1].getMethodName() + "// " + ex.getMessage() + " " + ex.toString());
        } catch (NoClassDefFoundError ex) {
            //System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName() + "// " + ex.getMessage() + " " + ex.toString());
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
            result.add(response);
            result.add(content);
        }

        return result;

    }

    public String createFile(ArrayList<String> cadena) {
        String response = "error.unknow";
        FileWriter flwriter = null;
        String ruta;
        Date date = new Date();
        DateFormat hourdateFormat = new SimpleDateFormat("dd-MM-yyyy-HH-mm-ss");
        String name_file = "zooResults-" + hourdateFormat.format(date) + ".txt";
        ruta = this.outputDir + name_file;
        try {

            flwriter = new FileWriter(ruta);

            BufferedWriter bfwriter = new BufferedWriter(flwriter);
            for (int i = 0; i < cadena.size(); i++) {
                bfwriter.write(cadena.get(i));
                bfwriter.newLine();
            }

            bfwriter.close();
            response = "success;"+name_file;

        } catch (IOException e) {
            response = "error";
            e.printStackTrace();
        } finally {
            if (flwriter != null) {
                try {//cierra el flujo principal
                    flwriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        
        return response;
    }

}
