package com.noble.developers.util;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.noble.developers.dto.CSVDataDto;
import com.noble.developers.exception.UnableToParseFileException;
import com.noble.developers.model.CSVDataModel;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.web.multipart.MultipartFile;


public final class CSVHelper {
    public static final String TYPE = "text/csv";
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    static Logger logger = Logger.getLogger(CSVHelper.class.getName());
    private static final String FIELD_FROM_DATE = "fromDate";
    private static final String FIELD_TO_DATE = "toDate";
    private static final String FIELD_SORTING_PRIORITY = "sortingPriority";
    private static final ModelMapper modelMapper = new ModelMapper();
    static {
        // configure ModelMapper if necessary
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }
    public static boolean hasCSVFormat(MultipartFile file) {

        if (!TYPE.equals(file.getContentType())) {
            return false;
        }

        return true;
    }

    public static List<CSVDataModel> csvToModels(MultipartFile file) {

        try (
                InputStream fileInputStream = file.getInputStream();
                BufferedReader fileReader = new BufferedReader(new InputStreamReader(fileInputStream, "UTF-8"));
             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

            List<CSVDataModel> dataModelList = new ArrayList<>();

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            csvRecords.forEach(csvRecord->{
                LocalDate fromDate = null;
                LocalDate  toDate = null;
                int sortingP = 0;

                try {
                    String fromDateStr = csvRecord.get(FIELD_FROM_DATE);
                    if(!fromDateStr.isEmpty())
                        fromDate = LocalDate.parse(fromDateStr, dateFormatter);
                }catch (DateTimeParseException | NoSuchElementException px){
                    logger.log(Level.WARNING , "Error parsing fromDate: " + csvRecord.get(FIELD_FROM_DATE), px);
                }
                try {
                    String toDateStr = csvRecord.get(FIELD_TO_DATE);
                    if(!toDateStr.isEmpty())
                        toDate = LocalDate.parse(toDateStr, dateFormatter);
                }catch (DateTimeParseException | NoSuchElementException px){
                    logger.log(Level.WARNING , "Error parsing toDate: " + csvRecord.get(FIELD_TO_DATE), px);

                }
                try {
                    sortingP = Integer.parseInt(csvRecord.get(FIELD_SORTING_PRIORITY));
                }
                catch (NumberFormatException | NoSuchElementException numberFormatException){
                    logger.log(Level.WARNING ,"sortingPriority is not a number or not present");
                }
                CSVDataModel dataModel = CSVDataModel.builder()
                        .code(csvRecord.get("code"))
                        .codeListCode(csvRecord.get("codeListCode"))
                        .source(csvRecord.get("source"))
                        .displayValue(csvRecord.get("displayValue"))
                        .longDescription(csvRecord.get("longDescription"))
                        .fromDate(fromDate)
                        .toDate(toDate)
                        .sortingPriority(sortingP)
                        .build();

                dataModelList.add(dataModel);
            });

            return dataModelList;
        } catch (Exception e) {
            throw new UnableToParseFileException("fail to parse CSV file: " + e.getMessage());
        }
    }



    public static CSVDataDto mapModelFromEntity(CSVDataModel model){
//        CSVDataDto dto = new CSVDataDto();
//        dto.setCode(model.getCode());
//        dto.setSource(model.getSource());
//        dto.setCodeListCode(model.getCodeListCode());
//        dto.setLongDescription(model.getLongDescription());
//        dto.setDisplayValue(model.getDisplayValue());
//        dto.setSortingPriority(model.getSortingPriority());
        CSVDataDto dto = modelMapper.map(model, CSVDataDto.class);

        LocalDate fromDate = model.getFromDate();
        LocalDate toDate = model.getToDate();
        if(fromDate != null)
            dto.setFromDate(dateFormatter.format(fromDate));
        if(toDate != null)
            dto.setToDate(dateFormatter.format(toDate));
        return dto;
    }

}