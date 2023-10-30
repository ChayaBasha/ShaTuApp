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
    
    private RotateView rotateView;
    
    private InitVarView initVarView;

    private ShiftRightView shiftRightView;
    
    private ExclusiveOrView exclusiveOrView;

    private AddTwoBitView addTwoBitView;
    
    private MajFunction majFunction;
    
    private ShaOne shaOne;
    
    private ChoiceFunctionView choiceFunctionView;
    
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
        exclusiveOrView = new ExclusiveOrView();
        addTwoBitView = new AddTwoBitView();
        majFunction = new MajFunction();
        shaOne = new ShaOne();
        add1View = new Add1View();
        padView = new PadView();
        rotateView = new RotateView();
        initVarView = new InitVarView();
        exclusiveOrView = new ExclusiveOrView();
        choiceFunctionView = new ChoiceFunctionView();
    }
    
    /**
     * Layout the child components in this view
     */
    private void initializeLayout() {
        add(encodeView, StepSelection.ENCODE.toString());
        add(prepareScheduleView, StepSelection.PREPARE.toString());
        add(compressionView, StepSelection.COMPRESS.toString());
        add(shiftRightView, StepSelection.SHIFT_RIGHT.toString());
        add(exclusiveOrView, StepSelection.XOR.toString());
        add(addTwoBitView, StepSelection.ADD_TWO_BIT.toString());
        add(majFunction, StepSelection.MAJ_FUNCTION.toString());
        add(shaOne, StepSelection.SHA_ONE.toString());
        add(add1View, StepSelection.ADD1.toString());
        add(padView, StepSelection.PAD.toString());
        add(rotateView, StepSelection.ROTATE_BITS.toString());
        add(initVarView, StepSelection.INIT_VARS.toString());
        add(exclusiveOrView, StepSelection.XOR.toString());
        add(choiceFunctionView, StepSelection.CHOICE_FUNCTION.toString());
    }
}
