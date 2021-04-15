
package com.rabo.tppapi.model;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "debtorIBAN",
    "creditorIBAN",
    "amount",
    "currency",
    "endToEndId"
})
public class PaymentInitiationRequest {

    @JsonProperty("debtorIBAN")
    private String debtorIBAN;
    @JsonProperty("creditorIBAN")
    private String creditorIBAN;
    @JsonProperty("amount")
    private String amount;
    @JsonProperty("currency")
    private String currency;
    @JsonProperty("endToEndId")
    private String endToEndId;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("debtorIBAN")
    public String getDebtorIBAN() {
        return debtorIBAN;
    }

    @JsonProperty("debtorIBAN")
    public void setDebtorIBAN(String debtorIBAN) {
        this.debtorIBAN = debtorIBAN;
    }

    @JsonProperty("creditorIBAN")
    public String getCreditorIBAN() {
        return creditorIBAN;
    }

    @JsonProperty("creditorIBAN")
    public void setCreditorIBAN(String creditorIBAN) {
        this.creditorIBAN = creditorIBAN;
    }

    @JsonProperty("amount")
    public String getAmount() {
        return amount;
    }

    @JsonProperty("amount")
    public void setAmount(String amount) {
        this.amount = amount;
    }

    @JsonProperty("currency")
    public String getCurrency() {
        return currency;
    }

    @JsonProperty("currency")
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @JsonProperty("endToEndId")
    public String getEndToEndId() {
        return endToEndId;
    }

    @JsonProperty("endToEndId")
    public void setEndToEndId(String endToEndId) {
        this.endToEndId = endToEndId;
    }

    
	@Override
	public String toString() {
		return "PaymentInitiationRequest [debtorIBAN=" + debtorIBAN + ", creditorIBAN=" + creditorIBAN + ", amount="
				+ amount + ", currency=" + currency + ", endToEndId=" + endToEndId + "]";
	}

	@JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(debtorIBAN).append(creditorIBAN).append(amount).append(currency).append(endToEndId).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof PaymentInitiationRequest) == false) {
            return false;
        }
        PaymentInitiationRequest rhs = ((PaymentInitiationRequest) other);
        return new EqualsBuilder().append(debtorIBAN, rhs.debtorIBAN).append(creditorIBAN, rhs.creditorIBAN).append(amount, rhs.amount).append(currency, rhs.currency).append(endToEndId, rhs.endToEndId).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
