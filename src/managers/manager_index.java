/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managers;

import classes.Animal;
import classes.FileManage;
import classes.Part;
import classes.Scene;
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
    //private Animal[][] apertura;
    //private Animal[][][] partes;

    Part[] parts;
    Scene [] opening;
    private int complejidad;

    public int getComplejidad() {
        return complejidad;
    }

    public void setComplejidad(int complejidad) {
        this.complejidad = complejidad;
    }

    public manager_index(Index index) {
        this.index = index;
        this.file = new FileManage();
        this.modal = new Dialogs();
        this.n = 0;
        this.m = 0;
        this.k = 0;
        this.complejidad = 0;
    }

    public void zooSolution() {
        switch (this.complejidad) {
            case 0:
                //Solución para complejidad n

                break;
            case 1:
                //Solución para complejidad nlogn

                break;
            case 2:
                //Solución para complejidad n^2
                cuadraticComplexity();
                break;
        }
    }

    public void cuadraticComplexity() {

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
        String data_processing;
        String[] animals_array, greatness_array;

        //Var n
        data_processing = data.get(0);
        data_processing = data_processing.replaceAll(" ", "");
        data_processing = data_processing.replaceAll(";", "");
        data_processing = data_processing.split("=")[1];
        this.n = Integer.parseInt(data_processing);

        //Var m
        data_processing = data.get(1);
        data_processing = data_processing.replaceAll(" ", "");
        data_processing = data_processing.replaceAll(";", "");
        data_processing = data_processing.split("=")[1];
        this.m = Integer.parseInt(data_processing);

        //Var k
        data_processing = data.get(2);
        data_processing = data_processing.replaceAll(" ", "");
        data_processing = data_processing.replaceAll(";", "");
        data_processing = data_processing.split("=")[1];
        this.k = Integer.parseInt(data_processing);

        //Var animals
        String name;
        int greatness;

        data_processing = data.get(3);
        data_processing = data_processing.replaceAll(" ", "");
        data_processing = data_processing.replaceAll(";", "");
        data_processing = data_processing.split("=")[1];
        data_processing = data_processing.replaceAll("\\{", "");
        data_processing = data_processing.replaceAll("}", "");
        animals_array = data_processing.split(",");

        data_processing = data.get(4);
        data_processing = data_processing.replaceAll(" ", "");
        data_processing = data_processing.replaceAll(";", "");
        data_processing = data_processing.split("=")[1];
        data_processing = data_processing.replaceAll("\\{", "");
        data_processing = data_processing.replaceAll("}", "");
        greatness_array = data_processing.split(",");

        this.animales = new Animal[animals_array.length];

        for (int i = 0; i < animals_array.length; i++) {

            name = animals_array[i];
            greatness = Integer.parseInt(greatness_array[i]);

            Animal animal = new Animal(name, greatness);
            this.animales[i] = animal;
        }

        // Var apertura
        String[] parte_aux;
        data_processing = data.get(5);
        data_processing = data_processing.replaceAll("\\{", "");
        data_processing = data_processing.replaceAll("}};", "");
        data_processing = data_processing.split("=")[1];

        //Extrae como una cadena las escenas
        parte_aux = data_processing.split("},");

        String[] escena_aux;
        Animal[] escena = new Animal[3];
        this.opening = new Scene[parte_aux.length];

        for (int i = 0; i < parte_aux.length; i++) {

            //Extrae nombres de animales de cada escena en una variable auxiliar
            escena_aux = parte_aux[i].split(",");
            
            //Inicializa un objeto escena en cada posición del arreglo opening
            this.opening[i] = new Scene();
            
            //Traduce nombres de animales de una escena a Objeto Animal
            //El arreglo escena contendrá los Animales de una escena
            for (int j = 0; j < escena_aux.length; j++) {
                
                //Agregar animal a una escena de opening
                this.opening[i].animales[j] = getAnimalByName(escena_aux[j].replaceAll(" ", ""));
                //System.out.println("Escena " + i + " Animal " + this.apertura[i][j].getName());
            }

            //Ingresa cada escena de Animales en la parte apertura
            //this.apertura[i] = escena;
        }
        
        for (int j = 0; j < this.opening.length; j++) {
                for (int l = 0; l < 3; l++) {
                    System.out.println("Apertura Escena " + j + " Animal " + this.opening[j].getAnimales()[l].getName());
                }
            }

        //Var partes
        //this.partes = new Animal[this.m - 1][this.k][3];
        int count_partes = 0;
        this.parts = new Part[this.m - 1];

        Animal[] animals_aux = new Animal[3];

        for (int h = 6; h < data.size(); h++) {

            data_processing = data.get(h);
            data_processing = data_processing.replaceAll("\\{", "");
            data_processing = data_processing.replaceAll("}};", "");
            data_processing = data_processing.split("=")[1];

            //Extrae como una cadena las escenas
            parte_aux = data_processing.split("},");
            
            //Inicializa un objeto Parte en el arreglo de partes 
            this.parts[count_partes] = new Part(this.k);

            for (int i = 0; i < parte_aux.length; i++) {

                //Extrae nombres de animales de cada escena en una variable auxiliar
                escena_aux = parte_aux[i].split(",");
                
                //Inicializa un objeto escena en el arreglo de escenas de cada parte
                this.parts[count_partes].scenes[i] = new Scene();
                
                //Traduce nombres de animales de una escena a Objeto Animal
                //El arreglo escena contendrá los Animales de una escena
                for (int j = 0; j < escena_aux.length; j++) {

                    //this.partes[count_partes][i][j] = getAnimalByName(escena_aux[j].replaceAll(" ", ""));
                    //System.out.println(this.parts[count_partes]);
                    //System.out.println(this.parts[count_partes].scenes[i]);
                    //System.out.println(this.parts[count_partes].scenes[i].animales[j]);
                    
                    //Agregar animal a una escena de una parte
                    this.parts[count_partes].scenes[i].animales[j] = getAnimalByName(escena_aux[j].replaceAll(" ", ""));
                    //this.parts[count_partes].scenes[i].animales[j] = getAnimalByName(escena_aux[j].replaceAll(" ", ""));
                    //System.out.println("Escena " + i + " Animal " + this.partes[count_partes][i][j].getName());
                }

            }
            
            //Coontador para las m-1 partes
            count_partes++;
            //Ingresa cada parte en el arreglo de partes
            //this.partes[h] = animal_parte_aux;
        }

        
        for (int i = 0; i < this.m - 1; i++) {
            for (int j = 0; j < this.k; j++) {
                for (int l = 0; l < 3; l++) {
                    System.out.println("Parte  " + i + " Escena " + j + " Animal " + this.parts[i].getScenes()[j].getAnimales()[l].getName());
                }
            }
        }
    }

    public Animal getAnimalByName(String name) {
        Animal animal = null;
        for (int i = 0; i < this.animales.length; i++) {
            if (this.animales[i].getName().equals(name)) {
                animal = this.animales[i];
                break;
            }
        }

        return animal;
    }

    public void resultUpload(ArrayList<Object> response, String type) {
        try {
            switch (response.get(0).toString()) {
                case "success":
                    modal.success_message("Carga masiva de " + type + ".", "Éxito al cargar archivo.", "Los " + type + " fueron cargados con éxito.", null, null);
                    // logs.escribirAccessLogs(Thread.currentThread().getStackTrace()[1].getMethodName() + "// Los " + type + " fueron cargados con éxito.");
                    loadData((ArrayList<String>) response.get(1));
                    //zooSolution();
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
