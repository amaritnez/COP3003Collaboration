package Lab1;
/* IMPORTANT, DO READ!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 * The following class uses the format of MSB at digits[0]
 * i.e. in the number 987654321, "9" is found at digits[0],
 * and "1" is found at digits[8]
 */

public class HugeInteger {
  
  private static final int NUM_DIGITS = 40;
  private int digitLength; //used to determine position during math
  private int digits[] = new int[NUM_DIGITS];
  private boolean positive;
  private static boolean wasCalled = false; //used to determine whether a method was already called
  private static boolean wasFlipped = false; //used to determine whether subtract called itself
  
  
  public HugeInteger(String num) {
    
    int digitValue; //holds the numeric value of the digit
    int position = 0; //the position of the number; starts at zero because arrays start at 0, and so are we
    int leadingZeroes = 0; //counts the amount of leading zeroes; to be used later
    boolean isOver = false; //tracks when the array is empty
    char singleDigit = num.charAt(position); //the char value of a digit in num
    
    if (num.charAt(position) == '-') {
      /* The following occurs for negative numbers */
      positive = false;
      digitLength = num.length() - 2; //minus 2 to take into account the negative sign in num
      position++;
      leadingZeroes++; //the negative sign counts as a leading zero
    } else {
      positive = true;
      digitLength = num.length() - 1; //minus 1 because we count arrays starting at 0
    }
    
    if (num.charAt(position) == '0') {
      /* the following is to deal with leading zeroes */
      boolean leadingZero = true;
      while (leadingZero == true) {
        leadingZeroes++;
        position++;
        singleDigit = num.charAt(position);
        leadingZero = singleDigit == '0'; //checks to see if the next char is also a 0
        digitLength--; //subtracts one from the length since the leading zero is irrelevant
      }
    }
    
    try {
      while (isOver == false) {
        /* loops until we run out of chars in num */
        singleDigit = num.charAt(position); //takes the char
        digitValue = Character.getNumericValue(singleDigit); //gets the numeric value
        digits[position - leadingZeroes] = digitValue; //adds it to the array
        /* we subtract by leadingZeroes since they are irrelevant and would skew the array otehrwise */
        position++;
      }
    } catch (Exception e) {
      isOver = true; //once we run out of chars, the exception is caught and the loop ends
    }
    
  }

  public boolean isEqualTo(HugeInteger h2) {
    boolean isEqual; //final value to determine truth of statement
    boolean sameSign; //checks signs
    int digitCount = 0; //counter number
    do {
      int digitOne = digits[digitCount]; //digit of h1
      int digitTwo = h2.digits[digitCount]; //digit of h2
      isEqual = (digitOne == digitTwo); //checks the two digits
      sameSign = (positive == h2.positive); //checks sign
      if (sameSign == false) {
        /* checks signs second; numbers could be the same, but different signs means different numbers */
        isEqual = false;
      }
      digitCount++;
    } while (isEqual == true && digitCount < NUM_DIGITS); //do-while loop so the comparison is done at least once
    return isEqual;
  }

  public boolean isNotEqualTo(HugeInteger h2) {
    return !isEqualTo(h2); //this is the opposite of isEqual, so returns the opposite value
  }

  public boolean isGreaterThan(HugeInteger h2) {
    if (isEqualTo(h2)) {
      return false; //if they're equal, they're not greater than one-another
    } else if (positive == true && h2.positive == false) {
      return true; //positive > negative
    } else if (positive == false && h2.positive == true) {
      return false; //negative < positive
      
    } else if (positive == true && h2.positive == true) {
      if (digitLength > h2.digitLength) {
        return true; //a number with 3 digits is larger than a number with 2 digits
      } else if (digitLength < h2.digitLength) {
        return false; //a number with 2 digits is small than a number with 3 digits
      } else {
        int digitCount = 0;
        while (digits[digitCount] == h2.digits[digitCount]) {
          digitCount++; //simply loops through both arrays until both share a dif. number
        }
        boolean isGreaterThan = digits[digitCount] > h2.digits[digitCount]; //checks whether h1 > h2
        return isGreaterThan;
      }
      
    } else {
      if (digitLength > h2.digitLength) {
        return false; //a negative number with 3 digits is smaller than one with 2, since bigger negative = smaller
      } else if (digitLength < h2.digitLength) {
        return true;//opposite of the previous comment
      } else {
        int digitCount = 0;
        while (digits[digitCount] == h2.digits[digitCount]) {
          digitCount++; //similar idea to the last while loop
        }
        boolean isGreaterThan = digits[digitCount] < h2.digits[digitCount];
        /* since negative, the smaller number is actually the bigger number */
        return isGreaterThan;
      }
    }
  }

