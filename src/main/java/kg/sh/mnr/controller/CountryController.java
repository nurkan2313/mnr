package kg.sh.mnr.controller;

import kg.sh.mnr.entity.dict.Country;
import kg.sh.mnr.repository.CountryRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/search-countries")
public class CountryController {

    private final CountryRepository countryRepository;

    public CountryController(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @GetMapping
    public List<Country> searchCountries(@RequestParam("query") String query) {
        return countryRepository.findTop5ByNameContainingIgnoreCase(query);
    }
}
