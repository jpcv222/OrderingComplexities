/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managers;

import classes.Animal;
import classes.FileManage;
import components.Dialogs;
import java.util.ArrayList;
import views.Index;

/**
 *
 * @author jpcv2
 */
public class manager_index {

    private final FileManage file;
    private Index index;
    private Dialogs modal;
    private Animal[] animales;
    private int n, m, k;
    private String [] apertura;

    public manager_index(Index index) {
        this.index = index;
        this.file = new FileManage();
        this.modal = new Dialogs();
        this.n = 0;
        this.m = 0;
        this.k = 0;
    }

    public void validateBtCargar() {
        String value = index.jLabelRutaArchivo.getText();
        if ((!value.equals("Archivo TXT")) && value.contains(".txt")) {
            index.jButtonCargar.setEnabled(true);
        } else {
            index.jButtonCargar.setEnabled(false);
        }
    }

    public void selectFile() {
        String response = FileManage.selectFile(file.calcularRutaArchivo());
        index.jLabelRutaArchivo.setText(response);
        validateBtCargar();

    }

    public void readTXTFile() {
        ArrayList<Object> response = FileManage.readFile(file.getRuta());
        resultUpload(response, "usuarios");
    }

    public void loadData(ArrayList<String> data) {
        String data_proccesing;
        String[] animals_array, greatness_array;

        //Var n
        data_proccesing = data.get(0);
        data_proccesing = data_proccesing.replaceAll(" ", "");
        data_proccesing = data_proccesing.replaceAll(";", "");
        data_proccesing = data_proccesing.split("=")[1];
        this.n = Integer.parseInt(data_proccesing);

        //Var m
        data_proccesing = data.get(1);
        data_proccesing = data_proccesing.replaceAll(" ", "");
        data_proccesing = data_proccesing.replaceAll(";", "");
        data_proccesing = data_proccesing.split("=")[1];
        this.n = Integer.parseInt(data_proccesing);

        //Var k
        data_proccesing = data.get(2);
        data_proccesing = data_proccesing.replaceAll(" ", "");
        data_proccesing = data_proccesing.replaceAll(";", "");
        data_proccesing = data_proccesing.split("=")[1];
        this.n = Integer.parseInt(data_proccesing);

        //Var animals
        String name;
        int greatness;

        data_proccesing = data.get(3);
        data_proccesing = data_proccesing.replaceAll(" ", "");
        data_proccesing = data_proccesing.replaceAll(";", "");
        data_proccesing = data_proccesing.split("=")[1];
        data_proccesing = data_proccesing.replaceAll("{", "");
        data_proccesing = data_proccesing.replaceAll("}", "");
        animals_array = data_proccesing.split(",");

        data_proccesing = data.get(4);
        data_proccesing = data_proccesing.replaceAll(" ", "");
        data_proccesing = data_proccesing.replaceAll(";", "");
        data_proccesing = data_proccesing.split("=")[1];
        data_proccesing = data_proccesing.replaceAll("{", "");
        data_proccesing = data_proccesing.replaceAll("}", "");
        greatness_array = data_proccesing.split(",");

        this.animales = new Animal[animals_array.length];

        for (int i = 0; i < animals_array.length; i++) {

            name = animals_array[i];
            greatness = Integer.parseInt(greatness_array[i]);

            Animal animal = new Animal(name, greatness);
            this.animales[i] = animal;
        }

        // Var apertura
        String [] apertura_aux; 
        data_proccesing = data.get(5);
        data_proccesing = data_proccesing.replaceAll("{", "");
        data_proccesing = data_proccesing.replaceAll("}}:", "");
        data_proccesing = data_proccesing.split("=")[1];
        
        //Extrae como una cadena las escenas
        apertura_aux = data_proccesing.split("},");
        
        
        data_proccesing = data_proccesing.replaceAll(";", "");
        data_proccesing = data_proccesing.replaceAll("{", "");
        data_proccesing = data_proccesing.replaceAll("}", "");
        greatness_array = data_proccesing.split(",");

        for (int i = 5; i < data.size(); i++) {
            if (i == 0) {

                //this.n = data.get(i);
            }
            System.out.println("Líena: " + i + " Dato: " + data.get(i).replaceAll(";", ""));
        }
    }

    public void resultUpload(ArrayList<Object> response, String type) {
        try {
            switch (response.get(0).toString()) {
                case "success":
                    modal.success_message("Carga masiva de " + type + ".", "Éxito al cargar archivo.", "Los " + type + " fueron cargados con éxito.", null, null);
                    // logs.escribirAccessLogs(Thread.currentThread().getStackTrace()[1].getMethodName() + "// Los " + type + " fueron cargados con éxito.");
                    loadData((ArrayList<String>) response.get(1));
                    break;
                case "error.noclassdeffounderror":
                    modal.error_message("Carga masiva de " + type + ".", "Error fatal.", "Librería de lectura de archivo extraviada.", "Comuníquese con el área de sistemas.", null);
                    // logs.escribirErrorLogs(Thread.currentThread().getStackTrace()[1].getMethodName() + "// Librería para carga CSV no encontrada.");
                    break;
                case "error.nullpointerexception":
                    modal.error_message("Carga masiva de " + type + ".", "Lectura archivo CSV errónea.", "El archivo CSV es null.", null, null);
                    // logs.escribirErrorLogs(Thread.currentThread().getStackTrace()[1].getMethodName() + "// Archivo CSV erróneo.");
                    break;
                case "error.unknow":
                    modal.error_message("Carga masiva de " + type + ".", "Lectura archivo CSV errónea.", "Error desconocido.", "Comuníquese con el área de sistemas.", null);
                    // logs.escribirErrorLogs(Thread.currentThread().getStackTrace()[1].getMethodName() + "// Error desconocidoa.");
                    break;
                default:
                    //  logs.escribirErrorLogs(Thread.currentThread().getStackTrace()[1].getMethodName() + "// Respuesta a petición inválida.");
                    break;
            }

        } catch (NullPointerException ex) {
            //logs.escribirExceptionLogs(Thread.currentThread().getStackTrace()[1].getMethodName() + "// " + ex.getMessage() + " " + ex.toString());
        }
    }
}
