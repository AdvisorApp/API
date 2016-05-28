package com.advisorapp.api.controller;

import com.advisorapp.api.model.Semester;
import com.advisorapp.api.exception.DataFormatException;
import com.advisorapp.api.model.Semester;
import com.advisorapp.api.model.Uv;
import com.advisorapp.api.service.SemesterService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;

@RestController
@RequestMapping(value = "/api/semesters")
@Api(value = "semesters", description = "Semester API")
public class SemesterController extends AbstractRestHandler {

    @Autowired
    private SemesterService semesterService;

    @RequestMapping(value = "",
            method = RequestMethod.GET,
            produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get a paginated list of all semesters.", notes = "The list is paginated. You can provide a page number (default 0) and a page size (default 100)")
    public
    @ResponseBody
    Page<Semester> getAllSemesters(@ApiParam(value = "The page number (zero-based)", required = true)
                           @RequestParam(value = "page", required = true, defaultValue = DEFAULT_PAGE_NUM) Integer page,
                           @ApiParam(value = "Tha page size", required = true)
                           @RequestParam(value = "size", required = true, defaultValue = DEFAULT_PAGE_SIZE) Integer size,
                           HttpServletRequest request, HttpServletResponse response) {
        return this.semesterService.getAllSemesters(page, size);
    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.GET,
            produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get a single semester.", notes = "You have to provide a valid semester ID.")
    public
    @ResponseBody
    Semester getSemester(@ApiParam(value = "The ID of the semester.", required = true)
                 @PathVariable("id") Long id,
                 HttpServletRequest request, HttpServletResponse response) throws Exception {
        Semester semester = this.semesterService.getSemester(id);
        checkResourceFound(semester);
        //todo: http://goo.gl/6iNAkz
        return semester;
    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.PUT,
            consumes = "application/json",
            produces = "application/json")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Update a semester resource.", notes = "You have to provide a valid semester ID in the URL and in the payload. The ID attribute can not be updated.")
    public void updateSemester(@ApiParam(value = "The ID of the existing semester resource.", required = true)
                           @PathVariable("id") Long id, @RequestBody Semester semester,
                           HttpServletRequest request, HttpServletResponse response) {
        checkResourceFound(this.semesterService.getSemester(id));
        if (id != semester.getId()) throw new DataFormatException("ID doesn't match!");
        this.semesterService.updateSemester(semester);
    }

    //todo: @ApiImplicitParams, @ApiResponses
    @RequestMapping(value = "/{id}",
            method = RequestMethod.DELETE,
            produces = "application/json")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Delete a semester resource.", notes = "You have to provide a valid semester ID in the URL. Once deleted the resource can not be recovered.")
    public void deleteSemester(@ApiParam(value = "The ID of the existing semester resource.", required = true)
                           @PathVariable("id") Long id, HttpServletRequest request,
                           HttpServletResponse response) {
        checkResourceFound(this.semesterService.getSemester(id));
        this.semesterService.deleteSemester(id);
    }

    // ----- Semester's UV requests handler

    @RequestMapping(value = "/{id}/uvs",
            method = RequestMethod.GET,
            produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get SP's semesters.", notes = "You have to provide a valid Semester ID.")
    public
    @ResponseBody
    Set<Uv> getSemesterBySP(@ApiParam(value = "The ID of the Semester.", required = true)
                                  @PathVariable("id") Long id,
                            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Semester semester = this.semesterService.getSemester(id);
        checkResourceFound(semester);
        return semester.getUvs();
    }

}
