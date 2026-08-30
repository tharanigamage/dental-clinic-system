package com.dental.clinic.dao;

import java.util.List;
import com.dental.clinic.model.Patient;

public interface PatientDAO {

    //Save new patient
    int save (Patient patient);

    //Find by id
    Patient findById (int patientId);

    //Find by nic
    Patient findByNic(String nic);

    //Find by nic prefix
    List<Patient> findByNicPrefix(String nicPrefix);

    //all patients
    List<Patient> findAll();
}
