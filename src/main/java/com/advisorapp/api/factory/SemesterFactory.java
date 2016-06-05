package com.advisorapp.api.factory;

import com.advisorapp.api.dao.UvRepository;
import com.advisorapp.api.model.Semester;
import com.advisorapp.api.model.StudyPlan;
import com.advisorapp.api.model.Uv;
import com.advisorapp.api.service.SemesterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SemesterFactory {
    @Autowired
    private SemesterService semesterService;

    @Autowired
    private UvRepository uvRepository;

    private HashMap<Integer, Set<String>> uvs;

    public SemesterFactory()
    {
        uvs =  new HashMap<>();

        uvs.put(1, new HashSet<>());
        uvs.put(2, new HashSet<>());
        uvs.put(3, new HashSet<>());
        uvs.put(4, new HashSet<>());
        uvs.put(5, new HashSet<>());
        uvs.put(6, new HashSet<>());
        uvs.put(7, new HashSet<>());
        uvs.put(8, new HashSet<>());
        uvs.put(9, new HashSet<>());

        //SEMESTRE 1
        uvs.get(1).add("0202103");
        uvs.get(1).add("1411100");
        uvs.get(1).add("1440131");
        uvs.get(1).add("1420101");
        uvs.get(1).add("1420102");
        uvs.get(1).add("1430115");
        uvs.get(1).add("1430116");

        //SEMESTRE 2

        uvs.get(2).add("0202104");
        uvs.get(2).add("0405102");
        uvs.get(2).add("0405101");
        uvs.get(2).add("1440161");
        uvs.get(2).add("0101100");
        uvs.get(2).add("1430117");
        uvs.get(2).add("1430118");

        //SEMESTRE 3
        uvs.get(3).add("0202110");
        uvs.get(3).add("1411113");
        uvs.get(3).add("0405201");
        uvs.get(3).add("0405221");
        uvs.get(3).add("1440211");
        uvs.get(3).add("0301150");

        //SEMESTRE 4
        uvs.get(4).add("0202207");
        uvs.get(4).add("1440261");
        uvs.get(4).add("0405311");
        uvs.get(4).add("0402207");
        uvs.get(4).add("0302160");
        uvs.get(4).add("CARTUV1");

        //SEMESTRE 5
        uvs.get(5).add("0405260");
        uvs.get(5).add("0405341");
        uvs.get(5).add("0405322");
        uvs.get(5).add("0405414");
        uvs.get(5).add("0405331");

        //SEMESTRE 6
        uvs.get(6).add("0405301");
        uvs.get(6).add("0405323");
        uvs.get(6).add("0405324");
        uvs.get(6).add("0405431");
        uvs.get(6).add("0405464");
        uvs.get(6).add("CARTUV2");

        //SEMESTRE 7
        uvs.get(7).add("0400490");

        //SEMESTRE 8
        uvs.get(8).add("0201100");
        uvs.get(8).add("0405461");
        uvs.get(8).add("0405433");
        uvs.get(8).add("0301215");
        uvs.get(8).add("CARTUV3");
        uvs.get(8).add("0405491");
        uvs.get(8).add("CARTUV4");

        //SEMESTRE 9
        uvs.get(9).add("0201101");
        uvs.get(9).add("0405432");
        uvs.get(9).add("0405401");
        uvs.get(9).add("0401301");
        uvs.get(9).add("CARTUV5");
        uvs.get(9).add("0405492");
        uvs.get(9).add("CARTUV6");
    }

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
        for (int i = 1; i <= 9; i++) {
            this.initDefautSemesterWithCorrespondingUV(this.createWithUvs(studyPlan, null));
        }
    }

    private void initDefautSemesterWithCorrespondingUV(Semester semester) {
        this.semesterService.updateSemester(semester.setUvs(uvRepository.findByRemoteIdIn(uvs.get(semester.getNumber()))));
    }
}
