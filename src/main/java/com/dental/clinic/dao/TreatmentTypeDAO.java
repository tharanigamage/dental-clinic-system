package com.dental.clinic.dao;

import com.dental.clinic.model.TreatmentType;
import java.util.List;

public interface TreatmentTypeDAO {

    List<TreatmentType> findAll();

    TreatmentType findById (int treatmentId);
}
