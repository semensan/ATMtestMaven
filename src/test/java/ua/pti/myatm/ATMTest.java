/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.pti.myatm;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import ua.pti.myatm.NoCardInsertedException;
import ua.pti.myatm.NotEnoughtMoneyInATMexception;
import ua.pti.myatm.NotEnoughtMoneyInAccountException;

public class ATMTest {

    @Test
    public void testGetMoneyInATMReturnsCorrectAmount() {
        ATM instance = new ATM(1000.0);
        double expResult = 1000.0;
        double result = instance.getMoneyInATM();
        
        assertEquals(expResult, result, 0.000001);
    }

    @Test
    public void testValidateCardTrueWithCorrectPinAndCard() {
    	ATM instance = new ATM(1000.0);
        Card card = mock(Card.class);
        
        when(card.checkPin(0000)).thenReturn(true);
		when(card.isBlocked()).thenReturn(false);
		
		assertTrue(instance.validateCard(card, 0000));
		verify(card).isBlocked();
		verify(card).checkPin(0000);
    }

    @Test
    public void testValidateCardFalseWithCorrectPinAndBlockedCard() {
    	ATM instance = new ATM(1000.0);
        Card card = mock(Card.class);
        
        when(card.checkPin(0000)).thenReturn(true);
		when(card.isBlocked()).thenReturn(true);
		
		assertFalse(instance.validateCard(card, 0000));
		verify(card).isBlocked();
		verify(card, never()).checkPin(0000);
    }
    
    @Test
    public void testValidateCardFalseWithIncorrectPinAndNotBlockedCard() {
    	ATM instance = new ATM(1000.0);
        Card card = mock(Card.class);
        
        when(card.checkPin(0000)).thenReturn(false);
		when(card.isBlocked()).thenReturn(false);
		
		assertFalse(instance.validateCard(card, 0000));
		verify(card).isBlocked();
		verify(card).checkPin(0000);
    }

    @Test
    public void testCheckBalanceReturnsCorrectAmount() throws NoCardInsertedException {
        ATM instance = new ATM(1000.0);
        Card card = mock(Card.class);
        Account account = mock(Account.class);
        double expResult = 500.0;
        
        when(card.checkPin(0000)).thenReturn(true);
		when(card.isBlocked()).thenReturn(false);
        when(card.getAccount()).thenReturn(account);
        when(account.getBalance()).thenReturn(500.0);
        
        instance.validateCard(card, 0000);
        
        assertEquals(expResult, instance.checkBalance(), 0.000001);
        verify(card).getAccount();
        verify(account).getBalance();
    }
    
    @Test(expected=NoCardInsertedException.class)
    public void testCheckBalanceThrowsExceptionWithNoCard() throws NoCardInsertedException {
        ATM instance = new ATM(1000.0);
        
        instance.checkBalance();
        
        fail("NoCardInsertedException expected");
    }

    @Test
    public void testGetCashReturnsCorrectAmountWithCorrectCall() throws NoCardInsertedException, NotEnoughtMoneyInAccountException, NotEnoughtMoneyInATMexception {
    	ATM instance = new ATM(1000.0);
        Card card = mock(Card.class);
        Account account = mock(Account.class);
        double amount = 50.0;
        double expResult = 450.0;
        
        when(card.checkPin(0000)).thenReturn(true);
		when(card.isBlocked()).thenReturn(false);
        when(card.getAccount()).thenReturn(account);
        stub(account.getBalance()).toReturn(500.0).toReturn(450.0);
        when(account.withdrow(amount)).thenReturn(amount);
        
        instance.validateCard(card, 0000);
        
        assertEquals(expResult, instance.getCash(amount), 0.000001);
        verify(card, times(3)).getAccount();
        verify(account).withdrow(amount);
        verify(account,times(2)).getBalance();
    }
    
    @Test(expected=NoCardInsertedException.class)
    public void testGetCashThrowsNoCardInsertedExceptionWithNoCard() throws NoCardInsertedException, NotEnoughtMoneyInAccountException, NotEnoughtMoneyInATMexception {
    	ATM instance = new ATM(1000.0);
    	instance.getCash(50.0);
    	
    	fail("NoCardInsertedException expected");
    }
    
    @Test(expected=NotEnoughtMoneyInAccountException.class)
    public void testGetCashThrowsNotEnoughtMoneyInAccountException() throws NoCardInsertedException, NotEnoughtMoneyInAccountException, NotEnoughtMoneyInATMexception {
    	ATM instance = new ATM(1000.0);
        Card card = mock(Card.class);
        Account account = mock(Account.class);
        double amount = 150.0;
        
        when(card.checkPin(0000)).thenReturn(true);
		when(card.isBlocked()).thenReturn(false);
        when(card.getAccount()).thenReturn(account);
        stub(account.getBalance()).toReturn(50.0).toReturn(-100.0);
        when(account.withdrow(amount)).thenReturn(amount);
        
        instance.validateCard(card, 0000);
        instance.getCash(amount);
    	
    	fail("NotEnoughtMoneyInAccountException expected");
    }
    
    @Test(expected=NotEnoughtMoneyInATMexception.class)
    public void testGetCashThrowsNotEnoughtMoneyInATMexception() throws NoCardInsertedException, NotEnoughtMoneyInAccountException, NotEnoughtMoneyInATMexception {
    	ATM instance = new ATM(100.0);
        Card card = mock(Card.class);
        Account account = mock(Account.class);
        double amount = 150.0;
        
        when(card.checkPin(0000)).thenReturn(true);
		when(card.isBlocked()).thenReturn(false);
        when(card.getAccount()).thenReturn(account);
        stub(account.getBalance()).toReturn(500.0).toReturn(350.0);
        when(account.withdrow(amount)).thenReturn(amount);
        
        instance.validateCard(card, 0000);
        instance.getCash(amount);
    	
    	fail("NotEnoughtMoneyInATMexception expected");
    }
    
}
