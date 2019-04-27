package revolutMoneyTransfer.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table(name="account")
public class Account {

	 
	@Id
	@GeneratedValue
	private long id; 
	private String ownerName;
	private String nickName;
	private Double balance;
	private Double blockedAmount;
	private String currency;
	public Account() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

	
	public Account(long id, String ownerName, String nickName, Double balance, Double blockedAmount, String currency) {
		super();
		this.id = id;
		this.ownerName = ownerName;
		this.nickName = nickName;
		this.balance = balance;
		this.blockedAmount = blockedAmount;
		this.currency = currency;
	}




	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getOwnerName() {
		return ownerName;
	}
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
	public Double getBalance() {
		return balance;
	}
	public void setBalance(Double balance) {
		this.balance = balance;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Double getBlockedAmount() {
		if(blockedAmount==null)
			return 0.0;
		return blockedAmount;
	}
	public void setBlockedAmount(Double blockedAmount) {
		this.blockedAmount = blockedAmount;
	}
	
	@Override
	public String toString() {
		return "Account [id=" + id + ", ownerName=" + ownerName + ", balance=" + balance + ", currency=" + currency
				+ "]";
	}




	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((balance == null) ? 0 : balance.hashCode());
		result = prime * result + ((blockedAmount == null) ? 0 : blockedAmount.hashCode());
		result = prime * result + ((currency == null) ? 0 : currency.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((nickName == null) ? 0 : nickName.hashCode());
		result = prime * result + ((ownerName == null) ? 0 : ownerName.hashCode());
		return result;
	}




	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		if (balance == null) {
			if (other.balance != null)
				return false;
		} else if (!balance.equals(other.balance))
			return false;
		if (blockedAmount == null) {
			if (other.blockedAmount != null)
				return false;
		} else if (!blockedAmount.equals(other.blockedAmount))
			return false;
		if (currency == null) {
			if (other.currency != null)
				return false;
		} else if (!currency.equals(other.currency))
			return false;
		if (id != other.id)
			return false;
		if (nickName == null) {
			if (other.nickName != null)
				return false;
		} else if (!nickName.equals(other.nickName))
			return false;
		if (ownerName == null) {
			if (other.ownerName != null)
				return false;
		} else if (!ownerName.equals(other.ownerName))
			return false;
		return true;
	}



	

	
	
	
	
	
	
	





}
