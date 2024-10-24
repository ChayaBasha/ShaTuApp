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
package edu.regis.shatu.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JProgressBar;

public class CustomProgressBar extends JProgressBar {
    private Font originalFont;

    public CustomProgressBar() {
        super();
        setString(null);  // Disable default string drawing
        setStringPainted(false);  // Disable default string painting

        // Capture the font set in NetBeans Design panel
        originalFont = getFont();  // Store the original font from the designer
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);  // Draw the progress bar

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Get the percentage string
        String progressText = getValue() + "%";
        int width = getWidth();
        int height = getHeight();

        // Calculate the height based on the progress
        int progressHeight = (int) (getPercentComplete() * height);  // Progress from bottom to top

        // Set the font with the desired size
        g2d.setFont(originalFont.deriveFont(18f));

        // Get text metrics for positioning
        int textWidth = g2d.getFontMetrics().stringWidth(progressText);
        int textX = width / 2 - textWidth / 2;
        int textY = height / 2 + g2d.getFontMetrics().getAscent() / 2 - 2;

        // 1. Draw the dark grey text (background color)
        g2d.setColor(Color.DARK_GRAY);
        g2d.drawString(progressText, textX, textY);

        // 2. Draw the white text where the progress overlaps from bottom to top
        g2d.setClip(0, height - progressHeight, width, progressHeight);  // Clip vertically from the bottom
        g2d.setColor(Color.WHITE);  // Set text color to white for the progress-covered area
        g2d.drawString(progressText, textX, textY);

        // Reset the clip to avoid affecting other components
        g2d.setClip(null);
    }
}

