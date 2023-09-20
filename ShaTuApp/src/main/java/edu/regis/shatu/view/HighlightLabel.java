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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

/**
 * A JLabel with mouse highlighting and selection support.
 * 
 * @author rickb
 */
public class HighlightLabel extends JLabel implements MouseListener {
    /**
     * This empty border serves as an inset so the text doesn't touch the edges.
     */
    private static final Border EMPTY_BORDER = new EmptyBorder(5, 10, 5, 10);

    /**
     * A simple black line surrounds this label when not highlighted.
     */
    private static final Border NORMAL_BORDER
            = new CompoundBorder(BorderFactory.createLineBorder(new Color(241,196,0),2), EMPTY_BORDER);

    /**
     * A red line with thickness two surrounds this label when highlighted.
     */
    private static final Border HIGHLIGHT_BORDER
            = new CompoundBorder(BorderFactory.createLineBorder(Color.RED, 2), EMPTY_BORDER);
    
    /**
     * A blue line with thickness two surrounds this label when selected.
     */
    private static final Border SELECTED_BORDER
            = new CompoundBorder(BorderFactory.createLineBorder(Color.BLUE, 2), EMPTY_BORDER);
    
    /**
     * The background of this label is pinkish when not highlighted.
     */
    private static final Color NORMAL_BACKGROUND = Color.WHITE; // pinkish
    
    /**
     * The background of this label is a light yellow when highlighted.
     */
    private static final Color HIGHLIGHT_BACKGROUND = new Color(255, 255, 205);
    
    /**
     * The background of this label when selected.
     */
    private static final Color SELECTED_BACKGROUND = new Color(204, 255, 255);
    
    /**
     * The view that is displayed when this label is selected.
     */
    private StepSelection stepSelection;
    
    private boolean isSelected;

    /**
     * Initialize this label with the given text and a size that was determined
     * empirically.
     * 
     * @param text the text of this label
     * @param stepSelection the name of the step (view) that is displayed when
     *                      this label is selected.
     */
    public HighlightLabel(String text) { //, StepSelection stepSelection) 
        super(text, SwingConstants.CENTER);
        
        //this.stepSelection = stepSelection;
        isSelected = false;

        Dimension d = new Dimension(140, 28); // Determined empirically

        setOpaque(true);
        setBackground(NORMAL_BACKGROUND);
        setBorder(NORMAL_BORDER);
        setMinimumSize(d);

        this.addMouseListener(this);
    }
    
    public void setStepSelection(StepSelection stepSelection) {
        this.stepSelection = stepSelection;
    }
    
    public void select() {
        GuiController.instance().getStepSelectorView().displayStep(stepSelection);
        isSelected = true;
        setBackground(SELECTED_BACKGROUND);
        setBorder(SELECTED_BORDER);
    }
    
    public void deselect() {
        isSelected = false;
        setBackground(NORMAL_BACKGROUND);
        setBorder(NORMAL_BORDER);
    }

    @Override
    public void mouseClicked(MouseEvent evt) {
        if (!isSelected) {
            select();
        }
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    /**
     * When the mouse enters this label, highlight it.
     * 
     * @param e ignored
     */
    @Override
    public void mouseEntered(MouseEvent e) {
        if (!isSelected) {
            setBorder(HIGHLIGHT_BORDER);
            setBackground(HIGHLIGHT_BACKGROUND);
        }
    }

    /**
     * When the mouse exits this label, unhighlight it.
     * 
     * @param e ignored
     */
    @Override
    public void mouseExited(MouseEvent e) {
        if (!isSelected) {
            setBorder(NORMAL_BORDER);
            setBackground(NORMAL_BACKGROUND);
        }
    }
}
