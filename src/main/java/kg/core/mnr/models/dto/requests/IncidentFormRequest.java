package kg.core.mnr.models.dto.requests;

import kg.core.mnr.models.entity.dict.TransitCountry;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class IncidentFormRequest {

    @NotBlank(message = "Дата регистрации обязательна")
    private String registeredAtString;  // Строка для даты регистрации

    private LocalDateTime registeredAt;  // Преобразованное значение

    @NotBlank(message = "Вид обязателен")
    private String species;

    private String photoPath;

    @NotBlank(message = "Описание обязательно")
    private String description;

    @NotNull(message = "Количество обязательно")
    @Positive(message = "Количество должно быть положительным числом")
    private Double count;

    // Связи с другими сущностями
    @NotNull(message = "Единица измерения обязательна")
    private Long unitOfMeasurementId;

    @NotNull(message = "Власть обязательна")
    private Long authorityId;

    @NotNull(message = "Метод обнаружения обязателен")
    private Long discoveryMethodId;

    @NotNull(message = "Причина изъятия обязательна")
    private Long reasonForSeizureId;

    @NotNull(message = "Метод транспортировки обязателен")
    private Long transportMethodId;

    @NotNull(message = "Направление торговли обязательно")
    private Long tradeDirectionId;

    private String hidingMethod;  // Способ сокрытия

    private String suspectedOriginCountry;  // Предполагаемая страна происхождения

    private String transitCountries; // Страна(ы) транзита

    private String finalDestination;  // Предполагаемый конечный пункт назначения

    private String additionalInfo;  // Дополнительная информация
}
