/*
 * SHATU: SHA-256 Tutor
 * 
 *  (C) Johanna & Richard Blumenthal, All rights reserved
 * 
 *  Unauthorized use, duplication or distribution without the authors'
 *  permission is strictly prohibited.
 * 
 *  Unless required by applicable law or agreed to in writing, this
 *  software is distributed on an "AS IS" basis without warranties
 *  or conditions of any kind, either expressed or implied.
 */
package edu.regis.shatu.model;
/**
 * An abstract class representing a model with a title and description.
 * All models extending this class will have a title and description that can be displayed to the user.
 * This class is typically used for entities that are displayed in a GUI.
 * 
 * @author rickb
 */
public abstract class TitledModel extends Model {
    // The title or name of the model, which is displayed to the user.
    protected String title;
    // A brief description of the model, also displayed to the user.
    protected String description;
    /**
     * Instantiate model with the default id, title, and description.
     */
    public TitledModel() {
        this(DEFAULT_ID);
    }
    /**
     * Instantiate model with a specific id.
     * 
     * @param id the model's unique id from the database.
     */
    public TitledModel(int id) {
        this(id, "");
    }
    /**
     * Instantiate model with a specific id and title.
     * 
     * @param id the model's unique id from the database.
     * @param title the title of the model.
     */
    public TitledModel(int id, String title) {
        super(id);
        this.title = title;
        this.description = "";
    }
    /**
     * Get title of model.
     * 
     * @return the title string.
     */
    public String getTitle() {
        return title;
    }
    /**
     * Set title of model.
     * 
     * @param title the new title to assign.
     */
    public void setTitle(String title) {
        this.title = title;
    }
    /**
     * Get description of model.
     * 
     * @return the description string.
     */
    public String getDescription() {
        return description;
    }
    /**
     * Set description of model.
     * 
     * @param description the new description to assign.
     */
    public void setDescription(String description) {
        this.description = description;
    }
}
