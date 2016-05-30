package com.advisorapp.api.factory;

import com.advisorapp.api.model.Semester;
import com.advisorapp.api.model.StudyPlan;
import com.advisorapp.api.model.Uv;
import com.advisorapp.api.service.SemesterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class SemesterFactory {
    @Autowired
    private SemesterService semesterService;

    public Semester createWithUvs(StudyPlan studyPlan, Set<Uv> uvSet)
    {
        Semester semester = new Semester();
        return this.semesterService.createSemester(
                semester
                        .setStudyPlan(studyPlan)
                        .setUvs(uvSet == null ? new HashSet<Uv>() : uvSet)
        );
    }

    public Semester create(StudyPlan studyPlan, Semester semester)
    {
        return this.semesterService.createSemester(semester.setStudyPlan(studyPlan));
    }

    public SemesterService getSemesterService()
    {
        return this.semesterService;
    }
}
