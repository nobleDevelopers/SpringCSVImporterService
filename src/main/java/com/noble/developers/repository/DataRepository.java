package com.noble.developers.repository;

import com.noble.developers.model.CSVDataModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DataRepository extends JpaRepository<CSVDataModel,String> {
    CSVDataModel findByCode(String code);
}
