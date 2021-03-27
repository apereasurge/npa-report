package com.myapp.web.rest;

import com.myapp.domain.NpaReport;
import com.myapp.repository.NpaReportRepository;
import com.myapp.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing {@link com.myapp.domain.NpaReport}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class NpaReportResource {
    private final Logger log = LoggerFactory.getLogger(NpaReportResource.class);

    private static final String ENTITY_NAME = "npaReport";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NpaReportRepository npaReportRepository;

    public NpaReportResource(NpaReportRepository npaReportRepository) {
        this.npaReportRepository = npaReportRepository;
    }

    /**
     * {@code POST  /npa-reports} : Create a new npaReport.
     *
     * @param npaReport the npaReport to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new npaReport, or with status {@code 400 (Bad Request)} if the npaReport has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/npa-reports")
    public ResponseEntity<NpaReport> createNpaReport(@RequestBody NpaReport npaReport) throws URISyntaxException {
        log.debug("REST request to save NpaReport : {}", npaReport);
        if (npaReport.getId() != null) {
            throw new BadRequestAlertException("A new npaReport cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NpaReport result = npaReportRepository.save(npaReport);
        return ResponseEntity
            .created(new URI("/api/npa-reports/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /npa-reports} : Updates an existing npaReport.
     *
     * @param npaReport the npaReport to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated npaReport,
     * or with status {@code 400 (Bad Request)} if the npaReport is not valid,
     * or with status {@code 500 (Internal Server Error)} if the npaReport couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/npa-reports")
    public ResponseEntity<NpaReport> updateNpaReport(@RequestBody NpaReport npaReport) throws URISyntaxException {
        log.debug("REST request to update NpaReport : {}", npaReport);
        if (npaReport.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        NpaReport result = npaReportRepository.save(npaReport);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, npaReport.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /npa-reports} : get all the npaReports.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of npaReports in body.
     */
    @GetMapping("/npa-reports")
    public List<NpaReport> getAllNpaReports() {
        log.debug("REST request to get all NpaReports");
        return npaReportRepository.findAll();
    }

    /**
     * {@code GET  /npa-reports/:id} : get the "id" npaReport.
     *
     * @param id the id of the npaReport to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the npaReport, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/npa-reports/{id}")
    public ResponseEntity<NpaReport> getNpaReport(@PathVariable Long id) {
        log.debug("REST request to get NpaReport : {}", id);
        Optional<NpaReport> npaReport = npaReportRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(npaReport);
    }

    /**
     * {@code DELETE  /npa-reports/:id} : delete the "id" npaReport.
     *
     * @param id the id of the npaReport to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/npa-reports/{id}")
    public ResponseEntity<Void> deleteNpaReport(@PathVariable Long id) {
        log.debug("REST request to delete NpaReport : {}", id);
        npaReportRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
