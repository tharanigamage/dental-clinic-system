package com.dental.clinic.service;

import com.dental.clinic.dao.PatientDAO;
import com.dental.clinic.model.Patient;

import java.util.ArrayList;
import java.util.List;

public class FakePatientDAO implements PatientDAO {

    private final List<Patient> patients = new ArrayList<>();
    private int nextId = 1;

    @Override
    public int save(Patient patient) {
        patient.setPatientId(nextId);
        patients.add(patient);
        return nextId++;
    }

    @Override
    public Patient findById(int patientId) {
        for (Patient p : patients) {
            if (p.getPatientId() == patientId) return p;
        }
        return null;
    }

    @Override
    public Patient findByNic(String nic) {
        for (Patient p : patients) {
            if (p.getNic().equals(nic)) return p;
        }
        return null;
    }

    @Override
    public List<Patient> findByNicPrefix(String nicPrefix) {
        List<Patient> result = new ArrayList<>();
        for (Patient p : patients) {
            if (p.getNic().startsWith(nicPrefix)) result.add(p);
        }
        return result;
    }

    @Override
    public List<Patient> findAll() {
        return patients;
    }
}