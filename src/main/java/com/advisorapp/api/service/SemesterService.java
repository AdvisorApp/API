package com.advisorapp.api.service;

import com.advisorapp.api.dao.SemesterRepository;
import com.advisorapp.api.model.Semester;
import com.advisorapp.api.model.StudyPlan;
import com.advisorapp.api.model.Uv;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.boot.actuate.metrics.GaugeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class SemesterService {

    private static final Logger log = LoggerFactory.getLogger(SemesterService.class);

    @Autowired
    private SemesterRepository semesterRepository;

    @Autowired
    CounterService counterService;

    @Autowired
    GaugeService gaugeService;

    public SemesterService() {
    }

    public Semester createSemester(Semester semester) {
        return semesterRepository.save(semester);
    }

    public Semester getSemester(long id) {
        return semesterRepository.findOne(id);
    }

    public void updateSemester(Semester semester) {
        semesterRepository.save(semester);
    }

    public void deleteSemester(Long id) {
        semesterRepository.delete(id);
    }

    //http://goo.gl/7fxvVf
    public Page<Semester> getAllSemesters(Integer page, Integer size) {
        Page pageOfSemesters = semesterRepository.findAll(new PageRequest(page, size));
        // example of adding to the /metrics
        if (size > 50) {
            counterService.increment("advisorapp.SemesterService.getAll.largePayload");
        }
        return pageOfSemesters;
    }

    public Set<String> handleAddUv(Semester semester, Uv uv) {

        Set<String> errors = new HashSet<>();
        StudyPlan studyPlan = semester.getStudyPlan();

        // Handle co-requisites UVs parallel addition.
        Set<Uv> corequisitesUVs = uv.getRealCorequisites();
        for (Uv corequisitesUV : corequisitesUVs) {
            errors.addAll(this.handleAddCorequisiteUv(semester, corequisitesUV));
            if (errors.size() > 0) {
                return errors;
            }
        }

        if (studyPlan.containUv(uv)) {
            errors.add("The current study plan already contains the UV");
        }

        if (!studyPlan.containPrerequisite(uv))
        {
            errors.add("The current study plan does not contains its prerequisites");
        }

        if (errors.size() > 0)
        {
            return errors;
        }

        this.updateSemester(semester.addUv(uv));

        if (semester.getTotalChs() > 18 || semester.getTotalChs() < 12)
        {
            errors.add("The number of CHS of the semester is not valid");
        }

        return errors;
    }

    private Set<String> handleAddCorequisiteUv(Semester semester, Uv uv) {

        Set<String> errors = new HashSet<>();
        StudyPlan studyPlan = semester.getStudyPlan();

        if (studyPlan.containUv(uv)) {
            errors.add("The current study plan already contains the UV");
        }

        if (!studyPlan.containPrerequisite(uv))
        {
            errors.add("The current study plan does not contains its prerequisites");
        }

        if (errors.size() > 0)
        {
            return errors;
        }

        this.updateSemester(semester.addUv(uv));
        return errors;
    }

    public Semester removeUvFromSemester(Semester semester, Uv uv) {
        return removeUvFromSemester(semester, uv, true);
    }

    public Semester removeUvFromSemester(Semester semester, Uv uv, boolean root) {
        if(root){
            Set<Uv> corequisitesUVs = uv.getRealCorequisites();
            for (Uv corequisitesUV : corequisitesUVs) {
                this.removeUvFromSemester(semester, corequisitesUV, false);
            }
        }
        semester.getUvs().remove(uv);
        return semesterRepository.save(semester);
    }
}
