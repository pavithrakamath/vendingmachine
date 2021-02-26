package com.sample.vendingmachine.model;

import com.sample.vendingmachine.model.exceptions.InvalidInputException;
import com.sample.vendingmachine.model.exceptions.InvalidOperationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MoneyTests {

  @Test
  public void testMoneyEquals_WhenTwoMoneysAreEmpty() {
    Money m1 = new Money();
    Money m2 = new Money();

    assertEquals(m1, m2);
  }

  @Test
  public void testMoneyEquals_WhenTwoMoneysHaveSameCurrencies() {
    Money m1 = new Money(-5, 5, 0);
    Money m2 = new Money(-5, 5, 0);
    assertEquals(m1, m2);
    assertEquals(m1.hashCode(),m2.hashCode());
  }

  @Test
  public void testMoneyNotEquals_WhenTwoMoneysHaveDifferentCurrencies()
      throws InvalidInputException {
    Money m1 = new Money();
    m1.setFiveRupeeCount(6);

    Money m2 = new Money();
    m2.setTenRupeeCount(3);

    assertNotEquals(m1, m2);
  }

  @Test
  public void testMoneyAdd_returnsValidResponse() {
    Money m1 = new Money(3, 0, 0);
    Money m2 = new Money(3, 3, 0);

    Money m3 = m1.add(m2);
    assertEquals(6, m3.getFiveRupeeCount());
    assertEquals(3, m3.getTenRupeeCount());
    assertEquals(0, m3.getTwentyRupeeCount());
  }
  @Test
  public void testMoneyAddNull_returnsValidResponse() {
    Money m1 = new Money(3, 0, 0);

    Money m3 = m1.add(null);
    assertEquals(3, m3.getFiveRupeeCount());
    assertEquals(0, m3.getTenRupeeCount());
    assertEquals(0, m3.getTwentyRupeeCount());
  }
  @Test
  public void testMoneyAdd_WhenBothEmptyMoney() {
    Money m1 = Money.NONE;
    Money m2 = Money.NONE;

    Money m3 = m1.add(m2);

    assertEquals(0, m3.getFiveRupeeCount());
    assertEquals(0, m3.getTenRupeeCount());
    assertEquals(0, m3.getTwentyRupeeCount());
  }

  @Test
  public void testMoneySubtract_WhenBothEmptyMoney() throws InvalidOperationException {
    Money m1 = Money.NONE;
    Money m2 = Money.NONE;

    Money m3 = m1.subtract(m2);

    assertEquals(Money.NONE, m3);
  }

  @Test
  public void testMoneyMultiply_WhenEmptyMoney() throws InvalidOperationException {
    Money m1 = Money.NONE;
    Money m3 = m1.multiply(1);

    assertEquals(Money.NONE, m3);
  }
  @Test
  public void testMoneySubtract_ReturnsCorrectResultWhenSubtractingValidInput()
      throws InvalidOperationException {
    Money m1 = new Money(5, 5, 5);
    Money m2 = new Money(4, 4, 4);

    Money m3 = m1.subtract(m2);

    assertEquals(1, m3.getFiveRupeeCount());
    assertEquals(1, m3.getTenRupeeCount());
    assertEquals(1, m3.getTwentyRupeeCount());
  }

  @Test
  public void testMoneyMultiply_ThrowsExceptionWhenfactorIsZero(){
    Money m1 = Money.TEN;
    assertThrows(InvalidOperationException.class,()->m1.multiply(0));
  }

  @Test
  public void testMoneySubtract_ThrowsExceptionWhenSubtractingMoreFromLess() {
    Money m1 = new Money(5, 5, 5);
    Money m2 = new Money(10, 1, 1);

    assertThrows(InvalidOperationException.class, () -> m1.subtract(m2));
  }

  @Test
  public void testMoney_SetNegativeCurrencyCountThrowsException() {
    Money m1 = Money.NONE;
    assertThrows(InvalidInputException.class, () -> m1.setFiveRupeeCount(-6));
    assertThrows(InvalidInputException.class, () -> m1.setTenRupeeCount(-2));
    assertThrows(InvalidInputException.class, () -> m1.setTwentyRupeeCount(-1));
  }

  @Test
  public void testMoneyValue() {
    Money m1 = new Money(2, 1, 1);
    long expectedValue = 2 * 5 + 10 + 20;
    long value = m1.getValue();
    assertEquals(expectedValue, value);
  }

  @Test
  public void testMoneyOf_returnsValidMoney() throws InvalidOperationException {
    Money m1 = Money.TEN;
    m1 = m1.add(Money.FIVE);
    m1 = m1.add(Money.FIVE);
    m1 = m1.add(Money.FIVE);
    m1 = m1.add(Money.TEN);
    m1 = m1.add(Money.TEN);

    Money m2 = Money.of(40,m1);
    assertEquals(40,m2.getValue());
    assertEquals(new Money(2,3,0), m2);
  }


  @Test
  public void testMoneyOfWithoutVault_returnsValidMoney() throws InvalidOperationException {

    Money m2 = Money.of(40);
    assertEquals(40,m2.getValue());
    assertEquals(new Money(0,0,2), m2);
  }
  @Test
  public void testMoneyOfWithoutVault_returnsExceptionWhenWrongInput(){
      assertThrows(InvalidOperationException.class,()->Money.of(41));
  }
@Test
  public void testMoneyOfWithVault_returnsExceptionWhenWrongInput() {
      assertThrows(InvalidOperationException.class,()->Money.of(41,null));
  }

}
