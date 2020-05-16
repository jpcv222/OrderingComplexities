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
    long TInicio, TFin, tiempo;

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
        TInicio = System.currentTimeMillis(); //Tomamos la hora en que inicio el algoritmo y la almacenamos en la variable inicio

        switch (this.complejidad) {
            case 0:
                //Solución para complejidad n
                linearComplexity();
                break;
            case 1:
                //Solución para complejidad nlogn

                break;
            case 2:
                //Solución para complejidad n^2
                break;
        }

        TFin = System.currentTimeMillis(); //Tomamos la hora en que finalizó el algoritmo y la almacenamos en la variable T
        tiempo = TFin - TInicio; //Calculamos los milisegundos de diferencia
        System.out.println("Tiempo de ejecución en milisegundos: " + tiempo);

    }


    public void linearComplexity() {
        int max = 0;
        //Ordenar apertura
         for (int j = 0; j < this.opening.getScenes().length; j++) {
                for (int l = 0; l < 3; l++) {
                    System.out.println("Apertura Escena " + j + " Animal " + this.opening.getScenes()[j].getAnimales()[l].getName());
                }
            }
        max = getMaxOverallGreatnessScenes(this.opening.getScenes());
        for (int i = 0; i < this.opening.getScenes().length; i++) {
            sortAnimals(this.opening.getScenes()[i].getAnimales());
        }
        this.opening.setScenes(countingSortScenes(this.opening.getScenes(), max));
        
        System.out.println("--- Ordenadas escenas y animales");
        
        for (int j = 0; j < this.opening.getScenes().length; j++) {
                for (int l = 0; l < 3; l++) {
                    System.out.println("Apertura Escena " + j + " Animal " + this.opening.getScenes()[j].getAnimales()[l].getName());
                }
            }
        //Ordenar m - 1 partes
        /*for (int i = 0; i < this.m - 1; i++) {
            for (int j = 0; j < this.k; j++) {
                for (int l = 0; l < 3; l++) {
                    System.out.println("Parte  " + i + " Escena " + j + " Animal " + this.parts[i].getScenes()[j].getAnimales()[l].getName());
                }
            }
        }*/
    }

    public int getMaxOverallGreatnessScenes(Scene[] arr) {
        int max = arr[0].getOverall_greatness();
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].getOverall_greatness() > max) {
                max = arr[i].getOverall_greatness();
            }
        }
        return max;
    }

    public void sortAnimals(Animal[] arr) {

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

    public Scene [] countingSortScenes(Scene[] arr, int max) {
        int[] conteo = new int[max + 1];
        Scene[] aux = new Scene[arr.length];

        for (int i = 0; i < conteo.length; i++) {
            conteo[i] = 0;
        }

        for (int i = 0; i < arr.length; i++) {
            conteo[arr[i].getOverall_greatness()]++;
        }

        for (int i = 2; i < conteo.length; i++) {
            conteo[i] = conteo[i] + conteo[i - 1];
        }

        for (int i = 0; i < arr.length; i++) {
            aux[conteo[arr[i].getOverall_greatness()]-1] = arr[i];
            //System.out.println("Aux pos "+ conteo[arr[i].getOverall_greatness()]);
            conteo[arr[i].getOverall_greatness()]--;
        }

        return aux;
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
    public void ordenaOpeningInsertion(Part part){
        ArrayList<Scene> openingAux = new ArrayList<>();
                
        for (int i = 0; i < part.getScenes().length; i++) {
            openingAux.add(part.getScenes()[i]);
        }                                
        for (int i = 1; i < part.getScenes().length; i++) { 
            Scene key = new Scene(part.getScenes()[i].getAnimales(), part.getScenes()[i].getOverall_greatness());                    
                 
        int j = i - 1;
        while (j >= 0 && openingAux.get(j).getOverall_greatness() > key.getOverall_greatness()) {
            openingAux.get(j+1).setAnimales(openingAux.get(j).getAnimales());
            openingAux.get(j+1).setOverall_greatness(openingAux.get(j).getOverall_greatness());

            j = j - 1;
        }
        openingAux.get(j+1).setAnimales(key.getAnimales()); 
        openingAux.get(j+1).setOverall_greatness(key.getOverall_greatness()); 
       }               
       
        for (int i = 0; i < openingAux.size(); i++) {
            part.getScenes()[i]=openingAux.get(i);
        }                              
    }
    
    //Ordena las partes por grandeza total de la parte, complejidad O(ncuadrado) - Insertion Sort
    public void ordenaPartsInsertion(Part[] part){
        ArrayList<Scene> openingAux = new ArrayList<>();
                                                      
        for (int i = 1; i < part.length; i++) { 
            Part key = new Part(part[i].getOverall_greatness(), part[i].getScenes());                    
                 
        int j = i - 1;
        while (j >= 0 && part[j].getOverall_greatness() > key.getOverall_greatness()) {
            part[j+1].setOverall_greatness(part[j].getOverall_greatness());
            part[j+1].setScenes(part[j].getScenes());

            j = j - 1;
        }
        part[j+1].setOverall_greatness(key.getOverall_greatness()); 
        part[j+1].setScenes(key.getScenes()); 
       }                                                         
    }
    //Hallar el animal que aparece en mas escenas
    public String repiteMas(Part part){
        String dato = "", valorMayor = "", valorMenor = "";
        String mensaje = "";
        int repeticionesMayor = 0;
        int repeticionesMenor = part.getScenes().length;
        
        for(int i=0; i<part.getScenes().length; i++){
            for(int j=0; j<3; j++){
                dato = part.getScenes()[i].getAnimales()[j].getName();                
                if(repeticionesMayor < verifica(dato, part)){
                    repeticionesMayor = verifica(dato, part);
                    valorMayor = part.getScenes()[i].getAnimales()[j].getName();
                }  
                if(repeticionesMenor > verifica(dato, part)){
                    repeticionesMenor = verifica(dato, part);                   
                    valorMenor = part.getScenes()[i].getAnimales()[j].getName();                   
                }    
            }
        }    
        mensaje = "\nEl animal que participo en mas escenas dentro del espectaculo fue: \n"
                  +valorMayor
                           +"\ncon "+repeticionesMayor*2+" escenas.\n";
        mensaje += "\nEl animal que menos participo en escenas dentro del espectaculo fue: \n"
                   +valorMenor
                           +"\ncon "+repeticionesMenor*2+" escenas.\n";
        return  mensaje;        
    }
    public int verifica(String dato, Part part){
        int x = 0;
        
        for (int i = 0; i < part.getScenes().length; i++) {
            for (int j = 0; j < 3; j++) {
                if(dato.equals(part.getScenes()[i].getAnimales()[j].getName())){
                x++;
                }
            }            
        }
        return x;
    }
    
    //Ordena la parte por grandeza del animal llamando al metodo sortAnimals
    public void ordenaGrandezaAnimal(Part part){
        String cadena = "[|";
        for (int i = 0; i < part.getScenes().length; i++) {
            sortAnimals(part.getScenes()[i].getAnimales());
        }
        for (int i = 0; i < part.getScenes().length; i++) {
            for (int j = 0; j < 3; j++) {
                cadena+=part.getScenes()[i].getAnimales()[j].getName()+ " ";
            }
            cadena += "|";
        }
        cadena+="]";
        System.out.println(cadena);
    }
    
    //Ordenar apertura con cost o(nlogn) MERGUE SORT-----------------------------------
    public void sort(Part part, int left, int right) {
        if(left < right){
          //Encuentra el punto medio del vector.
          int middle = (left + right) / 2;

          //Divide la primera y segunda mitad (llamada recursiva).
          sort(part, left, middle);
          sort(part, middle+1, right);

          //Une las mitades.
          OrdenaOpeningMerge(part, left, middle, right);
        }
    }
    public void OrdenaOpeningMerge(Part part, int left, int middle, int right) {
        //Encuentra el tamaño de los sub-vectores para unirlos.
        int n1 = middle - left + 1;
        int n2 = right - middle;

        //Vectores temporales.
        Scene leftArray[] = new Scene [n1];
        Scene rightArray[] = new Scene [n2];

        //Copia los datos a los arrays temporales.
        for (int i=0; i < n1; i++) {
          leftArray[i] = part.getScenes()[left+i];
        }
        for (int j=0; j < n2; j++) {
          rightArray[j] = part.getScenes()[middle + j + 1];
        }
        /* Une los vectorestemporales. */

        //Índices inicial del primer y segundo sub-vector.
        int i = 0, j = 0;

        //Índice inicial del sub-vector arr[].
        int k = left;

        //Ordenamiento.
        while (i < n1 && j < n2) {
          if (leftArray[i].getOverall_greatness() <= rightArray[j].getOverall_greatness()) {
            part.getScenes()[k] = leftArray[i];
            i++;
          } else {
              part.getScenes()[k] = rightArray[j];
              j++;
          }
          k++;
        }//Fin del while.

        /* Si quedan elementos por ordenar */
        //Copiar los elementos restantes de leftArray[].
        while (i < n1) {
          part.getScenes()[k] = leftArray[i];
          i++;
          k++;
        }
        //Copiar los elementos restantes de rightArray[].
        while (j < n2) {
          part.getScenes()[k] = rightArray[j];
          j++;
          k++;
        }
    }
    public void printArray(Part part) {
        String cadena = "[|";
        for (int i = 0; i < part.getScenes().length; i++) {                      
            for (int j = 0; j < 3; j++) {
                cadena+=part.getScenes()[i].getAnimales()[j].getName()+ " ";
            }            
            cadena+="|";
        }
        cadena+="]";
        System.out.println(cadena);
    }
    //-------------------------------------------------------------------------
    
    //Escena de mayor y menor gradeza con complejidad Constante
    public void mayorYmenorGrandeza1(){
        String mensaje = "";
        mensaje += "La escena de menor grandeza total fue la escena [ "; 

        for (int i = 0; i < 3; i++) {
             mensaje += opening.getScenes()[0].getAnimales()[i].getName()+" ";
         }  
        mensaje+="]";

        mensaje += "\n\nLa escena de mayor grandeza total fue la escena [ ";
        for (int i = 0; i < 3; i++) {
            mensaje += opening.getScenes()[(opening.getScenes().length-1)].getAnimales()[i].getName()+" ";
        } 
        mensaje+="]";
        System.out.println(mensaje);
    }
            
    public String mayorYmenorGrandeza2(){
        String mensaje = "";                                   
        int mayor = opening.getScenes()[0].getOverall_greatness();
        int menor = opening.getScenes()[0].getOverall_greatness();
        int posMayor = 0, posMenor = 0;                  
            
        for(int i=0; i<opening.getScenes().length; i++){
            if(opening.getScenes()[i].getOverall_greatness()>mayor){ // 
                mayor = opening.getScenes()[i].getOverall_greatness();  
                posMayor = i;
            }
            if(opening.getScenes()[i].getOverall_greatness()<menor){ // 
                menor = opening.getScenes()[i].getOverall_greatness();  
                posMenor = i;
            }
        }         
        mensaje += "\nLa escena de menor grandeza total fue la escena [ ";        
        for (int j = 0; j < 3; j++) {
            mensaje += opening.getScenes()[posMenor].getAnimales()[j].getName()+" ";
        }   
        mensaje+="]";
                
        mensaje += "\n\nLa escena de mayor grandeza total fue la escena [ ";
                
        for (int j = 0; j < 3; j++) {
            mensaje += opening.getScenes()[posMayor].getAnimales()[j].getName()+" ";
        }   
        mensaje+="]";
            
        return mensaje;
    }
    //Halla el promedio de grandeza de todas las escenas
    public String promedioGrandeza(Part part){
        String mensaje = "";        
        double promedio = 0, sumaTotal = 0, totalElementos = part.getScenes().length;
        
        for(int i = 0; i<part.getScenes().length; i++){                
            sumaTotal += part.getScenes()[i].getOverall_greatness();
        }       
        promedio = sumaTotal/totalElementos;
        
        mensaje = "\nEl promedio de grandeza de todo el espectaculo fue de "
                  +promedio+"\n"; 
            
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
                    modal.success_message("Carga masiva de " + type + ".", "Éxito al cargar archivo.", "Los " + type + " fueron cargados con éxito.", null, null);
                    // logs.escribirAccessLogs(Thread.currentThread().getStackTrace()[1].getMethodName() + "// Los " + type + " fueron cargados con éxito.");                    
                    loadData((ArrayList<String>) response.get(1));
                    zooSolution();
                    int n = opening.getScenes().length;
                    //printArray(opening);
                    //sort(opening, 0, n-1);
                    //printArray(opening);
                    System.out.println("\nEl orden en el que se debe presentar el espectaculo es");
                    System.out.println("\nApertura ordenada");
                    ordenaOpeningInsertion(opening);
                    ordenaGrandezaAnimal(opening);
                                        
                    ordenaPartsInsertion(parts);
                    System.out.println("\nPartes Ordenada: ");
                    for (int i = 0; i < parts.length; i++) {
                        ordenaOpeningInsertion(parts[i]);
                        ordenaGrandezaAnimal(parts[i]);
                    }                                                          
                    System.out.println(repiteMas(opening));
                    mayorYmenorGrandeza1();
                    System.out.println(promedioGrandeza(opening));                                        
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
