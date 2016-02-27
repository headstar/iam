package com.headstartech.iam.client;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.headstartech.iam.common.dto.Domain;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestOperations;

import java.net.URI;
import java.net.URISyntaxException;

public class DefaultIAMClient implements IAMClient {

    private final RestOperations restOperations;
    private final String baseRestURL;

    public DefaultIAMClient(RestOperations restOperations, String baseRestURL) {
        this.restOperations = restOperations;
        this.baseRestURL = baseRestURL;
    }

    @Override
    public Domain createDomain(Domain domain) {
        RequestEntity<Domain> request = RequestEntity.post(toURI(getDomainsBaseURL())).accept(MediaTypes.HAL_JSON).contentType(MediaType.APPLICATION_JSON).body(domain);
        ResponseEntity<DomainResource> responseEntity = restOperations.exchange(request, new ParameterizedTypeReference<DomainResource>() {});
        return responseEntity.getBody().getContent();
    }

    @Override
    public Domain getDomain(String id) {
        RequestEntity<Void> request = RequestEntity.get(toURI(getDomainURL(id))).accept(MediaTypes.HAL_JSON).build();
        ResponseEntity<DomainResource> responseEntity = restOperations.exchange(request, new ParameterizedTypeReference<DomainResource>() {});
        return responseEntity.getBody().getContent();
    }

    @Override
    public Domain updateDomain(Domain domain) {
        RequestEntity<Domain> request = RequestEntity.put(toURI(getDomainURL(domain.getId()))).accept(MediaTypes.HAL_JSON).contentType(MediaType.APPLICATION_JSON).body(domain);
        ResponseEntity<DomainResource> responseEntity = restOperations.exchange(request, new ParameterizedTypeReference<DomainResource>() {});
        return responseEntity.getBody().getContent();
    }

    @Override
    public void deleteDomain(String id) {
        restOperations.delete(getDomainURL(id));
    }

    private String getDomainsBaseURL() {
        return baseRestURL + "/domains";
    }

    private String getDomainURL(String domainId) {
        return String.format("%s/%s", getDomainsBaseURL(), domainId);
    }

    private URI toURI(String s) {
        try {
            return new URI(s);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    static class DomainResource extends Resource<Domain> {

        @JsonCreator
        public DomainResource(Domain domain) {
            super(domain);
        }
    }

}
