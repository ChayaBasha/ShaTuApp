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

import java.awt.CardLayout;
import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 * A view displaying a tutoring session.
 * 
 * @author rickb
 */
public class StepView extends JPanel {       
    /**
     * The name of the card (child view) currently displayed in this view.
     */
    private StepSelection selectedPanel;
    
    /**
     * The child ASCII child view (card) that can be displayed in this view 
     */
    private EncodeView encodeView;
    
    private CompressionCanvasView compressionView;
    
    private PrepareScheduleView prepareScheduleView;
    
    private Add1View add1View;
    
    private PadView padView;
    
    private TestView testView;
    
    private InitVarView initVarView;

    private RotateView rotateView;

    private ShiftRightView shiftRightView;

    private AddTwoBitView addTwoBitView;
    
    /**
     * Initialize and layout the child components (cards) displayed in this view.
     */
    public StepView() {
        GuiController.instance().setStepView(this);
        
        setLayout(new CardLayout());
        
        initializeComponents();
        initializeLayout();
        
         setBorder(BorderFactory.createLineBorder(Color.BLACK));
        
        selectPanel(StepSelection.ENCODE);
    }

    
    /**
     * Display the child view with the given name.
     * 
     * @param name a StepSelection
     */
    public void selectPanel(StepSelection name) {
        CardLayout cl = (CardLayout) getLayout();
       
        cl.show(this, name.toString());
        
        selectedPanel = name;
    }
    
    /**
     * Create the child GUI components appearing in this frame.
     */
    private void initializeComponents() { 
        encodeView = new EncodeView();
        prepareScheduleView = new PrepareScheduleView();
        compressionView = new CompressionCanvasView();
        rotateView = new RotateView();
        shiftRightView = new ShiftRightView();
        addTwoBitView = new AddTwoBitView();
        add1View = new Add1View();
        padView = new PadView();
        testView = new TestView();
        initVarView = new InitVarView();
    }
    
    /**
     * Layout the child components in this view
     */
    private void initializeLayout() {
        add(encodeView, StepSelection.ENCODE.toString());
        add(prepareScheduleView, StepSelection.PREPARE.toString());
        add(compressionView, StepSelection.COMPRESS.toString());
        add(rotateView, StepSelection.ROTATE_BITS.toString());
        add(shiftRightView, StepSelection.SHIFT_RIGHT.toString());
        add(addTwoBitView, StepSelection.ADD_TWO_BIT.toString());

        add(add1View, StepSelection.ADD1.toString());
        add(padView, StepSelection.PAD.toString());
        add(testView, StepSelection.TEST.toString());
        add(initVarView, StepSelection.INIT_VARS.toString());
    }
}
