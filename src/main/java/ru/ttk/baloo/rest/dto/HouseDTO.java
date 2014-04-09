package ru.ttk.baloo.rest.dto;


import java.io.Serializable;

/**
 *
 */
public class HouseDTO implements Serializable {

    private String uuid;                                // House id
    private String parentUuid;                          // может быть пустым - Parent of House - 'cause house could be located in street or directly in city

    private String number;                              // Number - Nomer
    private String corpuse;                             // Corpuse - Korpus
    private String structure;                           // Part of the house - Stroenie

    private DetailsDTO detailsDTO;

    public HouseDTO() {

    }

    public HouseDTO(String uuid, String parentUuid, String number, String corpuse, String structure, DetailsDTO detailsDTO) {
        this.uuid = uuid;
        this.parentUuid = parentUuid;
        this.number = number;
        this.corpuse = corpuse;
        this.structure = structure;
        this.detailsDTO = detailsDTO;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getParentUuid() {
        return parentUuid;
    }

    public void setParentUuid(String parentUuid) {
        this.parentUuid = parentUuid;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCorpuse() {
        return corpuse;
    }

    public void setCorpuse(String corpuse) {
        this.corpuse = corpuse;
    }

    public String getStructure() {
        return structure;
    }

    public void setStructure(String structure) {
        this.structure = structure;
    }

    public DetailsDTO getDetailsDTO() {
        return detailsDTO;
    }

    public void setDetailsDTO(DetailsDTO detailsDTO) {
        this.detailsDTO = detailsDTO;
    }
}
