package kg.sh.mnr.service;

import kg.sh.mnr.entity.dict.*;
import kg.sh.mnr.repository.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DictionaryService {
    CountryRepository countryRepository;
    AuthorityRepository authorityRepository;
    UnitOfMeasurementRepository unitOfMeasurementRepository;
    DiscoveryMethodRepository discoveryMethodRepository;
    ReasonForSeizureRepository reasonForSeizureRepository;
    TradeDirectionRepository tradeDirectionRepository;
    TransportMethodRepository transportMethodRepository;
    ProductRepository productRepository;

    public Page<Product> getFilteredProducts(String description, String code, Pageable pageable) {
        if (description != null && code != null) {
            return productRepository.findByDescriptionContainingAndCodeContaining(description, code, pageable);
        } else if (description != null) {
            return productRepository.findByDescriptionContaining(description, pageable);
        } else if (code != null) {
            return productRepository.findByCodeContaining(code, pageable);
        } else {
            return productRepository.findAll(pageable);
        }
    }

    public Product findProductByDescription(String description) {
        return productRepository.findFirstByDescription(description).orElse(null); // или другой подход
    }

    public List<Product> getSimilarProducts(String species) {
        return productRepository.findSimilarProducts(species);
    }

    public List<Country> getSimilarCountries(String country) {
        return countryRepository.getSimilarCountries(country);
    }

    public List<UnitOfMeasurement> getSimilarUnitOfMeasurements(String unitOfMeasurement) {
        return unitOfMeasurementRepository.searchUnitsByName(unitOfMeasurement);
    }

    public List<Country> searchCountriesByName(String country) {
        return countryRepository.searchCountriesByName(country);
    }

    public List<Product> getProductsAndCountries(String species, String country) {
        return productRepository.searchProductsAndCountries(species, country);
    }

    public Page<Product> getProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public List<Authority> getAuthorities() {
        return authorityRepository.findAll();
    }

    public List<UnitOfMeasurement> getUnitOfMeasurements() {return unitOfMeasurementRepository.findAll();}

    public List<DiscoveryMethod> getDiscoveryMethods() {return discoveryMethodRepository.findAll();}

    public List<ReasonForSeizure> getReasonForSeizures() {return reasonForSeizureRepository.findAll();}

    public List<TradeDirection> getTradeDirections() {return tradeDirectionRepository.findAll();}

    public List<TransportMethod> getTransportMethod() {return transportMethodRepository.findAll();
    }

    public List<Country> getAllCountries() {return countryRepository.findAll();}

    public PagedModel<Country> getCountries(String name, Pageable pageable) {
        if (name == null || name.isEmpty()) {
            return new PagedModel<>(countryRepository.findAll(pageable));
        } else {
            return new PagedModel<>(countryRepository.searchBy(name, pageable));
        }
    }
}
