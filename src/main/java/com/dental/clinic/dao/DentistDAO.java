package com.dental.clinic.dao;

import com.dental.clinic.model.Dentist;
import java.util.List;

public interface DentistDAO {

    List<Dentist> findAll();
    Dentist findById(int dentistId);
}
