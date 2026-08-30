package com.dental.clinic.dao;

import com.dental.clinic.model.Dentist;
import java.util.List;

public interface DentistDAO {

    //All dentists
    List<Dentist> findAll();

    //Find by id
    Dentist findById(int dentistId);
}
