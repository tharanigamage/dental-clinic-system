package com.dental.clinic.dao;

import com.dental.clinic.model.TreatmentType;
import java.util.List;

public interface TreatmentTypeDAO {

    // Find all treatment types
    List<TreatmentType> findAll();

    // Find by id
    TreatmentType findById (int treatmentId);
}
