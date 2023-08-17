package com.noble.developers.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;


@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)

public class CSVDataDto {
    String code;

    String source;

    String codeListCode;

    String displayValue;




    @Nullable
    String longDescription;
    @Nullable
    String fromDate;

    @Nullable
    String toDate;

    int sortingPriority;
}
