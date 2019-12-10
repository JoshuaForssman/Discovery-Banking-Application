package com.discovery.banking.wrapper;

import lombok.Data;

import java.util.List;

@Data
public class DenominationCountValueWrapper {

    private Integer[] denominationNoteValues;

    private Integer[] denominationNoteCounts;

}
