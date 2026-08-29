package com.dental.clinic.dao;

import java.util.List;
import com.dental.clinic.model.Patient;

public interface PatientDAO {

    int save (Patient patient);

    Patient findById (int patientId);

    Patient findByNic(String nic);

    List<Patient> findByNicPrefix(String nicPrefix);
}
