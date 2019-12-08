/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

/**
 *
 * @author Lukas Bjornvad
 */
public class SoftwareSpecs {
    String Version;
    String Compatability;

    public SoftwareSpecs() {
    }

    public SoftwareSpecs(String Version, String Compatability) {
        this.Version = Version;
        this.Compatability = Compatability;
    }

    public String getVersion() {
        return Version;
    }

    public void setVersion(String version) {
        this.Version = version;
    }

    public String getCompatability() {
        return Compatability;
    }

    public void setCompatability(String compatability) {
        this.Compatability = compatability;
    }
}
