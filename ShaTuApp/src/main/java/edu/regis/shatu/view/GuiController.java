/*
 * SHATU: SHA-256 Tutor
 * 
 *  (C) Johanna & Richard Blumenthal, All rights reserved
 * 
 *  Unauthorized use, duplication or distribution without the authors'
 *  permission is strictly prohibted.
 * 
 *  Unless required by applicable law or agreed to in writing, this
 *  software is distributed on an "AS IS" basis without warranties
 *  or conditions of any kind, either expressed or implied.
 */
package edu.regis.shatu.view;

import edu.regis.shatu.svc.SHA_256;

/**
 * A mediator between the GUI and the SHA-256 algorithm.
 * 
 * @author rickb
 */
public class GuiController {
    /**
     * The singleton instance of this controller.
     */
    private static GuiController SINGLETON;
    
    static {
        SINGLETON = new GuiController();
    }
    
    /**
     * Return the singleton instanced of this controller.
     * 
     * @return GuiController
     */
    public static GuiController instance() {
        return SINGLETON;
    }
    
    /**
     * A convenience reference to the step selector view.
     */
    private StepSelectorView stepSelectorView;
    
    /**
     * A convenience reference to the step view.
     */
    private StepView stepView;
    
    /**
     * The SHA-256 algorithm.
     */
    private final SHA_256 sha256Alg;
    
    /**
     * Return the SHA-256 algorithm.
     * 
     * @return SHA_256
     */
    public SHA_256 getSha256Alg() {
        return sha256Alg;
    }

    public StepSelectorView getStepSelectorView() {
        return stepSelectorView;
    }

    public void setStepSelectorView(StepSelectorView stepSelectionView) {
        this.stepSelectorView = stepSelectionView;
    }

    public StepView getStepView() {
        return stepView;
    }

    public void setStepView(StepView stepView) {
        this.stepView = stepView;
    }
    
    /**
     * If not already initialed, initialize the SHA-256 algorithm.
     */
    private GuiController() {
        sha256Alg = SHA_256.instance();
    }
}
