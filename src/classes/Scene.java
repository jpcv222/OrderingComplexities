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
public class Scene {
    
    int overall_greatness;
    public Animal [] animales =  new Animal[3];

    public Scene() {
    }

    public int getOverall_greatness() {
        return overall_greatness;
    }

    public void setOverall_greatness(int overall_greatness) {
        this.overall_greatness = overall_greatness;
    }

    public Animal[] getAnimales() {
        return animales;
    }

    public void setAnimales(Animal[] animales) {
        this.animales = animales;
    }
    
    
    
}
