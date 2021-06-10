/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package AlKhalil.underived;

/**
 * <p>This class provides implementations for arabic proper nouns.
 * each proper noun is characterized by its  unvoweled form,   voweled form, and type.
 *
 * </p>
 */
public class ProperNoun {
    // ------------------------------------------------------  Class Variables

    /** the unvoweled form of the proper noun  */
    private String unvoweledform;
    /** the voweled form of the proper noun  */
    private String voweledform;
    /** the type of the proper noun  */
    private String type;

    /** Creates a new instance of ProperNoun */
    public ProperNoun() {
    }
    // --------------------------------------------------------- Public Methods

    /** returns the unvoweled form of the word    */
    public String getUnvoweledform() {
        return unvoweledform;
    }

    /** returns the voweled form of the word    */
    public String getVoweledform() {
        return voweledform;
    }

    /** returns the type of the word    */
    public String getType() {
        return type;
    }

    /** sets  the unvoweled form of the word
    @param unvoweledform the unvoweled form of the word
     */
    public void setUnvoweledform(String unvoweledform) {
        this.unvoweledform = unvoweledform;
    }

    /** sets  the voweled form of the word
    @param voweledform the voweled form of the word
     */
    public void setVoweledform(String voweledform) {
        this.voweledform = voweledform;
    }

    /** sets  the type of the word
    @param type the type of the word
     */
    public void setType(String type) {
        this.type = type;
    }
}
