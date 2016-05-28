package com.advisorapp.api.controller;

import com.advisorapp.api.model.Semester;
import com.advisorapp.api.model.StudyPlan;
import com.advisorapp.api.exception.DataFormatException;
import com.advisorapp.api.model.StudyPlan;
import com.advisorapp.api.service.SemesterService;
import com.advisorapp.api.service.StudyPlanService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

/*
 * Demonstrates how to set up RESTful API endpoints using Spring MVC
 */

@RestController
@RequestMapping(value = "/api/studyPlans")
@Api(value = "studyPlans", description = "StudyPlan API")
public class StudyPlanController extends AbstractRestHandler {

    @Autowired
    private StudyPlanService studyPlanService;

    @Autowired
    private SemesterService semesterService;

    @RequestMapping(value = "",
            method = RequestMethod.GET,
            produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get a paginated list of all studyPlans.", notes = "The list is paginated. You can provide a page number (default 0) and a page size (default 100)")
    public
    @ResponseBody
    Page<StudyPlan> getAllStudyPlans(@ApiParam(value = "The page number (zero-based)", required = true)
                           @RequestParam(value = "page", required = true, defaultValue = DEFAULT_PAGE_NUM) Integer page,
                           @ApiParam(value = "Tha page size", required = true)
                           @RequestParam(value = "size", required = true, defaultValue = DEFAULT_PAGE_SIZE) Integer size,
                           HttpServletRequest request, HttpServletResponse response) {
        return this.studyPlanService.getAllStudyPlans(page, size);
    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.GET,
            produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get a single studyPlan.", notes = "You have to provide a valid studyPlan ID.")
    public
    @ResponseBody
    StudyPlan getStudyPlan(@ApiParam(value = "The ID of the studyPlan.", required = true)
                 @PathVariable("id") Long id,
                 HttpServletRequest request, HttpServletResponse response) throws Exception {
        StudyPlan studyPlan = this.studyPlanService.getStudyPlan(id);
        checkResourceFound(studyPlan);
        //todo: http://goo.gl/6iNAkz
        return studyPlan;
    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.PUT,
            consumes = "application/json",
            produces = "application/json")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Update a studyPlan resource.", notes = "You have to provide a valid studyPlan ID in the URL and in the payload. The ID attribute can not be updated.")
    public void updateStudyPlan(@ApiParam(value = "The ID of the existing studyPlan resource.", required = true)
                           @PathVariable("id") Long id, @RequestBody StudyPlan studyPlan,
                           HttpServletRequest request, HttpServletResponse response) {
        checkResourceFound(this.studyPlanService.getStudyPlan(id));
        if (id != studyPlan.getId()) throw new DataFormatException("ID doesn't match!");
        this.studyPlanService.updateStudyPlan(studyPlan);
    }

    //todo: @ApiImplicitParams, @ApiResponses
    @RequestMapping(value = "/{id}",
            method = RequestMethod.DELETE,
            produces = "application/json")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Delete a studyPlan resource.", notes = "You have to provide a valid studyPlan ID in the URL. Once deleted the resource can not be recovered.")
    public void deleteStudyPlan(@ApiParam(value = "The ID of the existing studyPlan resource.", required = true)
                           @PathVariable("id") Long id, HttpServletRequest request,
                           HttpServletResponse response) {
        checkResourceFound(this.studyPlanService.getStudyPlan(id));
        this.studyPlanService.deleteStudyPlan(id);
    }

    // ----- SP's semester requests handler

    @RequestMapping(value = "/{id}/semesters",
            method = RequestMethod.GET,
            produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get SP's semesters.", notes = "You have to provide a valid SP ID.")
    public
    @ResponseBody
    Set<Semester> getSemesterBySP(@ApiParam(value = "The ID of the SP.", required = true)
                                      @PathVariable("id") Long id,
                                  HttpServletRequest request, HttpServletResponse response) throws Exception {
        StudyPlan studyPlan = this.studyPlanService.getStudyPlan(id);
        checkResourceFound(studyPlan);
        return studyPlan.getSemesters();
    }

    @RequestMapping(value = "/{id}/semesters",
            method = RequestMethod.POST,
            consumes = "application/json",
            produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Create a semester for a SP.", notes = "Returns the URL of the new resource in the Location header.")
    public Semester createSemesterForSP(@ApiParam(value = "The ID of the SP.", required = true)
                                            @PathVariable("id") Long id,
                                            @RequestBody Semester semester,
                                            HttpServletRequest request, HttpServletResponse response) throws Exception {
        StudyPlan attachedSP = this.studyPlanService.getStudyPlan(id);
        checkResourceFound(attachedSP);
        int semesterNumber = attachedSP.getSemesters().size() + 1;
        semester.setStudyPlan(attachedSP);
        Semester createdSemester = this.semesterService.createSemester(semester);
        return createdSemester;
    }
}
