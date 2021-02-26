package com.sample.vendingmachine.model;

import com.sample.vendingmachine.model.exceptions.InvalidInputException;
import com.sample.vendingmachine.model.exceptions.InvalidOperationException;

import java.util.Objects;

public class Money {
  public static final Money NONE = new Money(0, 0, 0);
  public static final Money FIVE = new Money(1, 0, 0);
  public static final Money TEN = new Money(0, 1, 0);
  public static final Money TWENTY = new Money(0, 0, 1);

  private int fiveRupeeCount;
  private int tenRupeeCount;
  private int twentyRupeeCount;

  public Money() {}

  public Money(int fiveRupeeCount, int tenRupeeCount, int twentyRupeeCount) {

    this.fiveRupeeCount = fiveRupeeCount;
    this.tenRupeeCount = tenRupeeCount;
    this.twentyRupeeCount = twentyRupeeCount;
  }

  /**
   * @param value The value of the money to be created
   * @param vault The vault is the place that holds all possible currencies in the system
   * @return Money created with value @param Value and from denominations available in vault.
   */
  public static Money of(long value, Money vault) throws InvalidOperationException {
    if (vault != null) {
      int twenties = (int) value / 20;
      int remaining = (int) (value - twenties * 20);
      int tens = remaining / 10;
      int fives = (remaining - 10 * tens) / 5;

      int temp = vault.getTwentyRupeeCount();
      if (twenties > temp) {
        tens += (twenties - temp) * 2;
        twenties = temp;
      }
      temp = vault.getTenRupeeCount();
      if (tens > temp) {
        fives += (tens - temp) * 2;
        tens = temp;
      }
      temp = vault.getFiveRupeeCount();
      if (fives > temp) throw new InvalidOperationException("Cannot vend the correct Change");
      return new Money(fives, tens, twenties);
    }
    else{
      return of(value);
    }

  }
  /**
   * @param value The value of the money to be created
   * @return Money created with value having highest denomination currencies.
   */
  public static Money of(long value) throws InvalidOperationException {
    int twenties = (int) value / 20;
    int remaining = (int) (value - twenties * 20);
    int tens = remaining / 10;
    int fives = (remaining - 10 * tens) / 5;
    if((remaining-10*tens)%5>0){
      throw new InvalidOperationException("Not the kind of money I understand");
    }
    return new Money(fives,tens,twenties);
}

    public long getValue() {
    return fiveRupeeCount * 5L + tenRupeeCount * 10L + twentyRupeeCount * 20L;
  }

  public int getFiveRupeeCount() {
    return fiveRupeeCount;
  }

  public void setFiveRupeeCount(int fiveRupeeCount) throws InvalidInputException {
    if (fiveRupeeCount >= 0) this.fiveRupeeCount = fiveRupeeCount;
    else throw new InvalidInputException("Value cannot be negative");
  }

  public int getTenRupeeCount() {
    return tenRupeeCount;
  }

  public void setTenRupeeCount(int tenRupeeCount) throws InvalidInputException {
    if (tenRupeeCount >= 0) this.tenRupeeCount = tenRupeeCount;
    else throw new InvalidInputException("Value cannot be negative");
  }

  public int getTwentyRupeeCount() {
    return twentyRupeeCount;
  }

  public void setTwentyRupeeCount(int twentyRupeeCount) throws InvalidInputException {
    if (twentyRupeeCount >= 0) this.twentyRupeeCount = twentyRupeeCount;
    else throw new InvalidInputException("Value cannot be negative");
  }

  public Money add(Money money) {
    if (money != null) {
      return (new Money(
          this.fiveRupeeCount + money.fiveRupeeCount,
          this.tenRupeeCount + money.tenRupeeCount,
          this.twentyRupeeCount + money.twentyRupeeCount));
    } else {
      return (new Money(this.fiveRupeeCount, this.tenRupeeCount, this.twentyRupeeCount));
    }
  }

  public Money subtract(Money money) throws InvalidOperationException {
    if (money != null) {
      if (this.fiveRupeeCount >= money.fiveRupeeCount
          && this.tenRupeeCount >= money.tenRupeeCount
          && this.twentyRupeeCount >= money.tenRupeeCount) {
        this.fiveRupeeCount -= money.fiveRupeeCount;
        this.tenRupeeCount -= money.tenRupeeCount;
        this.twentyRupeeCount -= money.twentyRupeeCount;
      } else {
        throw new InvalidOperationException("Cannot subtract " + money + " from " + this);
      }
    }
    return this;
  }

  public Money multiply(int factor) throws InvalidOperationException {
    if (factor <= 0) throw new InvalidOperationException("factor must be positive");
    else {
      return new Money(factor * fiveRupeeCount, factor * tenRupeeCount, factor * twentyRupeeCount);
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Money money = (Money) o;
    return fiveRupeeCount == money.fiveRupeeCount
        && tenRupeeCount == money.tenRupeeCount
        && twentyRupeeCount == money.twentyRupeeCount;
  }

  @Override
  public int hashCode() {
    return Objects.hash(fiveRupeeCount, tenRupeeCount, twentyRupeeCount);
  }

  @Override
  public String toString() {
    return "Money{"
        + "fiveRupeeCount="
        + fiveRupeeCount
        + ", tenRupeeCount="
        + tenRupeeCount
        + ", twentyRupeeCount="
        + twentyRupeeCount
        + '}';
  }

}
