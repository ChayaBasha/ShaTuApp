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
package edu.regis.shatu.model.aol;

/**
 *
 * Integer.rotateRight(x, 6)
 * 
 * @author rickb
 */
public class RotateStep extends Step {
    /**
     * The direction to rotate
     */
    private enum Direction {RIGHT, LEFT};
    
    private Direction direction;
    
    /**
     * The number of bits to rotate in the given direction.
     */
    private int amount;
    
    /**
     * The bits being rotated.
     */
    private int data;
    
    public RotateStep(int id, int sequenceId) {
        super(id, sequenceId);
    }

    @Override
    public String getType() {
        return "Rotate";
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }
}
