package ua.pti.myatm;

import ua.pti.myatm.NoCardInsertedException;
import ua.pti.myatm.NotEnoughtMoneyInATMexception;
import ua.pti.myatm.NotEnoughtMoneyInAccountException;

public class ATM {
    private double moneyInATM;
    private Card card;
    
	//����� �������� ���������� ����� � ��������� 
    ATM(double moneyInATM){
         this.moneyInATM=moneyInATM;
         this.card=null;
    }

    // ���������� ����������� ����� � ���������
    public double getMoneyInATM() {
    	return moneyInATM;
    }
        
    //� ������ ������� ������ ���������� ������ � ������
    //����� ��������� ����� � ���-���, ��������� ���-��� ����� � �� ������������� �� ���
    //���� ������������ ���-��� ��� �������� �������������, ���������� false. ��� ����, ����� ���� ����������� ������� � ATM � ������ ������ ������ ������������ ���������� NoCardInserted
    public boolean validateCard(Card card, int pinCode){
    	if (!card.isBlocked())
    	if (card.checkPin(pinCode)) {
			this.card = card;
			return true;
		}
		
		return false;
    }
    
    //���������� ������� ����� ���� �� �����
    public double checkBalance() throws NoCardInsertedException {
    	if (card == null) throw new NoCardInsertedException("There is no card inserted");
		return card.getAccount().getBalance();
    }
    
    //����� ��� ������ ��������� �����
    //����� ���������� �����, ������� � ������� �������� �� ����� ����� ������
    //����� �������� �����, ����� ��� �� ������ ��������� ���������� �� ����� � ����� ���������
    //���� ������������ ����� �� �����, �� ������ �������������� ���������� NotEnoughMoneyInAccount 
    //���� ������������ ����� � ���������, �� ������ �������������� ���������� NotEnoughMoneyInATM 
    //��� �������� ������ �����, ��������� ����� ������ ����������� �� �����, � � ��������� ������ ����������� ���������� �����
    public double getCash(double amount) throws NoCardInsertedException, NotEnoughtMoneyInAccountException, NotEnoughtMoneyInATMexception {
    	if (card == null) 
			throw new NoCardInsertedException("There is no card inserted");
    	if (getMoneyInATM() < amount) 
			throw new NotEnoughtMoneyInATMexception("Not enought money in ATM");
		if (checkBalance() < amount) 
			throw new NotEnoughtMoneyInAccountException("Not enought money on your card");
		
		card.getAccount().withdrow(amount);
		moneyInATM -= amount;
		return card.getAccount().getBalance();
    }
}
