package kg.sh.mnr.model.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
public class IncidentFormRequest {

    private String registeredAtString;  // Строка для даты регистрации

    private LocalDateTime registeredAt;  // Преобразованное значение

    private String species;

    private String photoPath;

    private String description;

    private Double count;

    // Связи с другими сущностями
    private Long unitOfMeasurementId;

    private Long authorityId;

    private Long discoveryMethodId;

    private Long reasonForSeizureId;

    private Long transportMethodId;

    private Long tradeDirectionId;

    private String hidingMethod;  // Способ сокрытия

    private String suspectedOriginCountry;  // Предполагаемая страна происхождения

    private String transitCountries; // Страна(ы) транзита

    private String finalDestination;  // Предполагаемый конечный пункт назначения

    private String additionalInfo;  // Дополнительная информация
}
