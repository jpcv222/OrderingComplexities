/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

/**
 *
 * @author jpcv2
 */
public class Part {
    int overall_greatness;
    public Scene [] scenes ;

    public Part(int overall_greatness, Scene[] scenes) {
        this.overall_greatness = overall_greatness;
        this.scenes = scenes;
    }
    
    public Part(int k) {
        this.scenes = new Scene[k];
    }

    public int getOverall_greatness() {
        return overall_greatness;
    }

    public void setOverall_greatness(int overall_greatness) {
        this.overall_greatness = overall_greatness;
    }

    public Scene[] getScenes() {
        return scenes;
    }

    public void setScenes(Scene[] scenes) {
        this.scenes = scenes;
    }

    
}
