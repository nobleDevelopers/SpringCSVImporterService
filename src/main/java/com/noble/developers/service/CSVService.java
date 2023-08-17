package com.noble.developers.service;

import com.noble.developers.dto.CSVDataDto;
import com.noble.developers.exception.DataNotFoundException;
import com.noble.developers.exception.EmptyFileException;
import com.noble.developers.exception.InvalidFileTypeException;
import com.noble.developers.exception.UnableToParseFileException;
import com.noble.developers.model.CSVDataModel;
import com.noble.developers.repository.DataRepository;
import com.noble.developers.util.CSVHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CSVService {
    private final DataRepository repository;

    public CSVService(DataRepository repository) {
        this.repository = repository;
    }

    public void save(MultipartFile file)  {

        if(file.isEmpty())
            throw new EmptyFileException("File is empty");

        if(!CSVHelper.hasCSVFormat(file))
            throw new InvalidFileTypeException("File is not in CSV format");

        List<CSVDataModel> dataModelList = CSVHelper.csvToModels(file);
        if(dataModelList.isEmpty())
            throw new UnableToParseFileException("Unable to parse the file");

        repository.saveAll(dataModelList);
    }

    public List<CSVDataDto> getAllModels() {
        List<CSVDataDto> list =
                repository.findAll().stream().map(data -> CSVHelper.mapModelFromEntity(data)).collect(Collectors.toList());
        if(list.isEmpty())
            throw new DataNotFoundException("No Data Model is existed in in-memory db");
        return list;
    }

    public CSVDataDto fetchByCode(String code) {
        CSVDataModel model = repository.findByCode(code);
        if(model == null)
            throw new DataNotFoundException("Data Model with code "+code+" not existed");
        return CSVHelper.mapModelFromEntity(model);
    }

    public void deleteAll() {
       repository.deleteAll();
    }
}
