package com.noble.developers.model;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "tbl_dataModel")

public class CSVDataModel {
    @Id
    String code;

    String source;

    String codeListCode;

    String displayValue;

    @Column(nullable = true)
    String longDescription;

    @Column(nullable = true)
    LocalDate fromDate;

    @Column(nullable = true)
    LocalDate toDate;

    int sortingPriority;

    @Override
    public String toString() {
        return "CSVDataModel{" +
                "code='" + code + '\'' +
                ", source='" + source + '\'' +
                ", codeListCode='" + codeListCode + '\'' +
                ", displayValue='" + displayValue + '\'' +
                ", longDescription='" + longDescription + '\'' +
                ", fromDate=" + fromDate +
                ", toDate=" + toDate +
                ", sortingPriority=" + sortingPriority +
                '}';
    }
}
