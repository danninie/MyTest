package org.mycompany.web.rest;

import org.mycompany.TryPaymentApp;
import org.mycompany.domain.Payment;
import org.mycompany.repository.PaymentRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link PaymentResource} REST controller.
 */
@SpringBootTest(classes = TryPaymentApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PaymentResourceIT {

    private static final Long DEFAULT_BID_FOR_STORY_TREE_ID = 1L;
    private static final Long UPDATED_BID_FOR_STORY_TREE_ID = 2L;

    private static final Long DEFAULT_BIDDER_ID = 1L;
    private static final Long UPDATED_BIDDER_ID = 2L;

    private static final Long DEFAULT_STORY_TREE_ID = 1L;
    private static final Long UPDATED_STORY_TREE_ID = 2L;

    private static final Long DEFAULT_PRICE = 1L;
    private static final Long UPDATED_PRICE = 2L;

    private static final String DEFAULT_CLIENT_SECRET = "AAAAAAAAAA";
    private static final String UPDATED_CLIENT_SECRET = "BBBBBBBBBB";

    private static final String DEFAULT_PAYMENT_STATE = "AAAAAAAAAA";
    private static final String UPDATED_PAYMENT_STATE = "BBBBBBBBBB";

    private static final String DEFAULT_PAYMENT_MESSAGE = "AAAAAAAAAA";
    private static final String UPDATED_PAYMENT_MESSAGE = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPaymentMockMvc;

    private Payment payment;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Payment createEntity(EntityManager em) {
        Payment payment = new Payment()
            .bidForStoryTreeId(DEFAULT_BID_FOR_STORY_TREE_ID)
            .bidderID(DEFAULT_BIDDER_ID)
            .storyTreeID(DEFAULT_STORY_TREE_ID)
            .price(DEFAULT_PRICE)
            .clientSecret(DEFAULT_CLIENT_SECRET)
            .paymentState(DEFAULT_PAYMENT_STATE)
            .paymentMessage(DEFAULT_PAYMENT_MESSAGE)
            .createTime(DEFAULT_CREATE_TIME)
            .updateTime(DEFAULT_UPDATE_TIME);
        return payment;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Payment createUpdatedEntity(EntityManager em) {
        Payment payment = new Payment()
            .bidForStoryTreeId(UPDATED_BID_FOR_STORY_TREE_ID)
            .bidderID(UPDATED_BIDDER_ID)
            .storyTreeID(UPDATED_STORY_TREE_ID)
            .price(UPDATED_PRICE)
            .clientSecret(UPDATED_CLIENT_SECRET)
            .paymentState(UPDATED_PAYMENT_STATE)
            .paymentMessage(UPDATED_PAYMENT_MESSAGE)
            .createTime(UPDATED_CREATE_TIME)
            .updateTime(UPDATED_UPDATE_TIME);
        return payment;
    }

    @BeforeEach
    public void initTest() {
        payment = createEntity(em);
    }

    @Test
    @Transactional
    public void createPayment() throws Exception {
        int databaseSizeBeforeCreate = paymentRepository.findAll().size();
        // Create the Payment
        restPaymentMockMvc.perform(post("/api/payments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(payment)))
            .andExpect(status().isCreated());

        // Validate the Payment in the database
        List<Payment> paymentList = paymentRepository.findAll();
        assertThat(paymentList).hasSize(databaseSizeBeforeCreate + 1);
        Payment testPayment = paymentList.get(paymentList.size() - 1);
        assertThat(testPayment.getBidForStoryTreeId()).isEqualTo(DEFAULT_BID_FOR_STORY_TREE_ID);
        assertThat(testPayment.getBidderID()).isEqualTo(DEFAULT_BIDDER_ID);
        assertThat(testPayment.getStoryTreeID()).isEqualTo(DEFAULT_STORY_TREE_ID);
        assertThat(testPayment.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testPayment.getClientSecret()).isEqualTo(DEFAULT_CLIENT_SECRET);
        assertThat(testPayment.getPaymentState()).isEqualTo(DEFAULT_PAYMENT_STATE);
        assertThat(testPayment.getPaymentMessage()).isEqualTo(DEFAULT_PAYMENT_MESSAGE);
        assertThat(testPayment.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
        assertThat(testPayment.getUpdateTime()).isEqualTo(DEFAULT_UPDATE_TIME);
    }

    @Test
    @Transactional
    public void createPaymentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = paymentRepository.findAll().size();

        // Create the Payment with an existing ID
        payment.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPaymentMockMvc.perform(post("/api/payments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(payment)))
            .andExpect(status().isBadRequest());

        // Validate the Payment in the database
        List<Payment> paymentList = paymentRepository.findAll();
        assertThat(paymentList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPayments() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList
        restPaymentMockMvc.perform(get("/api/payments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(payment.getId().intValue())))
            .andExpect(jsonPath("$.[*].bidForStoryTreeId").value(hasItem(DEFAULT_BID_FOR_STORY_TREE_ID.intValue())))
            .andExpect(jsonPath("$.[*].bidderID").value(hasItem(DEFAULT_BIDDER_ID.intValue())))
            .andExpect(jsonPath("$.[*].storyTreeID").value(hasItem(DEFAULT_STORY_TREE_ID.intValue())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].clientSecret").value(hasItem(DEFAULT_CLIENT_SECRET)))
            .andExpect(jsonPath("$.[*].paymentState").value(hasItem(DEFAULT_PAYMENT_STATE)))
            .andExpect(jsonPath("$.[*].paymentMessage").value(hasItem(DEFAULT_PAYMENT_MESSAGE)))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(DEFAULT_CREATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].updateTime").value(hasItem(DEFAULT_UPDATE_TIME.toString())));
    }
    
    @Test
    @Transactional
    public void getPayment() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get the payment
        restPaymentMockMvc.perform(get("/api/payments/{id}", payment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(payment.getId().intValue()))
            .andExpect(jsonPath("$.bidForStoryTreeId").value(DEFAULT_BID_FOR_STORY_TREE_ID.intValue()))
            .andExpect(jsonPath("$.bidderID").value(DEFAULT_BIDDER_ID.intValue()))
            .andExpect(jsonPath("$.storyTreeID").value(DEFAULT_STORY_TREE_ID.intValue()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.intValue()))
            .andExpect(jsonPath("$.clientSecret").value(DEFAULT_CLIENT_SECRET))
            .andExpect(jsonPath("$.paymentState").value(DEFAULT_PAYMENT_STATE))
            .andExpect(jsonPath("$.paymentMessage").value(DEFAULT_PAYMENT_MESSAGE))
            .andExpect(jsonPath("$.createTime").value(DEFAULT_CREATE_TIME.toString()))
            .andExpect(jsonPath("$.updateTime").value(DEFAULT_UPDATE_TIME.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingPayment() throws Exception {
        // Get the payment
        restPaymentMockMvc.perform(get("/api/payments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePayment() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        int databaseSizeBeforeUpdate = paymentRepository.findAll().size();

        // Update the payment
        Payment updatedPayment = paymentRepository.findById(payment.getId()).get();
        // Disconnect from session so that the updates on updatedPayment are not directly saved in db
        em.detach(updatedPayment);
        updatedPayment
            .bidForStoryTreeId(UPDATED_BID_FOR_STORY_TREE_ID)
            .bidderID(UPDATED_BIDDER_ID)
            .storyTreeID(UPDATED_STORY_TREE_ID)
            .price(UPDATED_PRICE)
            .clientSecret(UPDATED_CLIENT_SECRET)
            .paymentState(UPDATED_PAYMENT_STATE)
            .paymentMessage(UPDATED_PAYMENT_MESSAGE)
            .createTime(UPDATED_CREATE_TIME)
            .updateTime(UPDATED_UPDATE_TIME);

        restPaymentMockMvc.perform(put("/api/payments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPayment)))
            .andExpect(status().isOk());

        // Validate the Payment in the database
        List<Payment> paymentList = paymentRepository.findAll();
        assertThat(paymentList).hasSize(databaseSizeBeforeUpdate);
        Payment testPayment = paymentList.get(paymentList.size() - 1);
        assertThat(testPayment.getBidForStoryTreeId()).isEqualTo(UPDATED_BID_FOR_STORY_TREE_ID);
        assertThat(testPayment.getBidderID()).isEqualTo(UPDATED_BIDDER_ID);
        assertThat(testPayment.getStoryTreeID()).isEqualTo(UPDATED_STORY_TREE_ID);
        assertThat(testPayment.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testPayment.getClientSecret()).isEqualTo(UPDATED_CLIENT_SECRET);
        assertThat(testPayment.getPaymentState()).isEqualTo(UPDATED_PAYMENT_STATE);
        assertThat(testPayment.getPaymentMessage()).isEqualTo(UPDATED_PAYMENT_MESSAGE);
        assertThat(testPayment.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
        assertThat(testPayment.getUpdateTime()).isEqualTo(UPDATED_UPDATE_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingPayment() throws Exception {
        int databaseSizeBeforeUpdate = paymentRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaymentMockMvc.perform(put("/api/payments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(payment)))
            .andExpect(status().isBadRequest());

        // Validate the Payment in the database
        List<Payment> paymentList = paymentRepository.findAll();
        assertThat(paymentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePayment() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        int databaseSizeBeforeDelete = paymentRepository.findAll().size();

        // Delete the payment
        restPaymentMockMvc.perform(delete("/api/payments/{id}", payment.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Payment> paymentList = paymentRepository.findAll();
        assertThat(paymentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