  public boolean isLessThan(HugeInteger h2) {
    if (isEqualTo(h2)) {
      return false; //if they're equal, they're not less than one-another
    } else {
      return !isGreaterThan(h2); //if they're not equal, then they're the opposite of greater than
    }
  }

  public boolean isGreaterThanOrEqualTo(HugeInteger h2) {
    return !isLessThan(h2); //if they're not equal or greater than, they must be less than
  }

  public boolean isLessThanOrEqualTo(HugeInteger h2) {
    return !isGreaterThan(h2); //if they're not less than or equal, they must be greater than
  }

  public void add(HugeInteger h2) {
    
    int addendSmall; //smaller number; size is determined by digit length (defaults to h1)
    int addendLarge; //larger numbers (defaults to h2)
    int digitsLeft = digitLength; //used to determine how many digits are left, and affects isOver
    int digitsLeft2 = h2.digitLength; //same as digitsLeft, but for h2
    int position = 0; //determines position for the stringbuilder
    int carry = 0; //keeps track of carries
    int smallSum; //the small sum of individual adding
    StringBuilder TotalSum = new StringBuilder();
    /* we use a stringbuilder so that we may ultimately reverse it in the end to keep with our format of numbers */
    boolean isOver = false; //keeps track of when there are no more numbers left
    
    if (positive != h2.positive) {
      /* adding a positive and a negative is the same as subtracting the two */
      if (wasCalled == false) {
        /* prevents add and subtract from infinitely looping by calling one-another */
        wasCalled = true;
        h2.positive = !h2.positive; //changing the operation means changing the sign
        this.subtract(h2); 
        h2.positive = !h2.positive; //we must change h2 back to it's original sign
        wasCalled = false; //sets back to false for the next time we call the method
        return; //ends the method, as all the computations were done in subtract
      }
    }
    
    if (digitLength > h2.digitLength) {
      /*if h1 has more digits than h2 */
      do {
        addendSmall = h2.digits[digitsLeft2];
        addendLarge = digits[digitsLeft];
        smallSum = addendSmall + addendLarge + carry; //adds the two numbers, plus any carries we may have
        if (smallSum > 9) {
          /* the criteria needed to perform a carry */
          smallSum = smallSum - 10; //take 10 away, and leave the difference
          carry = 1; //that '10' goes to the carry
        } else {
          carry = 0; //if we don't cary, set it to 0
        }
        TotalSum.insert(position, smallSum); //adds the small sum to a specific point in TotalSum
        position++;
        digitsLeft2--; //one less digit remaining
        digitsLeft--; //similar
        isOver = (digitsLeft2 < 0); 
        /*h2 is small in this case, so we first check to see when we run out for that number */
      } while (isOver == false); //do-while so we add at least once
      
      if (digitsLeft >= 0) {
        /* if there's any digits left in the larger number (h1) */
        isOver = false; //we're starting over, so isOver is false again
        while (isOver == false) {
          addendLarge = digits[digitsLeft]; //continues on from last time
          smallSum = addendLarge + carry; //same as before
          if (smallSum > 9) {
            smallSum = smallSum - 10; //same idea for carries
            carry = 1;
          } else {
            carry = 0;
          }
          TotalSum.insert(position, smallSum);
          digitsLeft--;
          position++;
          isOver = (digitsLeft < 0); //checks until we run out of numbers for our larger number
        }
      }
      
      if (positive == false && h2.positive == false) {
        TotalSum.insert(position, '-'); //if we add two negatives, we get a negative
        position++;
      }
      
      TotalSum.reverse(); //reverses the stringbuilder to keep with our number format
      HugeInteger sum = new HugeInteger(TotalSum.toString()); //creates a num HugeInteger object
      /* the following 3 lines changes the references of h1 to the contents of sum */
      this.digits = sum.digits;
      this.digitLength = sum.digitLength;
      this.positive = sum.positive;
      sum = null; //allows for sum to be collect by garbage collection
      
    } else {
      
      /*the following shares the same algorithim as the previous block, but with h1 and h2 swapping roles
       * to take into account h2 is now the bigger number; this is also the default for if both numbers are the
       * same
       */
      do {
        addendSmall = digits[digitsLeft];
        addendLarge = h2.digits[digitsLeft2];
        smallSum = addendSmall + addendLarge + carry;
        if (smallSum > 9) {
          smallSum = smallSum - 10;
          carry = 1;
        } else {
          carry = 0;
        }
        TotalSum.insert(position, smallSum);
        position++;
        digitsLeft2--;
        digitsLeft--;
        isOver = (digitsLeft < 0);
      } while (isOver == false);
      
      if (digitsLeft2 >= 0) {
        isOver = false;
        while (isOver == false) {
          addendLarge = h2.digits[digitsLeft2];
          smallSum = addendLarge + carry;
          if (smallSum > 9) {
            smallSum = smallSum - 10;
            carry = 1;
          } else {
            carry = 0;
          }
          TotalSum.insert(position, smallSum);
          digitsLeft2--;
          position++;
          isOver = (digitsLeft2 < 0);
        }
      } else if (carry == 1) {
        TotalSum.insert(position, carry);
        position++;
      }
      
      if (positive == false && h2.positive == false) {
        TotalSum.insert(position, '-');
        position++;
      }
      
      TotalSum.reverse();
      HugeInteger sum = new HugeInteger(TotalSum.toString());
      this.digits = sum.digits;
      this.digitLength = sum.digitLength;
      this.positive = sum.positive;
      sum = null;
    }
  }

