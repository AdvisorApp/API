package com.advisorapp.api.fixtures;

import com.advisorapp.api.factory.TankFactory;
import com.advisorapp.api.model.*;
import com.advisorapp.api.service.TankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
public class FixturingDatabase {

    @Autowired
    private TankFactory tankFactory;

    @Autowired
    private TankService tankService;

    private Map<String, Map<String, Object>> references;

    public FixturingDatabase() {
        this.references = new HashMap<>();
        this.references.put("UV", new HashMap<>());
        this.references.put("SEMESTERS", new HashMap<>());
        this.references.put("STUDY_PLAN", new HashMap<>());
        this.references.put("OPTION", new HashMap<>());
        this.references.put("UV_TYPE", new HashMap<>());
    }

    public void run() {
        this.destroyDatabase();
        this.initOption();
        this.initUvType();
        this.initStudyPlans();
        this.initSemesters();
        this.initUV();
    }

    protected void destroyDatabase() {

    }

    protected void initStudyPlans() {
        this.addEntry("STUDY_PLAN", "default-study-plan-option-1", this.tankFactory.getStudyPlanFactory().createStudyPlanWithoutUser("Default Study Plan Option 1", null, (Option) this.getEntry("OPTION", "option-1")));
        this.addEntry("STUDY_PLAN", "default-study-plan-option-2", this.tankFactory.getStudyPlanFactory().createStudyPlanWithoutUser("Default Study Plan Option 2", null, (Option) this.getEntry("OPTION", "option-2")));
    }

    protected void initSemesters() {
        StudyPlan option1 = (StudyPlan) this.getEntry("STUDY_PLAN", "default-study-plan-option-1");
        StudyPlan option2 = (StudyPlan) this.getEntry("STUDY_PLAN", "default-study-plan-option-2");

        for (int i = 1; i <= 9; i++) {
            this.addEntry(
                    "SEMESTERS",
                    semesterKey(i, 1),
                    this.tankFactory.getSemesterFactory().create(option1, null)
            );
            this.addEntry(
                    "SEMESTERS",
                    semesterKey(i, 2),
                    this.tankFactory.getSemesterFactory().create(option2, null)
            );
        }

        this.addEntry(
                "SEMESTERS",
                semesterKey(10, 1),
                this.tankFactory.getSemesterFactory().create(option1, null)
        );
    }

    protected void initOption() {
        this.addEntry("OPTION", "option-1", this.tankFactory.getOptionFactory().createOption("Option 1 : Co-op in industry"));
        this.addEntry("OPTION", "option-2", this.tankFactory.getOptionFactory().createOption("Option 2 : Graduation Project"));
    }

    protected void initUvType() {
        this.addEntry("UV_TYPE", "tp", this.tankFactory.getUvTypeFactory().create("TP", 2.5));
        this.addEntry("UV_TYPE", "td", this.tankFactory.getUvTypeFactory().create("TD", 1.0));
    }

    protected String unknown(int number) {
        return "unknown-" + number;
    }

    protected Set<Uv> getUvs(String... uvs) {
        Set<Uv> uvSet = new HashSet<>();
        for (String uv : uvs) {
            uvSet.add((Uv) this.getEntry("UV", uv));
        }
        return uvSet;
    }

    protected Set<String> getSet(Set<String> set, String option1, String option2) {
        set.clear();
        set.add(option1);
        if (option2 != null) {
            set.add(option2);
        }
        return set;
    }

    protected Set<String> getBothOption(Set<String> set, int semester) {
        return this.getSet(set, semesterKey(semester, 1), semesterKey(semester, 2));
    }

    protected String semesterKey(int semester, int option) {
        return "semester-" + semester + "-" + option;
    }

