package com.advisorapp.api.service;

import com.advisorapp.api.dao.SemesterRepository;
import com.advisorapp.api.model.Semester;
import com.advisorapp.api.model.StudyPlan;
import com.advisorapp.api.model.Uv;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class SemesterService {

    private static final Logger log = LoggerFactory.getLogger(SemesterService.class);

    @Autowired
    private SemesterRepository semesterRepository;

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

    public SemesterRepository getSemesterRepository()
    {
        return this.semesterRepository;
    }

    public Set<String> handleAddUv(Semester semester, Uv uv) {
        Set<String> errors = this.handleAddUv(semester, uv, true);

        if (errors.size() > 0)
        {
            return errors;
        }

        this.updateSemester(semester.addUv(uv));

        if (semester.getTotalChs() > 18)
        {
            errors.add("The number of CHS of the semester is not valid");
        }

        return errors;
    }

    public Set<String> handleAddUv(Semester semester, Uv uv, boolean root) {

        Set<String> errors = new HashSet<>();
        StudyPlan studyPlan = semester.getStudyPlan();

        if(root){
            // Handle co-requisites UVs parallel addition.
            Set<Uv> corequisitesUVs = uv.getRealCorequisites();
            for (Uv corequisitesUV : corequisitesUVs) {
                errors.addAll(this.handleAddUv(semester, corequisitesUV, false));
                if (errors.size() > 0) {
                    return errors;
                }
            }
        }


        if (studyPlan.containUv(uv)) {
            errors.add("The current study plan already contains the UV");
        }

        if (!studyPlan.containPrerequisite(uv)) {
            errors.add("The current study plan does not contains its prerequisites");
        }

        if (uv.isAvailableForCart() && studyPlan.getCartUvs().size() >= 6)
        {
            errors.add("The current study contains already the maximum number of chosen cart UVs");
        }

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