  public void subtract(HugeInteger h2) {
    
    int minuend = 0; //number we are subtracting
    int nextMinuend; //next number to be subtracted; used for borrows
    int subtrahend; //number we are subtracting by
    int digitsLeft = digitLength; //similar to add; used to determine how many are left + position + affects isOver
    int digitsLeft2 = h2.digitLength;
    int position = 0; //position for stringbuilder
    int smallDifference; //difference of individual numbers
    StringBuilder TotalDifference = new StringBuilder(); //same reasoning for using stringbuilder as in add
    boolean wasBorrowed = false; //keeps track of whether a borrow was used
    boolean isOver = false; //same as in add
    
    if (positive != h2.positive) {
      if (wasCalled == false) {
        wasCalled = true; //like in add, prevents the two methods from infinitely calling one-another
        h2.positive = !h2.positive;
        this.add(h2); //subtracting a negative is the same as adding a positive, and vice-versa
        h2.positive = !h2.positive; //changes h2 back to it's original sign to prevent permanent sign change
        wasCalled = false; //like before, used to allow for methods to be called again later
        return; //ends the method
      }
    }
    
    if (digitLength > h2.digitLength || this.absoluteIsGreaterThan(h2)) {
      /* the following method relies on the minuend being larger and greater than the subtrahend,
       * hence the if statement.
       */
      
      do {
        if (wasBorrowed == false) {
          /* if a borrow was performed, no need to assign the new minuend */
          minuend = digits[digitsLeft];
        }
        subtrahend = h2.digits[digitsLeft2]; 
        smallDifference = minuend - subtrahend; //simple subtraction
        if (smallDifference < 0) {
          /* if the subtraction is less than 0, we need to "borrow from our neighbor" */
          nextMinuend = digits[digitsLeft - 1]; //identifies the next minuend
          nextMinuend--; //"borrows" one from it
          minuend = minuend + 10; //and adds ten to our current minuend
          smallDifference = minuend - subtrahend; //does the subtraction again
          minuend = nextMinuend; //our new borrowed minuend becomes our new minuend
          wasBorrowed = true; //identifies that a borrow was performed
        } else {
          wasBorrowed = false; //identifies that a borrow was not performed
        }
        TotalDifference.insert(position, smallDifference); //adds the small difference into the stringbuilder
        digitsLeft--;
        digitsLeft2--;
        position++;
        isOver = (digitsLeft2 < 0); //checks whether our smaller number is out of numbers
      } while (isOver == false); //do-while so subtraction is done at least once
      
      if (digitsLeft >= 0) {
        /* checks if any digits remain in our larger number */
        smallDifference = minuend; //nothing to subtract by, so small difference is just larger number
        TotalDifference.insert(position, smallDifference);
        position++;
        digitsLeft--;
        isOver = (digitsLeft < 0);
        while (isOver == false) {
          /* in case there are even more numbers left in larger number */
          smallDifference = minuend;
          TotalDifference.insert(position, smallDifference);
          digitsLeft--;
          minuend = digits[digitsLeft];
        }
      }
      
      
    } else {
      
      /* this occurs when the absolute value of h1 is smaller than h2. Here, we flip the roles
       * (h2 - h1) and subtract the flipped numbers
       */
      wasFlipped = true; //identifies a flip was performed
      h2.subtract(this); //subtracts h2 by h1
      wasFlipped = false; //sets to false so it may be used again later on
      return; //since we technically already subtracted at this point, no need to go on
    }
    
    if ((this.isLessThan(h2) || wasFlipped) && !(this.isLessThan(h2) && wasFlipped)) {
      /* if the minuend is less than the subtrahend, or we flipped the subtraction, then our difference is
       * negative. However, if we did both, then our difference is actually positive
       */
      TotalDifference.insert(position, '-');
    }
    
    TotalDifference.reverse(); //reverse the stringbuilder to keep with our format
    HugeInteger dif = new HugeInteger(TotalDifference.toString()); //creates a new object
    if (wasFlipped == true) {
      /* if we did a flip, then technically h2 is actually h1, so we reference h2 to dif's contents, not h1 */
      h2.digits = dif.digits;
      h2.digitLength = dif.digitLength;
      h2.positive = dif.positive;
    } else {
      /* otherwise, we just do what we did in add */
      this.digits = dif.digits;
      this.digitLength = dif.digitLength;
      this.positive = dif.positive;
    }
    dif = null;
  }

