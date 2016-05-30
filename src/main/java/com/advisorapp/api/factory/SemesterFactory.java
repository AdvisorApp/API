package com.advisorapp.api.factory;

import com.advisorapp.api.dao.UvRepository;
import com.advisorapp.api.model.Option;
import com.advisorapp.api.model.Semester;
import com.advisorapp.api.model.StudyPlan;
import com.advisorapp.api.model.Uv;
import com.advisorapp.api.service.SemesterService;
import org.hibernate.annotations.SourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class SemesterFactory {
    @Autowired
    private SemesterService semesterService;

    @Autowired
    private UvRepository uvRepository;

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

    public void initDefaultSemesters(StudyPlan studyPlan) {
        Boolean isOption1 = studyPlan.getOption().getName().equals("Option 1 : Co-op in industry");
        for (int i = 1; i <= 9; i++) {
            this.initDefautSemesterWithCorrespondingUV(this.createWithUvs(studyPlan, null), studyPlan.getOption());
        }

        if (isOption1) {
            this.initDefautSemesterWithCorrespondingUV(this.createWithUvs(studyPlan, null), studyPlan.getOption());
        }
    }

    private void initDefautSemesterWithCorrespondingUV(Semester semester, Option option) {
        Set<Uv> uvs = new HashSet<>();
        Set<Uv> res = this.uvRepository.findByMinSemesterAndOption(semester.getNumber(), option);
        if (res != null)
        {
            uvs.addAll(res);
        }
        res = this.uvRepository.findByMinSemesterAndWithoutOption(semester.getNumber());

        if (res != null)
        {
            uvs.addAll(res);
        }

        this.semesterService.updateSemester(semester.setUvs(uvs));
    }
}
