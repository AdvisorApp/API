package com.advisorapp.api.fixtures;

import com.advisorapp.api.factory.TankFactory;
import com.advisorapp.api.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FixturingDatabase {

    @Autowired
    private TankFactory tankFactory;

    private Map<String, Map<String, Object>> references;

    public FixturingDatabase() {
        this.references = new HashMap<>();
        this.references.put("UV", new HashMap<>());
        this.references.put("SEMESTERS", new HashMap<>());
        this.references.put("STUDY_PLAN", new HashMap<>());
        this.references.put("UV_TYPE", new HashMap<>());
    }

    public void run() {
        if (this.tankFactory.getUserFactory().getUserService().getUserRepository().findAll().size() > 0) {
            System.out.println("TRYING TO DO A REMOVE ");
            this.destroyDatabase();
        }

        //INIT UVS
        //this.initUvType();
        //this.initUV();
//
        //this.tankFactory.getStudyPlanFactory().createDefaultStudyPlanForUser(null);
        //this.initUser();
    }

    private void initUser() {
        this.tankFactory.getStudyPlanFactory().createDefaultStudyPlanForUser(this.tankFactory.getUserFactory().createUser(
                "Safou",
                "Foufou",
                "sa@fou.com",
                new Date(),
                "Bgette44",
                "Bgette44"
        ));
    }

    protected void destroyDatabase() {
        this.tankFactory
                .getUvUserFactory()
                .getUvUserService()
                .getUvUserRepository()
                .findAll()
                .forEach(e -> this.tankFactory.getUvUserFactory().getUvUserService().deleteUvUser(e.getId()));

        this.tankFactory
                .getSemesterFactory()
                .getSemesterService()
                .getSemesterRepository()
                .findAll()
                .forEach(e -> this.tankFactory.getSemesterFactory().getSemesterService().deleteSemester(e.getId()));

        this.tankFactory
                .getStudyPlanFactory()
                .getStudyPlanService()
                .getStudyPlanRepository()
                .findAll()
                .forEach(e -> this.tankFactory.getStudyPlanFactory().getStudyPlanService().deleteStudyPlan(e.getId()));

        this.tankFactory
                .getUserFactory()
                .getUserService()
                .getUserRepository()
                .findAll()
                .stream()
                .forEach(e -> this.tankFactory.getUserFactory().getUserService().deleteUser(e.getId()));

        this.tankFactory
                .getUvTypeFactory()
                .getUvTypeService()
                .getAllUvTypes()
                .forEach(e -> this.tankFactory.getUvTypeFactory().getUvTypeService().delete(e.getId()));

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

        Arrays.asList(uvs).stream().forEach(e -> uvSet.add((Uv) this.getEntry("UV", e)));

        return uvSet;
    }

    protected void initUV() {
        /// SEMESTRE 1
        this.createUV(unknown(0202103), "Unknown 1", "0202103", false, 3, Location.UNIVERSITY, false, null, null);
        this.createUV(unknown(1411100), "Unknown 2", "1411100", false, 3, Location.UNIVERSITY, false, null, null);
        this.createUV(unknown(1440131), "Unknown 3", "1440131", false, 3, Location.FACULTY, false, null, null);
        this.createUV(unknown(1420101), "Unknown 4", "1420101", false, 3, Location.FACULTY, false, null, null);
        this.createUV("gen-chem-lab", "Gen. Chem. Labs", "1420102", false, 1, Location.FACULTY, true, this.getUvs(unknown(1420101)), null);
        this.createUV("physics-1", "Physics I", "1430115", false, 3, Location.FACULTY, false, null, null);
        this.createUV("physics-lab-1", "Physics I Lab", "1430116", false, 1, Location.FACULTY, true, this.getUvs("physics-1"), null);

        /// SEMESTRE 2
        this.createUV(unknown(0202104), "Unknown 5", "0202104", false, 3, Location.UNIVERSITY, false, null, this.getUvs(unknown(0202103)));
        this.createUV("eng-graphics", "Eng. Graphics.", "0405102", false, 1, Location.DEPARTMENT, true, null, this.getUvs(unknown(1411100)));
        this.createUV("int-to-ind-eng", "Intro. to Ind.", "0405101", false, 2, Location.DEPARTMENT, false, null, null);
        this.createUV(unknown(1440161), "Unknown 6", "1440161", false, 3, Location.FACULTY, false, null, this.getUvs(unknown(1440131)));
        this.createUV(unknown(101100), "Unknown 7", "0101100", false, 3, Location.UNIVERSITY, false, null, null);
        this.createUV("physics-2", "Physics II", "1430117", false, 3, Location.FACULTY, false, null, this.getUvs("physics-1"));
        this.createUV("physics-lab-2", "Physics II Lab", "1430118", false, 1, Location.FACULTY, true, this.getUvs("physics-2"), null);

        ///SEMESTRE 3
        this.createUV(unknown(0202110), "Unknown 8", "0202110", false, 3, Location.FACULTY, false, null, this.getUvs(unknown(0202103)));
        this.createUV("prog-for-engineers", "Prog. for Engineers", "1411113", false, 3, Location.FACULTY, true, null, this.getUvs(unknown(1411100)));
        this.createUV("manufacturing-tech", "Manufacturing Tech", "0405201", false, 2, Location.DEPARTMENT, true, null, this.getUvs("int-to-ind-eng"));
        this.createUV("eng-prob-stat", "Eng. Prob. & Stat.", "0405221", false, 3, Location.DEPARTMENT, false, null, this.getUvs(unknown(1440131)));
        this.createUV(unknown(1440211), "Unknown 9", "1440211", false, 3, Location.DEPARTMENT, false, null, this.getUvs(unknown(1440131)));
        this.createUV("int-to-eco", "Intro. to Economics", "0301150", false, 3, Location.DEPARTMENT, false, null, null);

        ///SEMESTRE 4
        this.createUV("technical-writing", "Technical Writing", "0202207", false, 3, Location.FACULTY, false, null, this.getUvs(unknown(0202104)));
        this.createUV(unknown(1440261), "Unknown 10", "1440261", false, 3, Location.FACULTY, false, null, this.getUvs(unknown(1440161)));
        this.createUV("or-1", "OR 1", "0405311", false, 4, Location.DEPARTMENT, true, null, this.getUvs(unknown(1440211)));
        this.createUV("app-elec-circ", "App. Elec. Circuit", "0402207", false, 3, Location.DEPARTMENT, true, null, this.getUvs("physics-2"));
        this.createUV("princ-of-mgt", "Principales of MGT", "0302160", false, 3, Location.DEPARTMENT, false, null, null);
        this.createUV("univ-elec-1", "University Elec. 1", "CARTUV1", true, 3, Location.UNIVERSITY, false, null, null);

        ///SEMESTRE 5
        this.createUV("db-mgt-iis", "DB MGT & IIS", "0405260", false, 4, Location.DEPARTMENT, true, null, this.getUvs("prog-for-engineers"));
        this.createUV("ergonomics", "Ergonomics", "0405341", false, 4, Location.DEPARTMENT, true, null, this.getUvs("eng-prob-stat", "manufacturing-tech"));
        this.createUV(unknown(0405322), "Unknown 11", "0405322", false, 3, Location.DEPARTMENT, false, null, this.getUvs("eng-prob-stat"));
        this.createUV("or-2", "OR 2", "0405414", false, 3, Location.DEPARTMENT, false, null, this.getUvs("eng-prob-stat", "or-1"));
        this.createUV("ind-autom", "Industrial Automation", "0405331", false, 4, Location.DEPARTMENT, true, null, this.getUvs("app-elec-circ"));

        ///SEMESTRE 6
        this.createUV("des-env-saf", "Design for the Environment & safety", "0405301", false, 3, Location.DEPARTMENT, false, null, this.getUvs("ergonomics"));
        this.createUV(unknown(0405323), "Unknown 12", "0405323", false, 3, Location.DEPARTMENT, false, null, this.getUvs("eng-prob-stat"));
        this.createUV("simulation", "Simulation", "0405324", false, 3, Location.DEPARTMENT, true, null, this.getUvs("eng-prob-stat"));
        this.createUV(unknown(0405431), "Unknown 13", "0405431", false, 3, Location.DEPARTMENT, false, null, this.getUvs("eng-prob-stat"));
        this.createUV(unknown(0405464), "Unknown 14", "0405464", false, 3, Location.DEPARTMENT, false, null, null);
        this.createUV("univ-elec-2", "University Elec. 2", "CARTUV2", true, 3, Location.UNIVERSITY, false, null, null);

        ///SEMESTRE 7
        this.createUV("pratical-training", "Pratical Training", "0400490", false, 0, Location.FACULTY, false, null, null);

        ///COMMON SEMESTRE 8
        this.createUV("arabic-1", "Arabic 1", "0201100", false, 3, Location.UNIVERSITY, false, null, null);
        this.createUV("prod-des-innov-mgt", "Product Design & Innov. MGT", "0405461", false, 3, Location.DEPARTMENT, false, null, null);
        this.createUV("supply-chain-mgt", "Supply Chain MGT", "0405433", false, 3, Location.DEPARTMENT, false, null, this.getUvs(unknown(0405431)));
        this.createUV(unknown(0301215), "Unknown 15", "0301215", false, 3, Location.DEPARTMENT, false, null, null);
        this.createUV("univ-elec-3", "University Elec. 3", "CARTUV3", true, 3, Location.UNIVERSITY, false, null, null);
        this.createUV("senior-project-1", "Senior Project I", "0405491", false, 1, Location.DEPARTMENT, false, null, this.getUvs("technical-writing"));
        this.createUV("depa-elec-1", "Department Elec. 1", "CARTUV4", true, 3, Location.DEPARTMENT, false, null, null);

        ///COMMON SEMESTRE 9
        this.createUV("arabic-2", "Arabic 2", "0201101", false, 3, Location.UNIVERSITY, false, null, this.getUvs("arabic-1"));
        this.createUV("facilities-planning", "Facilities Planning", "0405432", false, 3, Location.UNIVERSITY, false, null, this.getUvs("ergonomics"));
        this.createUV("eng-ethics-lead", "Eng. Ethics & Leadership", "0405401", false, 1, Location.FACULTY, false, null, null);
        this.createUV("eng-economics", "Eng. Economics", "0401301", false, 3, Location.FACULTY, false, null, null);
        this.createUV("univ-elec-4", "University Elec. 4", "CARTUV5", true, 3, Location.UNIVERSITY, false, null, null);
        this.createUV("senior-project-2", "Senior Project II", "0405492", false, 1, Location.DEPARTMENT, false, null, this.getUvs("senior-project-1"));
        this.createUV("depa-elec-2", "Department Elec. 2", "CARTUV6", true, 3, Location.DEPARTMENT, false, null, null);
    }

    protected Uv createUV(
            String entryName,
            String name,
            String remoteId,
            Boolean isAvailableForCart,
            int chs,
            Location location,
            Boolean isTp,
            Set<Uv> corequisites,
            Set<Uv> prerequisites
    ) {
        Uv uv = this.tankFactory.getUvFactory().createUV(
                name,
                "SOME DESCRIPTION",
                remoteId,
                isAvailableForCart,
                chs,
                location,
                (UvType) this.getEntry("UV_TYPE", isTp ? "tp" : "td"),
                corequisites,
                prerequisites
        );
        this.addEntry("UV", entryName, uv);

        return uv;
    }

    protected void addEntry(String objectType, String key, Object concerned) {
        this.references.get(objectType).put(key, concerned);
    }

    protected Object getEntry(String objectType, String key) {
        return this.references.get(objectType).get(key);
    }
}
