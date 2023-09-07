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

package edu.regis.shatu;

import edu.regis.shatu.svc.SHA_256;
import edu.regis.shatu.view.MainFrame;

/**
 * A standalone implementation of the SHA-256 intelligent tutoring application.
 * @author rickb
 */
public class ShaTuApp {
    /**
     * Main entry point for the ShaTut application, which will display the UI.
     * 
     * @param args ignored
     */
    public static void main(String[] args) {
        System.out.println("Initializing...");
        
      //  SHA_256 alg = new SHA_256();
        //alg.doit("Regis Computer Science Rocks!");
        
        MainFrame.instance();
        
        System.out.println("Finished initializing");
    }
}
