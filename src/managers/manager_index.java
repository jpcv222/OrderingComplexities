/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managers;

/**
 *
 * @author jpcv2
 */
public class manager_index {

    private final FileManage file;
    private Index index;

    public manager_index(Index index) {
        this.index = index;
        this.file = new FileManage();
    }

    public void selectFile() {
        String response = FileManage.selectFile(file.calcularRutaArchivo());
        index.jLabelRutaArchivo.setText(response);
        validateBtCargar();

    }

    public void readCSVFile() {
        String response = FileManage.readCSVFile(file.getRuta());
        resultUpload(response, "usuarios");

    }
}
