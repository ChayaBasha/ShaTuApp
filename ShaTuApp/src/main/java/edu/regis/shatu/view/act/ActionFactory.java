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
package edu.regis.shatu.view.act;

/**
 * Factory for creating the GUI actions used in the ShaTu user interface.
 * @author rickb
 */
public class ActionFactory {
    public static void createActions() { // frame, controller
        new SaveSessionAction(); // fame controller
    }
}
