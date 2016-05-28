package com.advisorapp.api.factory;

import com.advisorapp.api.model.Semester;
import com.advisorapp.api.model.StudyPlan;
import com.advisorapp.api.model.Uv;
import com.advisorapp.api.service.SemesterService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

public class SemesterFactory {
    @Autowired
    private SemesterService semesterService;

    public Semester create(StudyPlan studyPlan, Set<Uv> uvSet)
    {
        Semester semester = new Semester();
        return this.semesterService.createSemester(semester.setStudyPlan(studyPlan).setUvs(uvSet));
    }
}
