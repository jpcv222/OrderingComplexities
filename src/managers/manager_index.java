/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managers;

import classes.FileManage;
import components.Dialogs;
import views.Index;

/**
 *
 * @author jpcv2
 */
public class manager_index {

    private final FileManage file;
    private Index index;
    private Dialogs modal;

    public manager_index(Index index) {
        this.index = index;
        this.file = new FileManage();
        this.modal = new Dialogs();
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

    public void readCSVFile() {
        String response = FileManage.readFile(file.getRuta());
        resultUpload(response, "usuarios");

    }
    
     public void resultUpload(String response, String type) {
        try {
            switch (response) {
                case "success":
                    modal.success_message("Carga masiva de " + type + ".", "Éxito al cargar archivo.", "Los " + type + " fueron cargados con éxito.", null, null);
                   // logs.escribirAccessLogs(Thread.currentThread().getStackTrace()[1].getMethodName() + "// Los " + type + " fueron cargados con éxito.");
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
