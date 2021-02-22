package org.mycompany.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A Payment.
 */
@Entity
@Table(name = "payment")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Payment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "bid_for_story_tree_id")
    private Long bidForStoryTreeId;

    @Column(name = "bidder_id")
    private Long bidderID;

    @Column(name = "story_tree_id")
    private Long storyTreeID;

    @Column(name = "price")
    private Long price;

    @Column(name = "client_secret")
    private String clientSecret;

    @Column(name = "payment_state")
    private String paymentState;

    @Column(name = "payment_message")
    private String paymentMessage;

    @Column(name = "create_time")
    private Instant createTime;

    @Column(name = "update_time")
    private Instant updateTime;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBidForStoryTreeId() {
        return bidForStoryTreeId;
    }

    public Payment bidForStoryTreeId(Long bidForStoryTreeId) {
        this.bidForStoryTreeId = bidForStoryTreeId;
        return this;
    }

    public void setBidForStoryTreeId(Long bidForStoryTreeId) {
        this.bidForStoryTreeId = bidForStoryTreeId;
    }

    public Long getBidderID() {
        return bidderID;
    }

    public Payment bidderID(Long bidderID) {
        this.bidderID = bidderID;
        return this;
    }

    public void setBidderID(Long bidderID) {
        this.bidderID = bidderID;
    }

    public Long getStoryTreeID() {
        return storyTreeID;
    }

    public Payment storyTreeID(Long storyTreeID) {
        this.storyTreeID = storyTreeID;
        return this;
    }

    public void setStoryTreeID(Long storyTreeID) {
        this.storyTreeID = storyTreeID;
    }

    public Long getPrice() {
        return price;
    }

    public Payment price(Long price) {
        this.price = price;
        return this;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public Payment clientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
        return this;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getPaymentState() {
        return paymentState;
    }

    public Payment paymentState(String paymentState) {
        this.paymentState = paymentState;
        return this;
    }

    public void setPaymentState(String paymentState) {
        this.paymentState = paymentState;
    }

    public String getPaymentMessage() {
        return paymentMessage;
    }

    public Payment paymentMessage(String paymentMessage) {
        this.paymentMessage = paymentMessage;
        return this;
    }

    public void setPaymentMessage(String paymentMessage) {
        this.paymentMessage = paymentMessage;
    }

    public Instant getCreateTime() {
        return createTime;
    }

    public Payment createTime(Instant createTime) {
        this.createTime = createTime;
        return this;
    }

    public void setCreateTime(Instant createTime) {
        this.createTime = createTime;
    }

    public Instant getUpdateTime() {
        return updateTime;
    }

    public Payment updateTime(Instant updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public void setUpdateTime(Instant updateTime) {
        this.updateTime = updateTime;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Payment)) {
            return false;
        }
        return id != null && id.equals(((Payment) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Payment{" +
            "id=" + getId() +
            ", bidForStoryTreeId=" + getBidForStoryTreeId() +
            ", bidderID=" + getBidderID() +
            ", storyTreeID=" + getStoryTreeID() +
            ", price=" + getPrice() +
            ", clientSecret='" + getClientSecret() + "'" +
            ", paymentState='" + getPaymentState() + "'" +
            ", paymentMessage='" + getPaymentMessage() + "'" +
            ", createTime='" + getCreateTime() + "'" +
            ", updateTime='" + getUpdateTime() + "'" +
            "}";
    }
}
