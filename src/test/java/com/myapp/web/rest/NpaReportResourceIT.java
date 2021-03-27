package com.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.myapp.MyApp;
import com.myapp.domain.NpaReport;
import com.myapp.repository.NpaReportRepository;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link NpaReportResource} REST controller.
 */
@SpringBootTest(classes = MyApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class NpaReportResourceIT {
    private static final String DEFAULT_NPA_ID = "AAAAAAAAAA";
    private static final String UPDATED_NPA_ID = "BBBBBBBBBB";

    private static final String DEFAULT_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_LOCATION = "BBBBBBBBBB";

    @Autowired
    private NpaReportRepository npaReportRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNpaReportMockMvc;

    private NpaReport npaReport;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NpaReport createEntity(EntityManager em) {
        NpaReport npaReport = new NpaReport().npaId(DEFAULT_NPA_ID).location(DEFAULT_LOCATION);
        return npaReport;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NpaReport createUpdatedEntity(EntityManager em) {
        NpaReport npaReport = new NpaReport().npaId(UPDATED_NPA_ID).location(UPDATED_LOCATION);
        return npaReport;
    }

    @BeforeEach
    public void initTest() {
        npaReport = createEntity(em);
    }

    @Test
    @Transactional
    public void createNpaReport() throws Exception {
        int databaseSizeBeforeCreate = npaReportRepository.findAll().size();
        // Create the NpaReport
        restNpaReportMockMvc
            .perform(post("/api/npa-reports").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(npaReport)))
            .andExpect(status().isCreated());

        // Validate the NpaReport in the database
        List<NpaReport> npaReportList = npaReportRepository.findAll();
        assertThat(npaReportList).hasSize(databaseSizeBeforeCreate + 1);
        NpaReport testNpaReport = npaReportList.get(npaReportList.size() - 1);
        assertThat(testNpaReport.getNpaId()).isEqualTo(DEFAULT_NPA_ID);
        assertThat(testNpaReport.getLocation()).isEqualTo(DEFAULT_LOCATION);
    }

    @Test
    @Transactional
    public void createNpaReportWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = npaReportRepository.findAll().size();

        // Create the NpaReport with an existing ID
        npaReport.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNpaReportMockMvc
            .perform(post("/api/npa-reports").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(npaReport)))
            .andExpect(status().isBadRequest());

        // Validate the NpaReport in the database
        List<NpaReport> npaReportList = npaReportRepository.findAll();
        assertThat(npaReportList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllNpaReports() throws Exception {
        // Initialize the database
        npaReportRepository.saveAndFlush(npaReport);

        // Get all the npaReportList
        restNpaReportMockMvc
            .perform(get("/api/npa-reports?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(npaReport.getId().intValue())))
            .andExpect(jsonPath("$.[*].npaId").value(hasItem(DEFAULT_NPA_ID)))
            .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION)));
    }

    @Test
    @Transactional
    public void getNpaReport() throws Exception {
        // Initialize the database
        npaReportRepository.saveAndFlush(npaReport);

        // Get the npaReport
        restNpaReportMockMvc
            .perform(get("/api/npa-reports/{id}", npaReport.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(npaReport.getId().intValue()))
            .andExpect(jsonPath("$.npaId").value(DEFAULT_NPA_ID))
            .andExpect(jsonPath("$.location").value(DEFAULT_LOCATION));
    }

    @Test
    @Transactional
    public void getNonExistingNpaReport() throws Exception {
        // Get the npaReport
        restNpaReportMockMvc.perform(get("/api/npa-reports/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNpaReport() throws Exception {
        // Initialize the database
        npaReportRepository.saveAndFlush(npaReport);

        int databaseSizeBeforeUpdate = npaReportRepository.findAll().size();

        // Update the npaReport
        NpaReport updatedNpaReport = npaReportRepository.findById(npaReport.getId()).get();
        // Disconnect from session so that the updates on updatedNpaReport are not directly saved in db
        em.detach(updatedNpaReport);
        updatedNpaReport.npaId(UPDATED_NPA_ID).location(UPDATED_LOCATION);

        restNpaReportMockMvc
            .perform(
                put("/api/npa-reports").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(updatedNpaReport))
            )
            .andExpect(status().isOk());

        // Validate the NpaReport in the database
        List<NpaReport> npaReportList = npaReportRepository.findAll();
        assertThat(npaReportList).hasSize(databaseSizeBeforeUpdate);
        NpaReport testNpaReport = npaReportList.get(npaReportList.size() - 1);
        assertThat(testNpaReport.getNpaId()).isEqualTo(UPDATED_NPA_ID);
        assertThat(testNpaReport.getLocation()).isEqualTo(UPDATED_LOCATION);
    }

    @Test
    @Transactional
    public void updateNonExistingNpaReport() throws Exception {
        int databaseSizeBeforeUpdate = npaReportRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNpaReportMockMvc
            .perform(put("/api/npa-reports").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(npaReport)))
            .andExpect(status().isBadRequest());

        // Validate the NpaReport in the database
        List<NpaReport> npaReportList = npaReportRepository.findAll();
        assertThat(npaReportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteNpaReport() throws Exception {
        // Initialize the database
        npaReportRepository.saveAndFlush(npaReport);

        int databaseSizeBeforeDelete = npaReportRepository.findAll().size();

        // Delete the npaReport
        restNpaReportMockMvc
            .perform(delete("/api/npa-reports/{id}", npaReport.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<NpaReport> npaReportList = npaReportRepository.findAll();
        assertThat(npaReportList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
