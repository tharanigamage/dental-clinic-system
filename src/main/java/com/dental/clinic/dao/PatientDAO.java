package com.dental.clinic.dao;

import com.dental.clinic.model.Patient;

public interface PatientDAO {

    int save (Patient patient);
    Patient findById (int patientId);
}
