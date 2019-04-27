package revolutMoneyTransfer.model;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="transactions")
public class MoneyTransaction {
	
	@Id
	@GeneratedValue
	private Long id;
	private Long fromBankAccountId;
	private Long toBankAccountId;
	private Double amountToBeTransferred;
	private String currency;
	private Date creationDate;
	private Date updateDate;
	@Enumerated(EnumType.ORDINAL)
	private TransactionStatus status;
	private String message;
	public MoneyTransaction() {
		super();
		
	}
	public MoneyTransaction(Long id, Long fromBankAccountId, Long toBankAccountId, Double amountToBeTransferred,
			String currency, Date creationDate, Date updateDate, TransactionStatus status, String message) {
		super();
		this.id = id;
		this.fromBankAccountId = fromBankAccountId;
		this.toBankAccountId = toBankAccountId;
		this.amountToBeTransferred = amountToBeTransferred;
		this.currency = currency;
		this.creationDate = creationDate;
		this.updateDate = updateDate;
		this.status = status;
		this.message = message;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getFromBankAccountId() {
		return fromBankAccountId;
	}
	public void setFromBankAccountId(Long fromBankAccountId) {
		this.fromBankAccountId = fromBankAccountId;
	}
	public Long getToBankAccountId() {
		return toBankAccountId;
	}
	public void setToBankAccountId(Long toBankAccountId) {
		this.toBankAccountId = toBankAccountId;
	}
	public Double getAmountToBeTransferred() {
		return amountToBeTransferred;
	}
	public void setAmountToBeTransferred(Double amountToBeTransferred) {
		this.amountToBeTransferred = amountToBeTransferred;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public TransactionStatus getStatus() {
		return status;
	}
	public void setStatus(TransactionStatus status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	@Override
	public String toString() {
		return "Transaction [id=" + id + ", fromBankAccountId=" + fromBankAccountId + ", toBankAccountId="
				+ toBankAccountId + ", amountToBeTransferred=" + amountToBeTransferred + ", currency=" + currency
				+ ", creationDate=" + creationDate + ", updateDate=" + updateDate + ", status=" + status + ", message="
				+ message + "]";
	}


	
	




}