    protected void initUV() {
        Set<String> semesters = new HashSet<>();

        /// SEMESTRE 1
        this.createUV(unknown(0202103), "Unknown 1", "0202103", false, 1, 3, null, Location.UNIVERSITY, false, null, null, this.getBothOption(semesters, 1));
        this.createUV(unknown(1411100), "Unknown 2", "1411100", false, 1, 3, null, Location.UNIVERSITY, false, null, null, this.getBothOption(semesters, 1));
        this.createUV(unknown(1440131), "Unknown 3", "1440131", false, 1, 3, null, Location.FACULTY, false, null, null, this.getBothOption(semesters, 1));
        this.createUV(unknown(1420101), "Unknown 4", "1420101", false, 1, 3, null, Location.FACULTY, false, null, null, this.getBothOption(semesters, 1));
        this.createUV("gen-chem-lab", "Gen. Chem. Labs", "1420102", false, 1, 1, null, Location.FACULTY, true, this.getUvs(unknown(1420101)), null, this.getBothOption(semesters, 1));
        this.createUV("physics-1", "Physics I", "1430115", false, 1, 3, null, Location.FACULTY, false, null, null, this.getBothOption(semesters, 1));
        this.createUV("physics-lab-1", "Physics I Lab", "1430116", false, 1, 1, null, Location.FACULTY, true, this.getUvs("physics-1"), null, this.getBothOption(semesters, 1));

        /// SEMESTRE 2
        this.createUV(unknown(0202104), "Unknown 5", "0202104", false, 2, 3, null, Location.UNIVERSITY, false, null, this.getUvs(unknown(0202103)), this.getBothOption(semesters, 2));
        this.createUV("eng-graphics", "Eng. Graphics.", "0405102", false, 2, 1, null, Location.DEPARTMENT, true, null, this.getUvs(unknown(1411100)), this.getBothOption(semesters, 2));
        this.createUV("int-to-ind-eng", "Intro. to Ind.", "0405101", false, 2, 2, null, Location.DEPARTMENT, false, null, null, this.getBothOption(semesters, 2));
        this.createUV(unknown(1440161), "Unknown 6", "1440161", false, 2, 3, null, Location.FACULTY, false, null, this.getUvs(unknown(1440131)), this.getBothOption(semesters, 2));
        this.createUV(unknown(101100), "Unknown 7", "0101100", false, 2, 3, null, Location.UNIVERSITY, false, null, null, this.getBothOption(semesters, 2));
        this.createUV("physics-2", "Physics II", "1430117", false, 2, 3, null, Location.FACULTY, false, null, this.getUvs("physics-1"), this.getBothOption(semesters, 2));
        this.createUV("physics-lab-2", "Physics II Lab", "1430118", false, 2, 1, null, Location.FACULTY, true, this.getUvs("physics-2"), null, this.getBothOption(semesters, 2));

        ///SEMESTRE 3
        this.createUV(unknown(0202110), "Unknown 8", "0202110", false, 3, 3, null, Location.FACULTY, false, null, this.getUvs(unknown(0202103)), this.getBothOption(semesters, 3));
        this.createUV("prog-for-engineers", "Prog. for Engineers", "1411113", false, 3, 3, null, Location.FACULTY, true, null, this.getUvs(unknown(1411100)), this.getBothOption(semesters, 3));
        this.createUV("manufacturing-tech", "Manufacturing Tech", "0405201", false, 3, 2, null, Location.DEPARTMENT, true, null, this.getUvs("int-to-ind-eng"), this.getBothOption(semesters, 3));
        this.createUV("eng-prob-stat", "Eng. Prob. & Stat.", "0405221", false, 3, 3, null, Location.DEPARTMENT, false, null, this.getUvs(unknown(1440131)), this.getBothOption(semesters, 3));
        this.createUV(unknown(1440211), "Unknown 9", "1440211", false, 3, 3, null, Location.DEPARTMENT, false, null, this.getUvs(unknown(1440131)), this.getBothOption(semesters, 3));
        this.createUV("int-to-eco", "Intro. to Economics", "0301150", false, 3, 3, null, Location.DEPARTMENT, false, null, null, this.getBothOption(semesters, 3));

        ///SEMESTRE 4
        this.createUV("technical-writing", "Technical Writing", "0202207", false, 4, 3, null, Location.FACULTY, false, null, this.getUvs(unknown(0202104)), this.getBothOption(semesters, 4));
        this.createUV(unknown(1440261), "Unknown 10", "1440261", false, 4, 3, null, Location.FACULTY, false, null, this.getUvs(unknown(1440161)), this.getBothOption(semesters, 4));
        this.createUV("or-1", "OR 1", "0405311", false, 4, 4, null, Location.DEPARTMENT, true, null, this.getUvs(unknown(1440211)), this.getBothOption(semesters, 4));
        this.createUV("app-elec-circ", "App. Elec. Circuit", "0402207", false, 4, 3, null, Location.DEPARTMENT, true, null, this.getUvs("physics-2"), this.getBothOption(semesters, 4));
        this.createUV("princ-of-mgt", "Principales of MGT", "0302160", false, 4, 3, null, Location.DEPARTMENT, false, null, null, this.getBothOption(semesters, 4));

        ///CART SEMESTRE 4
        this.createUV("univ-elec-1", "University Elec. 1", "", true, 4, 3, null, Location.UNIVERSITY, false, null, null, this.getBothOption(semesters, 4));

        ///SEMESTRE 5
        this.createUV("db-mgt-iis", "DB MGT & IIS", "0405260", false, 5, 4, null, Location.DEPARTMENT, true, null, this.getUvs("prog-for-engineers"), this.getBothOption(semesters, 5));
        this.createUV("ergonomics", "Ergonomics", "0405341", false, 5, 4, null, Location.DEPARTMENT, true, this.getUvs("manufacturing-tech"), this.getUvs("eng-prob-stat"), this.getBothOption(semesters, 5));
        this.createUV(unknown(0405322), "Unknown 11", "0405322", false, 5, 3, null, Location.DEPARTMENT, false, null, this.getUvs("eng-prob-stat"), this.getBothOption(semesters, 5));
        this.createUV("or-2", "OR 2", "0405414", false, 5, 3, null, Location.DEPARTMENT, false, null, this.getUvs("eng-prob-stat", "or-1"), this.getBothOption(semesters, 5));
        this.createUV("ind-autom", "Industrial Automation", "0405331", false, 5, 4, null, Location.DEPARTMENT, true, null, this.getUvs("app-elec-circ"), this.getBothOption(semesters, 5));

        ///SEMESTRE 6
        this.createUV("des-env-saf", "Design for the Environment & safety", "0405301", false, 6, 3, null, Location.DEPARTMENT, false, null, this.getUvs("ergonomics"), this.getBothOption(semesters, 6));
        this.createUV(unknown(0405323), "Unknown 12", "0405323", false, 6, 3, null, Location.DEPARTMENT, false, null, this.getUvs("eng-prob-stat"), this.getBothOption(semesters, 6));
        this.createUV("simulation", "Simulation", "0405324", false, 6, 3, null, Location.DEPARTMENT, true, null, this.getUvs("eng-prob-stat"), this.getBothOption(semesters, 6));
        this.createUV(unknown(0405431), "Unknown 13", "0405431", false, 6, 3, null, Location.DEPARTMENT, false, null, this.getUvs("eng-prob-stat"), this.getBothOption(semesters, 6));
        this.createUV(unknown(0405464), "Unknown 14", "0405464", false, 6, 3, null, Location.DEPARTMENT, false, null, null, this.getBothOption(semesters, 6));

        ///CART SEMESTER 6
        this.createUV("univ-elec-2", "University Elec. 2", "", true, 6, 3, null, Location.UNIVERSITY, false, null, null, this.getBothOption(semesters, 6));

        ///SEMESTRE 7
        this.createUV("pratical-training", "Pratical Training", "0400490", false, 7, 0, null, Location.FACULTY, false, null, null, this.getBothOption(semesters, 7));

        ///COMMON SEMESTRE 8
        this.createUV("arabic-1", "Arabic 1", "0201100", false, 8, 3, null, Location.UNIVERSITY, false, null, null, this.getBothOption(semesters, 8));
        this.createUV("prod-des-innov-mgt", "Product Design & Innov. MGT", "0405461", false, 8, 3, null, Location.DEPARTMENT, false, null, null, this.getBothOption(semesters, 8));
        this.createUV("supply-chain-mgt", "Supply Chain MGT", "0405433", false, 8, 3, null, Location.DEPARTMENT, false, null, this.getUvs(unknown(0405431)), this.getBothOption(semesters, 8));
        this.createUV(unknown(0301215), "Unknown 15", "0301215", false, 8, 3, null, Location.DEPARTMENT, false, null, null, this.getBothOption(semesters, 8));

        ///CART COMMON SEMESTER 8
        this.createUV("univ-elec-3", "University Elec. 3", "", true, 8, 3, null, Location.UNIVERSITY, false, null, null, this.getBothOption(semesters, 8));

        ///SEMESTRE 8 OPTION 1
        //// NONE

        ///SEMESTRE 8 OPTION 2
        this.createUV("senior-project-1", "Senior Project I", "0405491", false, 8, 1, (Option) this.getEntry("OPTION", "option-2"), Location.DEPARTMENT, false, null, this.getUvs("technical-writing"), this.getSet(semesters, this.semesterKey(8, 2), null));

        /// CART SEMESTRE 8 OPTION 2
        this.createUV("depa-elec-1", "Department Elec. 1", "", true, 8, 3, (Option) this.getEntry("OPTION", "option-2"), Location.DEPARTMENT, false, null, null, this.getSet(semesters, this.semesterKey(8, 2), null));

        ///COMMON SEMESTRE 9
        this.createUV("arabic-2", "Arabic 2", "0201101", false, 9, 3, null, Location.UNIVERSITY, false, null, this.getUvs("arabic-1"), this.getBothOption(semesters, 9));
        this.createUV("facilities-planning", "Facilities Planning", "0405432", false, 9, 3, null, Location.UNIVERSITY, false, null, this.getUvs("ergonomics"), this.getBothOption(semesters, 9));
        this.createUV("eng-ethics-lead", "Eng. Ethics & Leadership", "0405401", false, 9, 1, null, Location.FACULTY, false, null, null, this.getBothOption(semesters, 9));
        this.createUV("eng-economics", "Eng. Economics", "0401301", false, 9, 3, null, Location.FACULTY, false, null, null, this.getBothOption(semesters, 9));

        ///CART COMMON SEMESTRE 9
        this.createUV("univ-elec-4", "University Elec. 4", "", true, 9, 3, null, Location.UNIVERSITY, false, null, null, this.getBothOption(semesters, 9));

        ///SEMESTRE 9 OPTION 1
        /// NONE

        ///SEMESTRE 9 OPTION 2
        this.createUV("senior-project-2", "Senior Project II", "0405492", false, 9, 1, (Option) this.getEntry("OPTION", "option-2"), Location.DEPARTMENT, false, null, this.getUvs("senior-project-1"), this.getSet(semesters, this.semesterKey(9, 2), null));

        ///CART SEMESTRE 9 OPTION 2
        this.createUV("depa-elec-2", "Department Elec. 2", "", true, 9, 3, (Option) this.getEntry("OPTION", "option-2"), Location.DEPARTMENT, false, null, null, this.getSet(semesters, this.semesterKey(9, 2), null));

        /// SEMESTRE 10 OPTION 1
        this.createUV("coop-industry", "Co-op in Industry", "0405499", false, 10, 7, (Option) this.getEntry("OPTION", "option-1"), Location.DEPARTMENT, false, null, null, this.getSet(semesters, this.semesterKey(10, 1), null));


    }

    protected Uv createUV(
            String entryName,
            String name,
            String remoteId,
            Boolean isAvailableForCart,
            Integer minSemester,
            int chs,
            Option option,
            Location location,
            Boolean isTp,
            Set<Uv> corequisites,
            Set<Uv> prerequisites,
            Set<String> semestersKey
    ) {

        Uv uv = this.tankFactory.getUvFactory().createUV(
                name,
                "SOME DESCRIPTION",
                remoteId,
                isAvailableForCart,
                minSemester,
                chs,
                option,
                location,
                (UvType) this.getEntry("UV_TYPE", isTp ? "tp" : "td"),
                corequisites,
                prerequisites
        );
        this.addEntry("UV", entryName, uv);

        for (String semesterKey : semestersKey) {
            this.tankService.getSemesterService().updateSemester(((Semester) this.getEntry("SEMESTERS", semesterKey)).addUv(uv));
        }

        return uv;
    }

    protected void addEntry(String objectType, String key, Object concerned) {
        this.references.get(objectType).put(key, concerned);
    }

    protected Object getEntry(String objectType, String key) {
        return this.references.get(objectType).get(key);
    }
}
