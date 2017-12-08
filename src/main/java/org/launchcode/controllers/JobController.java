package org.launchcode.controllers;

import org.launchcode.models.*;
import org.launchcode.models.data.JobFieldData;
import org.launchcode.models.forms.JobForm;
import org.launchcode.models.data.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.text.MessageFormat;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping(value = "job")
public class JobController {

    private JobData jobData = JobData.getInstance();

    // The detail display for a given Job at URLs like /job?id=17
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model, int id) {

        // TODO #1 - get the Job with the given ID and pass it into the view

        Job job = jobData.findById(id);
        model.addAttribute("job", job);

        return "job-detail";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute(new JobForm());
        return "new-job";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(Model model, @Valid JobForm jobForm, Errors errors) {

        // TODO #6 - Validate the JobForm model, and if valid, create a
        // new Job and add it to the jobData data store. Then
        // redirect to the job detail view for the new Job.

        JobFieldData<Employer> employerJobFieldData = new JobFieldData<>();
        JobFieldData<Location> locationJobFieldData = new JobFieldData<>();
        JobFieldData<PositionType> positionTypeJobFieldData = new JobFieldData<>();
        JobFieldData<CoreCompetency> coreCompetencyJobFieldData = new JobFieldData<>();

        String name = jobForm.getName();
        Employer employer = employerJobFieldData.findById(jobForm.getEmployerId());
        Location location = locationJobFieldData.findById(jobForm.getLocationId());
        PositionType positionType = positionTypeJobFieldData.findById(jobForm.getPositionTypeId());
        CoreCompetency coreCompetency = coreCompetencyJobFieldData.findById(jobForm.getCoreCompetencyId());

        Job job = new Job(name, employer, location, positionType, coreCompetency);

        // TODO fix bug: this is adding the job, but only the name field works - why??
        JobData.getInstance().add(job);

        return MessageFormat.format("redirect:?id={0}", job.getId());

    }
}
