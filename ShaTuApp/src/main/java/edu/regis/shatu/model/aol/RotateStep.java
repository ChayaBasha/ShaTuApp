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
public class RotateStep  {
    /**
     * The direction to rotate
     */
    public enum Direction {RIGHT, LEFT};
    
    private Direction direction;
    
    /**
     * The number of bits to rotate in the given direction.
     */
    private int amount;
    
    /**
     * The length of the bit string to be rotated
     */
    private int length;
    
    /**
     * The bits being rotated.
     */
    private String data;
    
    public RotateStep() {
        
    }

   // @Override
  //  public String getType() {
   //     return "RotateStep";
   // }

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
    
    public int getLength() {
       return length;
    }
    
    public void setLength(int length) {
       this.length = length;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
