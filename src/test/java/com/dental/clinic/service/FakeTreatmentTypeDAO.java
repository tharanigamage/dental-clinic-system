package com.dental.clinic.service;

import com.dental.clinic.dao.TreatmentTypeDAO;
import com.dental.clinic.model.TreatmentType;

import java.util.ArrayList;
import java.util.List;

public class FakeTreatmentTypeDAO implements TreatmentTypeDAO {

    private final List<TreatmentType> treatmentTypes = new ArrayList<>();

    public void addTreatment(int id, String name, double cost) {
        TreatmentType t = new TreatmentType();
        t.setTreatmentId(id);
        t.setTreatmentName(name);
        t.setCost(cost);
        treatmentTypes.add(t);
    }

    @Override
    public TreatmentType findById(int treatmentId) {
        for (TreatmentType t : treatmentTypes) {
            if (t.getTreatmentId() == treatmentId) return t;
        }
        return null;
    }

    @Override
    public List<TreatmentType> findAll() {
        return treatmentTypes;
    }
}