package kg.sh.mnr.model;


import kg.sh.mnr.entity.Incident;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class IncidentViewDto {
    private UUID id;
    private LocalDate registeredAt;
    private String species;
    private String latinName; // Из Product
    private String description;
    private Double count;
    private String photoPath;

    private String unitOfMeasurement;
    private String authority;
    private String discoveryMethod;
    private String reasonForSeizure;
    private String transportMethod;
    private String tradeDirection;

    private String hidingMethod;
    private String suspectedOriginCountry;
    private String transitCountries;
    private String finalDestination;
    private String additionalInfo;

    public IncidentViewDto(Incident incident, String latinName) {
        this.id = incident.getId();
        this.registeredAt = incident.getRegisteredAt();
        this.species = incident.getSpecies();
        this.latinName = latinName;
        this.description = incident.getDescription();
        this.count = incident.getCount();
        this.photoPath = incident.getPhotoPath();

        this.unitOfMeasurement = incident.getUnitOfMeasurement() != null ? incident.getUnitOfMeasurement().getUnit() : null;
        this.authority = incident.getAuthority() != null ? incident.getAuthority().getName() : null;
        this.discoveryMethod = incident.getDiscoveryMethod() != null ? incident.getDiscoveryMethod().getMethod() : null;
        this.reasonForSeizure = incident.getReasonForSeizure() != null ? incident.getReasonForSeizure().getReason() : null;
        this.transportMethod = incident.getTransportMethod() != null ? incident.getTransportMethod().getMethod() : null;
        this.tradeDirection = incident.getTradeDirection() != null ? incident.getTradeDirection().getDirection() : null;
        this.hidingMethod = incident.getHidingMethod();
        this.suspectedOriginCountry = incident.getSuspectedOriginCountry() != null ? incident.getSuspectedOriginCountry().getName() : null;
        this.transitCountries = incident.getTransitCountries();
        this.finalDestination = incident.getFinalDestination();
        this.additionalInfo = incident.getAdditionalInfo();
    }

}

