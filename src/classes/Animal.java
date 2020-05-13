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
public class Animal {

    private String name;
    private int greatness;

    public Animal(String name, int greatness) {
        this.name = name;
        this.greatness = greatness;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGreatness() {
        return greatness;
    }

    public void setGreatness(int greatness) {
        this.greatness = greatness;
    }

}
