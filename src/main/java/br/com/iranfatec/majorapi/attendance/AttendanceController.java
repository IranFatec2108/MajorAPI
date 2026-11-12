package br.com.iranfatec.majorapi.attendance;


import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/attendances")
public class AttendanceController {

    private final AttendanceManager attendanceManager;

    public AttendanceController(AttendanceManager attendanceManager) {
        this.attendanceManager = attendanceManager;
    }

    @PostMapping
    public ResponseEntity<AttendanceResponseDto> registerAttendance(@Valid @RequestBody AttendanceCreateRequestDto dto) {
        AttendanceResponseDto responseRegisterAttendance = attendanceManager.registerAttendance(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseRegisterAttendance);
    }

    @GetMapping
    public ResponseEntity<List<AttendanceResponseDto>> listAttendances(@RequestParam(required = false) Boolean valid) {
        List<AttendanceResponseDto> attendanceList;

        if (valid == null || valid) {
            attendanceList = attendanceManager.listAllValidAttendances();
        } else {
            attendanceList = attendanceManager.listAllInvalidAttendances();
        }

        return ResponseEntity.ok(attendanceList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AttendanceResponseDto> findAttendanceById(@PathVariable Long id) {
        AttendanceResponseDto responseAttendanceById = attendanceManager.findAttendanceById(id);
        return ResponseEntity.ok().body(responseAttendanceById);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<AttendanceResponseDto> updateAttendance(@PathVariable Long id, @Valid @RequestBody AttendanceUpdateRequestDto dto) {
        AttendanceResponseDto responseAttendanceUpdated = attendanceManager.updateAttendance(dto, id);
        return ResponseEntity.ok().body(responseAttendanceUpdated);
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<AttendanceResponseDto> deactivateAttendance(@PathVariable Long id) {
        AttendanceResponseDto responseAttendanceDeactivated = attendanceManager.deactivateAttendance(id);
        return ResponseEntity.ok().body(responseAttendanceDeactivated);
    }

    @PatchMapping("/{id}/reactivate")
    public ResponseEntity<AttendanceResponseDto> reactivateAttendance(@PathVariable Long id) {
        AttendanceResponseDto responseAttendanceReactivated = attendanceManager.reactivateAttendance(id);
        return ResponseEntity.ok().body(responseAttendanceReactivated);
    }

}