  public void multiply(HugeInteger h2) {
    //welp, you can only do so much. \_o_o_/
  }
  
  public String toString() {
    int digitsLeft = digitLength; //used for position
    /* the following calls a recursive toString method. This is done because default toString
     * can't take any parameters, but our recursion requires digitLength to work, so we create a separate
     * toString method and call that. This will return the generated string produced by the recursive
     * variant, plus a negative sign (if necessary).
     */
    if (positive == false) {
      return String.format("-%s", recursiveToString(digitsLeft)); //if it's negative, add a '-' sign
    } else {
      return String.format("%s", recursiveToString(digitsLeft));
    }
  }

  private Object recursiveToString(int digitsLeft) {
    if (digitsLeft > 0) {
      /* if there's still digits left, call itself again with one less digit. In the meantime, add the current
       * number to the String.format so that in the end, all the numbers with be added to String.Format
       */
      return String.format("%d%s", digits[digitLength - digitsLeft], recursiveToString(--digitsLeft));
    } else {
      /* once no more digits remain, add the last digit. From here, the method will return the string object of
       * the final number. Then the magic happens: it will return the number to the previous method, which
       * will return the returned numbers + it's own number to the previous method, which will return those
       * returned numbers plus it's own numbers, and so own and so forth until we reach the original
       * toString method.
       */
      return String.format("%d", digits[digitLength]);
    }
  }
  
  /* the following is a special variant of isGreaterThan, used for the subtraction method. This checks the
   * absolute values of h1 and h2, and sees if those are greater than one-another
   */
  public boolean absoluteIsGreaterThan(HugeInteger h2) {
    boolean flippedSign = false; //checks to see if the sign was flipped later on
    boolean flippedSign2 = false; //same here
    boolean absoluteIsGreaterThan; //this is what's returned in the end
    
    if (this.positive == false) {
      /* since this is absolute value, we don't care about signs, so we change it to positive */
      this.positive = true;
      flippedSign = true;
    }
    if (h2.positive == false) {
      h2.positive = true;
      flippedSign2 = true;
    }
    
    absoluteIsGreaterThan = this.isGreaterThan(h2); //with both numbers positive, we just use normal isGreaterThan
    
    if (flippedSign == true) {
      /* if we had to flip the sign, then we must flip it back to it's original */
      this.positive = false;
      flippedSign = false;
    }
    if (flippedSign2 == true) {
      h2.positive = false;
      flippedSign2 = false;
    }
    
    return absoluteIsGreaterThan;
  }
}
