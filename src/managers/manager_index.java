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
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    Part opening;
    private int complejidad;
    ArrayList<String> results;

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
        this.opening = new Part(k);
        this.parts = new Part[n];
    }

    public void zooSolution() {
        this.results = new ArrayList<>();

        long TInicio, TFin, tiempo;
        TInicio = System.nanoTime(); //Tomamos la hora en que inicio el algoritmo y la almacenamos en la variable inicio

        /*results.add("-----El desorden en del espectáculo debe ser:");
        results.add("Apertura: " + printPart(this.opening.getScenes())); //O(n)        
        for (int i = 0; i < this.parts.length; i++) { //O(n)
            results.add("Parte " + (i + 1) + ": " + printPart(this.parts[i].getScenes()) + " Grandeza: "+ this.parts[i].getOverall_greatness()); //O(n)
        }*/
         
        //Ordenar escenas dentro de apertura
        sortOpening(); //O(n)

        //Ordenar esccenas dentro de m-1 partes
        sortParts(); //O(n)

        // Ordenar m-1 partes del evento
        switch (this.complejidad) {
            case 0:
                //Solución para complejidad n
                results.add("Complejidad O(n)");
                linearComplexity(); //O(n)
                break;
            case 1:
                //Solución para complejidad nlogn
                results.add("Complejidad O(nlogn)");
                nlognComplexity(); //O(nlogn)
                break;
            case 2:
                //Solución para complejidad n^2
                results.add("Complejidad O(n^2)");
                cuadraticComplexity(); //O(n^2)
                break;
        }

        // Orden del espectáculo
        results.add("El orden en del espectáculo debe ser:");
        results.add("Apertura: " + printPart(this.opening.getScenes())); //O(n)        
        for (int i = 0; i < this.parts.length; i++) { //O(n)
            results.add("Parte " + (i + 1) + ": " + printPart(this.parts[i].getScenes())); //O(n)
        }

        // Animal que más participó en escenas del espectáculo           
        // Participaciones de animal que más participó en escenas del espectáculo        
        // Animal que menos participó en escenas del espectáculo        
        // Participaciones de animal que menos participó en escenas del espectáculo
        //-------------------------------------------------------------------------------results.add(repiteMasYmenos(opening)); //O(n^2)
        results.add(repiteMasYmenos2(opening)); //O(n + k)
        // Escena de mayor grandeza total        
        // Escena de menor grandeza total
        results.add(mayorYmenorGrandeza()); //O(n)
        // Promedio de grandeza de todo el espectáculo
        results.add(promedioGrandeza(opening)); //O(n)
        TFin = System.nanoTime(); //Tomamos la hora en que finalizó el algoritmo y la almacenamos en la variable T
        tiempo = TFin - TInicio; //Calculamos los milisegundos de diferencia
        results.add("Tiempo de ejecución en nanosegundos: " + tiempo);
        printResults();

    }

    public void cuadraticComplexity() {

        System.out.println("ENTRE2 O(N2)");
        ordenaPartsInsertion(parts);
        //ordenaPartsInsertion2(this.parts);

    }

    public void crearInput() {
        int n = 9;
        int m = 21;
        int k = 10;

        ArrayList<String> salida = new ArrayList<>();
        String[] animals = new String[n];
        int [] grandeza = new int[n];
        String [] apertura = new String[(m-1)*k];
        String [][] partes =  new String[m-1][k];
        
        char letra = 'a';
        String aux;

        for (int i = 0; i < animals.length; i++) {
            aux = "";
            for (int j = 0; j < i; j++) {
                aux += letra;
            }
            animals[i] = aux;
            grandeza[i] = i+1;
        }
        
        salida.add(animals.toString());
        salida.add(grandeza.toString());
        
        for (int i = 0; i < 10; i++) {
            
        }
        
    }

    public String printPart(Scene[] arr) {
        //Complejidad O(n)
        String result = "[";
        for (int i = 0; i < arr.length; i++) {
            result += "(";
            for (int j = 0; j < 3; j++) { //O(k)
                result += arr[i].getAnimales()[j].getName();
                if (j < 2) {
                    result += ",";
                }
            }
            result += ")";
            if (i < arr.length - 1) {
                result += ",";
            }

        }
        result += "]";

        return result;
    }

    public void sortOpening() {
        //Complejidad O(n)
        int max = 0;
        //Ordenar animales de apertura
        for (int i = 0; i < this.opening.getScenes().length; i++) {
            sortAnimals(this.opening.getScenes()[i].getAnimales());
        }
        //Ordenar escenas de apertura
        max = getMaxOverallGreatnessScenes(this.opening.getScenes());
        this.opening.setScenes(countingSortScenes(this.opening.getScenes(), max));
    }

    public void sortParts() {
        //Complejida O(n)
        int max = 0;
        //Ordenar animales de m-1 partes
        for (int i = 0; i < this.parts.length; i++) {
            for (int j = 0; j < this.k; j++) {
                sortAnimals(this.parts[i].getScenes()[j].getAnimales()); // O(k)
            }
        }
        //Ordenar escenas de m-1 partes
        for (int i = 0; i < this.m - 1; i++) {
            max = getMaxOverallGreatnessScenes(this.parts[i].getScenes()); //O(n)
            this.parts[i].setScenes(countingSortScenes(this.parts[i].getScenes(), max)); //O(n)
        }
    }

    public void printResults() {
        String[] response = this.file.createFile(this.results).split(";");
        switch (response[0]) {
            case "success":
                modal.success_message("Respuesta del programa.", "Éxito al crear archivo.", "Revise su respesta en capeta", "/OrderingComplexities/files/output/", "Archivo: " + response[1]);
                // logs.escribirAccessLogs(Thread.currentThread().getStackTrace()[1].getMethodName() + "// Los " + type + " fueron cargados con éxito.");                    
                break;
            case "error":
                modal.error_message("Error al crear el archivo.", "Error fatal.", "Comuníquese con el área de sistemas.", null, null);
                // logs.escribirErrorLogs(Thread.currentThread().getStackTrace()[1].getMethodName() + "// Librería para carga CSV no encontrada.");
                break;

            default:
                //  logs.escribirErrorLogs(Thread.currentThread().getStackTrace()[1].getMethodName() + "// Respuesta a petición inválida.");
                break;
        }

    }

    public void nlognComplexity() {
        int n = parts.length;
        sortParts(parts, 0, n - 1);
    }

    public void radixsort() {
        // Find the maximum number to know number of digits 
        int m = getMaxOverallGreatnessParts(this.parts);

        // Do counting sort for every digit. Note that instead 
        // of passing digit number, exp is passed. exp is 10^i 
        // where i is current digit number 
        for (int exp = 1; m / exp > 0; exp *= 10) {
            countSort(this.parts, this.m - 1, exp);
        }
    }

    public void countSort(Part arr[], int n, int exp) {
        Part output[] = new Part[n]; // output array 
        int i;
        int count[] = new int[10];
        Arrays.fill(count, 0);

        // Store count of occurrences in count[] 
        for (i = 0; i < n; i++) {
            count[(arr[i].getOverall_greatness() / exp) % 10]++;
        }

        // Change count[i] so that count[i] now contains 
        // actual position of this digit in output[] 
        for (i = 1; i < 10; i++) {
            count[i] += count[i - 1];
        }

        // Build the output array 
        for (i = n - 1; i >= 0; i--) {
            output[count[(arr[i].getOverall_greatness() / exp) % 10] - 1] = arr[i];
            count[(arr[i].getOverall_greatness() / exp) % 10]--;
        }

        // Copy the output array to arr[], so that arr[] now 
        // contains sorted numbers according to curent digit 
        for (i = 0; i < n; i++) {
            arr[i] = output[i];
        }
    }

    public void linearComplexity() {
        int max;
        //Ordenar m - 1 partes
        System.out.println("entre O(n)");
        max = getMaxOverallGreatnessParts(this.parts); //O(n)
        //this.parts = countingSortParts(this.parts, max); //O(n)
        radixsort();

    }

    public int getMaxOverallGreatnessScenes(Scene[] arr) {
        //Complejidad O(n)
        int max = arr[0].getOverall_greatness();
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].getOverall_greatness() > max) {
                max = arr[i].getOverall_greatness();
            }
        }
        return max;
    }

    public int getMaxOverallGreatnessParts(Part[] arr) {
        //Complejidad O(n)
        int max = arr[0].getOverall_greatness();
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].getOverall_greatness() > max) {
                max = arr[i].getOverall_greatness();
            }
        }
        return max;
    }

    public void sortAnimals(Animal[] arr) {
        //Complejidad O(k)
        Animal aux;
        if (arr[0].getGreatness() > arr[1].getGreatness()) {
            aux = arr[1];
            arr[1] = arr[0];
            arr[0] = aux;
        }
        if (arr[1].getGreatness() > arr[2].getGreatness()) {
            aux = arr[2];
            arr[2] = arr[1];
            arr[1] = aux;
        }

        if (arr[0].getGreatness() > arr[1].getGreatness()) {
            aux = arr[1];
            arr[1] = arr[0];
            arr[0] = aux;
        }
    }

    public Part[] countingSortParts(Part[] arr, int max) {
        //Complejidad O(n)
        int[] conteo = new int[max + 1];
        int pos;
        Part[] aux = new Part[arr.length];
        //ArrayList<ArrayList<Part>> count_aux = new ArrayList<ArrayList<Part>>();

        for (int i = 0; i < conteo.length; i++) {
            conteo[i] = 0;
            //count_aux.add(new ArrayList<>());
        }

        for (int i = 0; i < arr.length; i++) {
            conteo[arr[i].getOverall_greatness()]++;
            //count_aux.get(arr[i].getOverall_greatness()).add(arr[i]);
        }

        for (int i = 2; i < conteo.length; i++) {
            conteo[i] = conteo[i] + conteo[i - 1];
        }

        for (int i = 0; i < arr.length; i++) {
            //pos = getMaxRepeatedGreatnessPart(count_aux.get(arr[i].getOverall_greatness()));
            aux[conteo[arr[i].getOverall_greatness()] - 1] = arr[i];
            //System.out.println("Aux pos "+ conteo[arr[i].getOverall_greatness()]);
            //count_aux.get(arr[i].getOverall_greatness()).remove(pos);
            conteo[arr[i].getOverall_greatness()]--;
        }

        return aux;
    }

    public Scene[] countingSortScenes(Scene[] arr, int max) {
        //Complejidad O(n)
        int[] conteo = new int[max + 1];
        int pos;
        Scene[] aux = new Scene[arr.length];
        ArrayList<ArrayList<Scene>> count_aux = new ArrayList<ArrayList<Scene>>();

        for (int i = 0; i < conteo.length; i++) {
            conteo[i] = 0;
            count_aux.add(new ArrayList<>());
        }

        for (int i = 0; i < arr.length; i++) {
            conteo[arr[i].getOverall_greatness()]++;
            count_aux.get(arr[i].getOverall_greatness()).add(arr[i]);
        }

        for (int i = 2; i < conteo.length; i++) {
            conteo[i] = conteo[i] + conteo[i - 1];
        }

        for (int i = 0; i < arr.length; i++) {
            pos = getMaxRepeatedGreatnessScene(count_aux.get(arr[i].getOverall_greatness()));
            aux[conteo[arr[i].getOverall_greatness()] - 1] = count_aux.get(arr[i].getOverall_greatness()).get(pos);
            //System.out.println("Aux pos "+ conteo[arr[i].getOverall_greatness()]);
            count_aux.get(arr[i].getOverall_greatness()).remove(pos);
            conteo[arr[i].getOverall_greatness()]--;
        }

        return aux;

    }

    public int getMaxRepeatedGreatnessPart(ArrayList<Part> arreglo) {
        Scene[] aux;
        int grandAux;
        int posicion = 0;
        //asumo que la primera escena del arreglo es la que tiene el animal de mayor grandeza
        Scene[] mayorGrandeza = arreglo.get(0).getScenes();
        int maxGreat = mayorGrandeza[arreglo.size() - 1].getOverall_greatness();

        int grandeza;

        //for inicia desde 1 porque ya asumi que la escena de la posicion 0 es la que tiene animal con mayor grandeza
        for (int j = 1; j < arreglo.size(); j++) {
            aux = arreglo.get(j).getScenes();
            grandAux = aux[aux.length - 1].getOverall_greatness();

            if (grandAux > maxGreat) {
                maxGreat = grandAux;
                posicion = j;
            }

        }
        return posicion;
    }

    public int getMaxRepeatedGreatnessScene(ArrayList<Scene> arreglo) {
        Animal[] aux;
        int grandAux;
        int posicion = 0;
        //asumo que la primera escena del arreglo es la que tiene el animal de mayor grandeza
        Animal[] mayorGrandeza = arreglo.get(0).getAnimales();
        int maxGreat = mayorGrandeza[2].getGreatness();

        int grandeza;

        //for inicia desde 1 porque ya asumi que la escena de la posicion 0 es la que tiene animal con mayor grandeza
        for (int j = 1; j < arreglo.size(); j++) {
            aux = arreglo.get(j).getAnimales();
            grandAux = aux[2].getGreatness();

            if (grandAux > maxGreat) {
                maxGreat = grandAux;
                posicion = j;
            }

        }
        return posicion;

    }

    public void printParts(Part[] arr) {

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
        resultUpload(response, "espectáculo");
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
        this.opening = new Part(parte_aux.length);

        for (int i = 0; i < parte_aux.length; i++) {

            greatness = 0;

            //Extrae nombres de animales de cada escena en una variable auxiliar
            escena_aux = parte_aux[i].split(",");

            //Inicializa un objeto escena en cada posición del arreglo opening
            this.opening.scenes[i] = new Scene();

            //Traduce nombres de animales de una escena a Objeto Animal
            //El arreglo escena contendrá los Animales de una escena
            for (int j = 0; j < escena_aux.length; j++) {
                //Agregar animal a una escena de opening
                this.opening.scenes[i].animales[j] = getAnimalByName(escena_aux[j].replaceAll(" ", ""));
                //System.out.println("Escena " + i + " Animal " + this.apertura[i][j].getName());

                greatness += this.opening.scenes[i].animales[j].getGreatness();
            }
            this.opening.scenes[i].setOverall_greatness(greatness);
            //Ingresa cada escena de Animales en la parte apertura
            //this.apertura[i] = escena;
        }

        /* for (int j = 0; j < this.opening.getScenes().length; j++) {
            for (int l = 0; l < 3; l++) {
                System.out.println("Apertura Escena " + j + " Animal " + this.opening.getScenes()[j].getAnimales()[l].getName());
                System.out.println("Grandeza escena " + this.opening.getScenes()[j].getOverall_greatness());
            }
        }*/
        //Var partes
        //this.partes = new Animal[this.m - 1][this.k][3];
        int count_partes = 0;
        this.parts = new Part[this.m - 1];

        Animal[] animals_aux = new Animal[3];
        int greatness_part;

        for (int h = 6; h < data.size(); h++) {

            data_processing = data.get(h);
            data_processing = data_processing.replaceAll("\\{", "");
            data_processing = data_processing.replaceAll("}};", "");
            data_processing = data_processing.split("=")[1];

            //Extrae como una cadena las escenas
            parte_aux = data_processing.split("},");

            greatness_part = 0;

            //Inicializa un objeto Parte en el arreglo de partes 
            this.parts[count_partes] = new Part(this.k);

            for (int i = 0; i < parte_aux.length; i++) {

                greatness = 0;
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
                    greatness += this.parts[count_partes].scenes[i].animales[j].getGreatness();
                }
                this.parts[count_partes].scenes[i].setOverall_greatness(greatness);
                greatness_part += this.parts[count_partes].scenes[i].getOverall_greatness();
            }

            this.parts[count_partes].setOverall_greatness(greatness_part);

            //Coontador para las m-1 partes
            count_partes++;
            //Ingresa cada parte en el arreglo de partes
            //this.partes[h] = animal_parte_aux;
        }

        /*for (int i = 0; i < this.m - 1; i++) {
            for (int j = 0; j < this.k; j++) {
                for (int l = 0; l < 3; l++) {
                    System.out.println("Parte  " + i + " Escena " + j + " Animal "
                            + this.parts[i].getScenes()[j].getAnimales()[l].getName());
                     System.out.println("Grandeza parte " + this.parts[i].getOverall_greatness() + " Grandeza escena " +this.parts[i].getScenes()[j].getOverall_greatness());

                    System.out.println("Grandeza parte " + this.parts[i].getOverall_greatness() + " Grandeza escena " + this.parts[i].getScenes()[j].getOverall_greatness());

                }
            }
        }*/
    }

    //Ordena la apertura con costo O(n2) INSERTION SORT
    public void ordenaOpeningInsertion(Part part) {
        ArrayList<Scene> openingAux = new ArrayList<>();

        for (int i = 0; i < part.getScenes().length; i++) {
            openingAux.add(part.getScenes()[i]);
        }
        for (int i = 1; i < part.getScenes().length; i++) {
            Scene key = new Scene(part.getScenes()[i].getAnimales(), part.getScenes()[i].getOverall_greatness());

            int j = i - 1;
            while (j >= 0 && openingAux.get(j).getOverall_greatness() > key.getOverall_greatness()) {
                openingAux.get(j + 1).setAnimales(openingAux.get(j).getAnimales());
                openingAux.get(j + 1).setOverall_greatness(openingAux.get(j).getOverall_greatness());

                j = j - 1;
            }
            openingAux.get(j + 1).setAnimales(key.getAnimales());
            openingAux.get(j + 1).setOverall_greatness(key.getOverall_greatness());
        }

        for (int i = 0; i < openingAux.size(); i++) {
            part.getScenes()[i] = openingAux.get(i);
        }
    }

    //Ordena las partes por grandeza total de la parte, complejidad O(ncuadrado) - Insertion Sort
    public void ordenaPartsInsertion(Part[] part) {
        for (int i = 1; i < part.length; i++) {
            Part key = new Part(part[i].getOverall_greatness(), part[i].getScenes());

            int j = i - 1;
            while (j >= 0 && part[j].getOverall_greatness() > key.getOverall_greatness()) {
                part[j + 1].setOverall_greatness(part[j].getOverall_greatness());
                part[j + 1].setScenes(part[j].getScenes());

                j = j - 1;
            }
            part[j + 1].setOverall_greatness(key.getOverall_greatness());
            part[j + 1].setScenes(key.getScenes());
        }
    }

    public void ordenaPartsInsertion2(Part[] part) {
        Part aux;
        for (int i = 0; i < part.length; i++) {
            for (int j = 1; j < part.length; j++) {
                if (part[j].getOverall_greatness() < part[i].getOverall_greatness()) {
                    aux = part[i];
                    part[i] = part[j];
                    part[j] = aux;
                }
            }
        }
    }

    //Hallar el animal que aparece en mas y menos escenas
    public String repiteMasYmenos2(Part part) {
        //Complejidad O(n)
        String mensaje = "";
        Scene[] arr = part.getScenes();

        //Animal de grandeza máxima en toda la parte para iniciar conteo
        int max = arr[0].getAnimales()[2].getGreatness();
        for (int i = 0; i < arr.length; i++) { //O(n)
            if (arr[i].getAnimales()[2].getGreatness() > max) { //O(n)
                max = arr[i].getAnimales()[2].getGreatness(); //O(1)
            }
        }

        int pos;
        ArrayList<ArrayList<Animal>> count_aux = new ArrayList<ArrayList<Animal>>();

        //count_aux es un arreglo de listas de animales. En cada posición contiene un arreglo de animales. 
        //Su tamaño es max + 1; es decir, su tamaño es igual a la grandeza máxima de un animal en toda la parte
        //Esto hace posible que en cada posición se almacene una lista de animales de la misma grandeza.
        for (int i = 0; i < max + 1; i++) { //O(n), sabemos que la grandeza máxima que un animal puede tomar es inferior a 3n
            count_aux.add(new ArrayList<>());
        }

        //Llenar arreglo con lista de animales de la misma grandeza en cada posición
        for (int i = 0; i < arr.length; i++) { //O(n), sabemos que las escenas son menores a n
            for (int j = 0; j < 3; j++) { //O(3), sabemos que hay tres animales en cada escenas
                count_aux.get(arr[i].getAnimales()[j].getGreatness()).add(arr[i].getAnimales()[j]);
            }
        }

        //Recorrer arreglo de listas de animales. 
        //pos_mayor_repeticion (guarda posición del Arreglo de animales que mas animales tiene)
        //pos_menor_repeticion (guarda posición del Arreglo de animales que menos animales tiene)
        int pos_mayor_repeticion = 0, pos_menor_repeticion = 1;

        for (int i = 1; i < count_aux.size(); i++) { //O(n), sabemos que count_aux es de tamaño inferior a 3n
            //Valida si el tamaño del arreglo de animales de la posición i 
            //es mayor al tamaño del arreglo de animales de la posición pos_mayor_repeticion
            //En caso true, cambia pos_mayor_repeticion a i
            if (count_aux.get(i).size() > count_aux.get(pos_mayor_repeticion).size()) {
                pos_mayor_repeticion = i;
            }

            //Valida si el tamaño del arreglo de animales de la posición i 
            //es menor al tamaño del arreglo de animales de la posición pos_menor_repeticion
            //En caso true, cambia pos_menor_repeticion a i
            if (count_aux.get(i).size() < count_aux.get(pos_menor_repeticion).size()) {
                pos_menor_repeticion = i;
            }
        }

        //Retornar mensaje
        mensaje = "\nEl animal que participó en más escenas dentro del espectaculo fue: \n";
        for (int i = 0; i < count_aux.size(); i++) {
            for (int j = 0; j < count_aux.get(i).size(); j++) { //O(n), sabemos que hay máximo n animales
                //Valida si una lista de animales tiene la misma cantidad de animales que la lista de animales con mayor cantidad de animales
                //Es decir, valida todos los animales que participaron más veces dentro del espectáculo
                if (count_aux.get(i).size() == count_aux.get(pos_mayor_repeticion).size()) {

                    mensaje += count_aux.get(i).get(0).getName()
                            + " con " + count_aux.get(i).size() + " escenas.\n";

                    j = count_aux.get(i).size(); // j cambia su valor para no entrar más al ciclo. Es innecesario, ya que todos los animales en esa lista tienen la misma grandeza
                }
            }
        }

        mensaje += "\nEl animal que menos participo en escenas dentro del espectaculo fue: \n";
        for (int i = 0; i < count_aux.size(); i++) {
            for (int j = 0; j < count_aux.get(i).size(); j++) { //O(n), sabemos que hay máximo n animales
                //Valida si una lista de animales tiene la misma cantidad de animales que la lista de animales con menor cantidad de animales
                //Es decir, valida todos los animales que participaron menos veces dentro del espectáculo
                if (count_aux.get(i).size() == count_aux.get(pos_menor_repeticion).size()) {

                    mensaje += count_aux.get(i).get(0).getName()
                            + " con " + count_aux.get(i).size() + " escenas.\n";

                    j = count_aux.get(i).size(); // j cambia su valor para no entrar más al ciclo. Es innecesario, ya que todos los animales en esa lista tienen la misma grandeza
                }
            }
        }

        return mensaje;
    }

    //Hallar el animal que aparece en mas escenas
    public String repiteMasYmenos(Part part) {
        //Complejidad O(n^2)
        String dato = "", valorMayor = "", valorMenor = "";
        String mensaje = "";
        int repeticionesMayor = 0;
        int repeticionesMenor = part.getScenes().length;

        ArrayList<String> repiteMasAux = new ArrayList<>();
        ArrayList<String> repiteMenosAux = new ArrayList<>();

        for (int i = 0; i < part.getScenes().length; i++) {
            for (int j = 0; j < 3; j++) {
                dato = part.getScenes()[i].getAnimales()[j].getName();
                if (repeticionesMayor < verifica(dato, part)) {
                    repeticionesMayor = verifica(dato, part);
                    valorMayor = part.getScenes()[i].getAnimales()[j].getName();
                }
                if (repeticionesMenor > verifica(dato, part)) {
                    repeticionesMenor = verifica(dato, part);
                    valorMenor = part.getScenes()[i].getAnimales()[j].getName();
                }
            }
        }
        repiteMasAux.add(valorMayor);
        repiteMenosAux.add(valorMenor);

        for (int i = 0; i < part.getScenes().length; i++) {
            for (int j = 0; j < 3; j++) {
                dato = part.getScenes()[i].getAnimales()[j].getName();

                if (repeticionesMayor == verifica(dato, part) && dato != valorMayor) {
                    repiteMasAux.add(part.getScenes()[i].getAnimales()[j].getName());
                }
            }
        }
        for (int i = 0; i < part.getScenes().length; i++) {
            for (int j = 0; j < 3; j++) {
                dato = part.getScenes()[i].getAnimales()[j].getName();

                if (repeticionesMenor == verifica(dato, part) && dato != valorMenor) {
                    repiteMenosAux.add(part.getScenes()[i].getAnimales()[j].getName());
                }
            }
        }
        for (int i = 0; i < repiteMasAux.size(); i++) {
            for (int j = 0; j < repiteMasAux.size() - 1; j++) {
                if (i != j) {
                    if (repiteMasAux.get(i).equals(repiteMasAux.get(j))) {
                        repiteMasAux.remove(i);
                    }
                }
            }
        }
        for (int i = 0; i < repiteMenosAux.size(); i++) {
            for (int j = 0; j < repiteMenosAux.size() - 1; j++) {
                if (i != j) {
                    if (repiteMenosAux.get(i).equals(repiteMenosAux.get(j))) {
                        repiteMenosAux.remove(i);
                    }
                }
            }
        }
        String repiteMas = "";
        String repiteMenos = "";
        for (int i = 0; i < repiteMasAux.size(); i++) {
            repiteMas += repiteMasAux.get(i) + "\n";
        }
        for (int i = 0; i < repiteMenosAux.size(); i++) {
            repiteMenos += repiteMenosAux.get(i) + "\n";
        }

        mensaje = "\nEl animal que participo en mas escenas dentro del espectaculo fue: \n"
                + repiteMas
                + "con " + repeticionesMayor * 2 + " escenas.\n";
        mensaje += "\nEl animal que menos participo en escenas dentro del espectaculo fue: \n"
                + repiteMenos
                + "con " + repeticionesMenor * 2 + " escenas.\n";
        return mensaje;
    }

    public int verifica(String dato, Part part) {
        int x = 0;

        for (int i = 0; i < part.getScenes().length; i++) {
            for (int j = 0; j < 3; j++) {
                if (dato.equals(part.getScenes()[i].getAnimales()[j].getName())) {
                    x++;
                }
            }
        }
        return x;
    }

    //Ordena la parte por grandeza del animal llamando al metodo sortAnimals
    public void ordenaGrandezaAnimal(Part part) {
        String cadena = "[|";
        for (int i = 0; i < part.getScenes().length; i++) {
            sortAnimals(part.getScenes()[i].getAnimales());
        }
        for (int i = 0; i < part.getScenes().length; i++) {
            for (int j = 0; j < 3; j++) {
                cadena += part.getScenes()[i].getAnimales()[j].getName() + " ";
            }
            cadena += "|";
        }
        cadena += "]";
        System.out.println(cadena);
    }

    //Ordena las partes por grandeza total de la parte, costo o(nlogn) MERGUE SORT-----------------------------------
    public void sortParts(Part[] part, int left, int right) {
        if (left < right) {
            //Encuentra el punto medio del vector.
            int middle = (left + right) / 2;
            //Divide la primera y segunda mitad (llamada recursiva).
            sortParts(part, left, middle);
            sortParts(part, middle + 1, right);

            //Une las mitades.
            OrdenaPartMerge(part, left, middle, right);
        }
    }

    public void OrdenaPartMerge(Part[] part, int left, int middle, int right) {
        //Encuentra el tamaño de los sub-vectores para unirlos.
        int n1 = middle - left + 1;
        int n2 = right - middle;

        //Vectores temporales.
        Part leftArray[] = new Part[n1];
        Part rightArray[] = new Part[n2];

        //Copia los datos a los arrays temporales.
        for (int i = 0; i < n1; i++) {
            leftArray[i] = part[left + i];
        }
        for (int j = 0; j < n2; j++) {
            rightArray[j] = part[middle + j + 1];
        }
        /* Une los vectores temporales. */

        //Índices inicial del primer y segundo sub-vector.
        int i = 0, j = 0;

        //Índice inicial del sub-vector arr[].
        int k = left;

        //Ordenamiento.
        while (i < n1 && j < n2) {
            if (leftArray[i].getOverall_greatness() <= rightArray[j].getOverall_greatness()) {
                part[k] = leftArray[i];
                i++;
            } else {
                part[k] = rightArray[j];
                j++;
            }
            k++;
        }//Fin del while.

        /* Si quedan elementos por ordenar */
        //Copiar los elementos restantes de leftArray[].
        while (i < n1) {
            part[k] = leftArray[i];
            i++;
            k++;
        }
        //Copiar los elementos restantes de rightArray[].
        while (j < n2) {
            part[k] = rightArray[j];
            j++;
            k++;
        }
    }

    public void printArray(Part part) {
        String cadena = "[|";
        for (int i = 0; i < part.getScenes().length; i++) {
            for (int j = 0; j < 3; j++) {
                cadena += part.getScenes()[i].getAnimales()[j].getName() + " ";
            }
            cadena += "|";
        }
        cadena += "]";
        System.out.println(cadena);
    }
    //-------------------------------------------------------------------------

    //Escena de mayor y menor gradeza con complejidad Constante
    public String mayorYmenorGrandeza() {
        String mensaje = "";
        mensaje += "La escena de menor grandeza total fue la escena [ ";

        for (int i = 0; i < 3; i++) {
            mensaje += opening.getScenes()[0].getAnimales()[i].getName() + " ";
        }
        mensaje += "]";

        mensaje += "\n\nLa escena de mayor grandeza total fue la escena [ ";
        for (int i = 0; i < 3; i++) {
            mensaje += opening.getScenes()[(opening.getScenes().length - 1)].getAnimales()[i].getName() + " ";
        }
        mensaje += "]";
        return mensaje;
    }

    //Halla el promedio de grandeza de todas las escenas
    public String promedioGrandeza(Part part) {
        String mensaje = "";
        double promedio = 0, sumaTotal = 0, totalElementos = part.getScenes().length;

        for (int i = 0; i < part.getScenes().length; i++) {
            sumaTotal += part.getScenes()[i].getOverall_greatness();
        }
        promedio = sumaTotal / totalElementos;

        mensaje = "\nEl promedio de grandeza de todo el espectaculo fue de "
                + promedio + "\n";

        return mensaje;
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
                    modal.success_message("Carga de " + type + ".", "Éxito al cargar archivo.", "El " + type + " fue cargado con éxito.", null, null);
                    // logs.escribirAccessLogs(Thread.currentThread().getStackTrace()[1].getMethodName() + "// Los " + type + " fueron cargados con éxito.");                    
                    loadData((ArrayList<String>) response.get(1));
                    zooSolution();

                    /*System.out.println("\nEl orden en el que se debe presentar el espectaculo es");
                    System.out.println("\nApertura ordenada");
                    ordenaOpeningInsertion(opening);
                    ordenaGrandezaAnimal(opening);

                    //ordenaPartsInsertion(parts);
                    //[|ciempies libelula gato |gato perro tapir |]
                    //[|ciempies gato tapir |perro tapir nutria |]
                    //int n = parts.length;                    
                    //sort(parts, 0, n-1); 
                    System.out.println(repiteMasYmenos(opening));
                    System.out.println(mayorYmenorGrandeza());
                    System.out.println(promedioGrandeza(opening));
                    //zooSolution();*/
                    break;
                case "error.noclassdeffounderror":
                    modal.error_message("Carga de " + type + ".", "Error fatal.", "Librería de lectura de archivo extraviada.", "Comuníquese con el área de sistemas.", null);
                    // logs.escribirErrorLogs(Thread.currentThread().getStackTrace()[1].getMethodName() + "// Librería para carga CSV no encontrada.");
                    break;
                case "error.nullpointerexception":
                    modal.error_message("Carga de " + type + ".", "Lectura archivo TXT errónea.", "El archivo CSV es null.", null, null);
                    // logs.escribirErrorLogs(Thread.currentThread().getStackTrace()[1].getMethodName() + "// Archivo CSV erróneo.");
                    break;
                case "error.unknow":
                    modal.error_message("Carga de " + type + ".", "Lectura archivo TXT errónea.", "Error desconocido.", "Comuníquese con el área de sistemas.", null);
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
