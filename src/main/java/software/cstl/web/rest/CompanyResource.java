package software.cstl.web.rest;

import software.cstl.domain.Company;
import software.cstl.service.CompanyService;
import software.cstl.web.rest.errors.BadRequestAlertException;
import software.cstl.service.dto.CompanyCriteria;
import software.cstl.service.CompanyQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link software.cstl.domain.Company}.
 */
@RestController
@RequestMapping("/api")
public class CompanyResource {

    private final Logger log = LoggerFactory.getLogger(CompanyResource.class);

    private static final String ENTITY_NAME = "company";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CompanyService companyService;

    private final CompanyQueryService companyQueryService;

    public CompanyResource(CompanyService companyService, CompanyQueryService companyQueryService) {
        this.companyService = companyService;
        this.companyQueryService = companyQueryService;
    }

    /**
     * {@code POST  /companies} : Create a new company.
     *
     * @param company the company to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new company, or with status {@code 400 (Bad Request)} if the company has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/companies")
    public ResponseEntity<Company> createCompany(@Valid @RequestBody Company company) throws URISyntaxException {
        log.debug("REST request to save Company : {}", company);
        if (company.getId() != null) {
            throw new BadRequestAlertException("A new company cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Company result = companyService.save(company);
        return ResponseEntity.created(new URI("/api/companies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /companies} : Updates an existing company.
     *
     * @param company the company to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated company,
     * or with status {@code 400 (Bad Request)} if the company is not valid,
     * or with status {@code 500 (Internal Server Error)} if the company couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/companies")
    public ResponseEntity<Company> updateCompany(@Valid @RequestBody Company company) throws URISyntaxException {
        log.debug("REST request to update Company : {}", company);
        if (company.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Company result = companyService.save(company);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, company.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /companies} : get all the companies.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of companies in body.
     */
    @GetMapping("/companies")
    public ResponseEntity<List<Company>> getAllCompanies(CompanyCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Companies by criteria: {}", criteria);
        Page<Company> page = companyQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /companies/count} : count all the companies.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/companies/count")
    public ResponseEntity<Long> countCompanies(CompanyCriteria criteria) {
        log.debug("REST request to count Companies by criteria: {}", criteria);
        return ResponseEntity.ok().body(companyQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /companies/:id} : get the "id" company.
     *
     * @param id the id of the company to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the company, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/companies/{id}")
    public ResponseEntity<Company> getCompany(@PathVariable Long id) {
        log.debug("REST request to get Company : {}", id);
        Optional<Company> company = companyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(company);
    }

    /**
     * {@code DELETE  /companies/:id} : delete the "id" company.
     *
     * @param id the id of the company to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/companies/{id}")
    public ResponseEntity<Void> deleteCompany(@PathVariable Long id) {
        log.debug("REST request to delete Company : {}", id);
        companyService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/companies?query=:query} : search for the company corresponding
     * to the query.
     *
     * @param query the query of the company search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/companies")
    public ResponseEntity<List<Company>> searchCompanies(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Companies for query {}", query);
        Page<Company> page = companyService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
}
