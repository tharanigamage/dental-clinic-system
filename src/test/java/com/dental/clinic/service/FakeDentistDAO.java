package com.dental.clinic.service;

import com.dental.clinic.dao.DentistDAO;
import com.dental.clinic.model.Dentist;

import java.util.ArrayList;
import java.util.List;

public class FakeDentistDAO implements DentistDAO {

    private final List<Dentist> dentists = new ArrayList<>();

    public void addDentist(int id, String name, String specialization, double fee) {
        Dentist d = new Dentist();
        d.setDentistId(id);
        d.setName(name);
        d.setSpecialization(specialization);
        d.setConsultationFee(fee);
        dentists.add(d);
    }

    @Override
    public Dentist findById(int dentistId) {
        for (Dentist d : dentists) {
            if (d.getDentistId() == dentistId) return d;
        }
        return null;
    }

    @Override
    public List<Dentist> findAll() {
        return dentists;
    }
}