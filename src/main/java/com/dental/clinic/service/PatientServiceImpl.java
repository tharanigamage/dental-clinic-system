package com.dental.clinic.service;

import com.dental.clinic.dao.PatientDAO;
import com.dental.clinic.dao.PatientDAOImpl;
import com.dental.clinic.model.Patient;

import java.util.List;

public class PatientServiceImpl implements PatientService {

    private final PatientDAO patientDAO;

    public PatientServiceImpl() {
        this.patientDAO = new PatientDAOImpl();
    }

    public PatientServiceImpl(PatientDAO patientDAO) {
        this.patientDAO = patientDAO;
    }

    // All patients
    @Override
    public List<Patient> getAllPatients() {
        return patientDAO.findAll();
    }
}