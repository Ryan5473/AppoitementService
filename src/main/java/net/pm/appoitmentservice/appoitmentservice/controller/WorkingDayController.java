

package net.pm.appoitmentservice.appoitmentservice.controller;

import net.pm.appoitmentservice.appoitmentservice.dto.DoctorWorkingDayDTO;
import net.pm.appoitmentservice.appoitmentservice.service.WorkingDayService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/workingdays")
public class WorkingDayController {

    private final WorkingDayService workingDayService;

    public WorkingDayController(WorkingDayService workingDayService) {
        this.workingDayService = workingDayService;
    }

    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<DoctorWorkingDayDTO>> getWorkingDaysByDoctor(@PathVariable Long doctorId) {
        List<DoctorWorkingDayDTO> workingDays = workingDayService.getWorkingDaysByDoctor(doctorId);
        return ResponseEntity.ok(workingDays);
    }

    @PostMapping("/doctor/{doctorId}/generate7days")
    public ResponseEntity<Void> generate7WorkingDays(@PathVariable Long doctorId) {
        workingDayService.generate7WorkingDaysForDoctor(doctorId);
        return ResponseEntity.ok().build();
    }
}


























/*package net.pm.appoitmentservice.appoitmentservice.controller;

import net.pm.appoitmentservice.appoitmentservice.dto.DoctorWorkingDayDTO;
import net.pm.appoitmentservice.appoitmentservice.service.WorkingDayService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/workingdays")
public class WorkingDayController {

    private final WorkingDayService workingDayService;

    public WorkingDayController(WorkingDayService workingDayService) {
        this.workingDayService = workingDayService;
    }

    @GetMapping("/doctor/{doctorId}")
    public List<DoctorWorkingDayDTO> getWorkingDaysByDoctor(@PathVariable Long doctorId) {
        return workingDayService.getWorkingDaysByDoctor(doctorId);
    }

    @PostMapping("/doctor/{doctorId}/generate7days")
    public void generate7WorkingDays(@PathVariable Long doctorId) {
        workingDayService.generate7WorkingDaysForDoctor(doctorId);
    }
}
*/